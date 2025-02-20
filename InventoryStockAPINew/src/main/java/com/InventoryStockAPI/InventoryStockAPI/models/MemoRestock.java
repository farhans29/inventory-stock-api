package com.InventoryStockAPI.InventoryStockAPI.models;

import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;
import java.util.Date;

@Entity
@Table(name = "m_memo_restock")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor

public class MemoRestock {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    //DARI TABLE PRODUCT_DATA
    @Column(nullable = false, unique = true)
    private String productId;

    @Column(nullable = false)
    private String productName;

    @Column(nullable = false)
    private String productCategories;

    //UTK TABLE RESTOCK
    @Column(nullable = false, unique = true)
    private String restockId;

    @Column(nullable = false)
    private String restockQuantity;

    @Column(nullable = false)
    private String restockBuyPrice;

    @Column(nullable = false)
    private int totalRestockCost;

    @Column(nullable = false)
    private String restockNotes;

    @Column(nullable = false)
    private Date restockDate;

    @Column(nullable = false)
    private String restockStatus;

    @Column(nullable = false)
    private LocalDateTime created_at;

    @Column(nullable = false)
    private LocalDateTime updated_at;

    @PrePersist
    public void prePersist() {
        LocalDateTime now = LocalDateTime.now();
        this.created_at = now;
        this.updated_at = now;

        // Default status if not set
        if (this.restockStatus == null) {
            this.restockStatus = "PENDING";
        }
    }

    @PreUpdate
    public void preUpdate() {
        this.updated_at = LocalDateTime.now();
    }

}
