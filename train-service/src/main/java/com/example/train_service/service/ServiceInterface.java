package com.example.train_service.service;

import java.util.List;

import com.example.train_service.dto.TrainDTO;
import com.example.train_service.model.Train;

public interface ServiceInterface {
	String addTrain(TrainDTO trainDTO);
	String deleteTrain(long id);
	String updateTrainName(String name, String newName);
	String updateArrivalStation(String trainNo, String station);
	String updateDepartureStation(String trainNo, String station);
	String updateFare(String trainNo, double fare);
	List<Train> getTrainByArrivalAndDestination(String start, String dest);
	List<Train> getTrainByTrainName(String trainName);
	Train getTrain(String trainNo);
}
