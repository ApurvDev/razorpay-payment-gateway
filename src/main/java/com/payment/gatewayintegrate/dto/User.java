package com.payment.gatewayintegrate.dto;

import lombok.*;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class User {

    private String customerName;
    private String email;
    private String phoneNumber;
    private String amount;

}

