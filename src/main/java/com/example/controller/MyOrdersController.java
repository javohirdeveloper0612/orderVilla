package com.example.controller;

import com.example.entity.OrderDateEntity;
import com.example.entity.OrderHouseEntity;
import com.example.enums.Status;
import com.example.myTelegramBot.MyTelegramBot;
import com.example.repository.OrderDateRepository;
import com.example.repository.OrderHouseRepository;
import com.example.util.SendMsg;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.telegram.telegrambots.meta.api.objects.Message;

import java.util.List;

@Controller
@RequiredArgsConstructor
public class MyOrdersController {
    private final MyTelegramBot myTelegramBot;
    private final OrderHouseRepository repository;
    private final OrderDateRepository orderDateRepository;

    public void handle(Message message){
        List<OrderHouseEntity> list = repository.findByChatIdAndStatus(message.getChatId(), Status.ACTIVE);
        if (list.isEmpty()){
            myTelegramBot.send(SendMsg.sendMsg(message.getChatId(),"Заказов не найдено \uD83E\uDD37\u200D♂\uFE0F"));
        }

        StringBuffer stringBuffer = new StringBuffer();

        for (OrderHouseEntity orderHouseEntity : list) {
            stringBuffer.append("\t\t\t\uD83D\uDCCB Ваши заказы: \n\n\n");
            stringBuffer.append("\uD83D\uDC64 Имя и фамилия: "+orderHouseEntity.getFullName());
            stringBuffer.append("\n\n☎\uFE0F Номер телефона: "+orderHouseEntity.getPhone());
            stringBuffer.append("\n\n\uD83D\uDCC5 День, когда был сделан заказ: "+orderHouseEntity.getCreatedDate());
            stringBuffer.append("\n\n\uD83D\uDCB0 Цена: "+orderHouseEntity.getAmount());
            stringBuffer.append("\n\n\uD83D\uDDD3\uFE0F Список заказанных дней: \n\n");
            List<OrderDateEntity> dateList = orderDateRepository.findAllByOrder(orderHouseEntity);
            if (dateList != null){
                for (OrderDateEntity orderDateEntity : dateList) {
                    stringBuffer.append("\t\t\t \uD83D\uDCC6 "+orderDateEntity.getDate() + "\n\n");
                }
            }

            myTelegramBot.send(SendMsg.sendMsg(message.getChatId(), String.valueOf(stringBuffer)));
        }
    }
}
