package com.shawtypimp.kittenbot.bot;

import com.shawtypimp.kittenbot.client.KittenBotClient;
import com.shawtypimp.kittenbot.exception.ServiceException;
import com.shawtypimp.kittenbot.keyboards.InlineKeyboardCategories;
import com.shawtypimp.kittenbot.keyboards.InlineKeyboardEvaluation;
import com.shawtypimp.kittenbot.keyboards.ReplyKeyboard;
import com.vdurmont.emoji.EmojiParser;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.bots.TelegramLongPollingBot;
import org.telegram.telegrambots.meta.api.methods.AnswerCallbackQuery;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.methods.updatingmessages.DeleteMessage;
import org.telegram.telegrambots.meta.api.objects.CallbackQuery;
import org.telegram.telegrambots.meta.api.objects.Update;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;

import java.io.IOException;
import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneId;

@Component
public class KittenBot extends TelegramLongPollingBot {

    private long chatId;
    private String messageText;

    private LocalDateTime lastBotStartupTime;

    private static final Logger LOG = LoggerFactory.getLogger(KittenBot.class);

    private static final String START = "/start";
    private static final String HELP = "/help";
    private static final String CATEGORIES = "/categories";

    @Autowired
    private KittenBotClient kittenBotClient;


    public KittenBot(@Value("${bot.token}") String botToken) {
        super(botToken);
    }

    @Override
    public void onUpdateReceived(Update update) {

        var message = update.getMessage();
        var sendMessage = new SendMessage();

        if (update.hasMessage() && update.getMessage().hasText()) {

            //Берём дату сообщения и переводим её из формата Unix time в местное время и дату в Java
            long messageUnixTime = message.getDate();
            var instant = Instant.ofEpochSecond(messageUnixTime);
            var messageTime = LocalDateTime.ofInstant(instant, ZoneId.systemDefault());
            lastBotStartupTime = LocalDateTime.now();

            // Проверяем, если сообщение было отправлено до последнего запуска бота
            if (messageTime.isBefore(lastBotStartupTime)) {
                return; // Пропускаем обновление
            }

            chatId = update.getMessage().getChatId();
            messageText = update.getMessage().getText();

            sendMessage.setChatId(update.getMessage().getChatId().toString());

            switch (messageText) {
                case START:
                    sendMessage.setText("Привет " + message.getFrom().getFirstName() + "! Я отправляю котиков по твоему " +
                            "запросу, выбери категорию и наслаждайся.");
                    sendMessage.setReplyMarkup(new ReplyKeyboard().createReplyKeyboard());
                    try {
                        execute(sendMessage);
                    } catch (TelegramApiException e) {
                        throw new RuntimeException(e);
                    }
                    break;
                case HELP:
                    sendMessage.setText("/help - список команд\n" +
                            "/start - начать работу с ботом\n" +
                            "/categories - категории фото");
                    try {
                        execute(sendMessage);
                    } catch (TelegramApiException e) {
                        throw new RuntimeException(e);
                    }
                    break;
                case CATEGORIES:
                    try {
                        execute(InlineKeyboardCategories.inlineKeyboardCategories(chatId));
                    } catch (TelegramApiException e) {
                        throw new RuntimeException(e);
                    }
                    break;
                default:
                    sendMessage.setText("Неизвестная команда, вы можете ознакомиться со списком команд введя /help");
                    try {
                        execute(sendMessage);
                    } catch (TelegramApiException e) {
                        throw new RuntimeException(e);
                    }
                    break;
            }

        } else if (update.hasCallbackQuery()) {
            CallbackQuery callbackQuery = update.getCallbackQuery();
            var callbackData = callbackQuery.getData();
            int messageId = callbackQuery.getMessage().getMessageId();

            switch (callbackData) {
                case "like": {
                    var happyEmoji = EmojiParser.parseToUnicode(":blush:");
                    var answerCallbackQuery = new AnswerCallbackQuery();
                    answerCallbackQuery.setCallbackQueryId(update.getCallbackQuery().getId());
                    answerCallbackQuery.setText("Тебе понравилось!" + happyEmoji);
                    try {
                        execute(answerCallbackQuery);
                    } catch (TelegramApiException e) {
                        throw new RuntimeException(e);
                    }

                    break;
                }
                case "dislike": {
                    var sadEmoji = EmojiParser.parseToUnicode(":pensive:");
                    var answerCallbackQuery = new AnswerCallbackQuery();
                    answerCallbackQuery.setCallbackQueryId(update.getCallbackQuery().getId());
                    answerCallbackQuery.setText("Тебе не понравилось" + sadEmoji);
                    try {
                        execute(answerCallbackQuery);
                    } catch (TelegramApiException e) {
                        throw new RuntimeException(e);
                    }
                    break;
                }
                case "categories":
                    try {
                        execute(InlineKeyboardCategories.inlineKeyboardCategories(chatId));
                    } catch (TelegramApiException e) {
                        throw new RuntimeException(e);
                    }

                    break;
                default:
                    try {
                        //Удаляю клавиатуру после того как пользователь выбрал категорию
                        var deleteMessage = new DeleteMessage();
                        deleteMessage.setChatId(chatId);
                        deleteMessage.setMessageId(messageId);
                        execute(deleteMessage);

                        var imageUrl = kittenBotClient.makeRequest(callbackData);
                        execute(Sender.sendPhoto(chatId, imageUrl, " "));
                        execute(InlineKeyboardEvaluation.inlineKeyboardEvaluation(chatId));
                    } catch (ServiceException | TelegramApiException | IOException e) {
                        LOG.error("Error during processing callback query: " + e.getMessage());

                        e.printStackTrace();
                        throw new RuntimeException(e);
                    }
                    break;
            }
        }
    }

    @Override
    public String getBotUsername() {
        return "KittinPhotoBot";
    }
}
