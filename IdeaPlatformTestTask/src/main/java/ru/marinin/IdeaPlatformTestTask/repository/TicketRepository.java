package ru.marinin.IdeaPlatformTestTask.repository;

import com.fasterxml.jackson.annotation.JsonFormat;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import ru.marinin.IdeaPlatformTestTask.models.Carrier;
import ru.marinin.IdeaPlatformTestTask.models.Ticket;
import ru.marinin.IdeaPlatformTestTask.services.TicketService;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;


public interface TicketRepository extends JpaRepository<Ticket, Long> {


    @Query(value = "SELECT id FROM ticket WHERE origin=?1 AND origin_name=?2 AND destination=?3 AND destination_name=?4 AND departure_date=?5 AND departure_time=?6 AND arrival_date=?7 AND arrival_time=?8 AND carrier=?9 AND stops=?10 AND price=?11", nativeQuery = true)
    Long findSameTicket(String origin, String origin_name, String destination, String destination_name, LocalDate departure_date,
                        LocalTime departure_time, LocalDate arrival_date, LocalTime arrival_time, String carrier, int stops, int price);

    @Query(value = "SELECT price FROM ticket WHERE origin_name=?1 AND destination_name=?2", nativeQuery = true)
    int[] findPricesByCities(String cityOrigin, String cityDestiantion);

    @Query(value = "SELECT AVG(price) FROM ticket WHERE origin_name=?1 AND destination_name=?2", nativeQuery = true)
    int avgPriceByCities(String cityOrigin, String cityDestiantion);

    List<Ticket> findAll();

    @Query(value = "SELECT * FROM ticket WHERE carrier =?1 AND origin_name=?2 AND destination_name=?3", nativeQuery = true)
    List<Ticket> findAllByCarrierAndOriginAndDestination(String carrier, String origin_name, String destination_name);

}
