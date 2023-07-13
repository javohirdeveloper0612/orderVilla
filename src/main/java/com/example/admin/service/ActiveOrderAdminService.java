package com.example.admin.service;
import com.example.myTelegramBot.MyTelegramBot;
import com.example.util.SendMsg;
import org.springframework.stereotype.Service;
import org.telegram.telegrambots.meta.api.objects.Message;

@Service
public class ActiveOrderAdminService {

    private final MyTelegramBot myTelegramBot;

    public ActiveOrderAdminService(MyTelegramBot myTelegramBot) {
        this.myTelegramBot = myTelegramBot;
    }

    public void activeOrder(Message message) {

        myTelegramBot.send(SendMsg.sendMsg(message.getChatId(),
                "Active Order Bo'limi ishladi"));

    }
}
