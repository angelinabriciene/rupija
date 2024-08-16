package com.example.rupija.services;

import com.example.rupija.models.Supplier;
import com.example.rupija.repository.SupplierRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class SupplierService {

    @Autowired
    private SupplierRepository supplierRepository;

    public Supplier getSupplierById(Long id) {
        return supplierRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Invalid supplier id" + id));
    }

    public Iterable<Supplier> findAll() {
        return supplierRepository.findAll();
    }

    public Optional<Supplier> findById(Long id) {
        return supplierRepository.findById(id);
    }

    public Iterable<Supplier> searchSupplierByNaame(String name) {
        return supplierRepository.findSupplierByName(name);
    }

    public Supplier save(Supplier supplier) {
        return supplierRepository.save(supplier);
    }

    public boolean deleteById(Long id) {
        supplierRepository.deleteById(id);
        return true;
    }
}
