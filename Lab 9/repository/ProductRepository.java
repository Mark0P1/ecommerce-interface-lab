package com.ws101.maningcay.ecommerceapi.repository;

import com.ws101.maningcay.ecommerceapi.model.Product;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ProductRepository extends JpaRepository<Product, Long> {

    List<Product> findByNameContainingIgnoreCase(String name);

    List<Product> findByCategoryNameIgnoreCase(String categoryName);

    List<Product> findByPriceBetween(double minPrice, double maxPrice);
}