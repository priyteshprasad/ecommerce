package com.priytesh.ecommerce.modal;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode
public class VerificationCode {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    private String otp;
    private String email;

    @OneToOne
    private User user;

    @OneToOne
    private Seller seller;
}
