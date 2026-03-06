package com.userservice.userservice.service;

import com.userservice.userservice.dto.*;

import com.userservice.userservice.model.UserRole;

import java.util.List;

public interface UserService {

    UserResponse createUser(UserRequest request);

    UserResponse getUserById(Long id);

    List<UserResponse> getUsers(UserRole role);

    UserResponse getCurrentUser(Long userId);

    UserResponse updateProfessionalProfile(Long userId, ProfessionalProfileRequest request);

    UserResponse updateUserStatus(Long id, UserStatusRequest request);

    void deleteUser(Long id);
}