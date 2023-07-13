package com.example.Service;
import com.example.myTelegramBot.MyTelegramBot;
import com.example.util.Button;
import com.example.util.ButtonName;
import com.example.util.SendMsg;
import org.springframework.stereotype.Service;
import org.telegram.telegrambots.meta.api.objects.Message;

@Service
public class MainService {

    private final MyTelegramBot myTelegramBot;

    public MainService(MyTelegramBot myTelegramBot) {
        this.myTelegramBot = myTelegramBot;
    }

    public void mainMenu(Message message) {

        myTelegramBot.send(SendMsg.sendMsg(message.getChatId(),
                "⬇️\n" +
                        "\n" +
                        "*- Выберите нужный вам раздел*",
                Button.markup(

                        Button.rowList(

                                Button.row(
                                        Button.button(ButtonName.orderHouse)

                                ),

                                Button.row(
                                        Button.button(ButtonName.discountsHouse),
                                        Button.button(ButtonName.photoAndVideoHouse)

                                ),

                                Button.row(
                                        Button.button(ButtonName.rulesHouse),
                                        Button.button(ButtonName.botinstruction)
                                ),

                                Button.row(
                                        Button.button(ButtonName.contactHouse),
                                        Button.button(ButtonName.locationHouse)
                                )
                        )

                )));

    }
}
