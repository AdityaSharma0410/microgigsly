package com.adminservice.adminservice.controller;

import com.adminservice.adminservice.dto.AdminRequest;
import com.adminservice.adminservice.dto.AdminResponse;
import com.adminservice.adminservice.dto.CreateAdminRequest;
import com.adminservice.adminservice.service.AdminService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/admin")
@RequiredArgsConstructor
public class AdminController {

    private final AdminService adminService;

    @GetMapping("/me")
    public ResponseEntity<AdminResponse> getCurrentAdmin() {
        return ResponseEntity.ok(adminService.getCurrentAdmin());
    }

    @PatchMapping("/me")
    public ResponseEntity<AdminResponse> updateCurrentAdmin(
            @Valid @RequestBody AdminRequest request
    ) {
        return ResponseEntity.ok(adminService.updateCurrentAdmin(request));
    }

    @PostMapping("/admins")
    public ResponseEntity<AdminResponse> createAdmin(
            @Valid @RequestBody CreateAdminRequest request
    ) {
        return ResponseEntity.status(HttpStatus.CREATED).body(adminService.createAdminUser(request));
    }
}
