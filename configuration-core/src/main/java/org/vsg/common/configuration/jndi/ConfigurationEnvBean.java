/**
 * 
 */
package org.vsg.common.configuration.jndi;

import java.util.Properties;

import org.apache.commons.configuration2.Configuration;


/**
 * @author vison
 *
 */
public interface ConfigurationEnvBean {
	
	/**
	 * set one
	 * @return
	 */
	Configuration createConfigration();
	
	/**
	 * merge propers
	 * @param props
	 */
	Properties mergeProperties(Properties props);

}
