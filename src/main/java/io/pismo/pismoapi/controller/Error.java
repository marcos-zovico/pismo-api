package io.pismo.pismoapi.controller;

import lombok.Data;

@Data
public class Error {

    private final String fieldName;
    private final String message;

}
