package com.example.controller;


import com.example.service.MainService;
import com.example.service.OrderHouseService;
import com.example.util.Step;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Service;
import org.telegram.telegrambots.meta.api.objects.Message;
import org.telegram.telegrambots.meta.api.objects.Update;

@Service
public class CallBackQueryController {

    private final OrderHouseService orderHouseService;

    private final OrderHouseController orderHouseController;
    private final MainService mainService;

    @Lazy
    public CallBackQueryController(OrderHouseService orderHouseService, OrderHouseController orderHouseController, MainService mainService) {
        this.orderHouseService = orderHouseService;
        this.orderHouseController = orderHouseController;
        this.mainService = mainService;
    }

    public void handle(Update update) {

        Message message = update.getCallbackQuery().getMessage();
        String query = update.getCallbackQuery().getData();


        switch (query) {

            case "next", "cancel" -> orderHouseService.replyStart(message.getChatId(), message.getMessageId());

            case "accept_order_date" -> orderHouseService.getOrderDate(message);

            case "main_menu" -> mainService.mainMenu(message);

            case "next_to_phone" -> {
                orderHouseService.saveOrder(message);
                orderHouseService.sendPhoneNumber(message);
                orderHouseController.saveUser(message.getChatId()).setStep(Step.PHONE);
            }
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
