package com.egwallet.service.impl;

import com.egwallet.dto.request.LoginRequest;
import com.egwallet.dto.request.RegisterRequest;
import com.egwallet.dto.response.AuthResponse;
import com.egwallet.entity.User;
import com.egwallet.entity.Subscription;
import com.egwallet.exception.BadRequestException;
import com.egwallet.exception.DuplicateResourceException;
import com.egwallet.exception.ResourceNotFoundException;
import com.egwallet.repository.UserRepository;
import com.egwallet.repository.SubscriptionRepository;
import com.egwallet.security.JwtTokenProvider;
import com.egwallet.service.AuthService;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;

@Slf4j
@Service
@AllArgsConstructor
public class AuthServiceImpl implements AuthService {
    private final UserRepository userRepository;
    private final SubscriptionRepository subscriptionRepository;
    private final PasswordEncoder passwordEncoder;
    private final JwtTokenProvider jwtTokenProvider;

    @Override
    @Transactional
    public AuthResponse register(RegisterRequest request) {
        log.info("Registering new user with email: {}", request.getEmail());
        
        if (userRepository.existsByEmailIgnoreCase(request.getEmail())) {
            throw new DuplicateResourceException("email", request.getEmail());
        }

        User user = User.builder()
                .email(request.getEmail())
                .password(passwordEncoder.encode(request.getPassword()))
                .firstName(request.getFirstName())
                .lastName(request.getLastName())
                .phone(request.getPhone())
                .preferredLanguage(request.getPreferredLanguage())
                .preferredCurrency(request.getPreferredCurrency())
                .isActive(true)
                .isEmailVerified(false)
                .build();

        User savedUser = userRepository.save(user);
        
        // Create free subscription
        Subscription subscription = Subscription.builder()
                .user(savedUser)
                .planType("FREE")
                .status("ACTIVE")
                .startDate(LocalDateTime.now())
                .build();
        subscriptionRepository.save(subscription);

        String token = jwtTokenProvider.generateToken(savedUser.getId(), savedUser.getEmail(), "ROLE_USER");
        String refreshToken = jwtTokenProvider.generateRefreshToken(savedUser.getId(), savedUser.getEmail());

        log.info("User registered successfully: {}", savedUser.getId());
        
        return AuthResponse.builder()
                .id(savedUser.getId())
                .email(savedUser.getEmail())
                .firstName(savedUser.getFirstName())
                .lastName(savedUser.getLastName())
                .token(token)
                .refreshToken(refreshToken)
                .expiresIn(86400000L)
                .tokenType("Bearer")
                .build();
    }

    @Override
    public AuthResponse login(LoginRequest request) {
        log.info("User login attempt: {}", request.getEmail());
        
        User user = userRepository.findByEmailAndNotDeleted(request.getEmail())
                .orElseThrow(() -> new ResourceNotFoundException("User", "email", request.getEmail()));

        if (!passwordEncoder.matches(request.getPassword(), user.getPassword())) {
            throw new BadRequestException("Invalid email or password");
        }

        if (!user.getIsActive()) {
            throw new BadRequestException("User account is inactive");
        }

        user.setLastLoginAt(LocalDateTime.now());
        userRepository.save(user);

        String token = jwtTokenProvider.generateToken(user.getId(), user.getEmail(), "ROLE_USER");
        String refreshToken = jwtTokenProvider.generateRefreshToken(user.getId(), user.getEmail());

        Subscription subscription = subscriptionRepository.findByUserId(user.getId()).orElse(null);

        log.info("User logged in successfully: {}", user.getId());
        
        return AuthResponse.builder()
                .id(user.getId())
                .email(user.getEmail())
                .firstName(user.getFirstName())
                .lastName(user.getLastName())
                .token(token)
                .refreshToken(refreshToken)
                .expiresIn(86400000L)
                .tokenType("Bearer")
                .lastLoginAt(user.getLastLoginAt())
                .build();
    }

    @Override
    public AuthResponse refreshToken(String refreshToken) {
        if (!jwtTokenProvider.validateToken(refreshToken)) {
            throw new BadRequestException("Invalid refresh token");
        }

        Long userId = jwtTokenProvider.getUserIdFromToken(refreshToken);
        User user = userRepository.findByIdAndNotDeleted(userId)
                .orElseThrow(() -> new ResourceNotFoundException("User", "id", userId));

        String newToken = jwtTokenProvider.generateToken(user.getId(), user.getEmail(), "ROLE_USER");
        String newRefreshToken = jwtTokenProvider.generateRefreshToken(user.getId(), user.getEmail());

        return AuthResponse.builder()
                .id(user.getId())
                .email(user.getEmail())
                .firstName(user.getFirstName())
                .lastName(user.getLastName())
                .token(newToken)
                .refreshToken(newRefreshToken)
                .expiresIn(86400000L)
                .tokenType("Bearer")
                .build();
    }

    @Override
    public void forgotPassword(String email) {
        log.info("Forgot password request for email: {}", email);
        User user = userRepository.findByEmailAndNotDeleted(email)
                .orElseThrow(() -> new ResourceNotFoundException("User", "email", email));
        // TODO: Send reset email with code
    }

    @Override
    @Transactional
    public void resetPassword(String email, String resetCode, String newPassword) {
        log.info("Reset password request for email: {}", email);
        User user = userRepository.findByEmailAndNotDeleted(email)
                .orElseThrow(() -> new ResourceNotFoundException("User", "email", email));
        // TODO: Verify reset code
        user.setPassword(passwordEncoder.encode(newPassword));
        userRepository.save(user);
    }

    @Override
    public void verifyEmail(String email, String verificationCode) {
        log.info("Email verification for: {}", email);
        User user = userRepository.findByEmailAndNotDeleted(email)
                .orElseThrow(() -> new ResourceNotFoundException("User", "email", email));
        // TODO: Verify code
        user.setIsEmailVerified(true);
        userRepository.save(user);
    }

    @Override
    public void logout(Long userId) {
        log.info("User logout: {}", userId);
        // Token invalidation handled on client side
    }
}
