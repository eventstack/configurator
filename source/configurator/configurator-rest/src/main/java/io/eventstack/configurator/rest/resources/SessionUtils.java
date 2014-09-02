package io.eventstack.configurator.rest.resources;

import io.eventstack.configurator.rest.dao.UserSessionDao;
import io.eventstack.configurator.rest.entity.UserSession;
import io.eventstack.configurator.rest.exception.InvalidTokenException;

import java.net.UnknownHostException;

/**
 * Created by gavin on 9/1/14.
 */
public class SessionUtils {
    static String getUserIdFromSession(String sessionId) throws UnknownHostException {
        if (sessionId == null)
            return null;

        UserSession userSession = new UserSessionDao().find(sessionId);
        if (userSession == null)
            return null;

        return userSession.getUserId();
    }

    static void assertLoggedIn(String sessionId) throws UnknownHostException, InvalidTokenException {
        if (sessionId == null)
            throw new InvalidTokenException("User not logged in");

        UserSession userSession = new UserSessionDao().find(sessionId);
        if (userSession == null)
            throw new InvalidTokenException("Invalid session id");
    }
}
