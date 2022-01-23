package uk.co.cerihughes.mgm;

import org.eclipse.jetty.server.Server;
import org.eclipse.jetty.servlet.DefaultServlet;
import org.eclipse.jetty.servlet.ServletContextHandler;
import org.eclipse.jetty.servlet.ServletHolder;
import org.jboss.resteasy.plugins.server.servlet.HttpServletDispatcher;

public class Main {
    private static final String CONTEXT_ROOT = "/";

    public static void main(String[] args) throws Exception {
        int port = Integer.parseInt(System.getenv().getOrDefault("PORT", "8080"));
        final Server server = new Server(port);

        final ServletContextHandler context = new ServletContextHandler(
                server, CONTEXT_ROOT);

        final ServletHolder restEasyServlet = new ServletHolder(
                new HttpServletDispatcher());

        restEasyServlet.setInitParameter("javax.ws.rs.Application", MGMApplication.class.getName());
        context.addServlet(restEasyServlet, "/*");

        final ServletHolder defaultServlet = new ServletHolder(
                new DefaultServlet());
        context.addServlet(defaultServlet, CONTEXT_ROOT);

        server.start();
        server.join();
    }
}
