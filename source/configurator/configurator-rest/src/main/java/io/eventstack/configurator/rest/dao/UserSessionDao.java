package io.eventstack.configurator.rest.dao;

import com.mongodb.BasicDBObject;
import com.mongodb.DBCollection;
import com.mongodb.DBObject;
import io.eventstack.configurator.rest.entity.UserSession;
import org.bson.types.ObjectId;

import java.net.UnknownHostException;

/**
 * Created by gavin on 8/17/14.
 */
public class UserSessionDao {
    public UserSession create(UserSession userSession) throws UnknownHostException {
        DBObject insertObj = new BasicDBObject(userSession.toMap());
        getUserSessionCollection().insert(insertObj);

        userSession.setId(insertObj.get("_id").toString());

        return userSession;
    }

    public UserSession find(String sessionId) throws UnknownHostException {
        DBObject obj = getUserSessionCollection().findOne(new BasicDBObject("_id", new ObjectId(sessionId)));

        if (obj != null)
            return new UserSession(obj.toMap());

        return null;
    }

    private DBCollection getUserSessionCollection() throws UnknownHostException {
        return MongoUtil.getDBCollection(MongoUtil.SESSIONS_COLLECTION);
    }
}
