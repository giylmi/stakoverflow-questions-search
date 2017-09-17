package com.example.piano.api;

/**
 * Created by adel on 9/17/17.
 */
public class ApiError {
    private String id;
    private String message;
    private Object entity;

    public ApiError id(String id) {
        this.id = id;
        return this;
    }

    public ApiError message(String message) {
        this.message = message;
        return this;
    }

    public ApiError entity(Object entity) {
        this.entity = entity;
        return this;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public Object getEntity() {
        return entity;
    }

    public void setEntity(Object entity) {
        this.entity = entity;
    }
}
