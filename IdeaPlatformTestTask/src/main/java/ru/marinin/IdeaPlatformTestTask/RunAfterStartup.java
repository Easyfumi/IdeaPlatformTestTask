package ru.marinin.IdeaPlatformTestTask;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;
import ru.marinin.IdeaPlatformTestTask.services.CarrierService;
import ru.marinin.IdeaPlatformTestTask.services.TicketService;

import java.io.IOException;

@Component
@RequiredArgsConstructor
@Slf4j
public class RunAfterStartup {

    private final TicketService ticketService;
    private final CarrierService carrierService;
    @EventListener(ApplicationReadyEvent.class)
    public void registerTicket() {
        try {
            carrierService.init();
        } catch (IOException e) {
            log.error("init exception");
        }

        try {
            ticketService.minTimeTask("Владивосток", "Тель-Авив");
        } catch (IOException e) {
            log.error("minTimeTask exception");
        }

        try {
            ticketService.diffAvgAndMedianeTask("Владивосток", "Тель-Авив");
        } catch (IOException e) {
            log.error("diffAvgAndMediane exception");
        }


    }
}
