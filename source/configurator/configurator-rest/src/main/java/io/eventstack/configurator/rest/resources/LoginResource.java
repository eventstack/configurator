package io.eventstack.configurator.rest.resources;

import io.eventstack.PasscodeMap;
import io.eventstack.configurator.rest.representations.LoginRequest;
import io.eventstack.configurator.rest.representations.LoginResponse;
import io.eventstack.configurator.rest.representations.PasscodeRequest;
import io.eventstack.configurator.rest.sms.PasscodeSender;

import javax.ws.rs.Consumes;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by gavin on 8/16/14.
 */
@Path("login")
public class LoginResource {
    @POST
    @Path("sendPasscode")
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public Response sendPasscode(PasscodeRequest passcodeRequest) {
        String passcode = new PasscodeSender().sendPasscode(passcodeRequest.getMobile());
        // save mobile number and passcode

        Map<String, String> resp = new HashMap<String, String>();
        resp.put("message", "Passcode sent");

        return Response.ok(resp).build();
    }

    @POST
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public Response login(LoginRequest loginRequest) {

        LoginResponse loginResponse = new LoginResponse();
        loginResponse.setSuccess(PasscodeMap.verifyPasscode(loginRequest.getMobile(), loginRequest.getPasscode()));

        return Response.ok(loginResponse).build();
    }

}
