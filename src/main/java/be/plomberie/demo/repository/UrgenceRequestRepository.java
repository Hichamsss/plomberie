package be.plomberie.demo.repository;

import be.plomberie.demo.model.Client;
import be.plomberie.demo.model.UrgenceRequest;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface UrgenceRequestRepository extends JpaRepository<UrgenceRequest, Long> {

}
