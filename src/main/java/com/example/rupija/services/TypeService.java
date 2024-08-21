package com.example.rupija.services;

import com.example.rupija.models.Type;
import com.example.rupija.repository.TypeRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class TypeService {

    @Autowired
    private TypeRepository typeRepository;

    public Type getTypeById(Long id) {
        return typeRepository.findById(id).orElseThrow(() -> new IllegalArgumentException("Invalid type id" + id));
    }

    public Iterable<Type> findAll() {
        Iterable<Type> types = typeRepository.findAll();
        types.forEach(type -> System.out.println("Type: " + type));
        return types;
    }

    public Optional<Type> findById(Long id) {
        return typeRepository.findById(id);
    }

    public Iterable<Type> searchTypeByName(String name) {
        return typeRepository.findTypeByName(name);
    }

    public Type save(Type type) {
        return typeRepository.save(type);
    }

    public boolean deleteById(Long id) {
        typeRepository.deleteById(id);
        return true;
    }
}
