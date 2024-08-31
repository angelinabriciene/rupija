package com.example.rupija.controllers;

import com.example.rupija.models.Invoice;
import com.example.rupija.repository.InvoiceRepository;
import com.example.rupija.services.InvoiceService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.Resource;
import org.springframework.core.io.UrlResource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.net.MalformedURLException;
import java.util.Optional;
import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

@RestController
@RequestMapping("/api/saskaitos")

public class InvoiceController {

    @Autowired
    private InvoiceRepository invoiceRepository;

    @Value("${file.upload-dir}")
    private String uploadDir;

    @PostMapping("/{id}/upload")
    public ResponseEntity<String> uploadPdf(@PathVariable("id") long invoiceId,
                                            @RequestParam("file") MultipartFile file) {
        if (file.isEmpty()) {
            return new ResponseEntity<>("Please select a file to upload.", HttpStatus.BAD_REQUEST);
        }

        try {
            Path filePath = Paths.get(uploadDir + File.separator + file.getOriginalFilename());
            Files.write(filePath, file.getBytes());

            Invoice invoice = invoiceRepository.findById(invoiceId).orElseThrow();
            invoice.setPdfFilePath(filePath.toString());
            invoiceRepository.save(invoice);

            return new ResponseEntity<>("File uploaded successfully: " + file.getOriginalFilename(), HttpStatus.OK);
        } catch (IOException e) {
            return new ResponseEntity<>("File upload failed.", HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @GetMapping("/files/{filename}")
    public ResponseEntity<Resource> serveFile(@PathVariable String filename) {
        try {
            Path file = Paths.get(uploadDir).resolve(filename);
            Resource resource = new UrlResource(file.toUri());

            if (resource.exists() || resource.isReadable()) {
                return ResponseEntity.ok()
                        .header(HttpHeaders.CONTENT_DISPOSITION, "inline; filename=\"" + resource.getFilename() + "\"")
                        .body(resource);
            } else {
                return new ResponseEntity<>(HttpStatus.NOT_FOUND);
            }
        } catch (MalformedURLException e) {
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @Autowired
    private InvoiceService invoiceService;

    @GetMapping
    public Iterable<Invoice> getAllInvoices() {
        return invoiceService.findAll();
    }

    @GetMapping("/unpaid")
    public Iterable<Invoice> searchUnpaidInvoice() {
        return invoiceService.searchUnpaidInvoice();
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
            updatedInvoice.setInvoiceTypeId(invoiceDetails.getInvoiceTypeId());
            updatedInvoice.setInvoiceNumber(invoiceDetails.getInvoiceNumber());
            updatedInvoice.setInvoiceDate(invoiceDetails.getInvoiceDate());
            updatedInvoice.setSupplierId(invoiceDetails.getSupplierId());
            updatedInvoice.setSumBeforeTax(invoiceDetails.getSumBeforeTax());
            updatedInvoice.setTax(invoiceDetails.getTax());
            updatedInvoice.setSumAfterTax(invoiceDetails.getSumAfterTax());
            updatedInvoice.setUnpaid(invoiceDetails.isUnpaid());

            return ResponseEntity.ok(invoiceService.save(updatedInvoice));
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteInvoice(@PathVariable Long id) {
        invoiceService.deleteById(id);
        return ResponseEntity.noContent().build();
    }
}
