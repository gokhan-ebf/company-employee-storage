package com.ebf.storage.api.model;

public class FieldErrorDTO {

    private String path;
    private String message;

    public FieldErrorDTO(String path, String message) {
        this.path = path;
        this.message = message;
    }

    public String getPath() {
        return path;
    }

    public String getMessage() {
        return message;
    }
}