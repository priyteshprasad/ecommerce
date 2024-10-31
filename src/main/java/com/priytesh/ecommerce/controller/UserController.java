package com.priytesh.ecommerce.controller;

import com.priytesh.ecommerce.modal.User;
import com.priytesh.ecommerce.repository.UserRepository;
import com.priytesh.ecommerce.response.AuthResponse;
import com.priytesh.ecommerce.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
public class UserController {

    private final UserService userService;

    @GetMapping("users/profile")
    private ResponseEntity<User> userHandler(@RequestHeader("Authorization") String jwt) throws Exception {
        User user = userService.findUserByJwtToken(jwt);
        return ResponseEntity.ok(user);
    }

}
