package com.example.rupija.controllers;

import com.example.rupija.models.Supplier;
import com.example.rupija.services.SupplierService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

@RestController
@RequestMapping("/api/tiekejai")

public class SupplierControler {

    @Autowired
    private SupplierService supplierService;

    @GetMapping
    public Iterable<Supplier> getAllSupliers() {
        return supplierService.findAll();
    }

    @GetMapping("/{id}")
    public ResponseEntity<Supplier> getSupplierById(@PathVariable Long id) {
        Optional<Supplier> supplier = supplierService.findById(id);
        if (supplier.isPresent()) {
            return ResponseEntity.ok(supplier.get());
        }else {
            return ResponseEntity.notFound().build();
        }
    }

    @PostMapping
    public Supplier createSupplier(@RequestBody Supplier supplier) {
        return supplierService.save(supplier);
    }

    @GetMapping("/search")
    public Iterable<Supplier> searchSuppliers(@RequestParam String name) {
        return supplierService.searchSupplierByNaame(name);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Supplier> updateSupplier(@PathVariable Long id, @RequestBody Supplier supplierDetails) {
        Optional<Supplier> supplier = supplierService.findById(id);
        if (supplier.isPresent()) {
            Supplier updatedSupplier = supplier.get();
            updatedSupplier.setName(supplierDetails.getName());
            updatedSupplier.setSupplierCode(supplierDetails.getSupplierCode());
            updatedSupplier.setSupplierTaxCode(supplierDetails.getSupplierTaxCode());
            updatedSupplier.setAdress(supplierDetails.getAdress());
            updatedSupplier.setSupplierBankAcc(supplierDetails.getSupplierBankAcc());

            return ResponseEntity.ok(supplierService.save(updatedSupplier));
        }else {
            return ResponseEntity.notFound().build();
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteSupplier(@PathVariable Long id) {
        supplierService.deleteById(id);
        return  ResponseEntity.noContent().build();
    }
}
