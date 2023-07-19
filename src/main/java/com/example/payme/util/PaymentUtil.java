package com.example.payme.util;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.util.Base64;

@Service
public class PaymentUtil {
    @Value("${paycom.checkout.url}")
    private String checkoutUrl;

    @Value("${paycom.merchant.id}")
    private String merchantId;

    @Value("${bot.url}")
    private String botUrl;

    public String generatePaymentUrl(Long orderId,String phone, long amount) {
        String str = String.format("m=%s;ac.order_id=%s;ac.key=%s;a=%s;c=%s", merchantId, orderId,phone, amount, botUrl);
        String s = Base64.getEncoder().encodeToString(str.getBytes());
        return checkoutUrl + s;
    }
}
