package com.example.rupija.models;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIdentityInfo;
import com.fasterxml.jackson.annotation.ObjectIdGenerators;
import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDate;


@Entity
@Table(name = "saskaitos")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@JsonIdentityInfo(generator = ObjectIdGenerators.PropertyGenerator.class, property = "id")
public class Invoice {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private long id;

    @Column(name = "tipo_id")
    private long invoiceTypeId;

    @Column(name = "numeris")
    private String invoiceNumber;

    @Column(name = "data")
    @JsonFormat(pattern = "yyyy-MM-dd")
    private LocalDate invoiceDate;

    @Column(name = "tiekejo_id ")
    private long supplierId;

    @Column(name = "suma_be_pvm")
    private Double sumBeforeTax;

    @Column(name = "pvm")
    private Double tax;

    @Column(name = "suma_su_pvm")
    private Double sumAfterTax;
}
