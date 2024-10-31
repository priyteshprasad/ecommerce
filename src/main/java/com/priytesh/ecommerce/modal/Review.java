package com.priytesh.ecommerce.modal;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;
import java.util.List;


@Entity
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class Review {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long Id;

    @Column(nullable = false)
    private String reviewText;

    @Column(nullable = false)
    private double rating;

    @ElementCollection //creates a separate table
    private List<String> productImages;

    @JsonIgnore
    @ManyToOne
//    @Column(nullable = false) //give error in compilation
    @JoinColumn(nullable = false) //if not given it will still join the column
//    @NotNull // we can also give it like this
    private Product product;

    @ManyToOne
//    @Column(nullable = false) //giving error
    @JoinColumn(nullable = false)
    private User user;

    @Column(nullable = false)
    private LocalDateTime createdAt=LocalDateTime.now();


}
