package ru.marinin.IdeaPlatformTestTask;

import lombok.RequiredArgsConstructor;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import ru.marinin.IdeaPlatformTestTask.controllers.TicketController;
import ru.marinin.IdeaPlatformTestTask.services.TicketService;

import java.io.IOException;

@SpringBootApplication

public class IdeaPlatformTestTaskApplication {

	public static void main(String[] args) {

		SpringApplication.run(IdeaPlatformTestTaskApplication.class, args);

	}

}
