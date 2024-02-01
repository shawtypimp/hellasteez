package com.shawtypimp.kittenbot.keyboards;


import org.telegram.telegrambots.meta.api.objects.replykeyboard.ReplyKeyboardMarkup;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.buttons.KeyboardRow;


import java.util.Arrays;


public class ReplyKeyboard {

    public ReplyKeyboardMarkup createReplyKeyboard() {
        var row1 = new KeyboardRow();
        row1.add("/help");

        var row2 = new KeyboardRow();
        row2.add("/categories");

        ReplyKeyboardMarkup replyKeyboardMarkup = new ReplyKeyboardMarkup();
        replyKeyboardMarkup.setKeyboard(Arrays.asList(row1, row2));
        replyKeyboardMarkup.setResizeKeyboard(true);

        return replyKeyboardMarkup;
    }
}
