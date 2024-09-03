package com.example.rupija.services;

import com.example.rupija.models.CashRegisterExpense;
import com.example.rupija.repository.CashRegisterExpenseRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class CashRegisterExpenseService {

    @Autowired
    private CashRegisterExpenseRepository cashRegisterExpenseRepository;

    public CashRegisterExpense getCashRegisterExpenseById(Long id) {
        return cashRegisterExpenseRepository.findById(id).orElseThrow(() -> new IllegalArgumentException("Invalid cashRegisterExpense id" + id));
    }

    public Iterable<CashRegisterExpense> findAll() {
        Iterable<CashRegisterExpense> cashRegisterExpenses = cashRegisterExpenseRepository.findAll();
        cashRegisterExpenses.forEach(cashRegisterExpense -> System.out.println("CashRegisterExpense: " + cashRegisterExpense));
        return cashRegisterExpenses;
    }

    public Optional<CashRegisterExpense> findById(Long id) {
        return cashRegisterExpenseRepository.findById(id);
    }

    public Iterable<CashRegisterExpense> searchCashRegisterExpenseByNumber(String name) {
        return cashRegisterExpenseRepository.findCashRegisterExpenseByNumber(name);
    }

    public CashRegisterExpense save(CashRegisterExpense cashRegisterExpense) {
        return cashRegisterExpenseRepository.save(cashRegisterExpense);
    }

    public boolean deleteById(Long id) {
        cashRegisterExpenseRepository.deleteById(id);
        return true;
    }
}
