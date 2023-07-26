package com.example.service;
import com.example.myTelegramBot.MyTelegramBot;
import com.example.util.Button;
import com.example.util.ButtonName;
import com.example.util.InlineButton;
import com.example.util.SendMsg;
import org.springframework.stereotype.Service;
import org.telegram.telegrambots.meta.api.objects.Message;

@Service
public class CommandService {

    private final MyTelegramBot myTelegramBot;

    public CommandService(MyTelegramBot myTelegramBot) {
        this.myTelegramBot = myTelegramBot;
    }

    public void contactCommand(Message message) {

        myTelegramBot.send(SendMsg.sendMsg(message.getChatId(),
                "⬇️\n" +
                        "\n" +
                        "*- Присылайте нам свои вопросы, жалобы, обращения...*\n" +
                        "*- Наш администратор ответит вам как можно скорее...*\n" +
                        "\n" +
                        "*\uD83D\uDCF2  Связаться через Telegram...*\n" +
                        "\n" +
                        "➡️  https://t.me/romanmirzayev\n" +
                        "\n" +
                        "*☎️  Связаться по телефону...*\n" +
                        "\n" +
                        "➡️  +99899 820 70 74",

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
    public void creativeTeamCommand(Message message) {

        myTelegramBot.send(SendMsg.sendMsg(message.getChatId(),
                "⬇️\n" +
                        "\n" +
                        "*- Bot yaratuvchilari...\n" +
                        "\n" +
                        "\uD83D\uDD37  UZDP GROUP...\n" +
                        "\n" +
                        "- Takrorlanmas loyihalar biz bilan...\n" +
                        "- Mijozlar bilan ishonchli kelishuv...\n" +
                        "- Doimiy qo'llab quvatlash...\n" +
                        "- Didlaynga amal qilish...\n" +
                        "- Tajribali jamoa...\n" +
                        "\n" +
                        "- Aloqa uchun :\n" +
                        "- Telefon : +99895-102-40-55\n" +
                        "- Telegram : @hamdamboyismatov1\n" +
                        "- Hamdamboy Ismatov*",
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
    public void helpComand(Message message) {

        myTelegramBot.send(SendMsg.sendMsg(message.getChatId(),
                "⬇️\n" +
                        "\n" +
                        "*- Этот раздел будет запущен в ближайшее время*",
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
    public void locationCommand(Message message) {

        myTelegramBot.send(SendMsg.sendMsg(message.getChatId(),

                "*Fox River Location*",

                InlineButton.keyboardMarkup(

                        InlineButton.rowList(

                                InlineButton.row(

                                        InlineButton.button(
                                                "\uD83D\uDCCD  посмотреть местоположение",
                                                "view_loc2")
                                )

                        ))));


    }
    public void orderCommand(Message message) {

        myTelegramBot.send(SendMsg.sendMsg(message.getChatId(),

                "⬇️\n" +
                        "\n" +
                        "*- Инструкции по заказу...*\n" +
                        "\n" +
                        "Вам будет выдан календарь и вы выбираете дни в которые " +
                        "вы хотите купить дом, если вы хотите купить дом на 1 неделю," +
                        " бот выдаст вам календарь 7 раз на выбор 7 дней, вы должны " +
                        "подряд выбрать подходящие вам дни, а после выбора дней нажмите " +
                        "<<Принять>> и вы перейдете в следующий раздел, будьте внимательны" +
                        " при выборе дней...\n" +
                        "\n" +
                        "2️⃣ - Бот покажет вам дни, когда вы купили дом, если дни, " +
                        "которые вы выбрали, и дни, указанные ботом, верны, нажмите" +
                        " кнопку «Далее» и перейдите к следующему шагу, если указанные" +
                        " дни неверны, вы можете нажать кнопку «Назад», чтобы снова " +
                        "выбрать дни !!! Будьте внимательны при выборе дней...\n" +
                        "\n" +
                        "3️⃣ - Бот попросит вас ввести свой номер телефона, введите " +
                        "свой рабочий номер телефона, и бот отправит 6-значный код" +
                        " подтверждения на ваш номер телефона, и вы введете этот код " +
                        "в бота и перейдете к следующему шагу...\n" +
                        "\n" +
                        "4️⃣ - Бот попросит вас ввести свое имя и фамилию, и вы введете" +
                        " свое имя и фамилию и перейдете к следующему шагу...\n" +
                        "\n" +
                        "5️⃣ - Бот покажет вам сумму, которую вы должны заплатить " +
                        "за заказанный вами дом, если вы нажмете кнопку <<Я согласен>>," +
                        " вы перейдете к следующему шагу, т.е. к типу оплаты. Если " +
                        "вы нажмете <<Отменить заказ>>, заказ будет отменен.\n" +
                        "\n" +
                        "6️⃣ - Последний шаг – выбрать удобный для вас способ оплаты " +
                        "и произвести оплату, и заказ будет принят...",

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
