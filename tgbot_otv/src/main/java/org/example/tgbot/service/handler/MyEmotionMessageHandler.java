package org.example.tgbot.service.handler;

import org.example.tgbot.bot.ExchangeRatesBot;
import org.example.tgbot.repository.EmotionStatisticRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.meta.api.objects.Update;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

@Component
public class MyEmotionMessageHandler implements MessageHandler {

    @Autowired
    private EmotionStatisticRepository emotionStatisticRepository;

    private static final String PATTERN_DATE_TIME = "dd.MM.yyyy";

    @Override
    public boolean isValid(Update update) {
        if (update.getMessage().getText() != null) {
            String[] arr = update.getMessage().getText().split(" ");

            if (arr.length == 4 && arr[0].equals("/myemotion")) {
                return true;
            }
        }
        return false;
    }

    @Override
    public void execution(Update update, ExchangeRatesBot bot) {
        Long chatId = update.getMessage().getChatId();

        String dateStartStr = update.getMessage().getText().split(" ")[1];
        String dateEndStr = update.getMessage().getText().split(" ")[3];

        LocalDate dateStart;
        LocalDate dateEnd;

        try {
            dateStart = LocalDate.parse(dateStartStr, DateTimeFormatter.ofPattern(PATTERN_DATE_TIME));
            dateEnd = LocalDate.parse(dateEndStr, DateTimeFormatter.ofPattern(PATTERN_DATE_TIME));
        } catch (Exception e) {
            bot.sendMessage(String.valueOf(update.getMessage().getChatId()), "Ошибка парсинга дат, попробуйте ещё раз\nНапоминаем формат: dd.mm.yyyy - dd.mm.yyyy");
            return;
        }

        String chatIdStr = String.valueOf(chatId);

        if (emotionStatisticRepository.countMyColumns(chatIdStr).equals(0L)) {
            bot.sendMessage(String.valueOf(update.getMessage().getChatId()), "У вас пока нет данных\uD83D\uDE2D Отправляйте фото и статистика появится!!!");
            return;
        }

        if (emotionStatisticRepository.countMyColumnsByDate(chatIdStr, dateStart, dateEnd).equals(0L)) {
            bot.sendMessage(String.valueOf(update.getMessage().getChatId()), "У вас пока нет данных за данный период времени, попробуйте другой временной интервал");
            return;
        }

        String response = new StringBuilder().append("Ваши данные за период ").append(dateStartStr).append(" - ").append(dateEndStr).append(":\n\n")
                    .append("Нейтральность в среднем: ").append(emotionStatisticRepository.myNeutralEmotionAvgByDate(chatIdStr, dateStart, dateEnd)).append("%\n")
                    .append("Счастье в среднем: ").append(emotionStatisticRepository.myHappyEmotionAvgByDate(chatIdStr, dateStart, dateEnd)).append("%\n")
                    .append("Злость в среднем: ").append(emotionStatisticRepository.myAngryEmotionAvgByDate(chatIdStr, dateStart, dateEnd)).append("%\n")
                    .append("Страх в среднем: ").append(emotionStatisticRepository.myFearEmotionAvgByDate(chatIdStr, dateStart, dateEnd)).append("%\n")
                    .append("Грусть в среднем: ").append(emotionStatisticRepository.mySadEmotionAvgByDate(chatIdStr, dateStart, dateEnd)).append("%\n")
                    .append("Отвращение в среднем: ").append(emotionStatisticRepository.myDisgustEmotionAvgByDate(chatIdStr, dateStart, dateEnd)).append("%\n")
                    .append("Удивление в среднем: ").append(emotionStatisticRepository.mySurpriseEmotionAvgByDate(chatIdStr, dateStart, dateEnd)).append("%\n")
                .toString();

        bot.sendMessage(chatIdStr, response);
    }
}
