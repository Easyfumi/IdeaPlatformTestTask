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
        if (ticketRepository.findSameTicket(ticket.getOrigin(), ticket.getOrigin_name(), ticket.getDestination(),
                ticket.getDestination_name(), ticket.getDeparture_date(), ticket.getDeparture_time(), ticket.getArrival_date(),
                ticket.getArrival_time(), ticket.getCarrier(), ticket.getStops(), ticket.getPrice()) != null) {
            return false;
        } else {
            ticketRepository.save(ticket);
            return true;
        }
    }

    public void minTimeTask() throws IOException {
        List<String> carriersList = ticketRepository.findAllCarriers();
        for (String carrier : carriersList) {
            List<Ticket> ticketListByCarrier = ticketRepository.findAllByCarrier(carrier);
            answerToFile(findMinTime(ticketListByCarrier));
        }
    }

    public void diffAvgAndMediane() throws IOException {
        int[] prices = ticketRepository.findPricesByCities("Владивосток", "Тель-Авив");
        int diff = ticketRepository.avgPriceByCities("Владивосток", "Тель-Авив") - findMedian(prices);
        answerToFile("diffAvgAndMediane: " + diff);

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
        String filePath = "C:\\Users\\zik15\\Desktop\\answer.txt";
        FileWriter writer = new FileWriter(filePath, true);
        BufferedWriter bufferWriter = new BufferedWriter(writer);
        bufferWriter.write(answer + '\n');
        bufferWriter.close();
    }

    public int findMedian(int[] prices) {
        Arrays.sort(prices);
        if (prices.length % 2 == 0)
            return (prices[prices.length/2] + prices[prices.length/2 - 1])/2;
        else
            return prices[prices.length/2];
    }

}
