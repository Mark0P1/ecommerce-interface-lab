package com.ws101.maningcay.ecommerceapi.service;

import com.ws101.maningcay.ecommerceapi.exception.ProductNotFoundException;
import com.ws101.maningcay.ecommerceapi.model.Product;
import com.ws101.maningcay.ecommerceapi.repository.ProductRepository;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * Service class for product-related operations.
 *
 * Provides business logic for filtering, searching,
 * creating, updating, and deleting products.
 *
 * @author TechTrend PH
 */
@Service
public class ProductService {

    private final ProductRepository productRepository;

    public ProductService(ProductRepository productRepository) {
        this.productRepository = productRepository;
    }

    /**
     * Retrieves all products.
     *
     * @return list of products
     */
    public List<Product> getAllProducts() {
        return productRepository.findAll();
    }

    /**
     * Retrieves product by ID.
     *
     * @param id product ID
     * @return matching product
     * @throws ProductNotFoundException if product does not exist
     */
    public Product getProductById(Long id) {
        return productRepository.findById(id)
                .orElseThrow(() ->
                        new ProductNotFoundException(
                                "Product with ID " + id + " not found"
                        ));
    }

    /**
     * Creates a new product.
     *
     * @param product product to create
     * @return saved product
     */
    public Product createProduct(Product product) {
        validateProduct(product);
        return productRepository.save(product);
    }

    /**
     * Updates an existing product.
     *
     * @param id product ID
     * @param updatedProduct replacement data
     * @return updated product
     */
    public Product updateProduct(Long id, Product updatedProduct) {

        validateProduct(updatedProduct);

        Product existing = getProductById(id);

        existing.setName(updatedProduct.getName());
        existing.setDescription(updatedProduct.getDescription());
        existing.setPrice(updatedProduct.getPrice());
        existing.setStockQuantity(updatedProduct.getStockQuantity());
        existing.setImageUrl(updatedProduct.getImageUrl());
        existing.setCategory(updatedProduct.getCategory());

        return productRepository.save(existing);
    }

    /**
     * Deletes a product.
     *
     * @param id product ID
     */
    public void deleteProduct(Long id) {

        Product product = getProductById(id);

        productRepository.delete(product);
    }

    /**
     * Filters products by category.
     *
     * @param category category name
     * @return matching products
     */
    public List<Product> filterByCategory(String category) {
        return productRepository.findByCategoryNameIgnoreCase(category);
    }

    /**
     * Filters products by name.
     *
     * @param name product name
     * @return matching products
     */
    public List<Product> filterByName(String name) {
        return productRepository.findByNameContainingIgnoreCase(name);
    }

    /**
     * Filters products by price range.
     *
     * @param min minimum price
     * @param max maximum price
     * @return matching products
     */
    public List<Product> filterByPrice(double min, double max) {

        if (min < 0 || max < 0 || min > max) {
            throw new IllegalArgumentException(
                    "Invalid price range"
            );
        }

        return productRepository.findByPriceBetween(min, max);
    }

    /**
     * Validates product data.
     *
     * @param product product to validate
     */
    private void validateProduct(Product product) {

        if (product.getName() == null ||
                product.getName().trim().length() < 3) {

            throw new IllegalArgumentException(
                    "Product name must contain at least 3 characters"
            );
        }

        if (product.getPrice() <= 0) {

            throw new IllegalArgumentException(
                    "Price must be greater than zero"
            );
        }

        if (product.getCategory() == null) {

            throw new IllegalArgumentException(
                    "Category is required"
            );
        }

        if (product.getStockQuantity() < 0) {

            throw new IllegalArgumentException(
                    "Stock quantity cannot be negative"
            );
        }
    }
}