package com.priytesh.ecommerce.service.impl;

import com.priytesh.ecommerce.config.JwtProvider;
import com.priytesh.ecommerce.domain.USER_ROLE;
import com.priytesh.ecommerce.modal.Cart;
import com.priytesh.ecommerce.modal.User;
import com.priytesh.ecommerce.modal.VerificationCode;
import com.priytesh.ecommerce.repository.CartRepository;
import com.priytesh.ecommerce.repository.UserRepository;
import com.priytesh.ecommerce.repository.VerificationCodeRepository;
import com.priytesh.ecommerce.request.LoginRequest;
import com.priytesh.ecommerce.response.AuthResponse;
import com.priytesh.ecommerce.response.SignupRequest;
import com.priytesh.ecommerce.service.AuthService;
import com.priytesh.ecommerce.service.EmailService;
import com.priytesh.ecommerce.utils.OtpUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

@Service
@RequiredArgsConstructor

public class AuthServiceImpl implements AuthService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final CartRepository cartRepository;
    private final JwtProvider jwtProvider;
    private final VerificationCodeRepository verificationCodeRepository;
    private final EmailService emailService;
    private final CustomUserServiceImpl customUserService;
    @Override
    public String createUser(SignupRequest req) throws Exception {
        VerificationCode verificationCode = verificationCodeRepository.findByEmail(req.getEmail());
//        if verification code present
        if(verificationCode == null || !verificationCode.getOtp().equals(req.getOtp())){
            throw new Exception("wrong otp...");
        }

//        if user already exists
        User user = userRepository.findByEmail(req.getEmail());
        if (user == null){
//            create new user
            User createdUser = new User();
            createdUser.setEmail(req.getEmail());
            createdUser.setFullName(req.getFullName());
            createdUser.setRole(USER_ROLE.ROLE_CUSTOMER);
            createdUser.setMobile("9090909009");
            createdUser.setPassword(passwordEncoder.encode(req.getOtp()));

            user = userRepository.save(createdUser);

            Cart cart= new Cart();
            cart.setUser(user);
            cartRepository.save(cart);
        }
//        generate jwt
//        create authorization using granted authority
        List<GrantedAuthority> authorities = new ArrayList<>();
        authorities.add((new SimpleGrantedAuthority(USER_ROLE.ROLE_CUSTOMER.toString())));

        UsernamePasswordAuthenticationToken authentication = new UsernamePasswordAuthenticationToken(req.getEmail(),null, authorities );
        SecurityContextHolder.getContext().setAuthentication(authentication);
        return jwtProvider.generateToken(authentication);
    }

    @Override
    public void sendLoginOtp(String email) throws Exception {
        String SIGNING_PREFIX = "signin_";

        if(email.startsWith(SIGNING_PREFIX)){
            email = email.substring(SIGNING_PREFIX.length());

            User user = userRepository.findByEmail(email);
            if(user == null){
                throw new Exception("User not exists with provided email");
            }
        }
        VerificationCode isExist = verificationCodeRepository.findByEmail(email);

        if(isExist != null){
            verificationCodeRepository.delete(isExist);
        }
        String otp = OtpUtil.generateOtp();
        VerificationCode verificationCode = new VerificationCode();
        verificationCode.setOtp(otp);
        verificationCode.setEmail(email);
        verificationCodeRepository.save(verificationCode);

        String subject = "pp bazar login/signup otp";
        String text = "your login/signup otp is - "+ otp;

        emailService.sendVerificationOtpEmail(email, otp, subject, text);

    }

    @Override
    public AuthResponse signing(LoginRequest req) {
        String username = req.getEmail();
        String otp = req.getOtp();

        Authentication authentication = authenticate(username, otp);
        SecurityContextHolder.getContext().setAuthentication(authentication);

        String token = jwtProvider.generateToken(authentication);
        AuthResponse authResponse = new AuthResponse();
        authResponse.setJwt(token);
        authResponse.setMessage("Login success");

        Collection<? extends GrantedAuthority> authorities = authentication.getAuthorities();
        String roleName = authorities.isEmpty() ? null : authorities.iterator().next().getAuthority();

        authResponse.setRole(USER_ROLE.valueOf(roleName));

        return authResponse;
    }

    private Authentication authenticate(String username, String otp) {

        UserDetails userDetails = customUserService.loadUserByUsername(username);
        if(userDetails == null){
            throw new BadCredentialsException("Invalid username");
        }
        VerificationCode varificationCode = verificationCodeRepository.findByEmail(username);
        if(varificationCode == null || !varificationCode.getOtp().equals(otp)){
            throw new BadCredentialsException("wrong otp");
        }
        return new UsernamePasswordAuthenticationToken(userDetails, null, userDetails.getAuthorities());
    }
}
