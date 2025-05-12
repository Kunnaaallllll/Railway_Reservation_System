package com.example.payment_service.controller;

import java.util.HashMap;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.example.payment_service.model.PaymentInfo;
import com.example.payment_service.service.RazorPayService;
import com.razorpay.RazorpayException;

@RestController
@RequestMapping("/payments")
public class PaymentController {
    
    @Autowired
    RazorPayService razorPayService;
    
    @PostMapping("/create-order")
    public String createOrder(@RequestBody Map<String, Object> requestData) throws RazorpayException {
        String orderDetails = razorPayService.createOrder(requestData);
        System.out.println("Order Response: " + orderDetails); 
        return orderDetails;
    }
    
    @PostMapping("/payment-status")
    public PaymentInfo logPaymentStatus(@RequestBody Map<String, String> paymentStatus) {
        System.out.println("⚡ Payment Status Received: " + paymentStatus);
        return razorPayService.setStatus(paymentStatus.get("payment_id"), paymentStatus.get("status"));
    }
    
    @GetMapping("/get-amount")
    public Map<String, Object> getAmount() {
        Map<String, Object> response = new HashMap<>();
        response.put("amount", razorPayService.getFinalAmount()); 
        return response;
    }
    
    @GetMapping("/getPayment")
    public PaymentInfo getPayment(@RequestParam String paymentId) {
    	return razorPayService.getPayment(paymentId);
    }
}