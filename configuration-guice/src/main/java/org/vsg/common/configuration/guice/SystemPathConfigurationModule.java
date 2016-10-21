/**
 * 
 */
package org.vsg.common.configuration.guice;


import org.apache.commons.configuration2.Configuration;

import com.google.inject.AbstractModule;
import com.google.inject.matcher.Matchers;

/**
 * @author vison
 *
 */
public class SystemPathConfigurationModule extends AbstractModule {

	/* (non-Javadoc)
	 * @see com.google.inject.AbstractModule#configure()
	 */
	@Override
	protected void configure() {
		// TODO Auto-generated method stub
		
		SystemParamPropertiesPlaceHolder propPlaceHolder  = new SystemParamPropertiesPlaceHolder();
		try {
			propPlaceHolder.afterPropertiesSet();
			
			this.bindListener(Matchers.any(), new ConfigurationTypeListener(propPlaceHolder));
			this.bind(Configuration.class).toInstance( propPlaceHolder.getConfig() );
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		

	}

}
