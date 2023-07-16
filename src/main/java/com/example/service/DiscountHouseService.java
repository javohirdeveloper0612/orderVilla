package com.example.service;
import com.example.myTelegramBot.MyTelegramBot;
import com.example.util.Button;
import com.example.util.ButtonName;
import com.example.util.SendMsg;
import org.springframework.stereotype.Service;
import org.telegram.telegrambots.meta.api.objects.Message;

@Service
public class DiscountHouseService {

    private final MyTelegramBot myTelegramBot;

    public DiscountHouseService(MyTelegramBot myTelegramBot) {
        this.myTelegramBot = myTelegramBot;
    }

    public void discountHouse(Message message) {

        myTelegramBot.send(SendMsg.sendMsg(message.getChatId(),
                "Discount House ishladi Chegirmalar",
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
