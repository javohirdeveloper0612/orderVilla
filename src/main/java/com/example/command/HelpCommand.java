package com.example.command;


import com.example.myTelegramBot.MyTelegramBot;
import com.example.util.Button;
import com.example.util.ButtonName;
import com.example.util.SendMsg;
import org.springframework.stereotype.Service;
import org.telegram.telegrambots.meta.api.objects.Message;

import java.awt.*;

@Service
public class HelpCommand {

    private final MyTelegramBot myTelegramBot;

    public HelpCommand(MyTelegramBot myTelegramBot) {
        this.myTelegramBot = myTelegramBot;
    }

    public void helpComand(Message message) {

        myTelegramBot.send(SendMsg.sendMsg(message.getChatId(),
                "Help Command Ishladi",
                Button.markup(
                        Button.rowList(
                                Button.row(
                                        Button.button(
                                                ButtonName.backMainMenu
                                        )
                                )
                        )
                )));
    }
}
