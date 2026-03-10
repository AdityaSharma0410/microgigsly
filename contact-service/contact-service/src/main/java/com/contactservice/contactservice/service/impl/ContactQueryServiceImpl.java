package com.contactservice.contactservice.service.impl;

import com.contactservice.contactservice.dto.ContactQueryRequest;
import com.contactservice.contactservice.dto.ContactQueryResponse;
import com.contactservice.contactservice.dto.ContactResponseRequest;
import com.contactservice.contactservice.exception.AccessDeniedException;
import com.contactservice.contactservice.exception.BadRequestException;
import com.contactservice.contactservice.exception.ContactQueryNotFoundException;
import com.contactservice.contactservice.mapper.ContactQueryMapper;
import com.contactservice.contactservice.model.ContactQuery;
import com.contactservice.contactservice.model.QueryStatus;
import com.contactservice.contactservice.repository.ContactQueryRepository;
import com.contactservice.contactservice.security.CurrentUserService;
import com.contactservice.contactservice.service.ContactQueryService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
public class ContactQueryServiceImpl implements ContactQueryService {

    private final ContactQueryRepository contactQueryRepository;
    private final CurrentUserService currentUserService;

    @Override
    @Transactional
    public ContactQueryResponse submitQuery(ContactQueryRequest request) {
        ContactQuery query = ContactQueryMapper.toEntity(request);
        return ContactQueryMapper.toResponse(contactQueryRepository.save(query));
    }

    @Override
    @Transactional
    public ContactQueryResponse respondToQuery(Long queryId, ContactResponseRequest request) {
        ContactQuery query = findOrThrow(queryId);

        if (query.getStatus() == QueryStatus.RESOLVED || query.getStatus() == QueryStatus.CLOSED) {
            throw new BadRequestException("Query is already resolved or closed");
        }

        String role = currentUserService.getCurrentUserRole();
        if (!"ADMIN".equals(role)) {
            throw new AccessDeniedException("Only admins can respond to contact queries");
        }

        Long adminId = currentUserService.getCurrentUserId();
        String adminName = currentUserService.getCurrentUserName();

        query.respond(request.getResponse(), request.getStatus(), adminId, adminName);
        return ContactQueryMapper.toResponse(contactQueryRepository.save(query));
    }

    @Override
    @Transactional(readOnly = true)
    public List<ContactQueryResponse> listQueries(QueryStatus status) {
        String role = currentUserService.getCurrentUserRole();
        if (!"ADMIN".equals(role)) {
            throw new AccessDeniedException("Only admins can list all contact queries");
        }
        List<ContactQuery> queries = status == null
                ? contactQueryRepository.findAll()
                : contactQueryRepository.findByStatus(status);
        return queries.stream()
                .map(ContactQueryMapper::toResponse)
                .toList();
    }

    @Override
    @Transactional(readOnly = true)
    public ContactQueryResponse getQuery(Long id) {
        String role = currentUserService.getCurrentUserRole();
        if (!"ADMIN".equals(role)) {
            throw new AccessDeniedException("Only admins can view contact query details");
        }
        return ContactQueryMapper.toResponse(findOrThrow(id));
    }

    private ContactQuery findOrThrow(Long id) {
        return contactQueryRepository.findById(id)
                .orElseThrow(() -> new ContactQueryNotFoundException(id));
    }
}
