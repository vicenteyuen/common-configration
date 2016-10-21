package org.vsg.common.configuration.jndi;

import java.io.File;
import java.util.Enumeration;
import java.util.Hashtable;

import javax.naming.Context;
import javax.naming.Name;
import javax.naming.RefAddr;
import javax.naming.Reference;
import javax.naming.spi.ObjectFactory;

public class ConfigurationEnvBeanFactory implements ObjectFactory {
	
	private ConfigurationEnvBeanImpl configrationBean;

	@Override
	public Object getObjectInstance(Object obj, Name name, Context nameCtx,
			Hashtable<?, ?> environment) throws Exception {
		// TODO Auto-generated method stub


		// Customize the bean properties from our attributes
		Reference ref = (Reference) obj;
		
		boolean singleton = true;
		String systemPath = null;
		String envParam = "default";
		
		Enumeration<RefAddr> addrs = ref.getAll();
		while (addrs.hasMoreElements()) {
			RefAddr addr = (RefAddr) addrs.nextElement();
			String addrName = addr.getType();
			String addrValue = (String) addr.getContent();
			
			if ("singleton".equalsIgnoreCase(addrName)) {
				String value = addrValue.trim().toLowerCase();
				singleton = new Boolean(value);
			}
			else if ("configpath".equalsIgnoreCase(addrName)) {
				systemPath = addrValue.trim();

			}
			else if ("env".equalsIgnoreCase(addrName)) {
				envParam = addrValue.trim();

			}
		}
		
		ConfigurationEnvBeanImpl bean = null;
		
		// ---create instance ---
		if (singleton) {
			if ( configrationBean == null) {
				configrationBean = new ConfigurationEnvBeanImpl();
			}
			bean = configrationBean;			
		} else {
			bean = new ConfigurationEnvBeanImpl();
		}
		
		
		if (systemPath == null) {
			throw new Exception("Not defined System Property \"config.path\"!Please setting by using \"java -Dconfig.path=XXXX\" .");
		}

		if (!systemPath.endsWith(File.separator)) {
			systemPath = systemPath + File.separator;
		}		
		
	
		// check path exist
		File sysPath = new File(systemPath);
		if (!sysPath.exists()) {
			throw new Exception("System Folder [\""+sysPath+"\"] does not exist! Please check it!");
		}

		sysPath = new File(sysPath,envParam );
		if (!sysPath.exists()) {
			throw new Exception("System Folder [\""+sysPath+"\"] does not exist! Please check it!");
		}
		
		
		sysPath = new File(sysPath , "system.xml");
		if (!sysPath.exists()) {
			throw new Exception("System Folder [\""+sysPath+"\"] does not exist! Please check it!");
		}				
		// ---- create configration ----
		bean.setConfigPath( sysPath );

		// Return the customized instance
		return (bean);
	}

}
