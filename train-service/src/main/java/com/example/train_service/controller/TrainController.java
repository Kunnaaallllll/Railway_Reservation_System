package com.example.train_service.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import com.example.train_service.dto.TrainDTO;
import com.example.train_service.model.Train;
import com.example.train_service.service.ServiceInterface;
import jakarta.validation.Valid;

@RestController
@RequestMapping("/trains")

public class TrainController {
    @Autowired
    private ServiceInterface trainService;
    
    @PostMapping("/add")
    public String addTrain(@RequestBody @Valid TrainDTO trainDTO) { // validation
    	String res= trainService.addTrain(trainDTO);
    	return res;
    }
    
    @DeleteMapping("/delete/{id}")
    public String deleteTrain(@PathVariable long id) {
    	String res=trainService.deleteTrain(id);
    	return res;
    }
    
    @PutMapping("/updateTrainName/{name}")
    public String updateTrainName(@PathVariable String name, @RequestParam String newName) {
    	String res=trainService.updateTrainName(name, newName);
  
    	return res;
    }
    
    @PutMapping("/updateArrival/{TrainNo}")
    public String updateArrivalStation(@PathVariable String TrainNo, @RequestParam String station) {
    	String res=trainService.updateArrivalStation(TrainNo, station);
    	return res;
    }
    
    @PutMapping("/updateDeparture/{TrainNo}")
    public String updateDepartureStation(@PathVariable String TrainNo, @RequestParam String station) {
    	String res=trainService.updateDepartureStation(TrainNo, station);
    	return res;   
    }
    
    @PutMapping("/updateFare/{TrainNo}")
    public String updateFare(@PathVariable String TrainNo, @RequestParam double fare) {
    	String res=trainService.updateFare(TrainNo, fare);
    	return res;
    }
    
    @GetMapping("/getTrainsByStartAndDestination")
    public List<Train> getTrainByStartAndDestination(@RequestParam String start, @RequestParam String dest){
    	return trainService.getTrainByArrivalAndDestination(start, dest);
    }
    
    @GetMapping("/getByTrainName")
    public List<Train> getByTrainName(@RequestParam String trainName){
    	return trainService.getTrainByTrainName(trainName);
    }
    
    @GetMapping("/getTrain")
    public Train getTrain(@RequestParam String trainNo){
    	return trainService.getTrain(trainNo);
    }
}

