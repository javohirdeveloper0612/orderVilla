package com.example.payme.dto.result;

import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public class CancelTransactionResult {
    private String transaction;
    private long cancel_time;
    private Integer state;
}
