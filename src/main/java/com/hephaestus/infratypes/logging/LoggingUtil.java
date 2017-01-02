package com.hephaestus.infratypes.logging;

import org.apache.log4j.Appender;
import org.apache.log4j.Logger;

public class LoggingUtil
{
	private static final Logger log = Logger.getLogger(LoggingUtil.class);
	
	public static final String ENV_DEF = "-wrhEnv";
	
	/**
	 * The log4j properties file contains several Appenders. This method will
	 * remove appropriate Appenders depending on the environment.
	 * 
	 * @param operationalEnv
	 */
	public static void setupEnv(String operationalEnv)
	{
		log.info("Init Param -wrhEnv " + operationalEnv);
		if ("DEV".equalsIgnoreCase(operationalEnv))
		{
			Appender rfAppender = log.getRootLogger().getAppender("prod");
			if (rfAppender != null)
			{
				log.getRootLogger().removeAppender(rfAppender);
				log.info("IGNORE THE ABOVE ERROR! THIS ONLY HAPPENS IN DEV" + "\n\tRemoving RollingFileAppender from logging configuration");
			}
			else
			{
				log
				      .warn("Could Not Remove RollingFileAppender used in production from logging configuration");
			}
		}
		else
		// assume everything else is PROD
		{
			Appender conAppender = log.getRootLogger().getAppender("stdout");
			if (conAppender != null)
			{
				log.getRootLogger().removeAppender(conAppender);
			}
			else
			{
				log
				      .warn("Could Not Remove ConsoleAppender used in dev from logging configuration");
			}
		}
	}
	
	/**
	 * 
	 * @param envLogConfigFile - full name of log4j.properties
	 */
	public static void setupLoggingForEnv(String envLogConfigFile)
	{
	
	}
}
