package ru.marinin.IdeaPlatformTestTask;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import ru.marinin.IdeaPlatformTestTask.models.Carrier;
import ru.marinin.IdeaPlatformTestTask.models.Ticket;
import ru.marinin.IdeaPlatformTestTask.repository.CarrierRepository;
import ru.marinin.IdeaPlatformTestTask.repository.TicketRepository;
import ru.marinin.IdeaPlatformTestTask.services.TicketService;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;


public class TicketServiceTest {
    @Autowired
    TicketRepository ticketRepository;
    @Autowired
    CarrierRepository carrierRepository;
    private final TicketService ticketService = new TicketService(ticketRepository, carrierRepository);

    Ticket ticket1 = new Ticket(Long.valueOf(1), "VVO", "Владивосток", "TLV", "Тель-Авив",
            LocalDate.of(2012, 05, 18),
            LocalTime.of(16, 20),
            LocalDate.of(2012, 05, 18),
            LocalTime.of(22, 10),
            "TK", 3, 12400);

    Ticket ticket2 = new Ticket(Long.valueOf(2), "VVO", "Владивосток", "TLV", "Тель-Авив",
            LocalDate.of(2012, 05, 18),
            LocalTime.of(12, 10),
            LocalDate.of(2012, 05, 18),
            LocalTime.of(18, 10),
            "TK", 0, 15300);

    Ticket ticket3 = new Ticket(Long.valueOf(3), "VVO", "Владивосток", "TLV", "Тель-Авив",
            LocalDate.of(2012, 05, 18),
            LocalTime.of(17, 20),
            LocalDate.of(2012, 05, 18),
            LocalTime.of(23, 50),
            "TK", 1, 13100);


    @Test
    @DisplayName("median an even array")
    void getMedianEven() {
        int[] testInt = new int[]{8, 10, 12};
        int median = ticketService.findMedian(testInt);
        Assertions.assertEquals(median, 10);
    }

    @Test
    @DisplayName("median not an even array")
    void getMedianNotEven() {
        int[] testInt = new int[]{8, 10, 12, 14};
        int median = ticketService.findMedian(testInt);
        Assertions.assertEquals(median, 11);
    }

    @Test
    @DisplayName("min time for every carrier")
    void testMinTime() {
        List<Ticket> ticketList = new ArrayList<>();
        ticketList.add(ticket1);
        ticketList.add(ticket2);
        ticketList.add(ticket3);
        String rez = ticketService.findMinTime(ticketList);
        String currRez = "TK: 12:50";
        Assertions.assertTrue(rez.equals(currRez));
    }
}
