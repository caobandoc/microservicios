package com.tutorial.bikeservice.controller;

import com.tutorial.bikeservice.entity.Bike;
import com.tutorial.bikeservice.service.BikeService;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/bike")
@AllArgsConstructor
public class BikeController {

    BikeService bikeService;

    @GetMapping
    public ResponseEntity<List<Bike>> getAllUsers() {
        List<Bike> bikes = bikeService.getAllUsers();
        if (bikes.isEmpty()) {
            return ResponseEntity.noContent().build();
        }
        return ResponseEntity.ok(bikes);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Bike> getUserById(@PathVariable int id) {
        Bike bike = bikeService.getBikeById(id);
            if (bike == null) {
                return ResponseEntity.notFound().build();
            }
            return ResponseEntity.ok(bike);
    }

    @PostMapping()
    public ResponseEntity<Bike> saveUser(@RequestBody Bike bike) {
        return ResponseEntity.ok(bikeService.saveBike(bike));
    }

    @GetMapping("/byuser/{userId}")
    public ResponseEntity<List<Bike>> getByUserId(@PathVariable int userId) {
        List<Bike> bikes = bikeService.getByUserId(userId);
        if (bikes.isEmpty()) {
            return ResponseEntity.noContent().build();
        }
        return ResponseEntity.ok(bikes);
    }


}
