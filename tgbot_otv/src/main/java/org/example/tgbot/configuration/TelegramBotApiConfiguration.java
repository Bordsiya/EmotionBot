package org.example.tgbot.configuration;

import org.example.tgbot.bot.ExchangeRatesBot;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.telegram.telegrambots.meta.TelegramBotsApi;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;
import org.telegram.telegrambots.updatesreceivers.DefaultBotSession;

@Configuration
public class TelegramBotApiConfiguration {

    @Bean
    public TelegramBotsApi telegramBotsApi(ExchangeRatesBot exchangeRatesBot) throws TelegramApiException {
        TelegramBotsApi tg = new TelegramBotsApi(DefaultBotSession.class);
        tg.registerBot(exchangeRatesBot);

        return tg;
    }
}
