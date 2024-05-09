package ru.marinin.IdeaPlatformTestTask.services;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import lombok.Data;
import org.springframework.stereotype.Service;
import ru.marinin.IdeaPlatformTestTask.controllers.TicketController;
import ru.marinin.IdeaPlatformTestTask.models.Ticket;
import ru.marinin.IdeaPlatformTestTask.models.TicketDTO;

import java.io.File;
import java.io.IOException;
@Service
@Data
public class TicketDTOService {

    private final TicketController ticketController;


    public TicketDTOService(TicketController ticketController) throws IOException {
        this.ticketController = ticketController;
        final ObjectMapper objectMapper = new ObjectMapper();
        objectMapper.registerModule(new JavaTimeModule());

        File file = new File("IdeaPlatformTestTask/src/main/resources/tickets.json");

        TicketDTO tickets = objectMapper.readValue(file, TicketDTO.class);

        for (Ticket ticket : tickets.getTickets()) {
            ticketController.registerTicket(ticket);
        }

        ticketController.doTask();
    }
}
