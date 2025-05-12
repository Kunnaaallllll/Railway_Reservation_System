package com.example.client_service.service;

import java.util.List;

import com.example.client_service.model.Train;

public interface ClientServiceInterface {

	List<Train> getTrainByStartAndDestination(String start,String dest);
	List<Train> getByTrainNam(String name);
	Train getTrain(String trainNo);
}
