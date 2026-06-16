package com.ws101.maningcay.ecommerceapi.controller;

import com.ws101.maningcay.ecommerceapi.model.Product;
import com.ws101.maningcay.ecommerceapi.service.ProductService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * REST Controller for Product API.
 *
 * Provides CRUD endpoints and filtering endpoints.
 *
 * @author TechTrend PH
 */
@RestController
@RequestMapping("/api/v1/products")
public class ProductController {

    private final ProductService productService;

    public ProductController(ProductService productService) {
        this.productService = productService;
    }

    /**
     * Retrieve all products.
     *
     * @return all products
     */
    @GetMapping
    public ResponseEntity<List<Product>> getAllProducts() {
        return ResponseEntity.ok(
                productService.getAllProducts()
        );
    }

    /**
     * Retrieve a single product.
     *
     * @param id product id
     * @return matching product
     */
    @GetMapping("/{id}")
    public ResponseEntity<Product> getProductById(
            @PathVariable Long id
    ) {

        return ResponseEntity.ok(
                productService.getProductById(id)
        );
    }

    /**
     * Create a new product.
     *
     * @param product request body
     * @return created product
     */
    @PostMapping
    public ResponseEntity<Product> createProduct(
            @RequestBody Product product
    ) {

        Product saved =
                productService.createProduct(product);

        return ResponseEntity.status(201)
                .body(saved);
    }

    /**
     * Replace an existing product.
     *
     * @param id product id
     * @param product replacement data
     * @return updated product
     */
    @PutMapping("/{id}")
    public ResponseEntity<Product> updateProduct(
            @PathVariable Long id,
            @RequestBody Product product
    ) {

        return ResponseEntity.ok(
                productService.updateProduct(
                        id,
                        product
                )
        );
    }

    /**
     * Partial update.
     *
     * Simplified implementation.
     */
    @PatchMapping("/{id}")
    public ResponseEntity<Product> patchProduct(
            @PathVariable Long id,
            @RequestBody Product product
    ) {

        return ResponseEntity.ok(
                productService.updateProduct(
                        id,
                        product
                )
        );
    }

    /**
     * Delete product.
     *
     * @param id product id
     * @return no content
     */
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteProduct(
            @PathVariable Long id
    ) {

        productService.deleteProduct(id);

        return ResponseEntity.noContent().build();
    }

    /**
     * Filter products.
     *
     * Examples:
     * ?filterType=category&filterValue=Electronics
     * ?filterType=name&filterValue=Laptop
     * ?filterType=price&filterValue=1000-5000
     */
    @GetMapping("/filter")
    public ResponseEntity<List<Product>> filterProducts(
            @RequestParam String filterType,
            @RequestParam String filterValue
    ) {

        switch (filterType.toLowerCase()) {

            case "category":

                return ResponseEntity.ok(
                        productService.filterByCategory(
                                filterValue
                        )
                );

            case "name":

                return ResponseEntity.ok(
                        productService.filterByName(
                                filterValue
                        )
                );

            case "price":

                String[] values =
                        filterValue.split("-");

                double min =
                        Double.parseDouble(values[0]);

                double max =
                        Double.parseDouble(values[1]);

                return ResponseEntity.ok(
                        productService.filterByPrice(
                                min,
                                max
                        )
                );

            default:

                return ResponseEntity.badRequest()
                        .build();
        }
    }
}