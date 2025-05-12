package com.example.admin_service.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.admin_service.feignclients.TrainFeignClient;

@Service
public class AdminService implements AdminServiceInterface{
	@Autowired
	TrainFeignClient trainFeignClient;
	
	public String updateTrainName(String name, String newName) {
		String res=trainFeignClient.updateTrainName(name,newName);
		return res;
	}
	
	public String updateDepartureStation(String TrainNo, String station) {
		String res=trainFeignClient.updateDepartureStation(TrainNo, station);
		return res;
	}
	
	public String updateArrivalStation(String TrainNo, String station) {
		String res=trainFeignClient.updateArrivalStation(TrainNo, station);
		return res;
	}
	
	public String updateFare(String TrainNo, double fare) {
		String res=trainFeignClient.updateFare(TrainNo,fare);
		return res;
	}
	
}
