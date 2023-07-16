package com.example.controller;

import com.example.entity.OrderHouseEntity;
import com.example.enums.Status;
import com.example.myTelegramBot.MyTelegramBot;
import com.example.repository.OrderHouseRepository;
import com.example.service.MainService;
import com.example.service.OrderHouseService;
import com.example.util.*;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Controller;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Message;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.ReplyKeyboardRemove;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Controller
public class OrderHouseController {

    private final OrderHouseService service;

    private final MainService mainService;

    private OrderHouseEntity orderHouseEntity;

    private final OrderHouseRepository orderHouseRepository;

    private final MyTelegramBot myTelegramBot;

    private List<TelegramUsers> usersList = new ArrayList<>();

    @Lazy
    public OrderHouseController(OrderHouseService service, MainService mainService, OrderHouseRepository orderHouseRepository, MyTelegramBot myTelegramBot) {
        this.service = service;
        this.mainService = mainService;
        this.orderHouseRepository = orderHouseRepository;
        this.myTelegramBot = myTelegramBot;
    }

    public void handle(Message message) {

        if (message.hasText()) {

            String text = message.getText();

            TelegramUsers step = saveUser(message.getChatId());

            switch (text) {
                case ButtonName.orderHouse -> {
                    service.mainMenu(message);
                    step.setStep(Step.MAIN);
                }
                case ButtonName.backMainMenu -> mainService.mainMenu(message);

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

                    OrderHouseEntity orderClient = optional.get();
                    orderClient.setFullName(message.getText());
                    orderHouseRepository.save(orderClient);
                    myTelegramBot.send(SendMsg.sendMsg(message.getChatId(),"WORKING"));
                }
            }


        } else if (message.hasContact() && saveUser(message.getChatId()).getStep().equals(Step.PHONE)) {
            sendSmsCode(message,message.getContact().getPhoneNumber());
            saveUser(message.getChatId()).setStep(Step.SMSCODE);
        }

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
        ReplyKeyboardRemove replyKeyboardRemove = new ReplyKeyboardRemove();
        replyKeyboardRemove.setRemoveKeyboard(true);
        sendMessage.setReplyMarkup(replyKeyboardRemove);
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


}

