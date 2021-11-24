/**
 * 
 */
package net.iberdok.bsmvcw.util.env;

import java.util.Map;
import java.util.Map.Entry;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.InitializingBean;

/**
 * @author DOIBALMI
 *
 */
public class EnvironmentVariableReader implements InitializingBean{
	
	private Logger logger = Logger.getLogger(this.getClass());
	
	
	
	/**
	 * 
	 */
	public EnvironmentVariableReader() {
		super();
	}
	
	

	/**
	 * 
	 * @return
	 */
	public Map<String, String> readEnvironmentVariables() {
		for(Entry<String,String> entry : System.getenv().entrySet()) {
			logger.info(entry.getKey() + ": " + entry.getValue());
		}
		return System.getenv();
	}
	
	
    
	@Override
	public void afterPropertiesSet() throws Exception {
		// TODO Auto-generated method stub
		
	}

}
