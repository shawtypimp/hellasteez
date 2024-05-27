package com.shawtypimp.kittenbot.bot;

import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.methods.send.SendPhoto;
import org.telegram.telegrambots.meta.api.objects.InputFile;

import java.io.IOException;
import java.net.URL;


public class Sender {

    public static SendPhoto sendPhoto(Long chatId, String imageUrl, String caption) throws IOException {
        URL url = new URL(imageUrl);
        var photo = new InputFile(String.valueOf(url));
        var sendPhoto = new SendPhoto();
        sendPhoto.setChatId(chatId.toString());
        sendPhoto.setPhoto(photo);
        sendPhoto.setCaption(caption);
        return sendPhoto;
    }
}
