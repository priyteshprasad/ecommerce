package com.priytesh.ecommerce.service;

import com.priytesh.ecommerce.modal.User;

public interface UserService {
    User findUserByJwtToken(String jwt) throws Exception;
    User findUserByEmail(String email) throws Exception;
}
