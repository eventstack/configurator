package io.eventstack.configurator.rest.representations;

/**
 * Created by gavin on 9/1/14.
 */
public class OperationResponse {
    private boolean success;
    private String msg;

    public OperationResponse(boolean success, String msg) {
        this.success = success;
        this.msg = msg;
    }

    public boolean isSuccess() {
        return success;
    }

    public void setSuccess(boolean success) {
        this.success = success;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }
}
