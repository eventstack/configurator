package io.eventstack.configurator.client;

import io.eventstack.configurator.client.ConfiguratorClient;

import javax.servlet.ServletConfig;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import java.util.Map;

/**
 * Created by gavin on 8/16/14.
 */
public class ConfiguratorServlet extends HttpServlet {

    @Override
    public void init(ServletConfig config) throws ServletException {
        super.init(config);

        String appId = config.getInitParameter("appId");
        System.out.println(appId);

        Map<String,String> appConfig = ConfiguratorClient.getConfiguration(appId, true);
        System.out.println(appConfig);

        for (Map.Entry<String,String> entry:appConfig.entrySet())
            getServletContext().setAttribute(entry.getKey(), entry.getValue());
    }
}
