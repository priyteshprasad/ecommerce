package com.priytesh.ecommerce.repository;

import com.priytesh.ecommerce.modal.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<User, Long> {
//    <EntityForWhichWeWantTOCreateRepository, PrimaryKeyType>
    User findByEmail(String email);
}

