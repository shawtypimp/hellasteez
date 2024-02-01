package com.shawtypimp.kittenbot.keyboards;

import com.vdurmont.emoji.EmojiParser;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.InlineKeyboardMarkup;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.buttons.InlineKeyboardButton;

import java.util.ArrayList;
import java.util.List;

public class InlineKeyboardEvaluation {

    private static final String likeEmoji = EmojiParser.parseToUnicode(":heart:");
    private static final String dislikeEmoji = EmojiParser.parseToUnicode(":thumbsdown:");
    public static SendMessage inlineKeyboardEvaluation(long chatId) {

        var message = new SendMessage();
        message.setChatId(chatId);
        message.setText("Выберите действие:");

        var inlineKeyboardMarkup = new InlineKeyboardMarkup();
        List<List<InlineKeyboardButton>> rowsInline = new ArrayList<>();

        List<InlineKeyboardButton> rowsInline1 = new ArrayList<>();
        InlineKeyboardButton inlineKeyboardButton1 = new InlineKeyboardButton();
        inlineKeyboardButton1.setText(likeEmoji);
        inlineKeyboardButton1.setCallbackData("like");
        rowsInline1.add(inlineKeyboardButton1);

        InlineKeyboardButton inlineKeyboardButton2 = new InlineKeyboardButton();
        inlineKeyboardButton2.setText(dislikeEmoji);
        inlineKeyboardButton2.setCallbackData("dislike");
        rowsInline1.add(inlineKeyboardButton2);

        List<InlineKeyboardButton> rowsInline2 = new ArrayList<>();
        InlineKeyboardButton inlineKeyboardButton3 = new InlineKeyboardButton();
        inlineKeyboardButton3.setText("Выбрать категорию");
        inlineKeyboardButton3.setCallbackData("categories");
        rowsInline2.add(inlineKeyboardButton3);

        rowsInline.add(rowsInline1);
        rowsInline.add(rowsInline2);

        inlineKeyboardMarkup.setKeyboard(rowsInline);
        message.setReplyMarkup(inlineKeyboardMarkup);

        return message;
    }
}
