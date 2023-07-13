package com.example.command;
import com.example.myTelegramBot.MyTelegramBot;
import com.example.util.Button;
import com.example.util.ButtonName;
import com.example.util.SendMsg;
import org.springframework.stereotype.Service;
import org.telegram.telegrambots.meta.api.objects.Message;

@Service
public class OrderCommand {

    private final MyTelegramBot myTelegramBot;

    public OrderCommand(MyTelegramBot myTelegramBot) {
        this.myTelegramBot = myTelegramBot;
    }

    public void orderCommand(Message message) {

        myTelegramBot.send(SendMsg.sendMsg(message.getChatId(),
                "Order Command ishladi",
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
