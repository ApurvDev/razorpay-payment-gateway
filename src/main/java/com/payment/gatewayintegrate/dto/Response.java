package com.payment.gatewayintegrate.dto;
import lombok.*;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Setter
@Getter
public class Response {

    private int statusCode;
    private RazorPay razorPay;
}
