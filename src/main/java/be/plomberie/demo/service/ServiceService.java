package be.plomberie.demo.service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import be.plomberie.demo.model.PlomberieService;
import be.plomberie.demo.repository.ServiceRepository;

@Service
public class ServiceService {

    @Autowired
    private ServiceRepository serviceRepository;

    public List<PlomberieService> getAllServices() {
        return serviceRepository.findAll();
    }

    public Optional<PlomberieService> getServiceById(int id) {
        return serviceRepository.findById(id);
    }

    public PlomberieService saveService(PlomberieService service) {
        return serviceRepository.save(service);
    }

    public void deleteService(int id) {
        serviceRepository.deleteById(id);
    }

    public List<PlomberieService> searchServices(String name) {
        return serviceRepository.findByServiceContainingIgnoreCase(name);
    }
}
