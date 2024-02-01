package com.shawtypimp.kittenbot.keyboards;

import com.vdurmont.emoji.EmojiParser;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.InlineKeyboardMarkup;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.buttons.InlineKeyboardButton;

import java.util.ArrayList;
import java.util.List;

public class InlineKeyboardCategories {

    private static final String boxEmoji = EmojiParser.parseToUnicode(":package:");
    private static final String clothesEmoji = EmojiParser.parseToUnicode(":tshirt:");
    private static final String hatEmoji = EmojiParser.parseToUnicode(":billed_cap:");
    private static final String raisedEyebrowEmoji = EmojiParser.parseToUnicode(":face_with_raised_eyebrow:");
    private static final String explodingHeadEmoji = EmojiParser.parseToUnicode(":exploding_head:");
    private static final String sunglassesEmoji = EmojiParser.parseToUnicode(":dark_sunglasses:");
    private static final String necktieEmoji = EmojiParser.parseToUnicode(":necktie:");
    public static SendMessage inlineKeyboardCategories(long chatId) {

        var message = new SendMessage();
        message.setChatId(chatId);
        message.setText("Выберите категорию: ");

        var inlineKeyboardMarkup = new InlineKeyboardMarkup();
        List<List<InlineKeyboardButton>> rowsInline = new ArrayList<>();

        List<InlineKeyboardButton> rowsInline1 = new ArrayList<>();
        InlineKeyboardButton inlineKeyboardButton1 = new InlineKeyboardButton();
        inlineKeyboardButton1.setText("в коробке" + boxEmoji);
        inlineKeyboardButton1.setCallbackData("5");
        rowsInline1.add(inlineKeyboardButton1);

        List<InlineKeyboardButton> rowsInline2 = new ArrayList<>();
        InlineKeyboardButton inlineKeyboardButton2 = new InlineKeyboardButton();
        inlineKeyboardButton2.setText("в одежде" + clothesEmoji);
        inlineKeyboardButton2.setCallbackData("15");
        rowsInline2.add(inlineKeyboardButton2);

        List<InlineKeyboardButton> rowsInline3 = new ArrayList<>();
        InlineKeyboardButton inlineKeyboardButton3 = new InlineKeyboardButton();
        inlineKeyboardButton3.setText("с головным убором" + hatEmoji);
        inlineKeyboardButton3.setCallbackData("1");
        rowsInline3.add(inlineKeyboardButton3);

        List<InlineKeyboardButton> rowsInline4 = new ArrayList<>();
        InlineKeyboardButton inlineKeyboardButton4 = new InlineKeyboardButton();
        inlineKeyboardButton4.setText("в раковине" + raisedEyebrowEmoji);
        inlineKeyboardButton4.setCallbackData("14");
        rowsInline4.add(inlineKeyboardButton4);

        List<InlineKeyboardButton> rowsInline5 = new ArrayList<>();
        InlineKeyboardButton inlineKeyboardButton5 = new InlineKeyboardButton();
        inlineKeyboardButton5.setText("в космосе" + explodingHeadEmoji);
        inlineKeyboardButton5.setCallbackData("2");
        rowsInline5.add(inlineKeyboardButton5);

        List<InlineKeyboardButton> rowsInline6 = new ArrayList<>();
        InlineKeyboardButton inlineKeyboardButton6 = new InlineKeyboardButton();
        inlineKeyboardButton6.setText("в очках" + sunglassesEmoji);
        inlineKeyboardButton6.setCallbackData("4");
        rowsInline6.add(inlineKeyboardButton6);

        List<InlineKeyboardButton> rowsInline7 = new ArrayList<>();
        InlineKeyboardButton inlineKeyboardButton7 = new InlineKeyboardButton();
        inlineKeyboardButton7.setText("с галстуком" + necktieEmoji);
        inlineKeyboardButton7.setCallbackData("7");
        rowsInline7.add(inlineKeyboardButton7);

        rowsInline.add(rowsInline1);
        rowsInline.add(rowsInline2);
        rowsInline.add(rowsInline3);
        rowsInline.add(rowsInline4);
        rowsInline.add(rowsInline5);
        rowsInline.add(rowsInline6);
        rowsInline.add(rowsInline7);

        inlineKeyboardMarkup.setKeyboard(rowsInline);
        message.setReplyMarkup(inlineKeyboardMarkup);

        return message;
    }
}
