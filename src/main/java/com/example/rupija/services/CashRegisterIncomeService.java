package com.example.rupija.services;

import com.example.rupija.models.CashRegisterIncome;
import com.example.rupija.repository.CashRegisterIncomeRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class CashRegisterIncomeService {

    @Autowired
    private CashRegisterIncomeRepository cashRegisterIncomeRepository;

    public CashRegisterIncome getCashRegisterIncomeById(Long id) {
        return cashRegisterIncomeRepository.findById(id).orElseThrow(() -> new IllegalArgumentException("Invalid cashRegisterIncome id" + id));
    }

    public Iterable<CashRegisterIncome> findAll() {
        Iterable<CashRegisterIncome> cashRegisterIncomes = cashRegisterIncomeRepository.findAll();
        cashRegisterIncomes.forEach(cashRegisterIncome -> System.out.println("CashRegisterIncome: " + cashRegisterIncome));
        return cashRegisterIncomes;
    }

    public Optional<CashRegisterIncome> findById(Long id) {
        return cashRegisterIncomeRepository.findById(id);
    }

    public Iterable<CashRegisterIncome> searchCashRegisterIncomeByNumber(String name) {
        return cashRegisterIncomeRepository.findCashRegisterIncomeByNumber(name);
    }

    public CashRegisterIncome save(CashRegisterIncome cashRegisterIncome) {
        return cashRegisterIncomeRepository.save(cashRegisterIncome);
    }

    public boolean deleteById(Long id) {
        cashRegisterIncomeRepository.deleteById(id);
        return true;
    }
}
