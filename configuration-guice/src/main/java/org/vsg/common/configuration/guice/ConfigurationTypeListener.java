/**
 * 
 */
package org.vsg.common.configuration.guice;

import java.lang.reflect.Field;
import java.lang.reflect.Type;

import org.vsg.common.configration.CommonConfigurationSupport;
import org.vsg.common.configration.InjectConfigValue;

import com.google.inject.TypeLiteral;
import com.google.inject.spi.TypeEncounter;
import com.google.inject.spi.TypeListener;

/**
 * @author vison
 *
 */
public class ConfigurationTypeListener implements TypeListener {
	
	private CommonConfigurationSupport pph;
	
	
	
	public ConfigurationTypeListener(CommonConfigurationSupport pph) {
		this.pph = pph;
	}
	
	

	/* (non-Javadoc)
	 * @see com.google.inject.spi.TypeListener#hear(com.google.inject.TypeLiteral, com.google.inject.spi.TypeEncounter)
	 */
	public <I> void hear(TypeLiteral<I> type, TypeEncounter<I> encounter) {
		// TODO Auto-generated method stub
		Type t = type.getType();
		
		// --- call method ---
		for (Field field : type.getRawType().getDeclaredFields()) {

			if (field.isAnnotationPresent(InjectConfigValue.class)) {

				ConfigValueMemberInjector cvmi = new ConfigValueMemberInjector(
						field);
				cvmi.setConfiguration(this.pph.getConfig());
				encounter.register(cvmi);

			}
		}
		

	}

}
