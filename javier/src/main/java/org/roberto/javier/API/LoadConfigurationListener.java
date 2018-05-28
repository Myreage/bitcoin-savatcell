package org.roberto.javier.API;

import org.roberto.javier.PlugDB.PlugDB;

import javax.servlet.ServletContext;
import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;
import javax.servlet.annotation.WebListener;

@WebListener
public class LoadConfigurationListener implements ServletContextListener {
    public void contextInitialized(ServletContextEvent sce) {
        ServletContext context = sce.getServletContext();
        try {
        	context.setAttribute("pdb", new PlugDB("/dev/ttyACM0"));
        } catch(Exception e) {
        	System.out.println(e.toString());
        }
    }

    public void contextDestroyed(ServletContextEvent sce) {
        // ServletContext context = sce.getServletContext();
    }
}
