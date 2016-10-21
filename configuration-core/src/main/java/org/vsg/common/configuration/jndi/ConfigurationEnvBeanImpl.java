/**
 * 
 */
package org.vsg.common.configuration.jndi;

import java.io.File;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Properties;

import org.apache.commons.configuration2.CombinedConfiguration;
import org.apache.commons.configuration2.Configuration;
import org.apache.commons.configuration2.ex.ConfigurationException;

/**
 * @author vison
 *
 */
public class ConfigurationEnvBeanImpl implements ConfigurationEnvBean{
	
	private File configPath;
	

	public File getConfigPath() {
		return configPath;
	}

	public void setConfigPath(File configPath) {
		this.configPath = configPath;
	}
	
	private Configuration config;

	@Override
	public Configuration createConfigration() {
		// TODO Auto-generated method stub
		
		// load config using apache configartion
		/*
		DefaultConfigurationBuilder builder = new DefaultConfigurationBuilder();

		builder.setFile(getConfigPath() );
		config = null;
		try {
			config = builder.getConfiguration(true);
		} catch (ConfigurationException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}		
		return config;
		*/
		return null;
	}

	@Override
	public Properties mergeProperties(Properties props) {
		// TODO Auto-generated method stub
		// --- read all property ---
		Iterator keys = config.getKeys();
		String key = null;
		while (keys.hasNext()) {
			key = keys.next().toString();
			java.io.Serializable value = parsePropertiesForValues(key,(CombinedConfiguration)config);
			props.put(key, value);
		}
		
		return props;
	}

	
	private java.io.Serializable parsePropertiesForValues(String key  , CombinedConfiguration  config) {
		Object value = config.getProperty( key );
		if (value instanceof ArrayList) {
			List<Object> listValues = config.getList(key);
			StringBuilder values = new StringBuilder();
			for (int i = 0 ; i < listValues.size() ; i++) {
				if (i > 0) {
					//values.append(config.getListDelimiter());
				}
				values.append( listValues.get(i) );
			}
			return values.toString();
		} else {
			return (java.io.Serializable)value;
		}
	}

}
