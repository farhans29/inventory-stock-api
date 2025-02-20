package com.InventoryStockAPI.InventoryStockAPI.repositories;

//IMPORT MODEL
import com.InventoryStockAPI.InventoryStockAPI.models.MemoRestock;

//IMPORT REPO FROM
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface MemoRestockRepository extends JpaRepository<MemoRestock, Long> {
    MemoRestock findTopByOrderByIdDesc(); // Get the latest entry

}
