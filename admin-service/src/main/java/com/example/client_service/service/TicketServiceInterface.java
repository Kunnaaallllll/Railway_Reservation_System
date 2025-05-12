package com.example.client_service.service;

import com.example.client_service.model.Ticket;

public interface TicketServiceInterface {

	String bookTicket(Ticket ticketRequest);
	String cancelTicket(String pnr);
}
