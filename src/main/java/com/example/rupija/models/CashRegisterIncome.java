package com.example.rupija.models;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIdentityInfo;
import com.fasterxml.jackson.annotation.ObjectIdGenerators;
import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDate;


@Entity
@Table(name = "pajamos_grynais")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@JsonIdentityInfo(generator = ObjectIdGenerators.PropertyGenerator.class, property = "id")
public class CashRegisterIncome {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private long id;

    @Column(name = "numeris")
    private String cashRegisterIncomeNumber;

    @Column(name = "data")
    @JsonFormat(pattern = "yyyy-MM-dd")
    private LocalDate cashRegisterIncomeDate;

    @Column(name = "suma_be_pvm")
    private Double sumBeforeTax;

    @Column(name = "pvm")
    private Double tax;

    @Column(name = "suma_su_pvm")
    private Double sumAfterTax;

    @Column(name = "pdf_file_path")
    private String pdfFilePath;
}
