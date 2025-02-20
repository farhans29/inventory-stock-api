package com.InventoryStockAPI.InventoryStockAPI.models;

import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;

@Entity
@Table(name = "m_product_data")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor

public class ProductData {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, unique = true)
    private String productId;

    @Column(nullable = false)
    private String productName;

    @Column(nullable = false)
    private int productQuantity;

    @Column(nullable = false)
    private String productCategories;

    @Column(nullable = false)
    private double productBuyPrice;

    @Column(nullable = false)
    private double productSellPrice;

    @Column(nullable = false)
    private double possibilityIncome;

    @Column(nullable = false)
    private LocalDateTime created_at;

    @Column(nullable = false)
    private LocalDateTime updated_at;

    @PrePersist
    public void prePersist() {
        LocalDateTime now = LocalDateTime.now();
        this.created_at = now;
        this.updated_at = now;

        // Generate Product ID hanya jika belum ada
        if (this.productId == null && this.productName != null && this.productName.length() >= 3) {
            this.productId = generateProductId();
        }

        // Pastikan harga jual dihitung jika belum dihitung
        if (this.productSellPrice == 0) {
            this.productSellPrice = calculateSellPrice(this.productBuyPrice);
        }

        // Hitung kemungkinan pendapatan
        this.possibilityIncome = this.productQuantity * this.productSellPrice;
    }

    private String generateProductId() {
        // Ambil 3 huruf pertama dari nama produk dan buat huruf besar
        String prefix = productName.substring(0, 3).toUpperCase();

        // Hitung jumlah produk dengan prefix yang sama untuk ID unik
        long count = countExistingProductsWithPrefix(prefix) + 1;

        // Format productId seperti "KAN001"
        return String.format("%s%03d", prefix, count);
    }

    // Metode untuk menghitung jumlah produk yang sudah ada dengan prefix tertentu
    private long countExistingProductsWithPrefix(String prefix) {
        return 0; // Ini akan diganti dengan query di service/repository untuk menghitung produk dengan prefix yang sama
    }

    @PreUpdate
    public void preUpdate() {
        this.updated_at = LocalDateTime.now();

        // Pastikan productId tidak berubah saat update
        if (this.productId == null && this.productName != null && this.productName.length() >= 3) {
            this.productId = generateProductId(); // Jika null, buat ulang
        }
    }

    private double calculateSellPrice(double productBuyPrice) {
        if (productBuyPrice < 10_000) {
            return productBuyPrice * 1.2;
        } else if (productBuyPrice < 100_000) {
            return productBuyPrice * 1.25;
        } else {
            return productBuyPrice * 1.3;
        }
    }



}