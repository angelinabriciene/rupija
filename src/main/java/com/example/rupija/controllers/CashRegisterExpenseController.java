package com.example.rupija.controllers;

import com.example.rupija.models.CashRegisterExpense;
import com.example.rupija.repository.CashRegisterExpenseRepository;
import com.example.rupija.services.CashRegisterExpenseService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.Resource;
import org.springframework.core.io.UrlResource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.net.MalformedURLException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Optional;

@RestController
@RequestMapping("/api/islaidos_grynais")

public class CashRegisterExpenseController {

    @Autowired
    private CashRegisterExpenseRepository cashRegisterExpenseRepository;

    @Value("${file.upload-dir}")
    private String uploadDir;

    @PostMapping("/{id}/upload")
    public ResponseEntity<String> uploadPdf(@PathVariable("id") long cashRegisterExpenseId,
                                            @RequestParam("file") MultipartFile file) {
        if (file.isEmpty()) {
            return new ResponseEntity<>("Please select a file to upload.", HttpStatus.BAD_REQUEST);
        }

        try {
            Path filePath = Paths.get(uploadDir + File.separator + file.getOriginalFilename());
            Files.write(filePath, file.getBytes());

            CashRegisterExpense cashRegisterExpense = cashRegisterExpenseRepository.findById(cashRegisterExpenseId).orElseThrow();
            cashRegisterExpense.setPdfFilePath(filePath.toString());
            cashRegisterExpenseRepository.save(cashRegisterExpense);

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
    private CashRegisterExpenseService cashRegisterExpenseService;

    @GetMapping
    public Iterable<CashRegisterExpense> getAllCashRegisterExpenses() {
        return cashRegisterExpenseService.findAll();
    }

    @GetMapping("/{id}")
    public ResponseEntity<CashRegisterExpense> getCashRegisterExpenseById(@PathVariable Long id) {
        Optional<CashRegisterExpense> cashRegisterExpense = cashRegisterExpenseService.findById(id);
        if (cashRegisterExpense.isPresent()) {
            return ResponseEntity.ok(cashRegisterExpense.get());
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    @PostMapping
    public CashRegisterExpense createCashRegisterExpense(@RequestBody CashRegisterExpense cashRegisterExpense) {
        return cashRegisterExpenseService.save(cashRegisterExpense);
    }

    @GetMapping("/search")
    public Iterable<CashRegisterExpense> searchCashRegisterExpenses(@RequestParam String name) {
        return cashRegisterExpenseService.searchCashRegisterExpenseByNumber(name);
    }

    @PutMapping("/{id}")
    public ResponseEntity<CashRegisterExpense> updateCashRegisterExpense(@PathVariable Long id, @RequestBody CashRegisterExpense cashRegisterExpenseDetails) {
        Optional<CashRegisterExpense> cashRegisterExpense = cashRegisterExpenseService.findById(id);
        if (cashRegisterExpense.isPresent()) {
            CashRegisterExpense updatedCashRegisterExpense = cashRegisterExpense.get();
            updatedCashRegisterExpense.setCashRegisterExpenseNumber(cashRegisterExpenseDetails.getCashRegisterExpenseNumber());
            updatedCashRegisterExpense.setCashRegisterExpenseDate(cashRegisterExpenseDetails.getCashRegisterExpenseDate());
            updatedCashRegisterExpense.setSupplierId(cashRegisterExpenseDetails.getSupplierId());
            updatedCashRegisterExpense.setInvoiceId(cashRegisterExpenseDetails.getInvoiceId());
            updatedCashRegisterExpense.setSum(cashRegisterExpenseDetails.getSum());

            return ResponseEntity.ok(cashRegisterExpenseService.save(updatedCashRegisterExpense));
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteCashRegisterExpense(@PathVariable Long id) {
        cashRegisterExpenseService.deleteById(id);
        return ResponseEntity.noContent().build();
    }
}
