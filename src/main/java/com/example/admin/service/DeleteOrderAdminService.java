package com.example.admin.service;
import com.example.myTelegramBot.MyTelegramBot;
import com.example.util.SendMsg;
import org.springframework.stereotype.Service;
import org.telegram.telegrambots.meta.api.objects.Message;

@Service
public class DeleteOrderAdminService {

    private final MyTelegramBot myTelegramBot;

    public DeleteOrderAdminService(MyTelegramBot myTelegramBot) {
        this.myTelegramBot = myTelegramBot;
    }

    public void deleteOrder(Message message) {

        myTelegramBot.send(SendMsg.sendMsg(message.getChatId(),
                "Delete Order Bo'lim ishladi"));
    }
}
