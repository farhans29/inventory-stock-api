package com.InventoryStockAPI.InventoryStockAPI.controllers;

import com.InventoryStockAPI.InventoryStockAPI.models.Income;
import com.InventoryStockAPI.InventoryStockAPI.services.IncomeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/income")
public class IncomeController {

    @Autowired
    private IncomeService incomeService;

    // 🔹 Simpan income baru
    @PostMapping
    public Income saveIncome(@RequestBody Income income) {
        return incomeService.saveIncome(income);
    }

    // 🔹 Ambil semua income
    @GetMapping
    public List<Income> getAllIncome() {
        return incomeService.getAllIncome();
    }
}
