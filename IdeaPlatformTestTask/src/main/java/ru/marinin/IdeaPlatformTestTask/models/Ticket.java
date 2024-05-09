package ru.marinin.IdeaPlatformTestTask.models;

import com.fasterxml.jackson.annotation.JsonFormat;
import jakarta.persistence.*;
import lombok.Data;
import java.time.LocalDate;
import java.time.LocalTime;

@Entity
@Data
public class Ticket {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String origin;
    private String origin_name;
    private String destination;
    private String destination_name;
    @JsonFormat(pattern = "dd.MM.yy")
    private LocalDate departure_date;
    @JsonFormat(pattern = "H:mm")
    private LocalTime departure_time;
    @JsonFormat(pattern = "dd.MM.yy")
    private LocalDate arrival_date;
    @JsonFormat(pattern = "H:mm")
    private LocalTime arrival_time;
    private String carrier;
    private int stops;
    private int price;
}
