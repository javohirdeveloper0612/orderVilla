package com.example.util;

import lombok.Data;

import java.time.LocalDate;
import java.util.List;

@Data
public class TelegramUsers {


    private Long chatId;

    private Step step =Step.NONE;

    private List<LocalDate> localDates;

}
