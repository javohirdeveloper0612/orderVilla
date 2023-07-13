package com.example.Service;
import com.example.myTelegramBot.MyTelegramBot;
import org.springframework.stereotype.Service;


@Service
public class OrderHouseService {

    private final MyTelegramBot myTelegramBot;

    public OrderHouseService(MyTelegramBot myTelegramBot) {
        this.myTelegramBot = myTelegramBot;
    }

}
