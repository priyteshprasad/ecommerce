package com.priytesh.ecommerce.service;

import com.priytesh.ecommerce.request.LoginRequest;
import com.priytesh.ecommerce.response.AuthResponse;
import com.priytesh.ecommerce.response.SignupRequest;

public interface AuthService {
    String createUser(SignupRequest req) throws Exception;
    void sendLoginOtp(String email) throws Exception;
    AuthResponse signing(LoginRequest req);
}
