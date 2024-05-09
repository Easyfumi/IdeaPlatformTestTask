package ru.marinin.IdeaPlatformTestTask.controllers;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import ru.marinin.IdeaPlatformTestTask.models.Ticket;
import ru.marinin.IdeaPlatformTestTask.services.TicketService;

@Controller
@RequiredArgsConstructor
public class TicketController {

    private final TicketService ticketService;

    public void registerTicket(Ticket ticket) {
        ticketService.registerTicket(ticket);
    }

    public void doTask() {
        ticketService.task();
    }
}
