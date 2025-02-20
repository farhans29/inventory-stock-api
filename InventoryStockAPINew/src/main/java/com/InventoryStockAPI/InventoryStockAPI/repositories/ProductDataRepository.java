package com.InventoryStockAPI.InventoryStockAPI.repositories;

//IMPORT MODEL
import com.InventoryStockAPI.InventoryStockAPI.models.ProductData;

//IMPORT REPO FROM
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ProductDataRepository extends JpaRepository<ProductData, Long> {
}
