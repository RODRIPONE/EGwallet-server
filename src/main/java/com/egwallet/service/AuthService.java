package com.egwallet.service;

import com.egwallet.dto.request.LoginRequest;
import com.egwallet.dto.request.RegisterRequest;
import com.egwallet.dto.response.AuthResponse;

public interface AuthService {
    AuthResponse register(RegisterRequest request);
    AuthResponse login(LoginRequest request);
    AuthResponse refreshToken(String refreshToken);
    void forgotPassword(String email);
    void resetPassword(String email, String resetCode, String newPassword);
    void verifyEmail(String email, String verificationCode);
    void logout(Long userId);
}
