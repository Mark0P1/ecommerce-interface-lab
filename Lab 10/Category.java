package com.ws101.maningcay.ecommerceapi.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;

/**
 * Product category.
 *
 * One Category may contain many Products.
 *
 * @author TechTrend PH
 * @see Product
 */
@Entity
@Table(name = "categories")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Category {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String name;

    @OneToMany(
            mappedBy = "category",
            cascade = CascadeType.ALL,
            fetch = FetchType.LAZY
    )
    private List<Product> products = new ArrayList<>();
}