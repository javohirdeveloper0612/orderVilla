package com.example.controller;


import com.example.service.BotInstructionHouseService;
import com.example.service.ButtonCommandService;
import com.example.service.CommandService;
import com.example.service.MainService;
import com.example.util.ButtonName;
import com.example.util.Step;
import com.example.util.TelegramUsers;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Controller;
import org.telegram.telegrambots.meta.api.objects.Message;

import java.util.ArrayList;
import java.util.List;

@Controller
@Lazy
@RequiredArgsConstructor
public class MainController {

    private final List<TelegramUsers> usersList = new ArrayList<>();
    private final MainService mainService;
    private final OrderHouseController orderHouseController;
    private final BotInstructionHouseService botInstructionHouseService;
    private final ButtonCommandService buttonCommandService;
    private final CommandService commandService;
    private final MyOrdersController myOrdersController;



    public void handle(Message message) {

        var text = message.getText();
        var telegramUsers = saveUser(message.getChatId());

        //-> Command text

        if (message.hasText()) {

            switch (text) {

                case "/start" -> {
                    mainService.mainMenu(message.getChatId());
                    telegramUsers.setStep(Step.MAIN);
                }
                case "/help" -> {
                    commandService.helpComand(message);
                    telegramUsers.setStep(Step.HELPCOMMAND);
                }
                case "/order" -> {
                    commandService.orderCommand(message);
                    telegramUsers.setStep(Step.ORDERCOMMAND);
                }
                case "/botinstruction" -> {
                    commandService.instructionBotCommand(message);
                    telegramUsers.setStep(Step.BOTINSTRUCTIONCOMMAND);
                }
                case "/contact" -> {
                    commandService.contactCommand(message);
                    telegramUsers.setStep(Step.CONTACTCOMMAND);
                }
                case "/location" -> {
                    commandService.locationCommand(message);
                    telegramUsers.setStep(Step.LOCATIONCOMMAND);
                }
                case "/creativeteam" -> {
                    commandService.creativeTeamCommand(message);
                    telegramUsers.setStep(Step.CREATIVETEAMCOMMAND);
                }
            }

            //-> Command Step


            if (telegramUsers.getStep().equals(Step.ORDERHOUSE)) {
                orderHouseController.handle(message);
                return;
            }

            //-> Button text

            switch (text) {

                case ButtonName.orderHouse -> {
                    orderHouseController.handle(message);
                    telegramUsers.setStep(Step.ORDERHOUSE);
                }

                case ButtonName.myOrders -> {
                    myOrdersController.handle(message);
                    telegramUsers.setStep(Step.MYORDERS);
                }

                case ButtonName.discountsHouse -> {
                    buttonCommandService.discountHouse(message);
                    telegramUsers.setStep(Step.DISCOUNTHOUSE);
                }
                case ButtonName.photoAndVideoHouse -> {
                    buttonCommandService.photoAndVideoHouse(message);
                    telegramUsers.setStep(Step.PHOTOANDVIDEOHOUSE);
                }
                case ButtonName.rulesHouse -> {
                    buttonCommandService.rulesHouse(message);
                    telegramUsers.setStep(Step.RULESHOUSE);
                }
                case ButtonName.botinstruction -> {
                    botInstructionHouseService.botinstruction(message);
                    telegramUsers.setStep(Step.BOTINSTRUCTION);
                }
                case ButtonName.contactHouse -> {
                    buttonCommandService.contactHouse(message);
                    telegramUsers.setStep(Step.CONTACTHOUSE);
                }
                case ButtonName.locationHouse -> {
                    buttonCommandService.locationHouse(message);
                    telegramUsers.setStep(Step.LOCATIONHOUSE);
                }
                case ButtonName.backMainMenu -> {
                    mainService.mainMenu(message.getChatId());
                    telegramUsers.setStep(Step.MAIN);
                }
            }


        } else if (message.hasContact() && saveUser(message.getChatId()).getStep().equals(Step.ORDERHOUSE)) {
            orderHouseController.handle(message);
        }
    }

    public TelegramUsers saveUser(Long chatId) {
        return usersList.stream()
                .filter(users -> users.getChatId().equals(chatId))
                .findFirst()
                .orElseGet(() -> {
                    var users = new TelegramUsers();
                    users.setChatId(chatId);
                    usersList.add(users);
                    return users;
                });
    }
}
