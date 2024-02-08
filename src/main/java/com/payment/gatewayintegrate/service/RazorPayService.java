package com.payment.gatewayintegrate.service;

import com.google.gson.Gson;
import com.payment.gatewayintegrate.dto.RazorPay;
import com.payment.gatewayintegrate.dto.Response;
import com.payment.gatewayintegrate.dto.User;
import com.razorpay.Customer;
import com.razorpay.Order;
import com.razorpay.RazorpayClient;
import com.razorpay.RazorpayException;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.math.RoundingMode;

@Service
public class RazorPayService {

    @Value("${razorpay.api.key}")
    private static String apiKey;

    @Value("${razorpay.api.secret}")
    private static String apiSecret;

    private RazorpayClient client;
    private static final Gson gson = new Gson();

//    public RazorPayService() throws RazorpayException {
//        this.client =  new RazorpayClient(apiKey, apiSecret);
//    }

    public RazorpayClient getRazorpayClient() throws RazorpayException {
        return new RazorpayClient(apiKey, apiSecret);
    }

//    public String getKeyId() {
//        return apiKey; //
//    }

//    public String createOrder(User user) {
//        try {
//            Order order = createRazorPayOrder(user.getAmount());
//            RazorPay razorPay = getRazorPay(order.get("id"), user);
//
//            Response response = new Response();
//            response.setStatusCode(200);
//            response.setRazorPay(razorPay);
//
//            return String.valueOf((gson.toJson(response)));
//        } catch (RazorpayException e) {
//            e.printStackTrace();
//            return String.valueOf((gson.toJson(getResponse(new RazorPay(), 500))));
//        }
//    }

    public Response createOrder(User user) {
        try {
            Order order = createRazorPayOrder(user.getAmount());
            RazorPay razorPay = getRazorPay(order.get("id"), user);
            return new Response(HttpStatus.OK.value(), razorPay);
        } catch (RazorpayException e) {
            e.printStackTrace();
            return new Response(HttpStatus.EXPECTATION_FAILED.value(), new RazorPay());
        }
    }

    private Response getResponse(RazorPay razorPay, int statusCode) {
        Response response = new Response();
        response.setStatusCode(statusCode);
        response.setRazorPay(razorPay);
        return response;
    }

    private RazorPay getRazorPay(String orderId, User user) {
        RazorPay razorPay = new RazorPay();
        razorPay.setApplicationFee(convertRupeeToPaise(user.getAmount()));
        razorPay.setCustomerName(user.getCustomerName());
        razorPay.setCustomerEmail(user.getEmail());
        razorPay.setMerchantName("TechQWare");
        razorPay.setPurchaseDescription("%SALE%");
        razorPay.setRazorpayOrderId(orderId);
        razorPay.setSecretKey(apiKey);
        razorPay.setImageURL("/logo");
        razorPay.setTheme("#F37254");
        razorPay.setNotes("notes" + orderId);

        return razorPay;
    }

    private Order createRazorPayOrder(String amount) throws RazorpayException {
        JSONObject options = new JSONObject();
        options.put("amount", convertRupeeToPaise(amount));
        options.put("currency", "INR");
        options.put("receipt", "order_" + System.currentTimeMillis());
//        options.put("receipt", "txn_123456");
        options.put("payment_capture", 1);

        return client.orders.create(options);
    }

    private String convertRupeeToPaise(String paise) {
        BigDecimal b = new BigDecimal(paise);
        BigDecimal value = b.multiply(new BigDecimal("100"));
        return value.setScale(0, RoundingMode.UP).toString();
    }

//    public Order createOrder(int amount) throws RazorpayException {
//
//        JSONObject options = new JSONObject();
//        options.put("amount", amount * 100);
//        options.put("currency", "INR");
//        options.put("receipt", "order_" + System.currentTimeMillis());
//
//        RazorpayClient razorpayClient = getRazorpayClient();
//        return razorpayClient.orders.create(options);
//    }

}
