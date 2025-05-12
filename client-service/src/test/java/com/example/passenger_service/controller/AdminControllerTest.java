package com.example.passenger_service.controller;


import static org.mockito.Mockito.*;
import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import com.example.admin_service.controller.AdminController;
import com.example.admin_service.service.AdminServiceInterface;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@ExtendWith(MockitoExtension.class)
class AdminControllerTest {

    @Mock
    private AdminServiceInterface adminService;

    @InjectMocks
    private AdminController adminController;

    private MockMvc mockMvc;

    @Test
    void testUpdateTrainName() throws Exception {
        mockMvc = MockMvcBuilders.standaloneSetup(adminController).build();

        when(adminService.updateTrainName("Rajdhani Express", "New Rajdhani"))
            .thenReturn("Train name updated successfully");

        mockMvc.perform(put("/admin/updateTrainName/Rajdhani Express")
                .param("newName", "New Rajdhani")
                .contentType(MediaType.APPLICATION_JSON))//request ka body JSON format mein hoga
            .andExpect(status().isOk())
            .andExpect(content().string("Train name updated successfully"));
    }

    @Test
    void testUpdateDepartureStation() throws Exception {
        mockMvc = MockMvcBuilders.standaloneSetup(adminController).build();

        when(adminService.updateDepartureStation("12345", "Mumbai Central"))
            .thenReturn("Departure station updated successfully");

        mockMvc.perform(put("/admin/updateDeparture/12345")
                .param("station", "Mumbai Central")
                .contentType(MediaType.APPLICATION_JSON))
            .andExpect(status().isOk())
            .andExpect(content().string("Departure station updated successfully"));
    }

    @Test
    void testUpdateArrivalStation() throws Exception {
        mockMvc = MockMvcBuilders.standaloneSetup(adminController).build();

        when(adminService.updateArrivalStation("12345", "Delhi"))
            .thenReturn("Arrival station updated successfully");

        mockMvc.perform(put("/admin/updateArrival/12345")
                .param("station", "Delhi")
                .contentType(MediaType.APPLICATION_JSON))
            .andExpect(status().isOk())
            .andExpect(content().string("Arrival station updated successfully"));
    }

    @Test
    void testUpdateFare() throws Exception {
        mockMvc = MockMvcBuilders.standaloneSetup(adminController).build();

        when(adminService.updateFare("12345", 1500.0))
            .thenReturn("Fare updated successfully");

        mockMvc.perform(put("/admin/updateFare/12345")
                .param("fare", "1500.0")
                .contentType(MediaType.APPLICATION_JSON))
            .andExpect(status().isOk())
            .andExpect(content().string("Fare updated successfully"));
    }
}