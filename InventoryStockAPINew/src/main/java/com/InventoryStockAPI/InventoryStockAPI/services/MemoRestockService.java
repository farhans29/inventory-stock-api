package com.InventoryStockAPI.InventoryStockAPI.services;

import com.InventoryStockAPI.InventoryStockAPI.models.MemoRestock;
import com.InventoryStockAPI.InventoryStockAPI.repositories.MemoRestockRepository;

import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import jakarta.transaction.Transactional;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Service
public class MemoRestockService {

    @Autowired
    private MemoRestockRepository memoRestockRepository;

    @PersistenceContext
    private EntityManager entityManager;

    @Transactional
    public MemoRestock createMemoRestock(MemoRestock memoRestock) {
        try {
            // Generate restockId only if it's not set
            if (memoRestock.getRestockId() == null || memoRestock.getRestockId().isEmpty()) {
                memoRestock.setRestockId(generateRestockId());
            }

            // Set timestamps
            LocalDateTime now = LocalDateTime.now();
            memoRestock.setCreated_at(now);
            memoRestock.setUpdated_at(now);

            // Validate and Calculate totalRestockCost
            int quantity = parseInteger(memoRestock.getRestockQuantity());
            int buyPrice = parseInteger(memoRestock.getRestockBuyPrice());
            memoRestock.setTotalRestockCost(quantity * buyPrice);

            return memoRestockRepository.save(memoRestock);
        } catch (Exception e) {
            System.out.println("Error in createMemoRestock: " + e.getMessage());
            e.printStackTrace();
            throw new RuntimeException("Failed to create memo restock: " + e.getMessage());
        }
    }

    // Generate auto-incremented Restock ID (RES001, RES002, ...)
    private String generateRestockId() {
        String prefix = "RES";
        Integer maxNumber = entityManager.createQuery(
                "SELECT MAX(CAST(SUBSTRING(r.restockId, 4, LENGTH(r.restockId)) AS integer)) FROM MemoRestock r",
                Integer.class
        ).getSingleResult();

        int nextNumber = (maxNumber == null) ? 1 : maxNumber + 1;
        return String.format("%s%03d", prefix, nextNumber);
    }

    // Convert String to Integer with validation
    private int parseInteger(String value) {
        try {
            return Integer.parseInt(value);
        } catch (NumberFormatException e) {
            throw new RuntimeException("Invalid number format for value: " + value);
        }
    }

    // Fetch latest restockId and increment the number
    private int fetchNextRestockNumber() {
        Integer maxNumber = entityManager.createQuery(
                "SELECT MAX(CAST(SUBSTRING(r.restockId, 4) AS integer)) FROM MemoRestock r",
                Integer.class
        ).getSingleResult();
        return (maxNumber == null) ? 1 : maxNumber + 1;
    }

    public List<MemoRestock> getAllMemoRestocks() {
        return memoRestockRepository.findAll();
    }

    public Optional<MemoRestock> getMemoRestockById(Long id) {
        return memoRestockRepository.findById(id);
    }

    @Transactional
    public MemoRestock updateMemoRestock(Long id, MemoRestock updatedMemoRestock) {
        return memoRestockRepository.findById(id).map(existingMemo -> {
            updatedMemoRestock.setId(id);
            updatedMemoRestock.setUpdated_at(LocalDateTime.now());
            return memoRestockRepository.save(updatedMemoRestock);
        }).orElseThrow(() -> new RuntimeException("Memo Restock not found"));
    }

    @Transactional
    public void deleteMemoRestock(Long id) {
        memoRestockRepository.deleteById(id);
    }
}