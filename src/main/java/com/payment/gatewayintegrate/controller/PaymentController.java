package com.payment.gatewayintegrate.controller;

import lombok.extern.slf4j.Slf4j;
import org.json.JSONObject;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import com.razorpay.*;

import java.util.Map;

@Slf4j
@Controller
@RequestMapping("/user")
public class PaymentController {

//checking
    @RequestMapping(value = "/")
    public String getHome() {
        return "redirect:/home";
    }

    @RequestMapping(value = "/home")
    public String getHomeInit() {
        return "home";
    }

    @PostMapping("/create_order")
    @ResponseBody
    public String createOrder(@RequestBody Map<String, Object> data) throws RazorpayException {
        log.info("Data = {}", data);

        int amt = Integer.parseInt(data.get("amount").toString());

        RazorpayClient client = new RazorpayClient("rzp_test_2pXfcejfFOKN7P", "RNPUBSG6sO1kf92rDsjBWNgD");

        JSONObject obj = new JSONObject();
        obj.put("amount", amt * 100);
        obj.put("currency", "INR");
        obj.put("receipt", "txn7689_" + System.currentTimeMillis());

        Order order = client.orders.create(obj);
        log.info("Order = {}", order);
        return order.toString();

    }
}
