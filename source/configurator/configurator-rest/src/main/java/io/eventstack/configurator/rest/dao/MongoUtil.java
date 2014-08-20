package io.eventstack.configurator.rest.dao;

import com.mongodb.DBCollection;
import com.mongodb.MongoClient;
import com.mongodb.MongoClientURI;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.net.UnknownHostException;
import java.util.concurrent.atomic.AtomicReference;

/**
 * Created by gavin on 8/17/14.
 */
public class MongoUtil {

    static final String APPS_COLLECTION = "apps";
    static final String SESSIONS_COLLECTION = "sessions";

    private static final Logger LOGGER = LoggerFactory.getLogger(MongoUtil.class);

    private static AtomicReference<MongoClient> client = new AtomicReference<MongoClient>();

    private static AtomicReference<MongoClientURI> mongoClientURI = new AtomicReference<MongoClientURI>();

    static DBCollection getDBCollection(String collectionName) throws UnknownHostException {
        return getClient().getDB(mongoClientURI.get().getDatabase()).getCollection(collectionName);
    }

    static MongoClient getClient() throws UnknownHostException {
        if (client.get() == null)
            client.set(new MongoClient(getMongoURI()));

        return client.get();
    }

    static MongoClientURI getMongoURI() {
        synchronized (MongoUtil.class) {
            if (mongoClientURI.get() != null)
                return mongoClientURI.get();

            String mongoUri = System.getProperty("mongoUri");
            if (mongoUri == null || mongoUri.length() == 0) {
                LOGGER.warn("mongoUri not defined");
                return null;
            }

            MongoClientURI clientURI = new MongoClientURI(mongoUri);
            mongoClientURI.set(clientURI);

            return clientURI;
        }
    }
}
