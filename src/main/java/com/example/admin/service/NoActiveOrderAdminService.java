package com.example.admin.service;
import com.example.myTelegramBot.MyTelegramBot;
import com.example.util.SendMsg;
import org.springframework.stereotype.Service;
import org.telegram.telegrambots.meta.api.objects.Message;

@Service
public class NoActiveOrderAdminService {

    private final MyTelegramBot myTelegramBot;

    public NoActiveOrderAdminService(MyTelegramBot myTelegramBot) {
        this.myTelegramBot = myTelegramBot;
    }

    public void noActiveOrder(Message message) {

        myTelegramBot.send(SendMsg.sendMsg(message.getChatId(),
                "No Active Order Bo'limi ishladi !!!"));
    }
}
