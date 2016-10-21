package org.vsg.common.configuration.guice;

import java.io.File;
import java.util.Properties;

import org.apache.commons.configuration2.Configuration;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.vsg.common.configration.CommonConfigurationSupport;

public class SystemParamPropertiesPlaceHolder implements CommonConfigurationSupport{
	
	private static final Logger logger = LoggerFactory.getLogger(SystemParamPropertiesPlaceHolder.class);		
	
	private String configPath = "system:config.path";
	
	private final Properties props =  new Properties();	
	
	public Properties getProperties() {
		return this.props;
	}
	
	private Configuration config ; 
	
	
	
	public Configuration getConfig() {
		return config;
	}



	public void setConfig(Configuration config) {
		this.config = config;
	}



	public void afterPropertiesSet() throws Exception {
		String path = null;
		if (configPath != null) {
			if (configPath.startsWith("system:")) {
				path = configPath.split(":")[1];
			}

			else {
				throw new Exception("Did not support property path");
			}
		}

		// load System path
		String systemPath = null;
		systemPath = System.getProperty(path);
		
		if (systemPath == null) {
			throw new Exception("Not defined System Property \"config.path\"!Please setting by using \"java -Dconfig.path=XXXX\" .");
		}

		if (!systemPath.endsWith(File.separator)) {
			systemPath = systemPath + File.separator;
		}
		
		logger.debug("Loading System Properties Path [" + systemPath + "]");
		
		
		// check path exist
		File sysPath = new File(systemPath);
		if (!sysPath.exists()) {
			throw new Exception("System Folder [\""+systemPath+"\"] does not exist! Please check it!");
		}

		String systemConfFile = "system.xml";


		// load config using apache configartion
		/*
		DefaultConfigurationBuilder builder = new DefaultConfigurationBuilder();
		builder.setFile(new File(sysPath, systemConfFile) );
		config = builder.getConfiguration(true);
		*/
		
	}

}
