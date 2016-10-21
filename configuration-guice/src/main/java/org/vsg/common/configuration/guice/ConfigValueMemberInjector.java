package org.vsg.common.configuration.guice;

import java.lang.reflect.Field;

import org.apache.commons.configuration2.Configuration;
import org.vsg.common.configration.InjectConfigValue;

import com.google.inject.MembersInjector;

public class ConfigValueMemberInjector<T> implements MembersInjector<T>  {
	
	private Field field;
	
	
	private Configuration configuration;
	
	public ConfigValueMemberInjector(Field field) {
		this.field = field;
	}


	public Field getField() {
		return field;
	}


	public void setField(Field field) {
		this.field = field;
	}


	public Configuration getConfiguration() {
		return configuration;
	}


	public void setConfiguration(Configuration configuration) {
		this.configuration = configuration;
	}



	public void injectMembers(T instance) {
		// TODO Auto-generated method stub
		InjectConfigValue  icv =  field.getAnnotation( InjectConfigValue.class );
		String propKey = icv.value();
		
		
		boolean orgAccessible = field.isAccessible();

		try {
			
			if (!orgAccessible) {
				field.setAccessible(true);
			}
			
			Class typeCls = field.getType();		
			
			if ( typeCls.toString().equals("int") ) {
				
				field.setInt( instance , configuration.getInt( propKey ));

			}
			else if (typeCls.equals(  String.class )) {
				
				field.set(instance, configuration.getString( propKey ));
			}
			
			
		} catch (IllegalArgumentException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IllegalAccessException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (java.util.NoSuchElementException  e ) {
			e.printStackTrace();
		} finally {
			field.setAccessible( orgAccessible );
		}

		  
		  
		  
		
	}

	
	
}
