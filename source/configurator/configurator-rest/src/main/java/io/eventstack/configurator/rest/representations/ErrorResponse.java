package io.eventstack.configurator.rest.representations;

import com.fasterxml.jackson.databind.ObjectMapper;

import java.io.IOException;

/**
 * Created by gavin on 9/1/14.
 */
public class ErrorResponse {
    private final Integer errorCode;
    private final String message;
    public ErrorResponse(Integer errorCode, String message) {
        this.errorCode = errorCode;
        this.message = message;
    }

    public Integer getErrorCode() {
        return errorCode;
    }

    public String getMessage() {
        return message;
    }

    public String toJson() {
        try {
            return getMapper().writeValueAsString(this);
        } catch (IOException e) {
            return "error";
        }
    }

    static ObjectMapper getMapper() {
        final ObjectMapper mapper = new ObjectMapper();
        return mapper;
    }
}
