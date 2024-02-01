package com.shawtypimp.kittenbot.configuration;

import com.shawtypimp.kittenbot.bot.KittenBot;
import okhttp3.OkHttpClient;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.telegram.telegrambots.meta.TelegramBotsApi;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;
import org.telegram.telegrambots.updatesreceivers.DefaultBotSession;



@Configuration
public class KittenBotConfiguration {

    @Bean
    public TelegramBotsApi telegramBotsApi(KittenBot kittenBot) throws TelegramApiException {
        var api = new TelegramBotsApi(DefaultBotSession.class);
        api.registerBot(kittenBot);
        return api;
    }

    @Bean
    public OkHttpClient okHttpClient() {
        return new OkHttpClient();
    }





}
