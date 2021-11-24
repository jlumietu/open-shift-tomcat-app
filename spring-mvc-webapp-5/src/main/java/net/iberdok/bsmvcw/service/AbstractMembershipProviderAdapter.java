package net.iberdok.bsmvcw.service;

import java.net.InetAddress;
import java.net.UnknownHostException;
import java.security.AccessController;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.security.PrivilegedAction;

import org.apache.log4j.Logger;

public class AbstractMembershipProviderAdapter {
	
	private static Logger log = Logger.getLogger(AbstractMembershipProviderAdapter.class);
	
	protected MessageDigest md5;
	
	protected String localIp;
	
	public AbstractMembershipProviderAdapter() {
        try {
            md5 = MessageDigest.getInstance("md5");
        } catch (NoSuchAlgorithmException e) {
        	log.warn("Error " + e.getMessage(), e);
        }
        try {
			this.localIp = InetAddress.getLocalHost().getHostAddress();
		} catch (UnknownHostException e) {
			log.warn("Error " + e.getMessage(), e);
		}
    }

	/**
	 * Get value of environment variable.
	 * @param keys the environment variables
	 * @return the env variables values, or null if not found
	 */
	protected static String getEnv(String... keys) {
	    String val = null;
	    for (String key : keys) {
	        val = AccessController.doPrivileged((PrivilegedAction<String>) () -> System.getenv(key));
	        if (val != null) {
	            break;
	        }
	    }
	    return val;
	}

}