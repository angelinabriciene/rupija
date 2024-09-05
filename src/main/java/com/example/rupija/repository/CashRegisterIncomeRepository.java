package com.example.rupija.repository;

import com.example.rupija.models.CashRegisterIncome;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;

public interface CashRegisterIncomeRepository extends CrudRepository<CashRegisterIncome, Long> {

    @Query(
            value = "SELECT * FROM pajamos_grynais WHERE LOWER(numeris) LIKE LOWER(CONCAT('%', :name, '%'))",
            nativeQuery = true
    )
    Iterable<CashRegisterIncome> findCashRegisterIncomeByNumber(@Param("name") String name);
}
