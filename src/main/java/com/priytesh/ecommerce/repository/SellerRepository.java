package com.priytesh.ecommerce.repository;

import com.priytesh.ecommerce.modal.Seller;
import org.springframework.data.jpa.repository.JpaRepository;

public interface SellerRepository extends JpaRepository<Seller, Long> {
    Seller findByEmail(String email);
}
