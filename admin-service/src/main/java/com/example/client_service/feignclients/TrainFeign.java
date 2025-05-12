package com.example.client_service.feignclients;

import java.util.List;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.example.client_service.model.Train;


@FeignClient(value = "TRAIN-SERVICE",path = "/trains")
public interface TrainFeign {
	
	@GetMapping("/getTrainsByStartAndDestination")
    public List<Train> getTrainByStartAndDestination(@RequestParam String start, @RequestParam String dest);
    
    @GetMapping("/getByTrainName")
    public List<Train> getByTrainName(@RequestParam String trainName);
    
    @GetMapping("/getTrain")
    public Train getTrain(@RequestParam String trainNo);
}
