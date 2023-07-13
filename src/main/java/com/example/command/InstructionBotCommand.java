package com.example.command;
import com.example.myTelegramBot.MyTelegramBot;
import com.example.util.Button;
import com.example.util.ButtonName;
import com.example.util.SendMsg;
import org.springframework.stereotype.Service;
import org.telegram.telegrambots.meta.api.objects.Message;

@Service
public class InstructionBotCommand {

    private final MyTelegramBot myTelegramBot;

    public InstructionBotCommand(MyTelegramBot myTelegramBot) {
        this.myTelegramBot = myTelegramBot;
    }


    public void instructionBotCommand(Message message) {

        myTelegramBot.send(SendMsg.sendMsg(message.getChatId(),
                "⬇️\n" +
                        "\n" +
                        "*- Инструкция бота...*\n" +
                        "\n" +
                        "\uD83D\uDD35  Заказать дом на 6 человек со всеми " +
                        "условиями можно через бота, в процессе оформления " +
                        "заказа вы можете выбрать удобный для вас день и " +
                        "совершить денежный перевод в удобном для вас виде оплаты.\n" +
                        "\n" +
                        "\uD83D\uDD35  Посмотреть фото и видео дома можно через " +
                        "бота, в боте есть фото всех комнат дома и дополнительных " +
                        "услуг, советуем посмотреть фото и видео дома перед заказом.\n" +
                        "\n" +
                        "\uD83D\uDD35  Через бота вы можете получить информацию о том," +
                        " какие скидки вам положены в процессе заказа на дом, узнать об " +
                        "этих скидках вы можете, посетив раздел Скидки и акции.\n" +
                        "\n" +
                        "\uD83D\uDD35  Через бота вы можете ознакомиться с правилами " +
                        "входа и выхода в дом.Чтобы узнать эти правила, вы можете посетить " +
                        "раздел правил входа и выхода.\n" +
                        "\n" +
                        "\uD83D\uDD35  Через бота вы можете увидеть контакт для связи с" +
                        " управляющими дома, вы можете перейти в раздел контакты, чтобы узнать контакт\n" +
                        "\n" +
                        "\uD83D\uDD35  Узнать домашний адрес можно через бота, домашний адрес показывается с местоположением",
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
