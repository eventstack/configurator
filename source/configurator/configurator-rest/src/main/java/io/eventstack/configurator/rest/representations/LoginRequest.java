package io.eventstack.configurator.rest.representations;

/**
 * Created by gavin on 8/17/14.
 */
public class LoginRequest {
    private String mobile;
    private String passcode;

    public String getMobile() {
        return mobile;
    }

    public void setMobile(String mobile) {
        this.mobile = mobile;
    }

    public String getPasscode() {
        return passcode;
    }

    public void setPasscode(String passcode) {
        this.passcode = passcode;
    }
}
