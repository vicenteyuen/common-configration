/**
 * 
 */
package org.vsg.common.configuration.guice;

import java.io.File;
import java.util.Iterator;

import org.apache.commons.configuration2.Configuration;
import org.apache.commons.configuration2.FileBasedConfiguration;
import org.apache.commons.configuration2.PropertiesConfiguration;
import org.apache.commons.configuration2.builder.FileBasedConfigurationBuilder;
import org.apache.commons.configuration2.builder.fluent.Parameters;
import org.apache.commons.configuration2.ex.ConfigurationException;

import com.google.inject.AbstractModule;
import com.google.inject.name.Names;

/**
 * @author ruanweibiao
 *
 */
public class ClassPathFileConfigrationModule extends AbstractModule {
	
	private String propertiesFile = "config.properties";
	
	public ClassPathFileConfigrationModule() {
		
	}
	
	public ClassPathFileConfigrationModule(String propertiesFile) {
		this.propertiesFile = propertiesFile;
	}

	/* (non-Javadoc)
	 * @see com.google.inject.AbstractModule#configure()
	 */
	@Override
	protected void configure() {
		
		Configuration configration = loadConfigration(propertiesFile);
		
		
		Iterator<String> keysIter = configration.getKeys();
		
		while ( keysIter.hasNext() ) {
			String key = keysIter.next();
			configration.getString(key);
			bind(String.class).annotatedWith(Names.named(key)).toInstance(configration.getString(key));
		}

	}
	
	
	private static Configuration loadConfigration(String propFileStr) {
		Parameters params = new Parameters();
		// Read data from this file
		File propertiesFile = new File(propFileStr);

		FileBasedConfigurationBuilder<FileBasedConfiguration> builder =
		    new FileBasedConfigurationBuilder<FileBasedConfiguration>(PropertiesConfiguration.class)
		    .configure(params.fileBased()
		        .setFile(propertiesFile));
		try
		{
		    Configuration config = builder.getConfiguration();
		    return config;

		    // config contains all properties read from the file
		}
		catch(ConfigurationException cex)
		{
		    // loading of the configuration file failed
			cex.printStackTrace();
		}
		return null;
				
	}		

}
