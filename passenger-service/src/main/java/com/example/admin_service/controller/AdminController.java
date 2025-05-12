package com.example.admin_service.controller;

import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.example.admin_service.service.AdminService;
import com.example.admin_service.service.AdminServiceInterface;


@RestController
@RequestMapping("/admin")

public class AdminController {
	
	@Autowired
	AdminServiceInterface adminService;
	
	@PutMapping("/updateTrainName/{name}")
	public String updateTrainName(@PathVariable String name,@RequestParam String newName) throws Exception {
		String response = adminService.updateTrainName(name, newName);
		return response;
	}
	
	@PutMapping("/updateArrival/{TrainNo}")
    public String updateArrivalStation(@PathVariable String TrainNo, @RequestParam String station)throws Exception {
    	String res=adminService.updateArrivalStation(TrainNo, station);
    	return res;
    }
    @PutMapping("/updateDeparture/{TrainNo}")
    public String updateDepartureStation(@PathVariable String TrainNo, @RequestParam String station)throws Exception {
    	String res=adminService.updateDepartureStation(TrainNo, station);
    	return res;
    }
    @PutMapping("/updateFare/{TrainNo}")
    public String updateFare(@PathVariable String TrainNo, @RequestParam double fare) throws Exception{
    	String res=adminService.updateFare(TrainNo, fare);
    	return res;
    }
}
