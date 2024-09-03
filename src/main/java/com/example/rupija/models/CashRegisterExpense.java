package com.example.rupija.models;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIdentityInfo;
import com.fasterxml.jackson.annotation.ObjectIdGenerators;
import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDate;


@Entity
@Table(name = "islaidos_grynais")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@JsonIdentityInfo(generator = ObjectIdGenerators.PropertyGenerator.class, property = "id")
public class CashRegisterExpense {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private long id;

    @Column(name = "numeris")
    private String cashRegisterExpenseNumber;

    @Column(name = "data")
    @JsonFormat(pattern = "yyyy-MM-dd")
    private LocalDate cashRegisterExpenseDate;

    @Column(name = "tiekejo_id")
    private long supplierId;

    @Column(name = "saskaitos_id")
    private long invoiceId;

    @Column(name = "suma")
    private Double sum;

    @Column(name = "pdf_file_path")
    private String pdfFilePath;
}
