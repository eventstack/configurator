package io.eventstack.configurator.rest.dao;

import io.eventstack.configurator.rest.entity.App;
import org.junit.Before;
import org.junit.Test;

import java.util.Arrays;
import java.util.List;

import static org.junit.Assert.assertNotNull;

/**
 * Created by gavin on 8/17/14.
 */
public class AppDaoIntegrationTest {

    @Before
    public void setupConfig() {
        System.setProperty("mongoUri", "mongodb://localhost:27017/configurator");
    }

    @Test
    public void testCreateApp() {
        App app = new App();
        app.setId("test-app");
        app.setName("Test App");
        app.setOwners(Arrays.asList("123123123"));
        new AppDao().create(app);
    }

    @Test
    public void findAppsByOwnerId() {
        List<App> apps = new AppDao().findAppsByUser("123123123");
        System.out.println(Arrays.deepToString(apps.toArray()));
    }

    @Test
    public void findAppById() {
        App app = new AppDao().find("test-app");
        assertNotNull(app);
    }
}
