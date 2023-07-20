package com.example.service;

import com.example.entity.OrderDateEntity;
import com.example.entity.OrderHouseEntity;
import com.example.enums.Status;
import com.example.myTelegramBot.MyTelegramBot;
import com.example.repository.OrderDateRepository;
import com.example.repository.OrderHouseRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.time.LocalDate;
import java.util.List;

@Component
@RequiredArgsConstructor
public class ChangeStatusOrder {
    private final OrderHouseRepository repository;
    private final OrderDateRepository orderDateRepository;

    public void handle() {
        List<OrderHouseEntity> houseEntities = repository.findAllByStatusAndDate(Status.ACTIVE, LocalDate.now().minusDays(1));
        if (houseEntities.isEmpty()) {
            return;
        }
        for (OrderHouseEntity houseEntity : houseEntities) {

            List<OrderDateEntity> orderDateEntities = orderDateRepository.findAllByOrderAndStatus(houseEntity,Status.ACTIVE);
            if (orderDateEntities.isEmpty()) {
                houseEntity.setStatus(Status.FINISHED);
                repository.save(houseEntity);
            }
            for (OrderDateEntity orderDateEntity : orderDateEntities) {
                if (orderDateEntity.getDate().isBefore(LocalDate.now())) {
                    orderDateEntity.setStatus(Status.FINISHED);
                    orderDateRepository.save(orderDateEntity);
                }
            }
        }
    }
}
