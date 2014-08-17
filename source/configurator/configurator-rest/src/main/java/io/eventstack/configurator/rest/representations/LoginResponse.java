package io.eventstack.configurator.rest.representations;

/**
 * Created by gavin on 8/17/14.
 */
public class LoginResponse {
    private boolean success;
    private String sessionId;

    public boolean isSuccess() {
        return success;
    }

    public void setSuccess(boolean success) {
        this.success = success;
    }

    public String getSessionId() {
        return sessionId;
    }

    public void setSessionId(String sessionId) {
        this.sessionId = sessionId;
    }
}
