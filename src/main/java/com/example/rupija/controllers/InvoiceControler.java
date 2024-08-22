package com.example.rupija.controllers;

import com.example.rupija.models.Invoice;
import com.example.rupija.models.Type;
import com.example.rupija.services.InvoiceService;
import com.example.rupija.services.TypeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

@RestController
@RequestMapping("/api/saskaitos")

public class InvoiceControler {

    @Autowired
    private InvoiceService invoiceService;

    @Autowired
    private TypeService typeService;

    @GetMapping
    public Iterable<Invoice> getAllInvoices() {
        return invoiceService.findAll();
    }

    @GetMapping("/{id}")
    public ResponseEntity<Invoice> getInvoiceById(@PathVariable Long id) {
        Optional<Invoice> invoice = invoiceService.findById(id);
        if (invoice.isPresent()) {
            return ResponseEntity.ok(invoice.get());
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    @PostMapping
    public Invoice createInvoice(@RequestBody Invoice invoice) {
        return invoiceService.save(invoice);
    }

    @GetMapping("/search")
    public Iterable<Invoice> searchInvoices(@RequestParam String name) {
        return invoiceService.searchInvoiceByNumber(name);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Invoice> updateInvoice(@PathVariable Long id, @RequestBody Invoice invoiceDetails) {
        Optional<Invoice> invoice = invoiceService.findById(id);
        if (invoice.isPresent()) {
            Invoice updatedInvoice = invoice.get();
            Optional<Type> type = typeService.findById(invoiceDetails.getType().getId());

            if (type.isPresent()) {
                updatedInvoice.setType(type.get());
                updatedInvoice.setInvoiceNumber(invoiceDetails.getInvoiceNumber());
                updatedInvoice.setInvoiceDate(invoiceDetails.getInvoiceDate());
                updatedInvoice.setSupplierId(invoiceDetails.getSupplierId());
                updatedInvoice.setSumBeforeTax(invoiceDetails.getSumBeforeTax());
                updatedInvoice.setTax(invoiceDetails.getTax());
                updatedInvoice.setSumAfterTax(invoiceDetails.getSumAfterTax());

                invoiceService.save(updatedInvoice);

                return ResponseEntity.ok(invoiceService.save(updatedInvoice));
            } else {
                return ResponseEntity.notFound().build();
            }
        }else {
            return ResponseEntity.notFound().build();
        }
    }

        @DeleteMapping("/{id}")
        public ResponseEntity<Void> deleteInvoice (@PathVariable Long id){
            invoiceService.deleteById(id);
            return ResponseEntity.noContent().build();
        }
    }
