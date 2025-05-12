package com.example.train_service.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.example.train_service.model.Train;
import java.util.List;

@Repository
public interface TrainRepository extends JpaRepository<Train, Long> {
	List<Train> findByName(String name);
	Train findByTrainNo(String trainNo);
	List<Train> findByStartStationAndDestinationStation(String startStation, String destinationStation);
}

