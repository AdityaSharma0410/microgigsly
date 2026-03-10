package com.contactservice.contactservice.mapper;

import com.contactservice.contactservice.dto.ContactQueryRequest;
import com.contactservice.contactservice.dto.ContactQueryResponse;
import com.contactservice.contactservice.model.ContactQuery;

public final class ContactQueryMapper {

    private ContactQueryMapper() {}

    public static ContactQuery toEntity(ContactQueryRequest request) {
        return ContactQuery.builder()
                .name(request.getName())
                .email(request.getEmail())
                .mobile(request.getMobile())
                .queryType(request.getQueryType())
                .message(request.getMessage())
                .build();
    }

    public static ContactQueryResponse toResponse(ContactQuery query) {
        return ContactQueryResponse.builder()
                .id(query.getId())
                .name(query.getName())
                .email(query.getEmail())
                .mobile(query.getMobile())
                .queryType(query.getQueryType())
                .message(query.getMessage())
                .status(query.getStatus())
                .adminResponse(query.getAdminResponse())
                .respondedById(query.getRespondedById())
                .respondedByName(query.getRespondedByName())
                .respondedAt(query.getRespondedAt())
                .createdAt(query.getCreatedAt())
                .updatedAt(query.getUpdatedAt())
                .build();
    }
}
