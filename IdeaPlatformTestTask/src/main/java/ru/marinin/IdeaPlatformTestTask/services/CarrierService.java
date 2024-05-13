package ru.marinin.IdeaPlatformTestTask.services;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import ru.marinin.IdeaPlatformTestTask.models.Carrier;
import ru.marinin.IdeaPlatformTestTask.models.Ticket;
import ru.marinin.IdeaPlatformTestTask.models.TicketDTO;
import ru.marinin.IdeaPlatformTestTask.repository.CarrierRepository;
import ru.marinin.IdeaPlatformTestTask.repository.TicketRepository;

import java.io.File;
import java.io.IOException;

@Service
@RequiredArgsConstructor
public class CarrierService {
    private final CarrierRepository carrierRepository;
    private final TicketRepository ticketRepository;

    public void init() throws IOException {
        final ObjectMapper objectMapper = new ObjectMapper();

        objectMapper.registerModule(new JavaTimeModule());

        File file = new File("IdeaPlatformTestTask/src/main/resources/tickets.json");

        TicketDTO tickets = objectMapper.readValue(file, TicketDTO.class);

        for (Ticket ticket : tickets.getTickets()) {
            registerCarrierAndTickets(ticket);
        }
    }

    private void registerCarrierAndTickets(Ticket ticket) {
        if (carrierRepository.findByName(ticket.getCarrier())==null) {
            carrierRepository.save(new Carrier(ticket.getCarrier()));
        }
        Carrier carrier = carrierRepository.findByName(ticket.getCarrier());
        ticket.setCarrier(carrier.getId().toString());
        if (ticketRepository.findSameTicket(ticket.getOrigin(), ticket.getOrigin_name(), ticket.getDestination(),
                ticket.getDestination_name(), ticket.getDeparture_date(), ticket.getDeparture_time(), ticket.getArrival_date(),
                ticket.getArrival_time(), ticket.getCarrier(), ticket.getStops(), ticket.getPrice()) != null) {
        } else {
            ticketRepository.save(ticket);
        }
    }
}
