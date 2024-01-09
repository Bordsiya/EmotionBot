package org.example.tgbot.service.handler;

import org.example.tgbot.bot.ExchangeRatesBot;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.meta.api.objects.Update;

@Component
public class StartMessageHandler implements MessageHandler {
    @Override
    public boolean isValid(Update update) {
        if ("/start".equals(update.getMessage().getText())) {
            return true;
        }
        return false;
    }

    @Override
    public void execution(Update update, ExchangeRatesBot bot) {
        String response = new StringBuilder().append("Вас приветствует помошник EmotionBot, вот всё, что вы можете делать:\n")
                    .append("• отправить фото и получить по нему эмоциональную статистику\n")
                    .append("• /emotioninfo <название эмоции> - получить статистику о конкретной эмоции по всем пользователям\n")
                    .append("• /myemotion dd.mm.yyyy - dd.mm.yyyy - получить статистику о ваших эмоциях в определённый период\n\n")
                    .append("Названия эмоций: нейтральность, счастье, злость, страх, грусть, отвращение, удивление")
                .toString();

        bot.sendMessage(String.valueOf(update.getMessage().getChatId()), response);
    }
}
