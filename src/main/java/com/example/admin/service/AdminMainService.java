package com.example.admin.service;
import com.example.admin.util.AdminButtonName;
import com.example.myTelegramBot.MyTelegramBot;
import com.example.util.Button;
import com.example.util.SendMsg;
import org.springframework.stereotype.Service;
import org.telegram.telegrambots.meta.api.objects.Message;

@Service
public class AdminMainService {

    private final MyTelegramBot myTelegramBot;

    public AdminMainService(MyTelegramBot myTelegramBot) {
        this.myTelegramBot = myTelegramBot;
    }

    public void adminMainMenu(Message message) {

        myTelegramBot.send(SendMsg.sendMsg(message.getChatId(),
                "⬇️\n" +
                        "\n" +
                        "*-Выберите нужный вам раздел...*",
                Button.markup(

                        Button.rowList(

                                Button.row(

                                        Button.button(AdminButtonName.activeOrder),
                                        Button.button(AdminButtonName.noActiveOrder)

                        )
                ))));


    }
}
