package com.example.modul;

public class Apartment {
    private static final Long pricePerDay = 250l; // kundagi narxi
    private static final Long weeklyDiscount = 1500l; // haftalik chegirma
    private static final Long monthlyDiscount = 4000l; // oylik chegirma

    public static Long calculatePrice(int days) {
        Long totalPrice = days * pricePerDay;
        if (days >= 7 && days < 30) {
            totalPrice -= weeklyDiscount;
        } else if (days >= 30) {
            totalPrice -= monthlyDiscount;
        }
        return totalPrice;
    }
}
