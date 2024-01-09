package org.example.tgbot.service.handler;

import org.example.tgbot.bot.ExchangeRatesBot;
import org.example.tgbot.repository.EmotionStatisticRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.meta.api.objects.Update;

import java.util.List;
import java.util.stream.IntStream;

@Component
public class EmotionInfoMessageHandler implements MessageHandler {

    @Autowired
    private EmotionStatisticRepository emotionStatisticRepository;

    private final String[] emotions = {"нейтральность", "счастье", "злость", "страх", "грусть", "отвращение", "удивление"};

    @Override
    public boolean isValid(Update update) {
        if (update.getMessage().getText() != null) {
            String[] arr = update.getMessage().getText().split(" ");

            if (arr.length == 2 && arr[0].equals("/emotioninfo")) {
                return true;
            }
        }
        return false;
    }

    @Override
    public void execution(Update update, ExchangeRatesBot bot) {
        String emotion = update.getMessage().getText().split(" ")[1];


        boolean emotionContains = false;
        for (String em: emotions) {
            if (em.equals(emotion))
                emotionContains = true;
        }

        if (!emotionContains) {
            bot.sendMessage(String.valueOf(update.getMessage().getChatId()), "Эмоция некорректна");
            return;
        }

        if (emotionStatisticRepository.countColumns().equals(0L)) {
            bot.sendMessage(String.valueOf(update.getMessage().getChatId()), "Данных пока нет\uD83D\uDE2D Отправляйте фото и статистика появится!!!");
            return;
        }

        switch (emotion) {
            case "нейтральность": {
                List<Integer> emotionBd = emotionStatisticRepository.neutralEmotionAvg();
                Integer avrage = emotionBd.stream().reduce(Integer::sum).get() / emotionBd.size();
                bot.sendMessage(String.valueOf(update.getMessage().getChatId()), "Эмоция нейтральности проявлялась в среднем на " + avrage + "% y " + emotionBd.size() + (emotionBd.size() == 1 ? " человека" : " людей"));
                return;
            }
            case "счастье": {
                List<Integer> emotionBd = emotionStatisticRepository.happyEmotionAvg();
                Integer avrage = emotionBd.stream().reduce(Integer::sum).get() / emotionBd.size();
                bot.sendMessage(String.valueOf(update.getMessage().getChatId()), "Эмоция счастья проявлялась в среднем на " + avrage + "% y " + emotionBd.size() + (emotionBd.size() == 1 ? " человека" : " людей"));
                return;
            }
            case "злость": {
                List<Integer> emotionBd = emotionStatisticRepository.angryEmotionAvg();
                Integer avrage = emotionBd.stream().reduce(Integer::sum).get() / emotionBd.size();
                bot.sendMessage(String.valueOf(update.getMessage().getChatId()), "Эмоция злости проявлялась в среднем на " + avrage + "% y " + emotionBd.size() + (emotionBd.size() == 1 ? " человека" : " людей"));
                return;
            }
            case "страх": {
                List<Integer> emotionBd = emotionStatisticRepository.fearEmotionAvg();
                Integer avrage = emotionBd.stream().reduce(Integer::sum).get() / emotionBd.size();
                bot.sendMessage(String.valueOf(update.getMessage().getChatId()), "Эмоция страха проявлялась в среднем на " + avrage + "% y " + emotionBd.size() + (emotionBd.size() == 1 ? " человека" : " людей"));
                return;
            }
            case "грусть": {
                List<Integer> emotionBd = emotionStatisticRepository.sadEmotionAvg();
                Integer avrage = emotionBd.stream().reduce(Integer::sum).get() / emotionBd.size();
                bot.sendMessage(String.valueOf(update.getMessage().getChatId()), "Эмоция грусти проявлялась в среднем на " + avrage + "% y " + emotionBd.size() + (emotionBd.size() == 1 ? " человека" : " людей"));
                return;
            }
            case "отвращение": {
                List<Integer> emotionBd = emotionStatisticRepository.disgustEmotionAvg();
                Integer avrage = emotionBd.stream().reduce(Integer::sum).get() / emotionBd.size();
                bot.sendMessage(String.valueOf(update.getMessage().getChatId()), "Эмоция отвращения проявлялась в среднем на " + avrage + "% y " + emotionBd.size() + (emotionBd.size() == 1 ? " человека" : " людей"));
                return;
            }
            case "удивление": {
                List<Integer> emotionBd = emotionStatisticRepository.surpriseEmotionAvg();
                Integer avrage = emotionBd.stream().reduce(Integer::sum).get() / emotionBd.size();
                bot.sendMessage(String.valueOf(update.getMessage().getChatId()), "Эмоция удивления проявлялась в среднем на " + avrage + "% y " + emotionBd.size() + (emotionBd.size() == 1 ? " человека" : " людей"));
                return;
            }
        }
    }
}
