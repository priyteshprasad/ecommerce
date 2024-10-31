package com.priytesh.ecommerce.controller;


import com.priytesh.ecommerce.domain.USER_ROLE;
import com.priytesh.ecommerce.modal.VerificationCode;
import com.priytesh.ecommerce.repository.UserRepository;
import com.priytesh.ecommerce.request.LoginRequest;
import com.priytesh.ecommerce.response.ApiResponse;
import com.priytesh.ecommerce.response.AuthResponse;
import com.priytesh.ecommerce.response.SignupRequest;
import com.priytesh.ecommerce.service.AuthService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RequiredArgsConstructor //so that we do not need to create constructor
@RestController //make class act as controller class
@RequestMapping("/auth")
public class AuthController {

    private final UserRepository userRepository;
    private final AuthService authService;
    @PostMapping("/signup")
    public ResponseEntity<AuthResponse> createUserHandler(@RequestBody SignupRequest req) throws Exception {
        String jwt = authService.createUser(req);
        AuthResponse res = new AuthResponse();
        res.setJwt(jwt);
        res.setRole(USER_ROLE.ROLE_CUSTOMER);
        res.setMessage("register success");
        return ResponseEntity.ok(res);
    }

    @PostMapping("/sent/login-signup-otp")
    public ResponseEntity<ApiResponse> sendOtpHandler(@RequestBody VerificationCode req) throws Exception {
        authService.sendLoginOtp(req.getEmail());
        ApiResponse res = new ApiResponse();
        res.setMessage("otp sent successfully");
        return ResponseEntity.ok(res);
    }

    @PostMapping("/signing")
    public ResponseEntity<AuthResponse> loginHandler(@RequestBody LoginRequest req) throws Exception {

        AuthResponse res = authService.signing(req);
        res.setMessage("Login successful");
        return ResponseEntity.ok(res);
    }

}
