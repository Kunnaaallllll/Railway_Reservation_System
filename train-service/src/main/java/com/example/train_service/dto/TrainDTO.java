package com.example.train_service.dto;

import jakarta.validation.constraints.*;
import lombok.Getter;
import lombok.Setter;


public class TrainDTO {
    
    @NotBlank(message = "Train name cannot be empty")
    private String name;

    @NotBlank(message = "Start station cannot be empty")
    private String startStation;

    @NotBlank(message = "Destination station cannot be empty")
    private String destinationStation;

    private String departureTime;

    private String arrivalTime;

    @Positive(message = "Price must be a positive value")
    private Double price;

    @NotBlank(message = "Train number cannot be empty")
    @Pattern(regexp = "\\d{3,5}", message = "Train number must be between 3 to 5 digits")
    private String trainNo;
    
    
    
    
	public TrainDTO(String name,String startStation,String destinationStation,String departureTime,String arrivalTime,Double price,String trainNo) {
		this.name = name;
		this.startStation = startStation;
		this.destinationStation = destinationStation;
		this.departureTime = departureTime;
		this.arrivalTime = arrivalTime;
		this.price = price;
		this.trainNo = trainNo;
	}
	
	public TrainDTO() {
		super();
	}





	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getStartStation() {
		return startStation;
	}
	public void setStartStation(String startStation) {
		this.startStation = startStation;
	}
	public String getDestinationStation() {
		return destinationStation;
	}
	public void setDestinationStation(String destinationStation) {
		this.destinationStation = destinationStation;
	}
	public String getDepartureTime() {
		return departureTime;
	}
	public void setDepartureTime(String departureTime) {
		this.departureTime = departureTime;
	}
	public String getArrivalTime() {
		return arrivalTime;
	}
	public void setArrivalTime(String arrivalTime) {
		this.arrivalTime = arrivalTime;
	}
	public Double getPrice() {
		return price;
	}
	public void setPrice(Double price) {
		this.price = price;
	}
	public String getTrainNo() {
		return trainNo;
	}
	public void setTrainNo(String trainNo) {
		this.trainNo = trainNo;
	}

    
}

