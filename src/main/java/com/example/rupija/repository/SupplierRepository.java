package com.example.rupija.repository;

import com.example.rupija.models.Supplier;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;

public interface SupplierRepository extends CrudRepository<Supplier, Long> {

    @Query(
            value = "SELECT * FROM tiekejai WHERE LOWER(pavadinimas) LIKE LOWER(CONCAT('%', :name, '%'))",
            nativeQuery = true
    )
    Iterable<Supplier> findSupplierByName(@Param("name") String name);
}
