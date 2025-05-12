package com.example.client_service.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.example.client_service.model.Ticket;

@Repository
public interface TicketRepository extends JpaRepository<Ticket, Long>{
	Ticket findByPnr(String pnr);
}
