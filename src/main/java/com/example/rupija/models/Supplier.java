package com.example.rupija.models;

import com.fasterxml.jackson.annotation.JsonIdentityInfo;
import com.fasterxml.jackson.annotation.ObjectIdGenerators;
import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "tiekejai")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@JsonIdentityInfo(generator = ObjectIdGenerators.PropertyGenerator.class, property = "id")
public class Supplier {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private long id;

    @Column(name = "pavadinimas")
    private String name;

    @Column(name = "imones_kodas")
    private Long supplierCode;

    @Column(name = "pvm_kodas")
    private String supplierTaxCode;

    @Column(name = "adresas")
    private String adress;

    @Column(name = "banko_saskaita")
    private String supplierBankAcc;
}
