package io.eventstack.configurator.rest.resources;

import io.eventstack.configurator.rest.PasscodeMap;
import io.eventstack.configurator.rest.dao.UserSessionDao;
import io.eventstack.configurator.rest.entity.UserSession;
import io.eventstack.configurator.rest.representations.LoginRequest;
import io.eventstack.configurator.rest.representations.LoginResponse;
import io.eventstack.configurator.rest.representations.PasscodeRequest;
import io.eventstack.configurator.rest.sms.PasscodeSender;

import javax.servlet.http.HttpServletRequest;
import javax.ws.rs.Consumes;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.NewCookie;
import javax.ws.rs.core.Response;
import java.net.UnknownHostException;
import java.util.Date;
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
    public Response login(LoginRequest loginRequest, @Context HttpServletRequest r) throws UnknownHostException {

        LoginResponse loginResponse = new LoginResponse();
        loginResponse.setSuccess(PasscodeMap.verifyPasscode(loginRequest.getMobile(), loginRequest.getPasscode()));

        if (loginResponse.isSuccess()) {
            // create user session
            UserSession session = new UserSession();
            session.setUserId(loginRequest.getMobile());
            session.setCreatedDt(new Date());

            UserSession createdSession = new UserSessionDao().create(session);

            System.out.println("session id=" + createdSession.getId());

            r.getSession(true).setAttribute("sid", createdSession.getId());
            return Response.ok(loginResponse).cookie(new NewCookie("sid", createdSession.getId())).build();
        }

        return Response.ok(loginResponse).build();
    }
}
