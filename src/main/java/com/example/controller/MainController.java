package com.example.controller;
import com.example.Service.*;
import com.example.command.*;
import com.example.util.ButtonName;
import com.example.util.Step;
import com.example.util.TelegramUsers;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Controller;
import org.telegram.telegrambots.meta.api.objects.Message;
import java.util.ArrayList;
import java.util.List;

@Controller
public class MainController {

    private final List<TelegramUsers> usersList = new ArrayList<>();
    private final ContactCommand contactCommand;
    private final HelpCommand helpCommand;
    private final LocationCommand locationCommand;
    private final OrderCommand orderCommand;
    private final CreativeTeamCommand creativeTeamCommand;
    private final MainService mainService;
    private final DiscountHouseService discountHouseService;
    private final PhotoAndVideoService photoAndVideoService;
    private final RulesHouseService rulesHouseService;
    private final ContactHouseService contactHouseService;
    private final LocationHouseService locationHouseService;
    private final OrderHouseController orderHouseController;
    private final InstructionBotCommand instructionBotCommand;
    private final BotInstructionHouseService botInstructionHouseService;

    @Lazy
    @Autowired
    public MainController(ContactCommand contactCommand,
                          HelpCommand helpCommand,
                          LocationCommand locationCommand,
                          OrderCommand orderCommand,
                          CreativeTeamCommand creativeTeamCommand,
                          MainService mainService,
                          DiscountHouseService discountHouseService,
                          PhotoAndVideoService photoAndVideoService,
                          RulesHouseService rulesHouseService,
                          ContactHouseService contactHouseService,
                          LocationHouseService locationHouseService,
                          OrderHouseController orderHouseController,
                          InstructionBotCommand instructionBotCommand,
                          BotInstructionHouseService botInstructionHouseService) {

        this.contactCommand = contactCommand;
        this.helpCommand = helpCommand;
        this.locationCommand = locationCommand;
        this.orderCommand = orderCommand;
        this.creativeTeamCommand = creativeTeamCommand;
        this.mainService = mainService;
        this.discountHouseService = discountHouseService;
        this.photoAndVideoService = photoAndVideoService;
        this.rulesHouseService = rulesHouseService;
        this.contactHouseService = contactHouseService;
        this.locationHouseService = locationHouseService;
        this.orderHouseController = orderHouseController;
        this.instructionBotCommand = instructionBotCommand;
        this.botInstructionHouseService = botInstructionHouseService;
    }

