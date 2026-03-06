package com.userservice.userservice.controller;

import com.userservice.userservice.dto.ProfessionalProfileRequest;
import com.userservice.userservice.dto.UserRequest;
import com.userservice.userservice.dto.UserResponse;
import com.userservice.userservice.dto.UserStatusRequest;
import com.userservice.userservice.model.UserRole;
import com.userservice.userservice.service.UserService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/users")
@RequiredArgsConstructor
public class UserController {

    private final UserService userService;

    @PostMapping
    public ResponseEntity<UserResponse> createUser(
            @Valid @RequestBody UserRequest request
    ) {
        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(userService.createUser(request));
    }

    @GetMapping("/{id}")
    public ResponseEntity<UserResponse> getUserById(
            @PathVariable Long id
    ) {
        return ResponseEntity.ok(userService.getUserById(id));
    }

    @GetMapping
    public ResponseEntity<List<UserResponse>> getUsers(
            @RequestParam(required = false) UserRole role
    ) {
        return ResponseEntity.ok(userService.getUsers(role));
    }

    /*
       Gateway will inject:
       X-User-Id
     */
    @GetMapping("/me")
    public ResponseEntity<UserResponse> getCurrentUser(
            @RequestHeader("X-User-Id") Long userId
    ) {
        return ResponseEntity.ok(userService.getCurrentUser(userId));
    }

    @PutMapping("/me/profile")
    public ResponseEntity<UserResponse> updateProfessionalProfile(
            @RequestHeader("X-User-Id") Long userId,
            @Valid @RequestBody ProfessionalProfileRequest request
    ) {
        return ResponseEntity.ok(
                userService.updateProfessionalProfile(userId, request)
        );
    }

    @PatchMapping("/{id}/status")
    public ResponseEntity<UserResponse> updateUserStatus(
            @PathVariable Long id,
            @Valid @RequestBody UserStatusRequest request
    ) {
        return ResponseEntity.ok(
                userService.updateUserStatus(id, request)
        );
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteUser(
            @PathVariable Long id
    ) {
        userService.deleteUser(id);
        return ResponseEntity.noContent().build();
    }
}