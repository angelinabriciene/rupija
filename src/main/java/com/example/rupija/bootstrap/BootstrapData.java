package com.example.rupija.bootstrap;

import com.example.rupija.repository.SupplierRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

@Component
public class BootstrapData implements CommandLineRunner {

    private final SupplierRepository supplierRepository;

    public BootstrapData(SupplierRepository supplierRepository) {
        this.supplierRepository = supplierRepository;
    }

    @Override
    public void run(String... args) throws Exception {
    }
}
