package com.example.rupija.repository;

import com.example.rupija.models.CashRegisterExpense;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;

public interface CashRegisterExpenseRepository extends CrudRepository<CashRegisterExpense, Long> {

    @Query(
            value = "SELECT * FROM islaidos_grynais WHERE LOWER(numeris) LIKE LOWER(CONCAT('%', :name, '%'))",
            nativeQuery = true
    )
    Iterable<CashRegisterExpense> findCashRegisterExpenseByNumber(@Param("name") String name);
}
