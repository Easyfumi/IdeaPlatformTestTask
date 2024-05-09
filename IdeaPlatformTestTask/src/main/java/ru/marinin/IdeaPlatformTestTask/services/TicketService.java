package ru.marinin.IdeaPlatformTestTask.services;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import ru.marinin.IdeaPlatformTestTask.models.Ticket;
import ru.marinin.IdeaPlatformTestTask.repository.TicketRepository;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.time.Duration;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.*;

@Service
@RequiredArgsConstructor
public class TicketService {

    private final TicketRepository ticketRepository;

    public boolean registerTicket(Ticket ticket) {
        ticketRepository.save(ticket);
        return true;
    }

    public void task() throws IOException {

        List<Ticket> ticketList = ticketRepository.findAll();
        Set<String> setCarriers = new HashSet<>();

        for (Ticket ticket : ticketList) {
            setCarriers.add(ticket.getCarrier());
        }

        for (String carrier : setCarriers) {
            List<Ticket> ticketListByCarrier = ticketRepository.findAllByCarrier(carrier);
            answerToFile(findMinTime(ticketListByCarrier));
        }


    }

    public String findMinTime(List<Ticket> ticketList) {
        List<Duration> durationList = new ArrayList<>();

        for (Ticket ticket : ticketList) {
            LocalDateTime localDateTimeDeparture = LocalDateTime.of(ticket.getDeparture_date(), ticket.getDeparture_time());
            LocalDateTime localDateTimeArrival = LocalDateTime.of(ticket.getArrival_date(), ticket.getArrival_time()).plusHours(7);
            Duration flightTime = Duration.between(localDateTimeDeparture, localDateTimeArrival);
            durationList.add(flightTime);
        }

        Duration min = durationList.get(0);
        for (Duration duration : durationList) {
            if (min.compareTo(duration) > 0) {
                min = duration;
            }
        }

        int hours = Integer.parseInt(min.toMinutes() / 60 + "");
        int minutes = Integer.parseInt(min.toMinutes() % 60 + "");

        return ticketList.get(0).getCarrier() + ": " + LocalTime.of(hours, minutes);
    }

    public void answerToFile(String answer) throws IOException {
        String filePath = "IdeaPlatformTestTask/src/main/resources/answer.txt";
        FileWriter writer = new FileWriter(filePath, true);
        BufferedWriter bufferWriter = new BufferedWriter(writer);
        bufferWriter.write(answer + '\n');
        bufferWriter.close();
    }

}