    public void handle(Message message) {

        var text = message.getText();
        var telegramUsers = saveUser(message.getChatId());

        //-> Command text

        switch (text) {

            case "/start" -> {
                mainService.mainMenu(message);
                telegramUsers.setStep(Step.MAIN);
            }
            case "/help" -> {
                helpCommand.helpComand(message);
                telegramUsers.setStep(Step.HELPCOMMAND);
            }
            case "/order" -> {
                orderCommand.orderCommand(message);
                telegramUsers.setStep(Step.ORDERCOMMAND);
            }
            case "/botinstruction" -> {
                instructionBotCommand.instructionBotCommand(message);
                telegramUsers.setStep(Step.BOTINSTRUCTIONCOMMAND);
            }
            case "/contact" -> {
                contactCommand.contactCommand(message);
                telegramUsers.setStep(Step.CONTACTCOMMAND);
            }
            case "/location" -> {
                locationCommand.locationCommand(message);
                telegramUsers.setStep(Step.LOCATIONCOMMAND);
            }
            case "/creativeteam" -> {
                creativeTeamCommand.creativeTeamCommand(message);
                telegramUsers.setStep(Step.CREATIVETEAMCOMMAND);
            }
        }

        //-> Command Step

        if (telegramUsers.getStep().equals(Step.HELPCOMMAND)) {

            if (text.equals(ButtonName.backMainMenu)) {
                mainService.mainMenu(message);
                telegramUsers.setStep(Step.MAIN);
            }
        }
        if (telegramUsers.getStep().equals(Step.ORDERCOMMAND)) {

            if (text.equals(ButtonName.backMainMenu)) {
                mainService.mainMenu(message);
                telegramUsers.setStep(Step.MAIN);
            }
        }
        if (telegramUsers.getStep().equals(Step.CONTACTCOMMAND)) {

            if (text.equals(ButtonName.backMainMenu)) {
                mainService.mainMenu(message);
                telegramUsers.setStep(Step.MAIN);
            }
        }
        if (telegramUsers.getStep().equals(Step.LOCATIONCOMMAND)) {

            if (text.equals(ButtonName.backMainMenu)) {
                mainService.mainMenu(message);
                telegramUsers.setStep(Step.MAIN);
            }
        }
        if (telegramUsers.getStep().equals(Step.CREATIVETEAMCOMMAND)) {

            if (text.equals(ButtonName.backMainMenu)) {
                mainService.mainMenu(message);
                telegramUsers.setStep(Step.MAIN);
            }
        }
        if (telegramUsers.getStep().equals(Step.FEATUREBOTCOMMAND)) {

            if (text.equals(ButtonName.backMainMenu)) {
                mainService.mainMenu(message);
                telegramUsers.setStep(Step.MAIN);
            }
        }

        //-> Button text

        switch (text) {

            case ButtonName.orderHouse -> {
                orderHouseController.handle(message);
                telegramUsers.setStep(Step.ORDERHOUSE);
            }
            case ButtonName.discountsHouse -> {
                discountHouseService.discountHouse(message);
                telegramUsers.setStep(Step.DISCOUNTHOUSE);
            }
            case ButtonName.photoAndVideoHouse -> {
                photoAndVideoService.photoAndVideoHouse(message);
                telegramUsers.setStep(Step.PHOTOANDVIDEOHOUSE);
            }
            case ButtonName.rulesHouse -> {
                rulesHouseService.rulesHouse(message);
                telegramUsers.setStep(Step.RULESHOUSE);
            }
            case ButtonName.botinstruction -> {
                botInstructionHouseService.botinstruction(message);
                telegramUsers.setStep(Step.BOTINSTRUCTION);
            }
            case ButtonName.contactHouse -> {
                contactHouseService.contactHouse(message);
                telegramUsers.setStep(Step.CONTACTHOUSE);
            }
            case ButtonName.locationHouse -> {
                locationHouseService.locationHouse(message);
                telegramUsers.setStep(Step.LOCATIONHOUSE);
            }
        }

        //-> Button Step

        if (telegramUsers.getStep().equals(Step.DISCOUNTHOUSE)) {

            if (text.equals(ButtonName.backMainMenu)) {
                mainService.mainMenu(message);
                telegramUsers.setStep(Step.MAIN);
            }
        }
        if (telegramUsers.getStep().equals(Step.PHOTOANDVIDEOHOUSE)) {

            if (text.equals(ButtonName.backMainMenu)) {
                mainService.mainMenu(message);
                telegramUsers.setStep(Step.MAIN);
            }
        }
        if (telegramUsers.getStep().equals(Step.RULESHOUSE)) {

            if (text.equals(ButtonName.backMainMenu)) {
                mainService.mainMenu(message);
                telegramUsers.setStep(Step.MAIN);
            }
        }
        if(telegramUsers.getStep().equals(Step.BOTINSTRUCTION)){

            if(text.equals(ButtonName.backMainMenu)){
                mainService.mainMenu(message);
                telegramUsers.setStep(Step.MAIN);
            }
        }
        if (telegramUsers.getStep().equals(Step.CONTACTHOUSE)) {

            if (text.equals(ButtonName.backMainMenu)) {
                mainService.mainMenu(message);
                telegramUsers.setStep(Step.MAIN);
            }
        }
        if (telegramUsers.getStep().equals(Step.LOCATIONHOUSE)) {

            if (text.equals(ButtonName.backMainMenu)) {
                mainService.mainMenu(message);
                telegramUsers.setStep(Step.MAIN);
            }
        }

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
