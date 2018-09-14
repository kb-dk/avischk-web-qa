package dk.kb.avischk.qa.web;

import java.beans.PropertyVetoException;

import javax.naming.InitialContext;
import javax.naming.NamingException;
import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import dk.kb.avischk.dao.NewspaperQADaoFactory;

/**
 * Listener to handle the various setups and configuration sanity checks that can be carried out at when the
 * context is deployed/initalized.
 */
public class WebQAContextListener implements ServletContextListener {
    private final Logger log = LoggerFactory.getLogger(getClass());


    /**
     * On context initialisation this
     * i) Initialises the logging framework (logback).
     * ii) Initialises the configured DorqBackends 
     * @param sce
     * @throws java.lang.RuntimeException if anything at all goes wrong.
     */
    @Override
    public void contextInitialized(ServletContextEvent sce) {
        try {
            log.info("Initializing WebQA service v{}", getClass().getPackage().getImplementationVersion());
            InitialContext ctx = new InitialContext();
            String jdbcConnectionString = (String) ctx.lookup("java:/comp/env/avischk-web-qa/jdbc-connection-string");
            String jdbcUser = (String) ctx.lookup("java:/comp/env/avischk-web-qa/jdbc-user");
            String jdbcPassword = (String) ctx.lookup("java:/comp/env/avischk-web-qa/jdbc-password");
            NewspaperQADaoFactory.initialize(jdbcConnectionString, jdbcUser, jdbcPassword);
            
            String httpContentBase = (String) ctx.lookup("java:/comp/env/avischk-web-qa/http-content-base-string");
            ContentLocationResolver.setHttpContentBase(httpContentBase);
        } catch (NamingException e) {
            throw new RuntimeException("Failed to lookup settings", e);
        } catch (PropertyVetoException e) {
            throw new RuntimeException("Database connection driver issue", e);
        }
        log.info("WebQA service initialized.");
    }


    @Override
    public void contextDestroyed(ServletContextEvent sce) {
        log.debug("WebQA service destroyed");
    }

}