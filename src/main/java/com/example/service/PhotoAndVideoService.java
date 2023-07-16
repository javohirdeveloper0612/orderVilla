package com.example.service;
import com.example.myTelegramBot.MyTelegramBot;
import com.example.util.Button;
import com.example.util.ButtonName;
import com.example.util.SendMsg;
import org.springframework.stereotype.Service;
import org.telegram.telegrambots.meta.api.objects.Message;

@Service
public class PhotoAndVideoService {

    private final MyTelegramBot myTelegramBot;

    public PhotoAndVideoService(MyTelegramBot myTelegramBot) {
        this.myTelegramBot = myTelegramBot;
    }

    public void photoAndVideoHouse(Message message) {

        myTelegramBot.send(SendMsg.sendMsg(message.getChatId(),
                "Photo va Video bo'limi ishladi",
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