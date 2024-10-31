package com.priytesh.ecommerce.modal;

import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Entity
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor

public class Product {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    private String title;

    private String description;

    private String mrpPrice;

    private int sellingPrice;

    private int discountPercent;

    private int quantity;

    private String color;

    @ElementCollection // will create separate table for images //otherwise will add the images as a new column
    private List<String> image = new ArrayList<>();

    private int numRatings;

    @ManyToOne //men-shirt category will have multiple products
    private Category category;

    @ManyToOne
    private Seller seller;

    private LocalDateTime createdAt;

    private String Sizes;

    @OneToMany(mappedBy = "product", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Review> reviews = new ArrayList<>();



}
