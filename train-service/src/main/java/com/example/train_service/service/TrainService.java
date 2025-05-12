package com.example.train_service.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.validation.annotation.Validated;

import com.example.train_service.dto.TrainDTO;
import com.example.train_service.exception.ResourceNotFoundException;
import com.example.train_service.exceptions.TrainAlreadyExistsException;
import com.example.train_service.model.Train;
import com.example.train_service.repository.TrainRepository;
import java.util.List;

@Validated
@Service
public class TrainService implements ServiceInterface{
    @Autowired
    private TrainRepository trainRepository;
    
    public String addTrain(TrainDTO trainDTO) {
        if (trainRepository.findByTrainNo(trainDTO.getTrainNo()) == null) {
            Train train = new Train();
            train.setName(trainDTO.getName());
            train.setStartStation(trainDTO.getStartStation());
            train.setDestinationStation(trainDTO.getDestinationStation());
            train.setDepartureTime(trainDTO.getDepartureTime());
            train.setArrivalTime(trainDTO.getArrivalTime());
            train.setPrice(trainDTO.getPrice());
            train.setTrainNo(trainDTO.getTrainNo());
            trainRepository.save(train);
            return "Train added successfully!!";
        } else {
            throw new TrainAlreadyExistsException("Train with number " + trainDTO.getTrainNo() + " already exists.");
        }
    }
    
    public String deleteTrain(long id) {
        if (!trainRepository.findById(id).isPresent()) {
            throw new ResourceNotFoundException("Train with ID " + id + " does not exist.");
        }
        trainRepository.deleteById(id);
        return "Train Details Deleted";
    }
    
    public String updateTrainName(String name, String newName) {
        List<Train> list = trainRepository.findByName(name);
        if (list.isEmpty()) {
            throw new ResourceNotFoundException("No Train found with name: " + name);
        }
        for (Train t : list) {
            t.setName(newName);
            trainRepository.save(t);
        }
        return "Train Name Updated!!";
    }
    
    public String updateArrivalStation(String trainNo, String station) {
        Train train = trainRepository.findByTrainNo(trainNo);
        if (train == null) {
            throw new ResourceNotFoundException("Train with number " + trainNo + " does not exist.");
        }
        train.setStartStation(station);
        trainRepository.save(train);
        return "Start Station updated";
    }
    
    public String updateDepartureStation(String trainNo, String station) {
        Train train = trainRepository.findByTrainNo(trainNo);
        if (train == null) {
            throw new ResourceNotFoundException("Train with number " + trainNo + " does not exist.");
        }
        train.setDestinationStation(station);
        trainRepository.save(train);
        return "Destination Station updated";
    }
    
    public String updateFare(String trainNo, double fare) {
        Train train = trainRepository.findByTrainNo(trainNo);
        if (train == null) {
            throw new ResourceNotFoundException("Train with number " + trainNo + " does not exist.");
        }
        train.setPrice(fare);
        trainRepository.save(train);
        return "Fare updated";
    }
    
    public List<Train> getTrainByArrivalAndDestination(String start, String dest) {
        List<Train> list = trainRepository.findByStartStationAndDestinationStation(start, dest);
        if (list.isEmpty()) {
            throw new ResourceNotFoundException("No Trains found from " + start + " to " + dest);
        }
        return list;
    }
    
    public List<Train> getTrainByTrainName(String trainName) {
        List<Train> list = trainRepository.findByName(trainName);
        if (list.isEmpty()) {
            throw new ResourceNotFoundException("No Trains found with name: " + trainName);
        }
        return list;
    }
    
    public Train getTrain(String trainNo) {
        Train train = trainRepository.findByTrainNo(trainNo);
        if (train == null) {
            throw new ResourceNotFoundException("Train with number " + trainNo + " does not exist.");
        }
        return train;
    }
}
