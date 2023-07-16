package com.example.repository;

import com.example.entity.OrderDateEntity;
import com.example.enums.Status;
import org.springframework.data.jpa.repository.JpaRepository;

import java.time.LocalDate;

public interface OrderDateRepository extends JpaRepository<OrderDateEntity,Long> {

    boolean existsByDateAndStatus(LocalDate orderDate, Status status);

}
