package com.contactservice.contactservice.exception;

public class ContactQueryNotFoundException extends RuntimeException {
    public ContactQueryNotFoundException(Long id) { super("Contact query not found with id: " + id); }
}
