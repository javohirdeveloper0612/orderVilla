package com.example.service;
import com.example.myTelegramBot.MyTelegramBot;
import com.example.util.Button;
import com.example.util.ButtonName;
import com.example.util.SendMsg;
import org.springframework.stereotype.Service;
import org.telegram.telegrambots.meta.api.objects.Message;

@Service
public class RulesHouseService {

    private final MyTelegramBot myTelegramBot;

    public RulesHouseService(MyTelegramBot myTelegramBot) {
        this.myTelegramBot = myTelegramBot;
    }

    public void rulesHouse(Message message) {

        myTelegramBot.send(SendMsg.sendMsg(message.getChatId(),
                "Rules Kirish chiqish qonun qoidalar bo'lim ishladi",
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
