package com.example.repository;

import com.example.entity.OrderHouseEntity;
import com.example.enums.Status;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;

public interface OrderHouseRepository extends JpaRepository<OrderHouseEntity,Long> {

    @Query("SELECT oh FROM order_house oh JOIN oh.orderData od WHERE oh.chatId = :chatId AND oh.isVisible = true AND od.status = :status ORDER BY od.date DESC LIMIT 1")
    Optional<OrderHouseEntity> findLatestOrdersByChatIdAndStatus(@Param("chatId") Long chatId, @Param("status") Status status);

    @Query("SELECT oh FROM order_house oh JOIN oh.orderData od where oh.chatId = :chatId AND od.status = :status")
    Optional<OrderHouseEntity> findByChatIdAndStatus(Long chatId,Status status);


    @Query("SELECT oh FROM order_house oh JOIN oh.orderData od where oh.chatId = :chatId AND od.status = :status ORDER BY oh.createdDate DESC limit 1")
    Optional<OrderHouseEntity> findByChatIdOrderByCreatedDate(Long chatId,Status status);

}
