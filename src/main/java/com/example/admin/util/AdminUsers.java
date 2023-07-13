package com.example.admin.util;
import lombok.Data;

@Data
public class AdminUsers {


    private Long chatId;

    private AdminStep adminStep =AdminStep.NONE;

}
