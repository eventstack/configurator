package io.eventstack.configurator.client;

import org.glassfish.jersey.client.ClientConfig;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.ws.rs.client.Client;
import javax.ws.rs.client.ClientBuilder;
import javax.ws.rs.core.MediaType;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;

/**
 * Created by gavin on 8/16/14.
 */
public class ConfiguratorClient {

    private static final Logger LOGGER = LoggerFactory.getLogger(ConfiguratorClient.class);

    private static ConcurrentMap<String, Map<String, String>> configs = new ConcurrentHashMap<String, Map<String, String>>();

    private final String configServer;
    private final String appId;
    private final String env;

    public static Map<String, String> getConfiguration(String appId, boolean refresh) {
        String env = System.getProperty("env");
        String key = String.format("%s/%s", appId, env);
        Map<String, String> value = configs.get(key);
        if (refresh || value == null) {
            String configServer = System.getProperty("configServer");
            ConfiguratorClient client = new ConfiguratorClient(configServer, appId, env);
            value = client.loadConfig();
            configs.put(key, value);
        }

        return value;
    }

    public ConfiguratorClient(String configServer, String appId, String env) {
        this.configServer = configServer;
        this.appId = appId;
        this.env = env;
    }

    private Map<String, String> loadConfig() {

        Client client = ClientBuilder.newClient(new ClientConfig());

        final String target = String.format("%s/configs", configServer);
        final String path = String.format("%s/%s", appId, env);

        LOGGER.info("Loading configuration for {} from {}", path, target);

        Map<String, String> entity = client.target(target)
                .path(path)
                .request(MediaType.APPLICATION_JSON)
                .get(Map.class);

        return entity;
    }

    public static void main(String[] args) {
        Map<String,String> config = ConfiguratorClient.getConfiguration("configurator-sample", false);
        System.out.println(config);

        config = ConfiguratorClient.getConfiguration("configurator-sample", false);
        System.out.println(config);
    }
}
