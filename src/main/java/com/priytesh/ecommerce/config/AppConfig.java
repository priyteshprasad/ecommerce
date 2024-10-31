package com.priytesh.ecommerce.config;

import jakarta.servlet.http.HttpServletRequest;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.www.BasicAuthenticationFilter;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;

import java.util.Collections;

@Configuration //since it is a configuration class //indicates that class contains beans
@EnableWebSecurity //Enables Spring Security and provides a base for the customization of security settings.
public class AppConfig {
//    we have to configure spring security to have our own password and other changes
    @Bean //Indicates that the securityFilterChain method returns a bean that Spring will manage. This bean sets up the security filter chain, a core part of Spring Security’s configuration.
    SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception { //This is a filter chain applied to incoming HTTP requests to enforce security policies.
        http.sessionManagement((management)->management.sessionCreationPolicy(
                SessionCreationPolicy.STATELESS //This disables session management, setting the application to be stateless. Each request must be authenticated individually (no session storage), often the case with JWT-based authentication.
        )).authorizeRequests(authorize -> authorize
                .requestMatchers("/api/**").authenticated() //Secured API endpoints //without jwt, not available
                .requestMatchers("/api/products/*/reviews").permitAll() //public
                .anyRequest().permitAll()
        ).addFilterBefore(new JwtTokenValidator(), BasicAuthenticationFilter.class) //Adds a custom filter (JwtTokenValidator) to validate JWTs before the standard BasicAuthenticationFilter. This custom filter will check for a valid JWT token in each request and authenticate the user if valid.
                .csrf(csrf -> csrf.disable()) //Disables CSRF protection, as it is often unnecessary for stateless REST APIs.
                .cors(cors -> cors.configurationSource(corsConfigurationSource())); //Enables CORS with custom settings, allowing requests from specified origins, which are configured in the corsConfigurationSource method.
        return http.build();
    }
    private CorsConfigurationSource corsConfigurationSource(){
        return new CorsConfigurationSource() {
            @Override
            public CorsConfiguration getCorsConfiguration(HttpServletRequest request) {
                CorsConfiguration cfg = new CorsConfiguration();
                // Collections.singletonList() is a utility method in Java that creates an immutable list with a single element. It's often used when only one element is needed in a list
                cfg.setAllowedOrigins(Collections.singletonList("*")); //if we want we can give arraylist of allowed urls
                // cfg.setAllowedOrigins(Arrays.asList("http://localhost:3000", "https://example.com"));
                cfg.setAllowedMethods(Collections.singletonList("*"));// all GET, POST, DELETE...
                cfg.setAllowedHeaders(Collections.singletonList("*"));
                cfg.setAllowCredentials(true); //Allows credentials (cookies or HTTP authentication) to be sent across origins.
                cfg.setExposedHeaders(Collections.singletonList("Authorization")); //Exposes the Authorization header to the frontend.
                cfg.setMaxAge(3600l); //Sets the maximum age (in seconds) for caching preflight requests.
                return cfg;
            }
        };

    }

//    Defines a PasswordEncoder bean to encode and decode passwords securely using BCrypt, a widely used password hashing function
    @Bean
    PasswordEncoder passwordEncoder(){
        return new BCryptPasswordEncoder();
    }
    // Creates a RestTemplate bean, which is a helper for making HTTP requests from within the Spring application, typically to other services.
    @Bean
    public RestTemplate restTemplate(){
        return  new RestTemplate();
    }

}

