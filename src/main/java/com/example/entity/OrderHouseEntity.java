package com.example.entity;

import com.example.enums.Payment;
import com.example.enums.Status;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

@Getter
@Setter

@Table
@Entity(name = "order_house")
public class OrderHouseEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column
    private Long chatId;

    @Column
    private String fullName;

    @Column
    private String phone;

    @Column
    private Long amount;

    @Column
    private String smsCode;

    @Column
    @Enumerated(EnumType.STRING)
    private Status status;


    @OneToMany(mappedBy = "order", orphanRemoval = true, cascade = CascadeType.ALL)
    private List<OrderDateEntity> orderData;

    @Enumerated(EnumType.STRING)
    private Payment payment;


    @Column(name = "is_visible")
    private boolean isVisible = true;

    @Column
    private LocalDateTime createdDate = LocalDateTime.now();

}
