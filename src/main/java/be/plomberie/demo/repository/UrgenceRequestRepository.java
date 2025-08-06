package be.plomberie.demo.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import be.plomberie.demo.model.UrgenceRequest;

public interface UrgenceRequestRepository extends JpaRepository<UrgenceRequest, Long> {

}
