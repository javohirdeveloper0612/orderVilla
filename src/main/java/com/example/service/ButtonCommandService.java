package com.example.service;
import com.example.myTelegramBot.MyTelegramBot;
import com.example.util.Button;
import com.example.util.ButtonName;
import com.example.util.InlineButton;
import com.example.util.SendMsg;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.telegram.telegrambots.meta.api.objects.Message;

@Service
@RequiredArgsConstructor
public class ButtonCommandService {
    private final MyTelegramBot myTelegramBot;

    public void contactHouse(Message message) {

        myTelegramBot.send(SendMsg.sendMsg(message.getChatId(),
                "Konatkt bo'limi ishladi akalar",
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

    public void locationHouse(Message message) {

        myTelegramBot.send(SendMsg.sendMsg(message.getChatId(),

                "* Fox River Location *",

                InlineButton.keyboardMarkup(

                        InlineButton.rowList(

                                InlineButton.row(

                                        InlineButton.button(
                                                "\uD83D\uDCCD  посмотреть местоположение",
                                                "view_loc1")
                                )

                        ))));

    }


    public void rulesHouse(Message message) {

        myTelegramBot.send(SendMsg.sendMsg(message.getChatId(),
                "⬇️\n" +
                        "\n" +
                        "*- Правила входа и выхода из дома...*\n" +
                        "\n" +
                        "\uD83D\uDCCC  Если вы покупаете дом через бота" +
                        " в определенный день, бот пришлет смс на ваш номер " +
                        "телефона, то есть время заезда и выезда, могут быть" +
                        " люди, купившие дом после вас, если вы опоздаете, " +
                        "может быть нет возможности дать вам больше времени.\n" +
                        "\n" +
                        "\uD83D\uDCCC  Просим вас не портить личную обстановку " +
                        "дома, дом будет принят в том виде, в котором он был вам " +
                        "передан.\n" +
                        "\n" +
                        "\uD83D\uDCCC  Если вы приехали с семьей, если у вас есть" +
                        " маленькие дети, просим вас следить за ними, потому что " +
                        "возле дома есть бассейн, который очень опасен для маленьких детей.\n" +
                        "\n" +
                        "\uD83D\uDCCC  Мы просим вас быть осторожными, если во время " +
                        "отпуска у вас есть самовозгорающиеся устройства, так как это" +
                        " может привести к разного рода неприятным ситуациям.Ваш спокойный " +
                        "отдых важен для нас.",

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


    public void photoVilla(Message message) {

        myTelegramBot.send(SendMsg.sendMsg(message.getChatId(),
                "⬇️\n" +
                        "\n" +
                        "*- Нажмите на ссылку ниже, чтобы увидеть фотографии дома*  \uD83D\uDC47\n" +
                        "\n" +
                        "ссылку - https://telegra.ph/Fox-River-4-Photo-07-24\n" +
                        "\n",

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
