package ru.marinin.IdeaPlatformTestTask.services;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import ru.marinin.IdeaPlatformTestTask.models.Carrier;
import ru.marinin.IdeaPlatformTestTask.models.Ticket;
import ru.marinin.IdeaPlatformTestTask.repository.CarrierRepository;
import ru.marinin.IdeaPlatformTestTask.repository.TicketRepository;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.time.Duration;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

@Service
@RequiredArgsConstructor
@Slf4j
public class TicketService {

    private final TicketRepository ticketRepository;
    private final CarrierRepository carrierRepository;

    public void minTimeTask(String origin_name, String destination_name) {
        List<Carrier> carriersList = carrierRepository.findAll();
        for (Carrier carrier : carriersList) {
            List<Ticket> ticketListByCarrier = ticketRepository.findAllByCarrierAndOriginAndDestination(carrier.getId().toString(), origin_name, destination_name);

//            System.out.println(findMinTime(ticketListByCarrier));

            try {
                answerToFile(findMinTime(ticketListByCarrier));
            } catch (IOException e) {
                log.error("answerToFile in minTimeTask error: " + e);
            }
        }
    }

    public void diffAvgAndMedianeTask(String origin_name, String destination_name) {
        int[] prices = ticketRepository.findPricesByCities(origin_name, destination_name);
        int diff = ticketRepository.avgPriceByCities(origin_name, destination_name) - findMedian(prices);

//        System.out.println("diffAvgAndMediane: " + diff);

        try {
            answerToFile("diffAvgAndMediane: " + diff);
        } catch (IOException e) {
            log.error("answerToFile in diffAvgAndMedianeTask error: " + e);
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
        return carrierRepository.findById(Long.valueOf(ticketList.get(0).getCarrier())).get().getName() + ": " + LocalTime.of(hours, minutes);
        // в конкретной задаче optional не будет пустой, проверку не стал добавлять
    }

    private void answerToFile(String answer) throws IOException {
        String filePath = "IdeaPlatformTestTask/src/main/resources/answer.txt";
        FileWriter writer = new FileWriter(filePath, true);
        BufferedWriter bufferWriter = new BufferedWriter(writer);
        bufferWriter.write(answer + '\n');
        bufferWriter.close();
    }

    public int findMedian(int[] prices) {
        Arrays.sort(prices);
        if (prices.length % 2 == 0)
            return (prices[prices.length / 2] + prices[prices.length / 2 - 1]) / 2;
        else
            return prices[prices.length / 2];
    }

}
