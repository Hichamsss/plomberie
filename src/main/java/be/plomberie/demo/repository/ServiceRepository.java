package be.plomberie.demo.repository;

import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import be.plomberie.demo.model.PlomberieService;

public interface ServiceRepository extends JpaRepository<PlomberieService, Integer> {
    List<PlomberieService> findByServiceContainingIgnoreCase(String service);
}
