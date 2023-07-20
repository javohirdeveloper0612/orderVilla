package com.example.controller;

import com.example.entity.OrderDateEntity;
import com.example.entity.OrderHouseEntity;
import com.example.enums.Status;
import com.example.myTelegramBot.MyTelegramBot;
import com.example.repository.OrderDateRepository;
import com.example.repository.OrderHouseRepository;
import com.example.util.Button;
import com.example.util.ButtonName;
import com.example.util.InlineButton;
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

    public void handle(Long chatId){
        List<OrderHouseEntity> list = repository.findByChatIdAndStatus(chatId, Status.ACTIVE);
        if (list.isEmpty()){
            myTelegramBot.send(SendMsg.sendMsg(chatId,"Заказов не найдено \uD83E\uDD37\u200D♂\uFE0F"));
        }

        StringBuffer stringBuffer = new StringBuffer();

        for (OrderHouseEntity orderHouseEntity : list) {
            stringBuffer.append("\uD83D\uDC64 Заказчик: ").append(orderHouseEntity.getFullName());
            stringBuffer.append("\n☎\uFE0F Номер телефона: ").append(orderHouseEntity.getPhone());
            stringBuffer.append("\n\uD83D\uDCC5 Дата заказа: ").append(orderHouseEntity.getCreatedDate());
            stringBuffer.append("\n\uD83D\uDCB0 Стоимость: ").append(orderHouseEntity.getAmount());
            stringBuffer.append("\n\n\uD83D\uDDD3\uFE0F Список заказанных дней: \n");
            List<OrderDateEntity> dateList = orderDateRepository.findAllByOrderAndStatus(orderHouseEntity,Status.ACTIVE);
            if (dateList != null){
                stringBuffer.append("-------------------------------\n");
                for (OrderDateEntity orderDateEntity : dateList) {
                        stringBuffer.append("|\t\t\t \uD83D\uDCC6 ").append(orderDateEntity.getDate()).append("\t\t\t\t|\n");
                }
                stringBuffer.append("--------------------------------\n\n");
            }
            stringBuffer.append("\t\t\t\t\uD83D\uDCCB Ваши заказы");

            myTelegramBot.send(SendMsg.sendMsg(chatId, String.valueOf(stringBuffer),
                    Button.markup(
                            Button.rowList(
                                    Button.row(
                                            Button.button(ButtonName.backMainMenu)
                                    )
                            )
                    )));
        }
    }
}
