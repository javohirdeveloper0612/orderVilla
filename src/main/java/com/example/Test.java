package com.example;

import com.example.modul.CustomMap;
import com.example.util.SendMsg;

import java.util.HashMap;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Test {



    public static boolean checkPhone(String phone) {
        if (!phone.matches("^\\+998[01349]\\d{7}$")) {
            return false;
        }
        return true;
    }


        public static void main(String[] args) {
            String phoneNumber = "+998901234567";
            System.out.println(checkPhone(phoneNumber)); // Output: true
        }

}
