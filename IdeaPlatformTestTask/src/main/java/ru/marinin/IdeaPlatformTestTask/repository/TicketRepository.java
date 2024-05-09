package ru.marinin.IdeaPlatformTestTask.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import ru.marinin.IdeaPlatformTestTask.models.Ticket;
import ru.marinin.IdeaPlatformTestTask.services.TicketService;

import java.util.List;
import java.util.Optional;


public interface TicketRepository extends JpaRepository<Ticket, Long> {

    List<Ticket> findAll();

    List<Ticket> findAllByCarrier(String carrier);

}
