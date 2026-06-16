package com.ws101.maningcay.ecommerceapi.controller;

import com.ws101.maningcay.ecommerceapi.model.Product;
import com.ws101.maningcay.ecommerceapi.service.ProductService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;


@RestController
@RequestMapping("/api/v1/products")
public class ProductController {

    private final ProductService productService;

    public ProductController(ProductService productService) {
        this.productService = productService;
    }


    @GetMapping
    public ResponseEntity<List<Product>> getAllProducts() {
        return ResponseEntity.ok(
                productService.getAllProducts()
        );
    }


    @GetMapping("/{id}")
    public ResponseEntity<Product> getProductById(
            @PathVariable Long id
    ) {

        return ResponseEntity.ok(
                productService.getProductById(id)
        );
    }


    @PostMapping
    public ResponseEntity<Product> createProduct(
            @RequestBody Product product
    ) {

        Product saved =
                productService.createProduct(product);

        return ResponseEntity.status(201)
                .body(saved);
    }


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

    @PreAuthorize("hasRole('ADMIN')")
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