package com.example.controller;


import com.example.myTelegramBot.MyTelegramBot;
import com.example.service.OrderHouseService;
import com.example.util.SendMsg;
import com.example.util.Step;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Service;
import org.telegram.telegrambots.meta.api.objects.Message;
import org.telegram.telegrambots.meta.api.objects.Update;

@Service
@Lazy
@RequiredArgsConstructor
public class CallBackQueryController {
    private final OrderHouseService orderHouseService;
    private final OrderHouseController orderHouseController;
    private final MyTelegramBot myTelegramBot;

    public void handle(Update update) {

        Message message = update.getCallbackQuery().getMessage();
        String query = update.getCallbackQuery().getData();

        String[] parts = query.split("#");

        switch (parts[0]) {

            case "view_loc1" -> myTelegramBot.send(SendMsg.sendLocation(message.getChatId(),message.getMessageId()));
            case "view_loc2" -> myTelegramBot.send(SendMsg.sendLocation(message.getChatId(), message.getMessageId()));
            case "next", "cancel" -> orderHouseService.replyStart(message.getChatId(), message.getMessageId());
            case "accept_order_date" -> orderHouseService.getOrderDate(message);
            case "next_to_phone" -> {
                orderHouseService.saveOrder(message);
                orderHouseService.sendPhoneNumber(message);
                orderHouseController.saveUser(message.getChatId()).setStep(Step.PHONE);
            }
            case "pay_click" -> orderHouseService.successfullyPayment(message);
            case "pay_payme" -> orderHouseService.getPayment(message, Long.valueOf(parts[1]));
            case "step_pay" -> orderHouseService.choosePayment(message, Long.valueOf(parts[1]));
            case "cancelled_order","main_menu" -> orderHouseService.cancelledOrder(message);
        }


        String[] arr = query.split("/");
        if (arr.length < 2) {
            return;
        }

        if (arr[1].equals("year") || arr[1].equals("month")) {
            this.orderHouseService.sendCalendar(message, Integer.parseInt(arr[2]), Integer.parseInt(arr[3]));
        } else if (arr[1].equals("day")) {
            this.orderHouseService.getDate(message, Integer.parseInt(arr[2]), Integer.parseInt(arr[3]), Integer.parseInt(arr[4]));
        }
    }
}
