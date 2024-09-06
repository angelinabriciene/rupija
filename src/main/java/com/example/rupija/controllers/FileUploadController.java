package com.example.rupija.controllers;

import com.example.rupija.models.Transaction;
import com.example.rupija.repository.TransactionRepository;
import com.opencsv.CSVReader;
import com.opencsv.exceptions.CsvValidationException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import java.io.*;
import java.nio.file.Files;
import java.nio.file.Path;

@RestController
@RequestMapping("/api/uploads")
public class FileUploadController {

    @Autowired
    private TransactionRepository transactionRepository;

    @PostMapping("/upload")
    public ResponseEntity<String> uploadFile(@RequestParam("file") MultipartFile file) {
        if (file.isEmpty()) {
            return new ResponseEntity<>("Please select a file to upload.", HttpStatus.BAD_REQUEST);
        }

        try {
            Path filePath = Files.createTempFile("transactions-", file.getOriginalFilename());
            Files.write(filePath, file.getBytes());

            if (file.getOriginalFilename().endsWith(".csv")) {
                processCSVFile(filePath.toFile());
            } else {
                return new ResponseEntity<>("Unsupported file type.", HttpStatus.BAD_REQUEST);
            }

            return new ResponseEntity<>("File uploaded successfully.", HttpStatus.OK);
        } catch (IOException e) {
            return new ResponseEntity<>("File upload failed.", HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    private void processCSVFile(File file) {
        try (CSVReader reader = new CSVReader(new FileReader(file))) {
            String[] nextLine;
            while ((nextLine = reader.readNext()) != null) {
                try {
                    String date = nextLine[0];
                    String description = nextLine[1];
                    double amount = Double.parseDouble(nextLine[2]);

                    Transaction transaction = new Transaction();
                    transaction.setDate(date);
                    transaction.setDescription(description);
                    transaction.setAmount(amount);

                    transactionRepository.save(transaction);
                } catch (NumberFormatException e) {
                    System.err.println("Error parsing amount: " + nextLine[2] + ". Skipping line.");
                } catch (ArrayIndexOutOfBoundsException e) {
                    System.err.println("Malformed CSV line: " + String.join(",", nextLine) + ". Skipping line.");
                }
            }
        } catch (IOException | CsvValidationException e) {
            e.printStackTrace();
        }
    }
}
