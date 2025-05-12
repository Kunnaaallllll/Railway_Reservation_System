package com.example.client_service.service;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.client_service.model.PassengerDetails;
import com.example.client_service.model.Ticket;
import com.example.client_service.model.TicketInfo;
import com.example.client_service.model.Train;
import com.example.client_service.repository.PassengerDetailsRepository;
import com.example.client_service.repository.TicketInfoRepository;
import com.example.client_service.repository.TicketRepository;

import jakarta.transaction.Transactional;

@Service
public class TicketService implements TicketServiceInterface{

    @Autowired
    TicketRepository ticketRepository;
    
    @Autowired
    PassengerDetailsRepository passengerDetailsRepository;
    
    @Autowired
    ClientService clientService;
    
    @Autowired
    TicketInfoRepository ticketInfoRepository;
    
    private static final int TOTAL_SEATS = 100;
    private static final int QUOTA_SEATS = 10;
    private static final int GENERAL_SEATS = TOTAL_SEATS - QUOTA_SEATS;

    public String bookTicket(Ticket ticketRequest) {
        if (ticketRequest.getPassengerDetails() == null) {
            return "Passenger details cannot be null";
        }

        int bookedGeneralSeats = ticketInfoRepository.countByIsQuotaSeat(false);
        int bookedQuotaSeats = ticketInfoRepository.countByIsQuotaSeat(true);

        if (bookedGeneralSeats >= GENERAL_SEATS && bookedQuotaSeats >= QUOTA_SEATS) {
            return "Housefull! No more tickets available.";
        }

        Train train = clientService.getTrain(ticketRequest.getTrainNo());
        List<PassengerDetails> updatedPassengerList = new ArrayList<>();

        
        ticketRequest.setPnr(generatePnr());
        ticketRepository.save(ticketRequest);

        for (PassengerDetails passenger : ticketRequest.getPassengerDetails()) {
            boolean isEligibleForQuota = passenger.getAge() >= 65;

           
            if (bookedGeneralSeats < GENERAL_SEATS) {
                bookedGeneralSeats++;
            } else if (isEligibleForQuota && bookedQuotaSeats < QUOTA_SEATS) {
                bookedQuotaSeats++;
            } else {
                return "Housefull! No more tickets available.";
            }

            int seatNo = bookedGeneralSeats + bookedQuotaSeats;
            TicketInfo newTick = new TicketInfo();
            newTick.setTrainNo(ticketRequest.getTrainNo());
            newTick.setDate(ticketRequest.getDate());
            newTick.setName(passenger.getName());
            newTick.setGender(passenger.getGender());
            newTick.setAge(passenger.getAge());
            newTick.setFare(calculateFare(passenger.getAge(), passenger.getGender(), train.getPrice(), passenger.isFood()));
            newTick.setSeatNo(seatNo);
            newTick.setQuotaSeat(isEligibleForQuota);

            passenger.setTicket(ticketRequest);
            updatedPassengerList.add(passenger);
            ticketInfoRepository.save(newTick);
            if (bookedGeneralSeats + bookedQuotaSeats >= TOTAL_SEATS) {
                break;
            }
        }

        ticketRequest.getPassengerDetails().clear();
        ticketRequest.getPassengerDetails().addAll(updatedPassengerList);

        for (PassengerDetails p : ticketRequest.getPassengerDetails()) {
            passengerDetailsRepository.save(p);
        }

        ticketRepository.save(ticketRequest);

        return "Tickets Booked!";
    }

    private double calculateFare(int age, String gender, double baseFare, boolean food) {
        if (food) {
            baseFare *= 0.2;
        }
        if (age < 15 || age > 60) {
            baseFare -= 0.1 * baseFare;
        }
        if ("Female".equalsIgnoreCase(gender)) {
            baseFare -= 0.05 * baseFare;
        }
        return baseFare;
    }
    
    @Transactional
    public String cancelTicket(String pnr) {
        Ticket ticket = ticketRepository.findByPnr(pnr);
        
        if (ticket == null) {
            return "No ticket found for the PNR " + pnr;
        }
        String date =ticket.getDate();
        ticketInfoRepository.deleteAllByDate(date);
        passengerDetailsRepository.deleteAll(ticket.getPassengerDetails());
        ticketRepository.delete(ticket);
        return "Ticket Deleted";
    }

    private String generatePnr() {
        Random rand = new Random();
        int n = 100000 + rand.nextInt(90000);
        return "PNR" + n;
    }
}