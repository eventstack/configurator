package io.eventstack.configurator.rest.dao;

import com.mongodb.BasicDBObject;
import com.mongodb.DBCollection;
import com.mongodb.DBCursor;
import com.mongodb.DBObject;
import io.eventstack.configurator.rest.entity.AccessKey;
import io.eventstack.configurator.rest.entity.App;
import io.eventstack.configurator.rest.entity.Environment;
import io.eventstack.configurator.rest.entity.PropertyDef;

import java.net.UnknownHostException;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by gavin on 8/17/14.
 */
public class AppDao {

    public List<App> findAppsByUser(String ownerId) {
        DBCursor dbCursor = null;
        try {
            dbCursor = getAppsCollection().find(new BasicDBObject("owners", ownerId));

            List<App> apps = new ArrayList<App>();
            while (dbCursor.hasNext()) {
                DBObject dbObject = dbCursor.next();
                apps.add(new App(dbObject.toMap()));
            }

            return apps;
        } catch (UnknownHostException e) {
            e.printStackTrace();
        } finally {
            if (dbCursor != null) dbCursor.close();
        }
        return null;
    }

    public App create(App app) {
        try {
            if (app.getId() == null)
                app.setId(IdUtil.generateAppId());

            getAppsCollection().insert(new BasicDBObject(app.toMap()));
        } catch (UnknownHostException e) {
            e.printStackTrace();
        }

        return app;
    }

    public App find(String id) {
        try {
            DBObject o = getAppsCollection().findOne(new BasicDBObject("_id", id));
            if (o != null)
                return new App(o.toMap());
        } catch (UnknownHostException e) {
            e.printStackTrace();
        }

        return null;
    }

    public void createEnvironment(String appId, Environment environment) {
        try {
            DBObject q = new BasicDBObject("_id", appId);
            DBObject update = new BasicDBObject("$push",
                    new BasicDBObject("environments", new BasicDBObject(environment.toMap())));
            DBObject o = getAppsCollection().findAndModify(q, update);
        } catch (UnknownHostException e) {
            e.printStackTrace();
        }
    }

    public void deleteEnvironment(String appId, String environmentId) {

    }

    public void createPropertyDef(String appId, PropertyDef propertyDef) {
        try {
            DBObject q = new BasicDBObject("_id", appId);
            DBObject update = new BasicDBObject("$push",
                    new BasicDBObject("propertySet", new BasicDBObject(propertyDef.toMap())));
            getAppsCollection().update(q, update, true, false);
        } catch (UnknownHostException e) {
            e.printStackTrace();
        }
    }


    public AccessKey createAccessKey(String appId, String environmentId) {
        return null;
    }

    private DBCollection getAppsCollection() throws UnknownHostException {
        return MongoUtil.getDBCollection(MongoUtil.APPS_COLLECTION);
    }
}
