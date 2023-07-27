package com.example.entity;
import com.example.enums.Status;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import java.time.LocalDate;

@Getter
@Setter


@Table
@Entity(name = "order_date")
public class OrderDateEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column
    private LocalDate date;


    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "order_id")
    private OrderHouseEntity order;

    @Enumerated(EnumType.STRING)
    private Status status = Status.NOTACTIVE;
}
