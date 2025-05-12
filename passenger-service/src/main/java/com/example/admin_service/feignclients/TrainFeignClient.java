package com.example.admin_service.feignclients;

import java.util.List;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestParam;


@FeignClient(value = "TRAIN-SERVICE",path = "/trains")
public interface TrainFeignClient {
	
	@PutMapping("/updateTrainName/{name}")
	String updateTrainName(@PathVariable String name, @RequestParam String newName);
	
	
	@PutMapping("/updateArrival/{TrainNo}")
    public String updateArrivalStation(@PathVariable String TrainNo, @RequestParam String station);
	
	@PutMapping("/updateDeparture/{TrainNo}")
    public String updateDepartureStation(@PathVariable String TrainNo, @RequestParam String station);
	
	@PutMapping("/updateFare/{TrainNo}")
    public String updateFare(@PathVariable String TrainNo, @RequestParam double fare);
	
}
