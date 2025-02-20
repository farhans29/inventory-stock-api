package com.InventoryStockAPI.InventoryStockAPI.controllers;

import com.InventoryStockAPI.InventoryStockAPI.models.MemoRestock;
import com.InventoryStockAPI.InventoryStockAPI.services.MemoRestockService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/memo-restock")
public class MemoRestockController {

    @Autowired
    private MemoRestockService memoRestockService;

    // Create a new MemoRestock
    @PostMapping("/request-restock")
    public ResponseEntity<?> createMemoRestock(@RequestBody MemoRestock memoRestock) {
        try {
            MemoRestock savedMemo = memoRestockService.createMemoRestock(memoRestock);
            return ResponseEntity.ok(savedMemo);
        } catch (Exception e) {
            System.out.println("Error creating MemoRestock: " + e.getMessage());
            e.printStackTrace();
            return ResponseEntity.status(500).body("Error: " + e.getMessage());
        }
    }

    // Get all MemoRestocks
    @GetMapping
    public List<MemoRestock> getAllMemoRestocks() {
        return memoRestockService.getAllMemoRestocks();
    }

    // Get a MemoRestock by ID
    @GetMapping("/{id}")
    public Optional<MemoRestock> getMemoRestockById(@PathVariable Long id) {
        return memoRestockService.getMemoRestockById(id);
    }

    // Update MemoRestock
    @PutMapping("/{id}")
    public MemoRestock updateMemoRestock(@PathVariable Long id, @RequestBody MemoRestock updatedMemo) {
        return memoRestockService.updateMemoRestock(id, updatedMemo);
    }

    // Delete MemoRestock
    @DeleteMapping("/{id}")
    public String deleteMemoRestock(@PathVariable Long id) {
        memoRestockService.deleteMemoRestock(id);
        return "MemoRestock deleted successfully";
    }
}
