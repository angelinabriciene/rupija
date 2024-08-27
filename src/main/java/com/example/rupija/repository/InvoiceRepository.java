package com.example.rupija.repository;

import com.example.rupija.models.Invoice;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;

public interface InvoiceRepository extends CrudRepository<Invoice, Long> {

    @Query(
            value = "SELECT * FROM saskaitos WHERE LOWER(numeris) LIKE LOWER(CONCAT('%', :name, '%'))",
            nativeQuery = true
    )
    Iterable<Invoice> findInvoiceByNumber(@Param("name") String name);

    @Query(
            value = "SELECT * FROM saskaitos WHERE neapmoketa = true",
            nativeQuery = true
    )
    Iterable<Invoice> findUnpaidInvoice();
}
