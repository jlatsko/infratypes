package com.hephaestus.infratypes.utils;

import org.apache.log4j.Logger;
import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;

/**
 * Configures and provides access to Hibernate sessions. We no longer
 * need to tie the session to a ThreadLocal variable as of version 3.1.
 * 
 */
public class HibernateSessionFactory
{
    /**
     * Location of hibernate.cfg.xml file. Location should be on the
     * classpath as Hibernate uses #resourceAsStream style lookup for its
     * configuration file. The default classpath location of the hibernate
     * config file is in the default package. Use #setConfigFile() to update
     * the location of the configuration file for the current session.
     */
    
    private static final SessionFactory sessionFactory;

    private static final Logger log = Logger.getLogger(HibernateSessionFactory.class);
    
    // static initializer
    static
    {
        try 
        {
	     
	     log.debug("In HibernateUtil try-clause");
	     
            // Create the SessionFactory from hibernate.cfg.xml
            sessionFactory = new Configuration().configure().buildSessionFactory();
        } 
        catch (Throwable ex) 
        {
            // Make sure you log the exception, as it might be swallowed
            System.err.println("Initial SessionFactory creation failed." + ex);
            throw new ExceptionInInitializerError(ex);
        }
    }

    public static SessionFactory getSessionFactory() 
    {
        return sessionFactory;
    }

}