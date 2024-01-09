package org.example.tgbot.bot;

import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.SneakyThrows;
import org.example.tgbot.model.EmotionalInfo;
import org.example.tgbot.service.handler.MessageHandler;
import org.example.tgbot.service.redis.MessagePublisher;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.bots.TelegramLongPollingBot;
import org.telegram.telegrambots.meta.api.methods.GetFile;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.PhotoSize;
import org.telegram.telegrambots.meta.api.objects.Update;

import java.io.File;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

@Component
public class ExchangeRatesBot extends TelegramLongPollingBot {

    @Autowired
    @Qualifier("redisMessagePublisher")
    private MessagePublisher messagePublisher;

    private final ObjectMapper parser = new ObjectMapper();

    @Autowired
    private List<MessageHandler> actionsList;

    public ExchangeRatesBot() {
        super("6336242211:AAGbrJH1tlSoZKlsLZ7-TOKdyYeyStIwoUI");
    }

    @Override
    @SneakyThrows
    public void onUpdateReceived(Update update) {
        Optional<MessageHandler> action = actionsList.stream().filter(ac -> ac.isValid(update)).findFirst();

        if (action.isEmpty()) {
            sendMessage(String.valueOf(update.getMessage().getChatId()), "Я вас не понимаю, воспользуйтесь командой /help");
        } else {
            action.get().execution(update, this);
        }
    }

    @Override
    public String getBotUsername() {
        return "ОТВ_lab_bot";
    }

    @SneakyThrows
    public void sendMessage(String chatId, String text) {
        SendMessage sendMessage = new SendMessage(chatId, text);
        execute(sendMessage);
    }
}
