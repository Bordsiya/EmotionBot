package org.example.tgbot.service.handler;

import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.SneakyThrows;
import org.example.tgbot.bot.ExchangeRatesBot;
import org.example.tgbot.model.EmotionalInfo;
import org.example.tgbot.service.redis.MessagePublisher;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.meta.api.methods.GetFile;
import org.telegram.telegrambots.meta.api.objects.PhotoSize;
import org.telegram.telegrambots.meta.api.objects.Update;

import java.io.File;
import java.nio.file.Files;

@Component
public class PhotoMessageHandler implements MessageHandler {

    @Autowired
    @Qualifier("redisMessagePublisher")
    private MessagePublisher messagePublisher;

    private final ObjectMapper parser = new ObjectMapper();

    @Override
    public boolean isValid(Update update) {
        if (update.getMessage().hasPhoto()) {
            return true;
        }
        return false;
    }

    @Override
    @SneakyThrows
    public void execution(Update update, ExchangeRatesBot bot) {
        PhotoSize photoSize = update.getMessage().getPhoto().get(0);

        GetFile getFileMethod = new GetFile();
        getFileMethod.setFileId(photoSize.getFileId());
        File f = bot.downloadFile(bot.execute(getFileMethod).getFilePath());

        byte[] fileContent = Files.readAllBytes(f.toPath());
        messagePublisher.publish(parser.writeValueAsString(
                EmotionalInfo.builder()
                        .chatId(String.valueOf(update.getMessage().getChatId()))
                        .photoBytes(fileContent)
                        .build()
        ));
        bot.sendMessage(String.valueOf(update.getMessage().getChatId()), "Обработка фотографии");
    }
}
