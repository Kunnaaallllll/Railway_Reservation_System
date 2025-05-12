package com.example.payment_service.service;

import java.util.Map;

import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RequestBody;

import com.example.payment_service.model.PaymentInfo;
import com.example.payment_service.repository.PaymentInfoRepository;
import com.razorpay.Order;
import com.razorpay.RazorpayClient;
import com.razorpay.RazorpayException;

@Service
public class RazorPayService {
    
    @Value("${razorpay.api.key}")
    private String apiKey;
    
    @Value("${razorpay.api.secret}")
    private String apiSecret;
    
    @Autowired
    PaymentInfoRepository paymentInfoRepository;
    private int finalAmount;
    
    public int getFinalAmount() {
		return finalAmount;
	}

	public void setFinalAmount(int finalAmount) {
		this.finalAmount = finalAmount;
	}

	int amount;
	
	public String createOrder(@RequestBody Map<String, Object> requestData) throws RazorpayException {
	    if (!requestData.containsKey("amount") || !requestData.containsKey("currency")) {
	        throw new IllegalArgumentException("Invalid Request Format");
	    }

	    int amount = (Integer) requestData.get("amount");
	    String currency = (String) requestData.get("currency");

	    System.out.println("Received Amount: " + amount);
	    finalAmount = amount * 100;
	    RazorpayClient razorPayClient = new RazorpayClient(apiKey, apiSecret);
	    JSONObject orderRequest = new JSONObject();
	    orderRequest.put("amount", amount);
	    orderRequest.put("currency", currency);
	    orderRequest.put("receipt", "receipt#1");

	    Order order = razorPayClient.orders.create(orderRequest);

	    return order.toString();
	}
    
    public PaymentInfo setStatus(String paymentId,String status) {
    	PaymentInfo payment = new PaymentInfo();
    	payment.setAmount(finalAmount/100);
    	payment.setOrderStatus(status);
    	payment.setPaymentId(paymentId);
    	paymentInfoRepository.save(payment);
    	return payment;
    }
    
    public PaymentInfo getPayment(String paymentId) {
    	return paymentInfoRepository.getByPaymentId(paymentId);
    }
}