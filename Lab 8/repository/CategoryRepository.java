package com.ws101.maningcay.ecommerceapi.repository;

import com.ws101.maningcay.ecommerceapi.model.Category;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CategoryRepository extends JpaRepository<Category, Long> {
}