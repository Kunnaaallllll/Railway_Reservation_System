package com.example.train_service.service;

import com.example.train_service.dto.TrainDTO;
import com.example.train_service.model.Train;
import com.example.train_service.repository.TrainRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.*;
import java.util.*;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

public class TrainServiceTest {

	// @Mock is used to make dummy objects. 
	//Yeh actual database call avoid karta hai aur unit testing fast aur isolated banaata hai

    @Mock
    private TrainRepository trainRepository;

    //@InjectMocks is used in Mockito to automatically inject mock dependencies
    @InjectMocks
    private TrainService trainService;

    @BeforeEach
    public void setUp() {
    	// MockitoAnnotations.openMocks(this)  in Mockito it initializes @Mock or @InjectMocks and many other dependencies
        MockitoAnnotations.openMocks(this);
    }

    @Test
    public void testAddTrain_NewTrain_Success() {
        TrainDTO dto = new TrainDTO();
        dto.setTrainNo("123");
        dto.setName("Rajdhani");

        when(trainRepository.findByTrainNo("123")).thenReturn(null);

        String result = trainService.addTrain(dto);

        assertEquals("Train added successfully!!", result);
        verify(trainRepository, times(1)).save(any(Train.class));
    }

    @Test
    public void testAddTrain_ExistingTrain() {
        TrainDTO dto = new TrainDTO();
        dto.setTrainNo("123");

        when(trainRepository.findByTrainNo("123")).thenReturn(new Train());

        String result = trainService.addTrain(dto);

        assertEquals("Train already exists.", result);
        verify(trainRepository, never()).save(any());
    }

    @Test
    public void testDeleteTrain_TrainExists() {
        when(trainRepository.findById(1L)).thenReturn(Optional.of(new Train()));
//when we use Option.of then it promises that it is not going to return null value will return something  
        String result = trainService.deleteTrain(1L);

        assertEquals("Train Details Deleted", result);
        verify(trainRepository).deleteById(1L);
    }

    @Test
    public void testDeleteTrain_TrainDoesNotExist() {
        when(trainRepository.findById(1L)).thenReturn(Optional.empty());

        String result = trainService.deleteTrain(1L);

        assertEquals("Train does not exists.", result);
        verify(trainRepository, never()).deleteById(any());
    }

    @Test
    public void testUpdateTrainName_TrainFound() {
        Train train = new Train();
        train.setName("Old");
        List<Train> trains = Arrays.asList(train);

        when(trainRepository.findByName("Old")).thenReturn(trains);

        String result = trainService.updateTrainName("Old", "New");

        assertEquals("Train Name Updated!!", result);
        verify(trainRepository).save(train);
    }

    @Test
    public void testUpdateTrainName_TrainNotFound() {
        when(trainRepository.findByName("Old")).thenReturn(Collections.emptyList());

        String result = trainService.updateTrainName("Old", "New");

        assertEquals("No Train Found.", result);
    }

    @Test
    public void testUpdateFare_TrainExists() {
        Train train = new Train();
        when(trainRepository.findByTrainNo("123")).thenReturn(train);

        String result = trainService.updateFare("123", 500.0);

        assertEquals("Fare updated", result);
        verify(trainRepository).save(train);
    }

    @Test
    public void testUpdateFare_TrainNotFound() {
 //firstly method gets called (findByTrainNo) and return null forcefully means train is not there in the database
        when(trainRepository.findByTrainNo("123")).thenReturn(null);

        String result = trainService.updateFare("123", 500.0);

        assertEquals("Train does not exist.", result);
 //Yeh ensure karta hai ki trainRepository.save() method ek bhi baar call nahi hua, kyunki train exist nahi karti
        verify(trainRepository, never()).save(any());

    }
}