package com.adminservice.adminservice.service;

import com.adminservice.adminservice.dto.AdminRequest;
import com.adminservice.adminservice.dto.AdminResponse;
import com.adminservice.adminservice.dto.CreateAdminRequest;

public interface AdminService {

    AdminResponse getCurrentAdmin();

    AdminResponse updateCurrentAdmin(AdminRequest request);

    AdminResponse createAdminUser(CreateAdminRequest request);
}
