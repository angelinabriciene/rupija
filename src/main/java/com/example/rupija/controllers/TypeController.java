package com.example.rupija.controllers;

import com.example.rupija.models.Type;
import com.example.rupija.services.TypeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

@RestController
@RequestMapping("/api/tipai")

public class TypeController {

    @Autowired
    private TypeService typeService;

    @GetMapping
    public Iterable<Type> getAllTypes() {
        return typeService.findAll();
    }

    @GetMapping("/{id}")
    public ResponseEntity<Type> getTypeById(@PathVariable Long id) {
        Optional<Type> type = typeService.findById(id);
        if (type.isPresent()) {
            return ResponseEntity.ok(type.get());
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    @PostMapping
    public Type createType(@RequestBody Type type) {
        return typeService.save(type);
    }

    @GetMapping("/search")
    public Iterable<Type> searchTypes(@RequestParam String name) {
        return typeService.searchTypeByName(name);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Type> updateType(@PathVariable Long id, @RequestBody Type typeDetails) {
        Optional<Type> type = typeService.findById(id);
        if (type.isPresent()) {
            Type updatedType = type.get();
            updatedType.setName(typeDetails.getName());

            return ResponseEntity.ok(typeService.save(updatedType));
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteType(@PathVariable Long id) {
        typeService.deleteById(id);
        return ResponseEntity.noContent().build();
    }
}
