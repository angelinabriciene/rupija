package com.example.rupija.services;

import com.example.rupija.models.Invoice;
import com.example.rupija.repository.InvoiceRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class InvoiceService {

    @Autowired
    private InvoiceRepository invoiceRepository;

    public Invoice getInvoiceById(Long id) {
        return invoiceRepository.findById(id).orElseThrow(() -> new IllegalArgumentException("Invalid invoice id" + id));
    }

    public Iterable<Invoice> findAll() {
        Iterable<Invoice> invoices = invoiceRepository.findAll();
        invoices.forEach(invoice -> System.out.println("Invoice: " + invoice));
        return invoices;
    }

    public Optional<Invoice> findById(Long id) {
        return invoiceRepository.findById(id);
    }

    public Iterable<Invoice> searchInvoiceByNumber(String name) {
        return invoiceRepository.findInvoiceByNumber(name);
    }

    public Invoice save(Invoice invoice) {
        return invoiceRepository.save(invoice);
    }

    public boolean deleteById(Long id) {
        invoiceRepository.deleteById(id);
        return true;
    }
}
