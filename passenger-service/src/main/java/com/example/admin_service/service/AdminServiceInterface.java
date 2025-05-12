package com.example.admin_service.service;

public interface AdminServiceInterface {

	String updateTrainName(String name, String newName);
	String updateDepartureStation(String TrainNo, String station);
	String updateArrivalStation(String TrainNo, String station);
	String updateFare(String TrainNo, double fare);
}
