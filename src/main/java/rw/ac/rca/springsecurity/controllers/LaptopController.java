package rw.ac.rca.springsecurity.controllers;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import rw.ac.rca.springsecurity.dto.LaptopDTO;
import rw.ac.rca.springsecurity.entity.Laptop;
import rw.ac.rca.springsecurity.exceptions.ResourceNotFoundException;
import rw.ac.rca.springsecurity.services.LaptopService;

import java.util.List;

@RestController
@RequestMapping("/laptops")
public class LaptopController {

    @Autowired
    private LaptopService laptopService;

    @GetMapping("getAll")
    public List<Laptop> getAllLaptops() {
        return laptopService.getAllLaptops();
    }

    @GetMapping("getALaptop/{id}")
    public ResponseEntity<Laptop> getLaptopById(@PathVariable int id) {
        return laptopService.getLaptopById(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }


    @PostMapping("/addLaptop")
    public Laptop createLaptop(@RequestBody LaptopDTO laptop) {
        return laptopService.createLaptop(laptop);
    }

    @PutMapping("/updateLaptop/{id}")
    public ResponseEntity<Laptop> updateLaptop(@PathVariable int id, @RequestBody Laptop laptopDetails) {
        try {
            return ResponseEntity.ok(laptopService.updateLaptop(id, laptopDetails));
        } catch (ResourceNotFoundException e) {
            return ResponseEntity.notFound().build();
        }
    }

    @DeleteMapping("/deleteLaptop/{id}")
    public ResponseEntity<Void> deleteLaptop(@PathVariable int id) {
        laptopService.deleteLaptop(id);
        return ResponseEntity.noContent().build();
    }
}