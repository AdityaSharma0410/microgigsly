package com.contactservice.contactservice.repository;

import com.contactservice.contactservice.model.ContactQuery;
import com.contactservice.contactservice.model.QueryStatus;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ContactQueryRepository extends JpaRepository<ContactQuery, Long> {

    List<ContactQuery> findByStatus(QueryStatus status);

    List<ContactQuery> findByEmail(String email);
}
