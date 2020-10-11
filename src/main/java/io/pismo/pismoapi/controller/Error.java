package io.pismo.pismoapi.controller;

import lombok.Data;

@Data
public class Error {

    private final String fieldName;
    private final String message;

    public Error(final String fieldName, final String message) {
        this.fieldName = fieldName;
        this.message = message;
    }

    public Error(final String message) {
        this(null, message);
    }
}
