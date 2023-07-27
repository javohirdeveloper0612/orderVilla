package com.example.myTelegramBot;
import com.example.admin.controller.AdminMainController;
import com.example.config.BotConfig;
import com.example.controller.CallBackQueryController;
import com.example.controller.MainController;
import com.example.service.ChangeStatusOrder;
import com.example.util.SendNotification;
import com.example.util.TelegramUsers;
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
import java.util.*;

@Component
public class MyTelegramBot extends TelegramLongPollingBot {

    private List<TelegramUsers> usersList = new ArrayList<>();
    private final BotConfig botConfig;
    private final MainController mainController;
    private final AdminMainController adminMainController;
    private final CallBackQueryController queryController;
    private final SendNotification sendNotification;
    private final ChangeStatusOrder changeStatusOrder;


    @Lazy
    public MyTelegramBot(BotConfig botConfig,
                         MainController mainController,
                         AdminMainController adminMainController,
                         CallBackQueryController queryController,
                         SendNotification sendNotification,
                         ChangeStatusOrder changeStatusOrder) {

        this.botConfig = botConfig;
        this.mainController = mainController;
        this.adminMainController = adminMainController;
        this.queryController = queryController;
        this.sendNotification = sendNotification;
        this.changeStatusOrder = changeStatusOrder;
        scheduleTask();
    }


    @Override
    public void onUpdateReceived(Update update) {

        if (update.hasMessage()) {

            Message message = update.getMessage();
            TelegramUsers users = saveUser(message.getChatId());

            if (message.hasText()&&message.getText().equals("/test")){
                changeStatusOrder.handle();
                return;
            }

            if(message.getChatId() == 5530157790L){
                adminMainController.handle(update);
                return;
            }

            mainController.handle(message);

        } else if (update.hasCallbackQuery()) {
            queryController.handle(update);

        }

    }

    public TelegramUsers saveUser(Long chatId) {

        TelegramUsers user = usersList.stream().filter(u -> u.getChatId().equals(chatId)).findAny().orElse(null);

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
            return execute(message);
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
            return execute(deleteMessage);
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

    public void scheduleTask() {
        Timer timer = new Timer();
        timer.schedule(new TimerTask() {
            public void run() {
                // Soat 23:30 da bajariladigan ishning kodini yozing
                // Masalan, quyidagi qator orqali javob yuborish mumkin
                sendNotification.handle();
                changeStatusOrder.handle();

            }
        }, getScheduledTime());
    }

    private Date getScheduledTime() {
        Date now = new Date();
        Date scheduledTime = new Date(now.getYear(), now.getMonth(), now.getDate(), 16,25 , 0);
        if (scheduledTime.before(now)) {
            scheduledTime = new Date(scheduledTime.getTime() + 24 * 60 * 60 * 1000);
        }
        return scheduledTime;
    }


}
