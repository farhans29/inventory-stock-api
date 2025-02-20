package com.InventoryStockAPI.InventoryStockAPI.services;

import com.InventoryStockAPI.InventoryStockAPI.models.Income;
import com.InventoryStockAPI.InventoryStockAPI.repositories.IncomeRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.List;

@Service
public class IncomeService {

    @Autowired
    private IncomeRepository incomeRepository;

    // 🔹 Simpan income baru (cek profitId dulu)
    public Income saveIncome(Income income) {
        if (income.getProfitId() == null || income.getProfitId().isEmpty()) {
            income.setProfitId(generateProfitId());
        }
        return incomeRepository.save(income);
    }

    // 🔹 Ambil semua income dari database
    public List<Income> getAllIncome() {
        return incomeRepository.findAll();
    }

    // 🔹 Generate Profit ID (PROFIT001, PROFIT002, ...)
    private String generateProfitId() {
        String lastId = incomeRepository.findLastProfitId();

        if (lastId == null || !lastId.startsWith("PROFIT")) {
            return "PROFIT001";
        }

        // Ambil angka dari "PROFIT001" -> "001", lalu convert ke integer
        int nextId = Integer.parseInt(lastId.substring(6)) + 1;

        // Format jadi "PROFIT002", "PROFIT003", dst.
        return String.format("PROFIT%03d", nextId);
    }
}
