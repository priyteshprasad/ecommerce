package com.priytesh.ecommerce.repository;

import com.priytesh.ecommerce.modal.Cart;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CartRepository extends JpaRepository<Cart, Long> {

}
