package com.example.client_service.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.example.client_service.model.TicketInfo;

public interface TicketInfoRepository extends JpaRepository<TicketInfo, Long>{

	int countByIsQuotaSeat(boolean isQuotaSeat);
	void deleteAllByDate(String date);
}
