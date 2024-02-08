package com.payment.gatewayintegrate.dto;

import lombok.*;

@Data
//@NoArgsConstructor
//@AllArgsConstructor
@Getter
@Setter
public class RazorPay {

    private String applicationFee;

    private String razorpayOrderId;

    private String secretKey;

    private String paymentId;

    private String notes;

    private String imageURL;

    private String theme;

    private String  merchantName;

    private String purchaseDescription;

    private String customerName;

    private String customerEmail;

    private String customerContact;

    public RazorPay() {}

    public RazorPay(String applicationFee) {
        this.applicationFee = applicationFee;
    }
}
