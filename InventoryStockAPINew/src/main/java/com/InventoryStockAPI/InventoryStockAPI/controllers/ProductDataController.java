package com.InventoryStockAPI.InventoryStockAPI.controllers;

import com.InventoryStockAPI.InventoryStockAPI.models.ProductData;
import com.InventoryStockAPI.InventoryStockAPI.services.ProductDataService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
// import java.util.Optional;

@RestController
@RequestMapping("/api/products")
public class ProductDataController {

    @Autowired
    private final ProductDataService productDataService;

    public ProductDataController(ProductDataService productDataService) {
        this.productDataService = productDataService;
    }

    // Get all products
    @GetMapping
    public List<ProductData> getAllProducts() {
        return productDataService.getAllProducts();
    }

    // Get product by ID
    @GetMapping("/{id}")
    public ResponseEntity<ProductData> getProductById(@PathVariable Long id) {
        return productDataService.getProductById(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    // Create a new product
    @PostMapping("/add-data")
    public ResponseEntity<ProductData> createProduct(@RequestBody ProductData productData) {
        try {
            ProductData createdProduct = productDataService.createProduct(productData);
            return ResponseEntity.ok(createdProduct);
        } catch (RuntimeException e) {
            return ResponseEntity.badRequest().build();
        }
    }

    // Update a product
    @PutMapping("/update/{id}")
    public ResponseEntity<ProductData> updateProduct(@PathVariable Long id, @RequestBody ProductData updatedProductData) {
        System.out.println("Attempting to update product with ID: " + id);
        try {
            ProductData updatedProduct = productDataService.updateProduct(id, updatedProductData);
            System.out.println("Product updated successfully: " + updatedProduct.getProductName());
            return ResponseEntity.ok(updatedProduct);
        } catch (RuntimeException e) {
            System.out.println("Error: " + e.getMessage());
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(null);
        }
    }


    // Delete a product
    @DeleteMapping("/delete/{id}")
    public ResponseEntity<Void> deleteProduct(@PathVariable Long id) {
        try {
            productDataService.deleteProduct(id);
            return ResponseEntity.noContent().build();
        } catch (RuntimeException e) {
            return ResponseEntity.notFound().build();
        }
    }
}
