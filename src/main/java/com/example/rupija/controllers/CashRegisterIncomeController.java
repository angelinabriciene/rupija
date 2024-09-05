package com.example.rupija.controllers;

import com.example.rupija.models.CashRegisterIncome;
import com.example.rupija.repository.CashRegisterIncomeRepository;
import com.example.rupija.services.CashRegisterIncomeService;
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
@RequestMapping("/api/pajamos_grynais")

public class CashRegisterIncomeController {

    @Autowired
    private CashRegisterIncomeRepository cashRegisterIncomeRepository;

    @Value("${file.upload-dir}")
    private String uploadDir;

    @PostMapping("/{id}/upload")
    public ResponseEntity<String> uploadPdf(@PathVariable("id") long cashRegisterIncomeId,
                                            @RequestParam("file") MultipartFile file) {
        if (file.isEmpty()) {
            return new ResponseEntity<>("Please select a file to upload.", HttpStatus.BAD_REQUEST);
        }

        try {
            Path filePath = Paths.get(uploadDir + File.separator + file.getOriginalFilename());
            Files.write(filePath, file.getBytes());

            CashRegisterIncome cashRegisterIncome = cashRegisterIncomeRepository.findById(cashRegisterIncomeId).orElseThrow();
            cashRegisterIncome.setPdfFilePath(filePath.toString());
            cashRegisterIncomeRepository.save(cashRegisterIncome);

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
    private CashRegisterIncomeService cashRegisterIncomeService;

    @GetMapping
    public Iterable<CashRegisterIncome> getAllCashRegisterIncomes() {
        return cashRegisterIncomeService.findAll();
    }

    @GetMapping("/{id}")
    public ResponseEntity<CashRegisterIncome> getCashRegisterIncomeById(@PathVariable Long id) {
        Optional<CashRegisterIncome> cashRegisterIncome = cashRegisterIncomeService.findById(id);
        if (cashRegisterIncome.isPresent()) {
            return ResponseEntity.ok(cashRegisterIncome.get());
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    @PostMapping
    public CashRegisterIncome createCashRegisterIncome(@RequestBody CashRegisterIncome cashRegisterIncome) {
        return cashRegisterIncomeService.save(cashRegisterIncome);
    }

    @GetMapping("/search")
    public Iterable<CashRegisterIncome> searchCashRegisterIncomes(@RequestParam String name) {
        return cashRegisterIncomeService.searchCashRegisterIncomeByNumber(name);
    }

    @PutMapping("/{id}")
    public ResponseEntity<CashRegisterIncome> updateCashRegisterIncome(@PathVariable Long id, @RequestBody CashRegisterIncome cashRegisterIncomeDetails) {
        Optional<CashRegisterIncome> cashRegisterIncome = cashRegisterIncomeService.findById(id);
        if (cashRegisterIncome.isPresent()) {
            CashRegisterIncome updatedCashRegisterIncome = cashRegisterIncome.get();
            updatedCashRegisterIncome.setCashRegisterIncomeNumber(cashRegisterIncomeDetails.getCashRegisterIncomeNumber());
            updatedCashRegisterIncome.setCashRegisterIncomeDate(cashRegisterIncomeDetails.getCashRegisterIncomeDate());
            updatedCashRegisterIncome.setSumBeforeTax(cashRegisterIncomeDetails.getSumBeforeTax());
            updatedCashRegisterIncome.setTax(cashRegisterIncomeDetails.getTax());
            updatedCashRegisterIncome.setSumAfterTax(cashRegisterIncomeDetails.getSumAfterTax());

            return ResponseEntity.ok(cashRegisterIncomeService.save(updatedCashRegisterIncome));
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteCashRegisterIncome(@PathVariable Long id) {
        cashRegisterIncomeService.deleteById(id);
        return ResponseEntity.noContent().build();
    }
}
