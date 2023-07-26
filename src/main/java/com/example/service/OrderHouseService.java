package com.example.service;

import com.example.entity.OrderDateEntity;
import com.example.entity.OrderHouseEntity;
import com.example.enums.Status;
import com.example.modul.CustomMap;
import com.example.myTelegramBot.MyTelegramBot;
import com.example.payme.util.PaymentUtil;
import com.example.repository.OrderDateRepository;
import com.example.repository.OrderHouseRepository;
import com.example.util.*;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Service;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.methods.updatingmessages.EditMessageText;
import org.telegram.telegrambots.meta.api.objects.Message;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.buttons.InlineKeyboardButton;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;


@Service
@Lazy
@RequiredArgsConstructor
public class OrderHouseService {

    private final MyTelegramBot myTelegramBot;
    private final OrderHouseRepository repository;
    private final OrderDateRepository orderDateRepository;
    private final CalendarUtil calendarUtil;
    private final PaymentUtil paymentUtil;
    private final MainService mainService;
    private final CustomMap<Long, LocalDate> localDates = new CustomMap<>();


    // instruction bot
    public void mainMenu(Message message) {

        myTelegramBot.send(SendMsg.sendMsg(message.getChatId(), "⬇\uFE0F\n" +
                "\n" +
                "- Инструкции бота...\n" +
                "\n" +
                "\uD83D\uDD35 Заказать дом на 6 человек со всеми условиями можно через бота, выбрать удобный для вас день в процессе оформления заказа и совершить денежный перевод в удобной для вас форме платежа.\n" +
                "\n" +
                "\uD83D\uDD35 Посмотреть фото и видео дома можно через бота, в боте есть фото всех комнат дома и дополнительных услуг, советуем посмотреть фото и видео дома перед оформлением заказа.\n" +
                "\n" +
                "\uD83D\uDD35 Через бота вы можете получить информацию о том, какие скидки у вас есть в процессе заказа дома, вы можете зайти в раздел Скидки и акции и получить информацию об этих скидках.\n" +
                "\n" +
                "\uD83D\uDD35 Через бота вы можете ознакомиться с правилами входа и выхода в дом.Чтобы узнать об этих правилах, вы можете посетить раздел правил входа и выхода.\n" +
                "\n" +
                "\uD83D\uDD35 Через бота вы можете увидеть контакт для связи с управляющими дома, вы можете перейти в раздел контакты, чтобы узнать контакт\n" +
                "\n" +
                "\uD83D\uDD35 Узнать домашний адрес можно через бота, домашний адрес будет отображаться с местоположением\n" +
                "\n" +
                "\uD83D\uDD35 Если вы знакомы со всей информацией, нажимайте кнопку «Далее»!", InlineButton.keyboardMarkup(
                InlineButton.rowList(InlineButton.row(InlineButton.button("далее ➡\uFE0F", "next")))
        )));
    }


    // calendarni chiqrish

    public void sendCalendar(Message message, int year, int month) {

        EditMessageText editMessageText = new EditMessageText();
        editMessageText.setChatId(message.getChatId());
        editMessageText.setMessageId(message.getMessageId());
        editMessageText.setText("*Пожалуйста, выберите дату, которую вы хотите заказать !*");
        editMessageText.setParseMode("Markdown");
        editMessageText.setReplyMarkup(calendarUtil.makeYearKeyBoard(year, month));
        myTelegramBot.send(editMessageText);
    }


    // Bu ham calendarni chiqarish


    public void getDate(Message message, int year, int month, int day) {
        localDates.put(message.getChatId(), LocalDate.of(year, month, day));

        myTelegramBot.send(SendMsg.deleteMessage(message));

        SendMessage sendMessage = new SendMessage();
        sendMessage.setChatId(message.getChatId());
        sendMessage.setText("*1 - шаг  ⬇️\n" +
                "\n" +
                "- Пожалуйста, выберите дату, которую вы хотите заказать  ⬇️*");
        sendMessage.setParseMode("Markdown");
        sendMessage.setReplyMarkup(
                calendarUtil.makeYearKeyBoard(year, month));

        myTelegramBot.send(sendMessage);
    }

    // tanlagan kunlarini userga korsatish uchun
    public void getOrderDate(Message message) {

        if (localDates.get(message.getChatId()) == null) {
            myTelegramBot.send(SendMsg.sendMsg(message.getChatId(), "*Нет даты, которые вы хотите забронировать!*"));
            replyStart(message.getChatId(), message.getMessageId());
            return;
        }

        myTelegramBot.send(SendMsg.deleteMessage(message));

        List<LocalDate> dateList = RemoveDublicatElementsList.removeDuplicates(localDates.get(message.getChatId()));


        StringBuffer info = new StringBuffer();
        info.append("\uD83D\uDDD3\uFE0F Даты, которые вы хотите забронировать -> \n\n\n");
        for (LocalDate localDate : dateList) {
            if (orderDateRepository.existsByDateAndStatus(localDate, Status.ACTIVE)) {
                dateList.remove(localDate);
            }
            info.append("\t\t\t  \uD83D\uDCC5 Дата : " + localDate + "\t\n\n");
        }
        info.append("\n✅ Если все правильно, нажмите кнопку «Далее»,\n\n" +
                "❌ Если неправильно, нажмите кнопку «Назад» !");
        dateList.clear();

        myTelegramBot.send(SendMsg.sendMsg(message.getChatId(), String.valueOf(info), InlineButton.keyboardMarkup(
                InlineButton.rowList(
                        InlineButton.row(
                                InlineButton.button("Назад \uD83D\uDD19", "cancel"),
                                InlineButton.button("Далее ➡\uFE0F", "next_to_phone")
                        )
                )
        )));
    }

