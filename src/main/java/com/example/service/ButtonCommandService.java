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

    public void discountHouse(Message message) {

        myTelegramBot.send(SendMsg.sendMsg(message.getChatId(),
                "⬇️\n" +
                        "\n" +
                        "*- Скидки и акции...*\n" +
                        "\n" +
                        "\uD83D\uDCB8  на 1 день - 2.000.000 сум\n" +
                        "\uD83D\uDCB8  на 1 неделю - 8.750.000 сум\n" +
                        "\uD83D\uDCB8  на 1 месяц - 16.000.000 сум\n" +
                        "\n" +
                        "*- Цены со скидкой...*\n" +
                        "\n" +
                        "\n" +
                        "*\uD83D\uDCB8 1 день скидки...*\n" +
                        "\n" +
                        "\n" +
                        "\uD83D\uDCC9  1-дневная скидка на дни после 1 недели 750.000 сум\n" +
                        "\n" +
                        "\uD83D\uDCB8  вы оплачиваете 1.250.000 сум за 1 день со скидкой\n" +
                        "\n" +
                        "\uD83D\uDCC9  Скидка на 1 день для дней, прибывающих после 1 месяца 1.467.000 сум\n" +
                        "\n" +
                        "\uD83D\uDCB8  вы оплачиваете 533.000 сум за 1 день со скидкой\n" +
                        "\n" +
                        "\n" +
                        "*\uD83D\uDCB8 скидка на 1 неделю...*\n" +
                        "\n" +
                        "\n" +
                        "\uD83D\uDCC9  Вы получите скидку 5.250.000 сум при бронировании дома на 1 неделю\n" +
                        "\n" +
                        "\uD83D\uDCB8  вы заплатите 8 750 000 сум за 1 неделю со скидкой\n" +
                        "\n" +
                        "\n" +
                        "*\uD83D\uDCB8 скидка на 1 месяц...*\n" +
                        "\n" +
                        "\n" +
                        "\uD83D\uDCC9  При бронировании дома на 1 месяц вы получаете скидку 44 000 000 сум\n" +
                        "\n" +
                        "\uD83D\uDCB8  Вы заплатите 16 000 сумов за 1 месяц со скидкой",

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
