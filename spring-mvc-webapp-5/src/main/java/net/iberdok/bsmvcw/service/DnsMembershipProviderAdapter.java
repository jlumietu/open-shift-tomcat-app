/**
 * 
 */
package net.iberdok.bsmvcw.service;

import java.io.IOException;
import java.io.Serializable;
import java.io.UnsupportedEncodingException;
import java.net.InetAddress;
import java.net.URLEncoder;
import java.net.UnknownHostException;
import java.util.ArrayList;
import java.util.List;

import org.apache.log4j.Logger;
import org.springframework.stereotype.Service;

import net.iberdok.apache.catalina.tribes.Member;
import net.iberdok.util.marshall.JacksonJsonMarshaller;

/**
 * @author DOIBALMI
 *
 */
@Service
public class DnsMembershipProviderAdapter extends AbstractMembershipProviderAdapter{
	
	private Logger log = Logger.getLogger(this.getClass());
	
	protected static final String CUSTOM_ENV_PREFIX = "OPENSHIFT_KUBE_PING_";
	
	private String dnsServiceName;
	
	public String getDnsServiceName() {
		// Set up Kubernetes API parameters
        dnsServiceName = getEnv("DNS_MEMBERSHIP_SERVICE_NAME");
        if (dnsServiceName == null) {
            dnsServiceName = getNamespace();
        }

        if (log.isDebugEnabled()) {
            log.debug(String.format("Namespace [%s] set; clustering enabled", dnsServiceName));
        }
        try {
			dnsServiceName = URLEncoder.encode(dnsServiceName, "UTF-8");
			return dnsServiceName;
		} catch (UnsupportedEncodingException e) {
			log.error("Error " + e.getMessage(), e);
			return e.getMessage();
		}
	}
	
	public String getDnsMembers() {
		InetAddress[] inetAddresses = null;
        try {
            inetAddresses = InetAddress.getAllByName(dnsServiceName);
        } catch (UnknownHostException exception) {
            log.warn("dnsMembershipProvider.dnsError " +  dnsServiceName + ": " + exception.getMessage(), exception);
            return exception.getMessage();
        }
        ArrayList<DnsMember> dnsMembers = new ArrayList<DnsMember>();
        if (inetAddresses != null) {
            for (InetAddress inetAddress : inetAddresses) {
                String ip = inetAddress.getHostAddress();
                byte[] id = md5.digest(ip.getBytes());
                dnsMembers.add(new DnsMember(ip, id, ip.equals(localIp)));
            }
        }
        try {
			return new JacksonJsonMarshaller().marshall(dnsMembers);
		} catch (IOException e) {
			this.log.error("Error " + e.getMessage(), e);
			return e.getMessage();
		}
	}
	
	/**
     * Get the Kubernetes namespace, or "tomcat" if the Kubernetes environment variable
     * cannot be found (with a warning log about the missing namespace).
     * @return the namespace
     */
    protected String getNamespace() {
        String namespace = getEnv(CUSTOM_ENV_PREFIX + "NAMESPACE", "KUBERNETES_NAMESPACE");
        if (namespace == null || namespace.length() == 0) {
            log.warn("kubernetesMembershipProvider.noNamespace");
            namespace = "tomcat";
        }
        return namespace;
    }
    
    public static class DnsMember implements Serializable {
    	
    	/**
		 * 
		 */
		private static final long serialVersionUID = 1L;

		private String ip;
    	
    	private byte[] id;
    	
    	private boolean self;
    	
    	/**
		 * @param ip
		 * @param id
		 * @param self
		 */
		public DnsMember(String ip, byte[] id, boolean self) {
			super();
			this.ip = ip;
			this.id = id;
			this.self = self;
		}

		/**
		 * @param ip
		 * @param id
		 */
		public DnsMember(String ip, byte[] id) {
			super();
			this.ip = ip;
			this.id = id;
		}

		/**
		 * @return the ip
		 */
		public String getIp() {
			return ip;
		}

		/**
		 * @param ip the ip to set
		 */
		public void setIp(String ip) {
			this.ip = ip;
		}

		/**
		 * @return the id
		 */
		public byte[] getId() {
			return id;
		}

		/**
		 * @param id the id to set
		 */
		public void setId(byte[] id) {
			this.id = id;
		}

		/**
		 * @return the self
		 */
		public boolean isSelf() {
			return self;
		}

		/**
		 * @param self the self to set
		 */
		public void setSelf(boolean self) {
			this.self = self;
		}
    	
    	
    	
    }

}
