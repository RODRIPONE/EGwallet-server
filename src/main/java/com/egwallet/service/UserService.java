package com.egwallet.service;

import com.egwallet.dto.request.UpdateUserRequest;
import com.egwallet.dto.response.UserResponse;

public interface UserService {
    UserResponse getUserById(Long userId);
    UserResponse updateUser(Long userId, UpdateUserRequest request);
    void changePassword(Long userId, String currentPassword, String newPassword);
    void deleteAccount(Long userId);
    boolean existsByEmail(String email);
}
