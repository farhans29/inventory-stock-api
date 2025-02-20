package com.InventoryStockAPI.InventoryStockAPI.repositories;  // Sesuaikan dengan foldernya

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import com.InventoryStockAPI.InventoryStockAPI.models.Income;

@Repository
public interface IncomeRepository extends JpaRepository<Income, Long> {

    // 🔹 Ambil Profit ID terakhir dari database
    @Query("SELECT i.profitId FROM Income i ORDER BY i.id DESC LIMIT 1")
    String findLastProfitId();
}
