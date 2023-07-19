package com.example.controller;

import com.example.entity.OrderDateEntity;
import com.example.entity.OrderHouseEntity;
import com.example.enums.Payment;
import com.example.enums.Status;
import com.example.modul.Apartment;
import com.example.myTelegramBot.MyTelegramBot;
import com.example.repository.OrderHouseRepository;
import com.example.service.MainService;
import com.example.service.OrderHouseService;
import com.example.util.*;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Controller;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Message;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Controller
@Lazy
@RequiredArgsConstructor
public class OrderHouseController {

    private final OrderHouseService service;
    private final MainService mainService;
    private final OrderHouseRepository orderHouseRepository;
    private final MyTelegramBot myTelegramBot;

    private List<TelegramUsers> usersList = new ArrayList<>();



    public void handle(Message message) {

        if (message.hasText()) {

            String text = message.getText();

            TelegramUsers step = saveUser(message.getChatId());

            switch (text) {
                case ButtonName.orderHouse -> {
                    service.mainMenu(message);
                    step.setStep(Step.MAIN);
                }
                case ButtonName.backMainMenu -> mainService.mainMenu(message.getChatId());

            }

            switch (step.getStep()) {
                case PHONE -> {
                    if (checkPhone(message)) {
                        sendSmsCode(message,message.getText());
                        step.setStep(Step.SMSCODE);
                    }
                }

                case SMSCODE -> {
                    if (checkSmsCode(message)) {
                        service.getFullName(message.getChatId());
                        step.setStep(Step.GETFULLNAME);
                    } else {
                        myTelegramBot.send(SendMsg.sendMsgParse(message.getChatId(),
                                "*Код подтверждения неверен. Пожалуйста, введите повторно*"));
                    }
                }
                case GETFULLNAME -> {
                    Optional<OrderHouseEntity> optional = orderHouseRepository.findLatestOrdersByChatIdAndStatus(message.getChatId(), Status.NOTACTIVE);

                    if (optional.isEmpty()) {
                        return;
                    }

                   List<OrderDateEntity> listOrderDate = orderHouseRepository.findByChatIdLastOrderNotActive(message.getChatId(),Status.NOTACTIVE);

                    OrderHouseEntity orderClient = optional.get();
                    orderClient.setFullName(message.getText());
                    orderClient.setAmount(Apartment.calculatePrice(calculateSum(message,listOrderDate)));
                    orderHouseRepository.save(orderClient);

                    showPrice(message, orderClient);
                }
            }


        } else if (message.hasContact() && saveUser(message.getChatId()).getStep().equals(Step.PHONE)) {
            sendSmsCode(message,message.getContact().getPhoneNumber());
            saveUser(message.getChatId()).setStep(Step.SMSCODE);
        }

    }

    private void showPrice(Message message, OrderHouseEntity order) {
        myTelegramBot.send(SendMsg.sendMsg(message.getChatId(),"Цена вашего заказа: " +order.getAmount(),
                InlineButton.keyboardMarkup(
                        InlineButton.rowList(
                                InlineButton.row(
                                        InlineButton.button("Я согласен ✅","step_pay#"+order.getId()),
                                        InlineButton.button("Отмена заказа ❌","cancelled_order")
                                )
                        )
                )));
    }

    public int calculateSum(Message message,List<OrderDateEntity> list){
        if (list.isEmpty()) {
            myTelegramBot.send(SendMsg.sendMsg(message.getChatId(),"Sizning buyurtmalaringiz topilmadi"));
        }
        int countOrder=0;
        for (OrderDateEntity orderDateEntity : list) {
            countOrder++;
        }
        return countOrder;
    }

   public boolean checkSmsCode(Message message) {
        Optional<OrderHouseEntity> optional = orderHouseRepository.findByChatIdOrderByCreatedDate(message.getChatId(),Status.NOTACTIVE);
        if (optional.isEmpty()) {
            return false;
        }

        return message.getText().equals(optional.get().getSmsCode());
    }



    public void sendSmsCode(Message message,String phone) {

        String randomNumber = RandomUtil.getRandomNumber();
        Optional<OrderHouseEntity> optional = orderHouseRepository.findByChatIdOrderByCreatedDate(message.getChatId(),Status.NOTACTIVE);
        if (optional.isEmpty()) {
            return;
        }

        OrderHouseEntity orderClient = optional.get();
        orderClient.setSmsCode(randomNumber);
        orderClient.setPhone(phone);
        orderHouseRepository.save(orderClient);
        SmsServiceUtil.sendSmsCode(SmsServiceUtil.removePlusSign(phone), randomNumber);
        SendMessage sendMessage = SendMsg.sendMsgParse(message.getChatId(),
                "*" + phone + "*" + "**" +
                        "*\n- Пожалуйста, введите проверочный код  ✅*");
        sendMessage.setReplyMarkup(KeyboardRemove.keyboardRemove());
        myTelegramBot.send(sendMessage);


    }

    public boolean checkPhone(Message message) {
        if (!message.getText().startsWith("+998") || message.getText().length() != 13) {
            myTelegramBot.send(SendMsg.sendMsgParse(message.getChatId(),
                    "*Пожалуйста, введите номер телефона в форму ниже!*" +
                            "*\nНапример : +998901234567  ✅*"));
            return false;
        }

        for (int i = 0; i < message.getText().length(); i++) {
            if (Character.isAlphabetic(message.getText().charAt(i))) {
                myTelegramBot.send(SendMsg.sendMsgParse(message.getChatId(),
                        "*Пожалуйста, введите номер телефона в форму ниже!*" +
                                "*\nНапример : +998901234567  ✅*"));
                return false;
            }
        }

        return true;
    }

    public TelegramUsers saveUser(Long chatId) {
        for (TelegramUsers users : usersList) {
            if (users.getChatId().equals(chatId)) {
                return users;
            }
        }
        var users = new TelegramUsers();
        users.setChatId(chatId);
        usersList.add(users);
        return users;
    }


    public void acceptOrder(Long chatId, Long orderId, Payment payment) {
        Optional<OrderHouseEntity> optional = orderHouseRepository.findById(orderId);
        if (optional.isEmpty()) {
            myTelegramBot.send(SendMsg.sendMsg(chatId,
                    "Ошибка при заказе, попробуйте еще раз  \uD83D\uDD04"));
            return;
        }
        OrderHouseEntity orderClient = optional.get();
        orderClient.setStatus(Status.ACTIVE);
        orderClient.setPayment(payment);


        myTelegramBot.send(SendMsg.sendMsg(chatId,
                "*Ваш заказ принят, наши специалисты свяжутся с вами в ближайшее время  ✅*"));

        orderHouseRepository.save(orderClient);

        mainService.mainMenu(chatId);
//        sendOrder(orderClient);  adminga habar yuborish uchun tulov haqida !
    }

}

