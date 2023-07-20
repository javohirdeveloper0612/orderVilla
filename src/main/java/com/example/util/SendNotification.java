package com.example.util;


import com.example.entity.OrderHouseEntity;
import com.example.enums.Status;
import com.example.myTelegramBot.MyTelegramBot;
import com.example.repository.OrderHouseRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.time.LocalDate;
import java.util.List;

@Component
@RequiredArgsConstructor
public class SendNotification {

private final OrderHouseRepository orderHouseRepository;
private final MyTelegramBot myTelegramBot;

    public void handle() {
        List<OrderHouseEntity> listOrders = orderHouseRepository.findAllByStatusAndDate(Status.ACTIVE, LocalDate.now());
        if (listOrders.isEmpty()){
            return;
        }
        for (OrderHouseEntity listOrder : listOrders) {
         myTelegramBot.send(SendMsg.sendMsg(listOrder.getChatId(),"Уважаемый Заказчик "+ listOrder.getFullName()+", вы сегодня заказали дачу",
                 InlineButton.keyboardMarkup(
                         InlineButton.rowList(
                                 InlineButton.row(
                                         InlineButton.button("\uD83D\uDCCD Адрес","location#"+listOrder.getChatId())
                                 )
                         )
                 )));
         SmsServiceUtil.sendSmsOrder(SmsServiceUtil.removePlusSign(listOrder.getPhone()),"Уважаемый Заказчик "+ listOrder.getFullName()+", вы сегодня заказали дачу");
        }
    }
}
