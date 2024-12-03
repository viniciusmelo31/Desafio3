package com.example.desafio2.service;

import com.example.desafio2.entity.Destination;
import com.example.desafio2.repository.DestinationRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class DestinationService {

    @Autowired
    private DestinationRepository destinationRepository;

    public List<Destination> getAllDestinations() {
        return destinationRepository.findAll();
    }

    public List<Destination> searchDestinations(String query) {
        return destinationRepository.findByNomeContainingIgnoreCaseOrLocalizacaoContainingIgnoreCase(query, query);
    }

    public Optional<Destination> getDestinationById(Long id) {
        return destinationRepository.findById(id);
    }

    public Destination addDestination(Destination destination) {
        return destinationRepository.save(destination);
    }

    public Optional<Destination> reservePackage(Long id) {
        return destinationRepository.findById(id).map(d -> {
            d.setDisponivel(false);
            return destinationRepository.save(d);
        });
    }

    public boolean deleteDestination(Long id) {
        if (destinationRepository.existsById(id)) {
            destinationRepository.deleteById(id);
            return true;
        }
        return false;
    }

    public List<Destination> getReservedDestinations() {
        return destinationRepository.findAll().stream()
                .filter(d -> !d.isDisponivel())
                .toList();
    }
}
