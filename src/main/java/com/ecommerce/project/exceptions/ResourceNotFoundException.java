package com.ecommerce.project.exceptions;

// This is our custom exception, with custom fields and constructors to provide a more detail error
public class ResourceNotFoundException extends RuntimeException {
    public String resourceName;
    public String field;
    public String fieldName;
    public Long fieldId;

    // We need a blank constructor here is for Spring to register this exception class as a bean
    // Because we've already have other constructors, so Java won't automatically create a blank constructor for us
    public ResourceNotFoundException() {
    }

    // We have two constructors for different resources
    public ResourceNotFoundException(String resourceName, String field, String fieldName) {
        // Invoke the super class constructor (RunTimeException -> Exception -> Throwable) with message param
        super(String.format("%s is not found with %s: %s", resourceName, field, fieldName));
        this.resourceName = resourceName;
        this.field = field;
        this.fieldName = fieldName;
    }

    public ResourceNotFoundException(String resourceName, String field, Long fieldId) {
        super(String.format("%s is not found with %s: %d", resourceName, field, fieldId));
        this.resourceName = resourceName;
        this.field = field;
        this.fieldId = fieldId;
    }
}
