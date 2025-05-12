package com.example.client_service.controller;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;

import org.springframework.web.bind.annotation.PostMapping;

import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.example.client_service.model.Ticket;
import com.example.client_service.model.Train;

import com.example.client_service.service.ClientServiceInterface;

import com.example.client_service.service.TicketServiceInterface;

import io.github.resilience4j.circuitbreaker.annotation.CircuitBreaker;

@RestController
@RequestMapping("/client")

public class ClientController {
	@Autowired
	TicketServiceInterface ticketService;
	
	@Autowired
	ClientServiceInterface clientService;
	
	@CircuitBreaker(name="client-service", fallbackMethod="clientServiceFallbackMethod")
	@GetMapping("/getTrainsByStartAndDestination")
    public List<Train> getTrainByStartAndDestination(@RequestParam String start, @RequestParam String dest) throws Exception{
		return clientService.getTrainByStartAndDestination(start, dest);
	}
	
	public List<Train> clientServiceFallbackMethod(Exception e){
		Train train = new Train();
		train.setName("Service is not Available");
		List<Train> al = new ArrayList<>();
		al.add(train);
		return al; 
	}
	
	@CircuitBreaker(name="client-service", fallbackMethod="clientServiceFallbackMethod")
	@GetMapping("/getByTrainName")
    public List<Train> getByTrainName(@RequestParam String trainName){
		return clientService.getByTrainNam(trainName);
	}
	
	@CircuitBreaker(name="client-service", fallbackMethod="getTrainFalbackMethod")
	@GetMapping("/getTrain")
    public Train getTrain(@RequestParam String trainNo) throws Exception {
		return clientService.getTrain(trainNo);
    }
	public Train getTrainFalbackMethod(Exception e) {
		return new Train();
	}
	
    @PostMapping("/bookTicket")
    public String bookTicket(@RequestBody Ticket TicketRequest) {
    	String res = ticketService.bookTicket(TicketRequest);
    	return res;
    }
    
    @DeleteMapping("/cancelTicket")
    public String cancelTicket(@RequestParam String pnr) {
    	String res = ticketService.cancelTicket(pnr);
    	return res;
    }
        
}
