package com.example.util;

import org.telegram.telegrambots.meta.api.objects.replykeyboard.ReplyKeyboardRemove;

public class KeyboardRemove {
    public static ReplyKeyboardRemove keyboardRemove(){
        ReplyKeyboardRemove replyKeyboardRemove = new ReplyKeyboardRemove();
        replyKeyboardRemove.setRemoveKeyboard(true);
        return replyKeyboardRemove;
    }
}
