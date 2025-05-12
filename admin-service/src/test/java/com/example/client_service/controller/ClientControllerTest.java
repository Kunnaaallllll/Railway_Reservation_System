package com.example.client_service.controller;

import static org.mockito.Mockito.*;
import static org.junit.jupiter.api.Assertions.*;

import java.util.Arrays;
import java.util.List;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import com.example.client_service.model.Train;
import com.example.client_service.controller.ClientController;
import com.example.client_service.model.Ticket;
import com.example.client_service.service.ClientServiceInterface;
import com.example.client_service.service.TicketServiceInterface;

@ExtendWith(SpringExtension.class)
@WebMvcTest(ClientController.class)
public class ClientControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private TicketServiceInterface ticketService;

    @MockBean
    private ClientServiceInterface clientService;

    @Test
    public void testGetTrainByStartAndDestination() throws Exception {
        Train train = new Train();
        train.setName("Express Train");
        List<Train> trainList = Arrays.asList(train);

        when(clientService.getTrainByStartAndDestination("Mumbai", "Delhi")).thenReturn(trainList);

        mockMvc.perform(MockMvcRequestBuilders.get("/client/getTrainsByStartAndDestination")
                .param("start", "Mumbai")
                .param("dest", "Delhi")
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$[0].name").value("Express Train"));
    }

    @Test
    public void testGetByTrainName() throws Exception {
        Train train = new Train();
        train.setName("Shatabdi Express");
        List<Train> trainList = Arrays.asList(train);

        when(clientService.getByTrainNam("Shatabdi Express")).thenReturn(trainList);

        mockMvc.perform(MockMvcRequestBuilders.get("/client/getByTrainName")
                .param("trainName", "Shatabdi Express")
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$[0].name").value("Shatabdi Express"));
    }

    @Test
    public void testGetTrain() throws Exception {
        Train train = new Train();
        train.setName("Rajdhani Express");

        when(clientService.getTrain("12345")).thenReturn(train);

        mockMvc.perform(MockMvcRequestBuilders.get("/client/getTrain")
                .param("trainNo", "12345")
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.name").value("Rajdhani Express"));
    }

    @Test
    public void testBookTicket() throws Exception {
        when(ticketService.bookTicket(any())).thenReturn("Tickets Booked!");

        mockMvc.perform(MockMvcRequestBuilders.post("/client/bookTicket")
                .contentType(MediaType.APPLICATION_JSON)
                .content("{\"trainNo\":\"12345\", \"passengerDetails\":[]}"))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.content().string("Tickets Booked!"));
    }

    @Test
    public void testCancelTicket() throws Exception {
        when(ticketService.cancelTicket("PNR12345")).thenReturn("Ticket Deleted");

        mockMvc.perform(MockMvcRequestBuilders.delete("/client/cancelTicket")
                .param("pnr", "PNR12345")
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.content().string("Ticket Deleted"));
    }
}