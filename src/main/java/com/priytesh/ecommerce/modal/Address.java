package com.priytesh.ecommerce.modal;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.*;

@Entity
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode //needed because we are using it in Set in Address //generates equals() and hashCode() methods for a class definition
public class Address {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    private String name;

    private String locality;
    private String address;
    private String city;

    private String state;

    private String pinCode;
    private String mobileNumber;
}

