package com.adminservice.adminservice.service.impl;

import com.adminservice.adminservice.client.UserClient;
import com.adminservice.adminservice.dto.*;
import com.adminservice.adminservice.exception.AdminAccessDeniedException;
import com.adminservice.adminservice.exception.AdminNotFoundException;
import com.adminservice.adminservice.exception.BadRequestException;
import com.adminservice.adminservice.exception.ConflictException;
import com.adminservice.adminservice.mapper.AdminMapper;
import com.adminservice.adminservice.model.Admin;
import com.adminservice.adminservice.repository.AdminRepository;
import com.adminservice.adminservice.security.CurrentUserService;
import com.adminservice.adminservice.service.AdminService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class AdminServiceImpl implements AdminService {

    private final AdminRepository adminRepository;
    private final UserClient userClient;
    private final CurrentUserService currentUserService;

    @Override
    @Transactional(readOnly = true)
    public AdminResponse getCurrentAdmin() {
        Long currentUserId = currentUserService.getCurrentUserId();
        String currentRole = currentUserService.getCurrentUserRole();

        if (!"ADMIN".equals(currentRole)) {
            throw new AdminAccessDeniedException("Admin privileges required");
        }

        Admin admin = adminRepository.findByUserId(currentUserId)
                .orElseThrow(() -> new AdminNotFoundException(currentUserId));

        if (Boolean.FALSE.equals(admin.getIsActive())) {
            throw new AdminAccessDeniedException("Admin account is inactive");
        }

        UserFeignResponse user = getUserQuietly(currentUserId);
        return AdminMapper.toResponse(admin, user);
    }

    @Override
    @Transactional
    public AdminResponse updateCurrentAdmin(AdminRequest request) {
        Long currentUserId = currentUserService.getCurrentUserId();
        String currentRole = currentUserService.getCurrentUserRole();

        if (!"ADMIN".equals(currentRole)) {
            throw new AdminAccessDeniedException("Admin privileges required");
        }

        Admin admin = adminRepository.findByUserId(currentUserId)
                .orElseThrow(() -> new AdminNotFoundException(currentUserId));

        admin.updateProfile(request.getDisplayName(), request.getIsActive());
        Admin saved = adminRepository.save(admin);

        UserFeignResponse user = getUserQuietly(currentUserId);
        return AdminMapper.toResponse(saved, user);
    }

    @Override
    @Transactional
    public AdminResponse createAdminUser(CreateAdminRequest request) {
        String currentRole = currentUserService.getCurrentUserRole();
        if (!"ADMIN".equals(currentRole)) {
            throw new AdminAccessDeniedException("Only admins can create other admin users");
        }

        // Create the user in user-service
        UserCreateRequest userRequest = new UserCreateRequest();
        userRequest.setFullName(request.getFullName().trim());
        userRequest.setEmail(request.getEmail().trim().toLowerCase());
        userRequest.setPassword(request.getPassword());
        userRequest.setRole("ADMIN");
        userRequest.setBio("Admin account created via admin-service");

        UserFeignResponse createdUser;
        try {
            createdUser = userClient.createUser(userRequest);
        } catch (Exception e) {
            String msg = e.getMessage() != null ? e.getMessage() : "";
            if (msg.contains("409") || msg.contains("already")) {
                throw new ConflictException("A user with email '" + userRequest.getEmail() + "' already exists");
            }
            throw new BadRequestException("Failed to create user: " + msg);
        }

        if (adminRepository.existsByUserId(createdUser.id())) {
            throw new ConflictException("Admin record already exists for this user");
        }

        String displayName = (request.getDisplayName() != null && !request.getDisplayName().isBlank())
                ? request.getDisplayName().trim()
                : createdUser.fullName();

        Admin admin = Admin.builder()
                .userId(createdUser.id())
                .displayName(displayName)
                .isActive(true)
                .build();

        Admin saved = adminRepository.save(admin);
        return AdminMapper.toResponse(saved, createdUser);
    }

    private UserFeignResponse getUserQuietly(Long userId) {
        try { return userClient.getUser(userId); } catch (Exception e) { return null; }
    }
}
