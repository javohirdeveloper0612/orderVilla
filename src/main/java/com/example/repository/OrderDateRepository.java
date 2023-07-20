package com.example.repository;

import com.example.entity.OrderDateEntity;
import com.example.entity.OrderHouseEntity;
import com.example.enums.Status;
import jakarta.transaction.Transactional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.time.LocalDate;
import java.util.List;

public interface OrderDateRepository extends JpaRepository<OrderDateEntity,Long> {

    boolean existsByDateAndStatus(LocalDate orderDate, Status status);

    List<OrderDateEntity> findAllByOrderAndStatus(OrderHouseEntity orderId,Status status);

    @Transactional
    @Modifying
    @Query("UPDATE order_date set status=?2 where id=?1")
    void changeStatus(Long id, Status status);
}
