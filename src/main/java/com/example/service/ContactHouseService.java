package com.example.service;
import com.example.myTelegramBot.MyTelegramBot;
import com.example.util.Button;
import com.example.util.ButtonName;
import com.example.util.SendMsg;
import org.springframework.stereotype.Service;
import org.telegram.telegrambots.meta.api.objects.Message;

@Service
public class ContactHouseService {


    private final MyTelegramBot myTelegramBot;

    public ContactHouseService(MyTelegramBot myTelegramBot) {
        this.myTelegramBot = myTelegramBot;
    }

    public void contactHouse(Message message) {

        myTelegramBot.send(SendMsg.sendMsg(message.getChatId(),
                "Konatkt bo'limi ishladi akalar",
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
