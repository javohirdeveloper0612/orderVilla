package com.example.admin.service;
import com.example.myTelegramBot.MyTelegramBot;
import com.example.util.SendMsg;
import org.springframework.stereotype.Service;
import org.telegram.telegrambots.meta.api.objects.Message;

@Service
public class UpdateOrderAdminService {

    private final MyTelegramBot myTelegramBot;

    public UpdateOrderAdminService(MyTelegramBot myTelegramBot) {
        this.myTelegramBot = myTelegramBot;
    }

    public void updateOrder(Message message) {

        myTelegramBot.send(SendMsg.sendMsg(message.getChatId(),
                "Update Order Bo'lim ishladi"));

    }
}
