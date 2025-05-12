package com.example.train_service.controller;

import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import java.util.Arrays;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import com.example.train_service.dto.TrainDTO;
import com.example.train_service.model.Train;
import com.example.train_service.service.TrainService;
import com.fasterxml.jackson.databind.ObjectMapper;

@ExtendWith(MockitoExtension.class)
//enables integration of mokito framework

public class TrainControllerTest {

    private MockMvc mockMvc;
    //it is a spring tool used to test web controllers of spring 
    //Yeh bina server start kiye controller endpoints ko simulate karta hai aur HTTP requests send karta hai

    @Mock  //creates fake version  
    private TrainService trainService;

    @InjectMocks  // Yeh mocked dependencies ko automatically inject karta hai
    private TrainController trainController; 

    private TrainDTO trainDTO;
    private Train train;

    @BeforeEach//maintains code cleanness we dont have to initiallise all the things in another tests 
    void setUp() {
        mockMvc = MockMvcBuilders.standaloneSetup(trainController).build();

        trainDTO = new TrainDTO();
        trainDTO.setName("Rajdhani Express");
        trainDTO.setTrainNo("12345");
        trainDTO.setStartStation("Mumbai");
        trainDTO.setDestinationStation("Delhi");
        trainDTO.setDepartureTime("06:00");
        trainDTO.setArrivalTime("18:00");
        trainDTO.setPrice(1200.0);

        train = new Train();
        train.setId(1L);  
        train.setName(trainDTO.getName());
        train.setTrainNo(trainDTO.getTrainNo());
        train.setStartStation(trainDTO.getStartStation());
        train.setDestinationStation(trainDTO.getDestinationStation());
        train.setDepartureTime(trainDTO.getDepartureTime());
        train.setArrivalTime(trainDTO.getArrivalTime());
        train.setPrice(trainDTO.getPrice());
    }

    @Test
    void testAddTrain() throws Exception {
        when(trainService.addTrain(any(TrainDTO.class))).thenReturn("Train added successfully");

        mockMvc.perform(post("/trains/add")
                .contentType("application/json")
                .content(new ObjectMapper().writeValueAsString(trainDTO)))
                .andExpect(status().isOk())
                .andExpect(content().string("Train added successfully"));
    }

    @Test
    void testDeleteTrain() throws Exception {
        when(trainService.deleteTrain(12345L)).thenReturn("Train deleted successfully");

        mockMvc.perform(delete("/trains/delete/12345"))
                .andExpect(status().isOk())
                .andExpect(content().string("Train deleted successfully"));
    }

    @Test
    void testGetTrainByName() throws Exception {
        when(trainService.getTrainByTrainName("Rajdhani Express")).thenReturn(Arrays.asList(train));

        mockMvc.perform(get("/trains/getByTrainName")
                .param("trainName", "Rajdhani Express"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].name").value("Rajdhani Express"));
    }
}