    // order tanlangan kunlarni databasega NOTACTIVE qilib saqlab quyish uchun ishlatiladi !

    public void saveOrder(Message message) {
        Optional<OrderHouseEntity> optional = repository.findLatestOrdersByChatIdAndStatus
                (message.getChatId(), Status.NOTACTIVE);

        optional.ifPresent(repository::delete);

        OrderHouseEntity orderClient = new OrderHouseEntity();
        orderClient.setChatId(message.getChatId());
        orderClient.setStatus(Status.NOTACTIVE);
        orderClient.setVisible(true);

        repository.save(orderClient);

        List<LocalDate> localDatesList = RemoveDublicatElementsList.removeDuplicates(localDates.get(message.getChatId()));

        for (LocalDate localDate : localDatesList) {
            OrderDateEntity orderDateEntity = new OrderDateEntity();
            orderDateEntity.setOrder(orderClient);
            orderDateEntity.setStatus(Status.NOTACTIVE);
            orderDateEntity.setDate(localDate);
            orderDateRepository.save(orderDateEntity);
        }
    }


    // zakaz qilishni boshlagandagi button va mapda tanlagan kunlari qolib ketgan bolsa shularni ham uchirib yuboradi
    public void replyStart(Long chatId, Integer messageId) {


        myTelegramBot.send(SendMsg.deleteMessage(chatId,messageId));

        localDates.removeKey(chatId);

        SendMessage sendMessage = new SendMessage();
        sendMessage.setChatId(chatId);
        sendMessage.setText("*1 - шаг  ⬇️\n" +
                "\n" +
                "- Пожалуйста, выберите дату, которую вы хотите заказать  ⬇️*");
        sendMessage.setParseMode("Markdown");
        sendMessage.setReplyMarkup(
                calendarUtil.makeYearKeyBoard(
                        LocalDate.now().getYear(), LocalDate.now().getMonthValue()));

        myTelegramBot.send(sendMessage);
    }


    public void sendPhoneNumber(Message message) {

        myTelegramBot.send(SendMsg.deleteMessage(message));

        myTelegramBot.send(SendMsg.sendMsg(message.getChatId(), "Пожалуйста введите ваш номер телефона! (+998996731741)", ButtonUtil.getContact()));
    }


    public void getFullName(Long chatId) {
        myTelegramBot.send(SendMsg.sendMsgParse(chatId,
                "*5 - шаг  ⬇️\n" +
                        "\n" +
                        "- Пожалуйста, введите ваше полное имя\n" +
                        "Например : Коржабов Шахзод* ✅"));
    }


    public void successfullyPayment(Message message) {

        myTelegramBot.send(SendMsg.sendMsg(message.getChatId(), "Successfully Payment"));
    }


    //Payme orqali tulov qilish uchun method va payme tulov uchun link generate qilib beradi

    public void getPayment(Message message, Long id) {
        Optional<OrderHouseEntity> optional = repository.findById(id);
        if (optional.isEmpty()) {
            myTelegramBot.send(SendMsg.sendMsg(message.getChatId(), "Order not found"));
            return;
        }
        OrderHouseEntity orderClient = optional.get();
        if (orderClient.getStatus() == Status.ACTIVE) {
            myTelegramBot.send(SendMsg.sendMsg(message.getChatId(), "Order already payed"));
            return;
        }

        repository.save(orderClient);
        InlineKeyboardButton button = new InlineKeyboardButton("▶️ Оплата");
        button.setUrl(paymentUtil.generatePaymentUrl(id, orderClient.getPhone(), orderClient.getAmount()));
        myTelegramBot.send(SendMsg.sendMsg(message.getChatId(),
                "*⬇️ Произведите оплату, перейдя по ссылке ниже и нажмите кнопку ✅ Проверить:*",
                InlineButton.keyboardMarkup(InlineButton.rowList(
                        InlineButton.row(button)
                )))

        );
    }


    // Tulov turini tanlash uchun method

    public void choosePayment(Message message, Long orderId) {

        myTelegramBot.send(SendMsg.deleteMessage(message.getChatId(),message.getMessageId()));

        myTelegramBot.send(SendMsg.sendMsg(message.getChatId(), "Выберите тип оплаты: ",
                InlineButton.keyboardMarkup(
                        InlineButton.rowList(
                                InlineButton.row(InlineButton.button("\uD83D\uDCB3 Payme", "pay_payme#" + orderId)),
                                InlineButton.row(InlineButton.button("\uD83D\uDCB3 Click", "pay_click")),
                                InlineButton.row(InlineButton.button(ButtonName.backMainMenu, "main_menu"))
                        )
                )));
    }

    public void cancelledOrder(Message message) {

        myTelegramBot.send(SendMsg.deleteMessage(message));

        Optional<OrderHouseEntity> optional = repository.findLatestOrdersByChatIdAndStatus(message.getChatId(), Status.NOTACTIVE);

        if (optional.isEmpty())
            myTelegramBot.send(SendMsg.sendMsg(1024661500L, "Nimadi Xatolik chiqdi" + message.getChatId() + "Shu Idli foydalanuvchi tamondan"));
        else
            repository.delete(optional.get());
        mainService.mainMenu(message.getChatId());
    }
}
