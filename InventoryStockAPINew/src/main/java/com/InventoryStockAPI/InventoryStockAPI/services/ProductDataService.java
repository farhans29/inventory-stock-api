package com.InventoryStockAPI.InventoryStockAPI.services;

import com.InventoryStockAPI.InventoryStockAPI.models.ProductData;
import com.InventoryStockAPI.InventoryStockAPI.repositories.ProductDataRepository;

import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Service
public class ProductDataService {

    @Autowired
    private ProductDataRepository productDataRepository;

    @Transactional
    public ProductData createProduct(ProductData productData) {
        if (productData.getProductId() == null && productData.getProductName() != null) {
            productData.setProductId(generateProductId(productData.getProductName()));
        }
        // Atur harga jual berdasarkan ketentuan
        productData.setProductSellPrice(calculateSellPrice(productData.getProductBuyPrice()));

        productData.setPossibilityIncome(productData.getProductQuantity() * productData.getProductSellPrice());

        return productDataRepository.save(productData);
    }

    private String generateProductId(String productName) {
        String prefix = productName.substring(0, 3).toUpperCase();
        long count = productDataRepository.count() + 1; // Hitung jumlah produk untuk ID unik
        return String.format("%s%03d", prefix, count);
    }

    private double calculateSellPrice(double productBuyPrice) {
        if (productBuyPrice < 10_000) {
            return productBuyPrice * 1.2; // 120%
        } else if (productBuyPrice < 100_000) {
            return productBuyPrice * 1.25; // 125%
        } else {
            return productBuyPrice * 1.3; // 130%
        }
    }


    // Get all products
    public List<ProductData> getAllProducts() {
        return productDataRepository.findAll();
    }

    // Get product by ID
    public Optional<ProductData> getProductById(Long id) {
        return productDataRepository.findById(id);
    }

    // Update a product
    @Transactional
    public ProductData updateProduct(Long id, ProductData updatedProductData) {
        Optional<ProductData> existingProduct = productDataRepository.findById(id);

        if (existingProduct.isPresent()) {
            ProductData existing = existingProduct.get();

            // Tetapkan ID agar tidak berubah
            updatedProductData.setId(id);

            // Tetapkan productId agar tidak berubah
            updatedProductData.setProductId(existing.getProductId());

            // Retain the original 'created_at' value
            updatedProductData.setCreated_at(existing.getCreated_at());

            // Automatically update 'updated_at'
            updatedProductData.setUpdated_at(LocalDateTime.now());

            // Jika productBuyPrice berubah, hitung ulang productSellPrice
            if (updatedProductData.getProductBuyPrice() != existing.getProductBuyPrice()) {
                updatedProductData.setProductSellPrice(calculateSellPrice(updatedProductData.getProductBuyPrice()));
            } else {
                updatedProductData.setProductSellPrice(existing.getProductSellPrice());
            }

            // Hitung possibilityIncome berdasarkan Qty * SellPrice
            updatedProductData.setPossibilityIncome(updatedProductData.getProductQuantity() * updatedProductData.getProductSellPrice());

            return productDataRepository.save(updatedProductData);
        } else {
            throw new RuntimeException("Product not found");
        }
    }

    // Delete a product
    public void deleteProduct(Long id) {
        if (productDataRepository.existsById(id)) {
            productDataRepository.deleteById(id);
        } else {
            throw new RuntimeException("Product not found");
        }
    }
}
