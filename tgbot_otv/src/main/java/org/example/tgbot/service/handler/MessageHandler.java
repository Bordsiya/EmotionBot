package org.example.tgbot.service.handler;

import org.example.tgbot.bot.ExchangeRatesBot;
import org.telegram.telegrambots.meta.api.objects.Update;

public interface MessageHandler {
    boolean isValid(Update update);
    void execution(Update update, ExchangeRatesBot bot);
}
