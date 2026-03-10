package com.contactservice.contactservice.service;

import com.contactservice.contactservice.dto.ContactQueryRequest;
import com.contactservice.contactservice.dto.ContactQueryResponse;
import com.contactservice.contactservice.dto.ContactResponseRequest;
import com.contactservice.contactservice.model.QueryStatus;

import java.util.List;

public interface ContactQueryService {

    ContactQueryResponse submitQuery(ContactQueryRequest request);

    ContactQueryResponse respondToQuery(Long queryId, ContactResponseRequest request);

    List<ContactQueryResponse> listQueries(QueryStatus status);

    ContactQueryResponse getQuery(Long id);
}
