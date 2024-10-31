package com.priytesh.ecommerce.service.impl;

import com.priytesh.ecommerce.config.JwtProvider;
import com.priytesh.ecommerce.modal.User;
import com.priytesh.ecommerce.repository.UserRepository;
import com.priytesh.ecommerce.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;
    private final JwtProvider jwtProvider;

    @Override
    public User findUserByJwtToken(String jwt) throws Exception {
        String email = jwtProvider.getEmailFromJwtToken(jwt);
        return this.findUserByEmail(email);

    }

    @Override
    public User findUserByEmail(String email) throws Exception {
        User user = userRepository.findByEmail(email);
        if(user == null) throw new Exception("user not found with email- "+ email);
        return user;
    }
}
