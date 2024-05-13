package ru.marinin.IdeaPlatformTestTask.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.marinin.IdeaPlatformTestTask.models.Carrier;

public interface CarrierRepository extends JpaRepository<Carrier, Long> {
    public Carrier findByName(String name);
}
