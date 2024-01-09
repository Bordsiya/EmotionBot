package org.example.tgbot.service.redis;

import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.SneakyThrows;
import org.example.tgbot.bot.ExchangeRatesBot;
import org.example.tgbot.model.EmotionStatisticEntity;
import org.example.tgbot.model.EmotionalResponse;
import org.example.tgbot.repository.EmotionStatisticRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.connection.Message;
import org.springframework.data.redis.connection.MessageListener;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Service
public class RedisMessageSubscriber implements MessageListener {

    private ExchangeRatesBot exchangeRatesBot;

    private EmotionStatisticRepository emotionStatisticRepository;

    public RedisMessageSubscriber(ExchangeRatesBot exchangeRatesBot, EmotionStatisticRepository emotionStatisticRepository) {
        this.exchangeRatesBot = exchangeRatesBot;
        this.emotionStatisticRepository = emotionStatisticRepository;
    }

    private final ObjectMapper parser = new ObjectMapper();
    public static List<String> messageList = new ArrayList<String>();

    @SneakyThrows
    public void onMessage(final Message message, final byte[] pattern) {
        messageList.add(message.toString());
        EmotionalResponse res = parser.readValue(message.getBody(), EmotionalResponse.class);

        if (res.getResult().getNeutral().equals(-1.0)) {
            exchangeRatesBot.sendMessage(String.valueOf(res.getChatId()), "Некорректное фото");
        } else {

            Integer neutral = (int) (res.getResult().getNeutral() * 100);
            Integer happy = (int) (res.getResult().getHappy() * 100);
            Integer angry = (int) (res.getResult().getAngry() * 100);
            Integer fear = (int) (res.getResult().getFear() * 100);
            Integer sad = (int) (res.getResult().getSad() * 100);
            Integer disgust = (int) (res.getResult().getDisgust() * 100);
            Integer surprise = (int) (res.getResult().getSurprise() * 100);

            EmotionStatisticEntity emotionStatistic = new EmotionStatisticEntity();
            emotionStatistic.setChatId(String.valueOf(res.getChatId()));
            emotionStatistic.setNeutral(neutral);
            emotionStatistic.setHappy(happy);
            emotionStatistic.setAngry(angry);
            emotionStatistic.setFear(fear);
            emotionStatistic.setSad(sad);
            emotionStatistic.setDisgust(disgust);
            emotionStatistic.setSurprise(surprise);
            emotionStatistic.setDateCreation(LocalDate.now());

            emotionStatisticRepository.save(emotionStatistic);

            String response = new StringBuilder().append("Нейтральность: ").append(neutral).append(" %\n")
                        .append("Счастье: ").append(happy).append(" %\n")
                        .append("Злость: ").append(angry).append(" %\n")
                        .append("Страх: ").append(fear).append(" %\n")
                        .append("Грусть: ").append(sad).append(" %\n")
                        .append("Отвращение: ").append(disgust).append(" %\n")
                        .append("Удивление: ").append(surprise).append(" %\n")
                    .toString();

            exchangeRatesBot.sendMessage(String.valueOf(res.getChatId()), response);
        }
    }
}