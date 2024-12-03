package com.example.desafio2.controller;

import com.example.desafio2.entity.Destination;
import com.example.desafio2.service.DestinationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/Destination")
public class DestinationController {

    @Autowired
    private DestinationService destinationService;

    // listar todos os destinos
    @GetMapping
    public List<Destination> getAllDestinations() {
        return destinationService.getAllDestinations();
    }

    // buscar destinos por nome ou localização
    @GetMapping("/search")
    public List<Destination> searchDestinations(@RequestParam String query) {
        return destinationService.searchDestinations(query);
    }

    // visualizar informações de um destino específico
    @GetMapping("/{id}")
    public ResponseEntity<Destination> getDestinationById(@PathVariable Long id) {
        Optional<Destination> destination = destinationService.getDestinationById(id);
        return destination.map(ResponseEntity::ok)
                .orElseGet(() -> new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }

    // cadastrar um novo destino
    @PostMapping
    public ResponseEntity<Destination> addDestination(@RequestBody Destination destination) {
        Destination createdDestination = destinationService.addDestination(destination);
        return new ResponseEntity<>(createdDestination, HttpStatus.CREATED);
    }

    // reservar um pacote de viagem para um destino
    @PostMapping("/{id}/reserve")
    public ResponseEntity<Destination> reservePackage(@PathVariable Long id) {
        Optional<Destination> reservedDestination = destinationService.reservePackage(id);
        return reservedDestination.map(ResponseEntity::ok)
                .orElseGet(() -> new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }

    // excluir um destino
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteDestination(@PathVariable Long id) {
        boolean deleted = destinationService.deleteDestination(id);
        return deleted ? new ResponseEntity<>(HttpStatus.NO_CONTENT)
                : new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }

    // listar destinos reservados
    @GetMapping("/reserved")
    public List<Destination> getReservedDestinations() {
        return destinationService.getReservedDestinations();
    }

}
