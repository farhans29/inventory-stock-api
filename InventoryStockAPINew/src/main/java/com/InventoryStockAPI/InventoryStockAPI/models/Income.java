package com.InventoryStockAPI.InventoryStockAPI.models;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "m_income")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Income {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, unique = true)
    private String profitId;

    @Column(nullable = false, unique = true)
    private String profitWeekly;

    @Column(nullable = false)
    private String productSold;

    @Column(nullable = false, unique = true)
    private String productId;

    @Column(nullable = false)
    private String productName;

    @Column(nullable = false)
    private String productCategories;

    @Column(nullable = false)
    private double productBuyPrice;

    @Column(nullable = false)
    private double productSellPrice;

    // 🔹 Generate profitId otomatis kalau belum ada
    @PrePersist
    public void generateProfitIdIfNeeded() {
        if (this.profitId == null) {
            this.profitId = generateProfitId();
        }
    }

    // 🔹 Method untuk bikin profitId otomatis (format PROFIT001, PROFIT002, ...)
    private String generateProfitId() {
        int nextId = getNextProfitNumber(); // Dapatkan nomor terbaru
        return String.format("PROFIT%03d", nextId);
    }

    // 🔹 Simulasi ambil ID terakhir dari database (HARUS DIGANTI QUERY ASLI)
    private int getNextProfitNumber() {
        String lastId = getLastProfitIdFromDatabase();
        if (lastId == null || !lastId.startsWith("PROFIT")) {
            return 1; // Kalau belum ada, mulai dari 1
        }

        String numberPart = lastId.substring(6); // Ambil angka setelah "PROFIT"
        return Integer.parseInt(numberPart) + 1;
    }

    // 🔹 Dummy method buat ambil ID terakhir (NANTI HARUS PAKAI DATABASE)
    private String getLastProfitIdFromDatabase() {
        return "PROFIT007"; // Contoh, nanti pakai query asli
    }
}
