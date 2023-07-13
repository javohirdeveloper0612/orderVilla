package com.example.myTelegramBot;
import com.example.admin.controller.AdminMainController;
import com.example.config.BotConfig;
import com.example.controller.MainController;
import com.example.util.TelegramUsers;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.bots.TelegramLongPollingBot;
import org.telegram.telegrambots.meta.api.methods.send.SendDocument;
import org.telegram.telegrambots.meta.api.methods.send.SendLocation;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.methods.send.SendPhoto;
import org.telegram.telegrambots.meta.api.methods.updatingmessages.DeleteMessage;
import org.telegram.telegrambots.meta.api.methods.updatingmessages.EditMessageText;
import org.telegram.telegrambots.meta.api.objects.Message;
import org.telegram.telegrambots.meta.api.objects.Update;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;
import java.util.ArrayList;
import java.util.List;

@Component
public class MyTelegramBot extends TelegramLongPollingBot {

    private List<TelegramUsers> usersList = new ArrayList<>();
    private final BotConfig botConfig;
    private final MainController mainController;
    private final AdminMainController adminMainController;

    @Lazy
    @Autowired
    public MyTelegramBot(BotConfig botConfig,
                         MainController mainController,
                         AdminMainController adminMainController) {

        this.botConfig = botConfig;
        this.mainController = mainController;
        this.adminMainController = adminMainController;
    }

    @Override
    public void onUpdateReceived(Update update) {

        if(update.hasMessage()){

            Message message = update.getMessage();
            TelegramUsers users = saveUser(message.getChatId());

            mainController.handle(message); //-> MainController

            if(message.getChatId() == 123456778L){
                adminMainController.handle(update);
            }

        }


    }

    public TelegramUsers saveUser(Long chatId) {

        TelegramUsers user = usersList.stream().filter
                (u -> u.getChatId().equals(chatId)).findAny().orElse(null);

        if (user != null) {
            return user;
        }

        TelegramUsers users = new TelegramUsers();
        users.setChatId(chatId);
        usersList.add(users);

        return users;
    }


    public void send(SendMessage message) {
        try {
            execute(message);
        } catch (TelegramApiException e) {
            throw new RuntimeException(e);
        }
    }
    public Message send(SendPhoto message) {
        try {
            return  execute(message);
        } catch (TelegramApiException e) {
            throw new RuntimeException(e);
        }
    }

    public void send(SendDocument message) {
        try {
            execute(message);
        } catch (TelegramApiException e) {
            throw new RuntimeException(e);
        }
    }

    public Message send(SendLocation message) {
        try {
            return execute(message);
        } catch (TelegramApiException e) {
            throw new RuntimeException(e);
        }
    }

    public Message send(EditMessageText editMessageText) {
        try {
            return (Message) execute(editMessageText);
        } catch (TelegramApiException e) {
            throw new RuntimeException(e);
        }
    }

    public Boolean send(DeleteMessage deleteMessage) {
        try {
            return  execute(deleteMessage);
        } catch (TelegramApiException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public String getBotUsername() {
        return botConfig.getBotName();
    }

    @Override
    public String getBotToken() {
        return botConfig.getToken();
    }


}
