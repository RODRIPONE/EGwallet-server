package com.egwallet.exception;

public class DuplicateResourceException extends RuntimeException {
    private String fieldName;
    private Object fieldValue;

    public DuplicateResourceException(String message) {
        super(message);
    }

    public DuplicateResourceException(String fieldName, Object fieldValue) {
        super(String.format("%s already exists: %s", fieldName, fieldValue));
        this.fieldName = fieldName;
        this.fieldValue = fieldValue;
    }

    public String getFieldName() {
        return fieldName;
    }

    public Object getFieldValue() {
        return fieldValue;
    }
}
