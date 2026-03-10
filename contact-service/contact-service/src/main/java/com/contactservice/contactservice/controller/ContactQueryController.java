package com.contactservice.contactservice.controller;

import com.contactservice.contactservice.dto.ContactQueryRequest;
import com.contactservice.contactservice.dto.ContactQueryResponse;
import com.contactservice.contactservice.dto.ContactResponseRequest;
import com.contactservice.contactservice.model.QueryStatus;
import com.contactservice.contactservice.service.ContactQueryService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/contact-queries")
@RequiredArgsConstructor
public class ContactQueryController {

    private final ContactQueryService contactQueryService;

    /** Public endpoint – anyone can submit a contact query (no auth required at service level). */
    @PostMapping
    public ResponseEntity<ContactQueryResponse> submitQuery(
            @Valid @RequestBody ContactQueryRequest request
    ) {
        return ResponseEntity.status(HttpStatus.CREATED).body(contactQueryService.submitQuery(request));
    }

    @PostMapping("/{id}/respond")
    public ResponseEntity<ContactQueryResponse> respondToQuery(
            @PathVariable Long id,
            @Valid @RequestBody ContactResponseRequest request
    ) {
        return ResponseEntity.ok(contactQueryService.respondToQuery(id, request));
    }

    @GetMapping
    public ResponseEntity<List<ContactQueryResponse>> listQueries(
            @RequestParam(required = false) QueryStatus status
    ) {
        return ResponseEntity.ok(contactQueryService.listQueries(status));
    }

    @GetMapping("/{id}")
    public ResponseEntity<ContactQueryResponse> getQuery(@PathVariable Long id) {
        return ResponseEntity.ok(contactQueryService.getQuery(id));
    }
}
