package com.payment.gatewayintegrate.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class Order {

    private String id;
    private int amount;
    private String currency;
    private String receipt;


    @Override
    public String toString() {
        return "Order{" +
                "id='" + id + '\'' +
                ", amount=" + amount +
                ", currency='" + currency + '\'' +
                ", receipt='" + receipt + '\'' +
                '}';
    }
}
