package com.example.passenger_service.service;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import com.example.admin_service.feignclients.TrainFeignClient;
import com.example.admin_service.service.AdminService;

@ExtendWith(MockitoExtension.class)
class AdminServiceTest {

    @Mock
    private TrainFeignClient trainFeignClient;

    @InjectMocks
    private AdminService adminService;

    @BeforeEach
    void setUp() {
    }

    @Test
    void testUpdateTrainName() {
        when(trainFeignClient.updateTrainName("Rajdhani Express", "New Rajdhani"))
            .thenReturn("Train name updated successfully");

        String response = adminService.updateTrainName("Rajdhani Express", "New Rajdhani");

        assertEquals("Train name updated successfully", response);
    }

    @Test
    void testUpdateDepartureStation() {
        when(trainFeignClient.updateDepartureStation("12345", "Mumbai Central"))
            .thenReturn("Departure station updated successfully");

        String response = adminService.updateDepartureStation("12345", "Mumbai Central");

        assertEquals("Departure station updated successfully", response);
    }

    @Test
    void testUpdateArrivalStation() {
        when(trainFeignClient.updateArrivalStation("12345", "Delhi"))
            .thenReturn("Arrival station updated successfully");

        String response = adminService.updateArrivalStation("12345", "Delhi");

        assertEquals("Arrival station updated successfully", response);
    }

    @Test
    void testUpdateFare() {
        when(trainFeignClient.updateFare("12345", 1500.0))
            .thenReturn("Fare updated successfully");

        String response = adminService.updateFare("12345", 1500.0);

        assertEquals("Fare updated successfully", response);
    }
}