package com.example.repository;

import com.example.entity.OrderDateEntity;
import com.example.entity.OrderHouseEntity;
import com.example.enums.Status;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;

public interface OrderHouseRepository extends JpaRepository<OrderHouseEntity,Long> {

    @Query("SELECT oh FROM order_house oh JOIN oh.orderData od WHERE oh.chatId = :chatId AND oh.isVisible = true AND od.status = :status ORDER BY oh.createdDate DESC LIMIT 1")
    Optional<OrderHouseEntity> findLatestOrdersByChatIdAndStatus(@Param("chatId") Long chatId, @Param("status") Status status);

    @Query("SELECT od from order_house oh join oh.orderData od where oh.chatId = :chatId and oh.isVisible = true and oh.status = :status and od.status = :status order by oh.createdDate DESC")
    List<OrderDateEntity> findByChatIdLastOrderNotActive(@Param("chatId") Long chatId,@Param("status") Status status);

    @Query("SELECT oh FROM order_house oh JOIN oh.orderData od where oh.chatId = :chatId AND od.status = :status")
    Optional<OrderHouseEntity> getByChatIdAndStatus(Long chatId,Status status);

    List<OrderHouseEntity> findByChatIdAndStatus(Long chatId,Status status);



    @Query("SELECT oh FROM order_house oh JOIN oh.orderData od where oh.chatId = :chatId AND oh.status = :status ORDER BY oh.createdDate DESC limit 1")
    Optional<OrderHouseEntity> findByChatIdOrderByCreatedDate(Long chatId,Status status);

}
