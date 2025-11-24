package org.mql.darija.translator;

import org.glassfish.jersey.jackson.JacksonFeature;
import org.glassfish.jersey.server.ResourceConfig;
import org.glassfish.jersey.servlet.ServletContainer;

import org.eclipse.jetty.server.Server;
import org.eclipse.jetty.servlet.ServletContextHandler;
import org.eclipse.jetty.servlet.ServletHolder;

public class App {

    public static void main(String[] args) throws Exception {

        // 1) Configuration Jersey
        ResourceConfig config = new ResourceConfig();
        config.packages("org.mql.darija.translator");
        config.register(JacksonFeature.class);

        // 2) Jetty server
        Server server = new Server(8080);

        ServletContextHandler context = new ServletContextHandler(server, "/");
        ServletHolder servlet = new ServletHolder(new ServletContainer(config));
        context.addServlet(servlet, "/*");

        // 3) Start server
        server.start();
        System.out.println("Backend running at: http://localhost:8080/");
        server.join();
    }
}
