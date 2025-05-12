 package com.example.client_service.model;

import java.util.ArrayList;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonProperty;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.Data;


@Entity
public class Ticket {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;
    
    private String pnr;
    private String trainNo;
    private String date;
    
    
    
    public long getId() {
		return id;
	}



	public void setId(long id) {
		this.id = id;
	}



	public String getPnr() {
		return pnr;
	}



	public void setPnr(String pnr) {
		this.pnr = pnr;
	}



	public String getTrainNo() {
		return trainNo;
	}



	public void setTrainNo(String trainNo) {
		this.trainNo = trainNo;
	}



	public String getDate() {
		return date;
	}



	public void setDate(String date) {
		this.date = date;
	}



	public List<PassengerDetails> getPassengerDetails() {
		return passengerDetails;
	}



	public void setPassengerDetails(List<PassengerDetails> passengerDetails) {
		this.passengerDetails = passengerDetails;
	}



	@OneToMany(mappedBy = "ticket", cascade = CascadeType.ALL, orphanRemoval = true)
	// all associated PassengerDetails will be deleted when ticket get deleted

    @JsonProperty("passengers")
    private List<PassengerDetails> passengerDetails = new ArrayList<>();
}
