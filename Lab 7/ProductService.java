package com.ws101.maningcay.ecommerceapi.service;

import com.ws101.maningcay.ecommerceapi.model.Product;
import com.ws101.maningcay.ecommerceapi.exception.ProductNotFoundException;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class ProductService {

    private final List<Product> productList = new ArrayList<>();
    private Long idCounter = 1L;

    public ProductService() {
        productList.add(new Product(idCounter++, "Headphones", "Sound", 2499, "Electronics", 10, "img"));
        productList.add(new Product(idCounter++, "Shoes", "Running shoes", 2299, "Footwear", 15, "img"));
        productList.add(new Product(idCounter++, "Watch", "Smart watch", 4899, "Electronics", 5, "img"));
    }

    public List<Product> getAllProducts() {
        return productList;
    }

    public Product getProductById(Long id) {
        return productList.stream()
                .filter(p -> p.getId().equals(id))
                .findFirst()
                .orElseThrow(() -> new ProductNotFoundException("Product not found"));
    }

    public Product createProduct(Product product) {
        validate(product);
        product.setId(idCounter++);
        productList.add(product);
        return product;
    }

    public Product updateProduct(Long id, Product updated) {
        validate(updated);

        for (Product p : productList) {
            if (p.getId().equals(id)) {
                p.setName(updated.getName());
                p.setDescription(updated.getDescription());
                p.setPrice(updated.getPrice());
                p.setCategory(updated.getCategory());
                p.setStockQuantity(updated.getStockQuantity());
                p.setImageUrl(updated.getImageUrl());
                return p;
            }
        }
        throw new ProductNotFoundException("Product not found");
    }

    public boolean deleteProduct(Long id) {
        return productList.removeIf(p -> p.getId().equals(id));
    }

    public List<Product> filterByCategory(String category) {
        return productList.stream()
                .filter(p -> p.getCategory().equalsIgnoreCase(category))
                .collect(Collectors.toList());
    }

    public List<Product> filterByName(String name) {
        return productList.stream()
                .filter(p -> p.getName().toLowerCase().contains(name.toLowerCase()))
                .collect(Collectors.toList());
    }

    public List<Product> filterByPrice(double min, double max) {
        return productList.stream()
                .filter(p -> p.getPrice() >= min && p.getPrice() <= max)
                .collect(Collectors.toList());
    }

    private void validate(Product p) {
        if (p.getName() == null || p.getName().length() < 3)
            throw new IllegalArgumentException("Invalid name");

        if (p.getPrice() <= 0)
            throw new IllegalArgumentException("Invalid price");

        if (p.getCategory() == null || p.getCategory().isEmpty())
            throw new IllegalArgumentException("Category required");

        if (p.getStockQuantity() < 0)
            throw new IllegalArgumentException("Invalid stock");
    }
}