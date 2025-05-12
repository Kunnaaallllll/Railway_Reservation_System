package com.example.client_service.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.client_service.feignclients.TrainFeign;
import com.example.client_service.model.Train;

@Service
public class ClientService implements ClientServiceInterface{
	
	@Autowired
	TrainFeign trainFeign;
	
	public List<Train> getTrainByStartAndDestination(String start,String dest){
		return trainFeign.getTrainByStartAndDestination(start,dest);
	}
	
	public List<Train> getByTrainNam(String name){
		return trainFeign.getByTrainName(name);
	}
	
	public Train getTrain(String trainNo){
		return trainFeign.getTrain(trainNo);
	}
}
