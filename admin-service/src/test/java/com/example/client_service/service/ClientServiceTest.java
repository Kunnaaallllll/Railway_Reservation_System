package com.example.client_service.service;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

import java.util.Arrays;
import java.util.List;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import com.example.client_service.feignclients.TrainFeign;
import com.example.client_service.model.Train;

@ExtendWith(MockitoExtension.class)
class ClientServiceTest {

    @Mock
    private TrainFeign trainFeign;

    @InjectMocks
    private ClientService clientService;

    private Train mockTrain1;
    private Train mockTrain2;

    @BeforeEach
    void setUp() {
        mockTrain1 = new Train();
        mockTrain1.setName("Rajdhani Express");

        mockTrain2 = new Train();
        mockTrain2.setName("Shatabdi Express");
    }

    @Test
    void testGetTrainByStartAndDestination() {
        when(trainFeign.getTrainByStartAndDestination("Mumbai", "Delhi"))
            .thenReturn(Arrays.asList(mockTrain1, mockTrain2));

        List<Train> result = clientService.getTrainByStartAndDestination("Mumbai", "Delhi");

        assertNotNull(result);
        assertEquals(2, result.size());
        assertEquals("Rajdhani Express", result.get(0).getName());
        assertEquals("Shatabdi Express", result.get(1).getName());
    }

    @Test
    void testGetByTrainName() {
        when(trainFeign.getByTrainName("Rajdhani Express"))
            .thenReturn(Arrays.asList(mockTrain1));

        List<Train> result = clientService.getByTrainNam("Rajdhani Express");

        assertNotNull(result);
        assertEquals(1, result.size());
        assertEquals("Rajdhani Express", result.get(0).getName());
    }

    @Test
    void testGetTrain() {
        when(trainFeign.getTrain("12345")).thenReturn(mockTrain1);

        Train result = clientService.getTrain("12345");

        assertNotNull(result);
        assertEquals("Rajdhani Express", result.getName());
    }
}