/**
 * 
 */
package net.iberdok.bsmvcw.service;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;
import java.nio.file.FileSystems;
import java.nio.file.Files;
import java.util.HashMap;
import java.util.stream.Collectors;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.stereotype.Service;

import net.iberdok.apache.catalina.tribes.Channel;
import net.iberdok.apache.catalina.tribes.membership.cloud.CertificateStreamProvider;
import net.iberdok.apache.catalina.tribes.membership.cloud.KubernetesMembershipProvider;
import net.iberdok.apache.catalina.tribes.membership.cloud.StreamProvider;
import net.iberdok.apache.catalina.tribes.membership.cloud.TokenStreamProvider;

/**
 * @author DOIBALMI
 *
 */
@Service
public class KubernetesMembershipProviderAdapter extends AbstractMembershipProviderAdapter implements InitializingBean{
	
	private Logger logger = Logger.getLogger(this.getClass());
	
	private KubernetesMembershipProvider provider;
	
	/**
	 * 
	 */
	public KubernetesMembershipProviderAdapter() {
		super();
		provider = new KubernetesMembershipProvider();
	}

	@Override
	public void afterPropertiesSet() throws Exception {
		try {
			//provider.start(Channel.DEFAULT);
		} catch (Exception e) {
			logger.debug("Error starting channel " + e.getMessage(), e);
			throw new IOException(e);
		}
	}
	
	/**
	 * 
	 * @return
	 * @throws IOException
	 */
	public String getKubernetesMembershipServiceUrl() throws IOException {
		try {
			String url = provider.getUrl();
			return url;
		} catch (Exception e) {
			logger.debug("Error starting channel " + e.getMessage(), e);
			throw new IOException(e);
		}
		
    }
	
	/**
	 * 
	 * @return
	 */
	public String getServiceResponse() {
		return this.provider.getServiceResponse();
	}	
	
	protected static final String CUSTOM_ENV_PREFIX = "OPENSHIFT_KUBE_PING_";
    
	public String getKubernetesMembershipServiceUrlAlternative () {
		String protocol = getEnv(CUSTOM_ENV_PREFIX + "MASTER_PROTOCOL", "KUBERNETES_MASTER_PROTOCOL");
		String masterHost = getEnv(CUSTOM_ENV_PREFIX + "MASTER_HOST", "KUBERNETES_SERVICE_HOST");
		String masterPort = getEnv(CUSTOM_ENV_PREFIX + "MASTER_PORT", "KUBERNETES_SERVICE_PORT");
		
		String ver = getEnv(CUSTOM_ENV_PREFIX + "API_VERSION", "KUBERNETES_API_VERSION");
        if (ver == null) {
            ver = "v1";
        }
        
        String namespace = getNamespace();
        try {
			namespace = URLEncoder.encode(namespace, "UTF-8");
		} catch (UnsupportedEncodingException e) {
			logger.warn(e.getMessage());
		}
		
		String url = String.format("%s://%s:%s/api/%s/namespaces/%s/pods", protocol, masterHost, masterPort, ver, namespace);
		logger.debug("url: " + url);
		return url;
	}
	
	/**
     * Get the Kubernetes namespace, or "tomcat" if the Kubernetes environment variable
     * cannot be found (with a warning log about the missing namespace).
     * @return the namespace
     */
    protected String getNamespace() {
        String namespace = getEnv(CUSTOM_ENV_PREFIX + "NAMESPACE", "KUBERNETES_NAMESPACE");
        if (namespace == null || namespace.length() == 0) {
            logger.warn("kubernetesMembershipProvider.noNamespace");
            namespace = "tomcat";
        }
        return namespace;
    }
    
    private String theUrl;
    
    private StreamProvider streamProvider;
    
    /**
     * 
     * @throws Exception
     * @throws UnsupportedEncodingException
     */
	protected void initializeStreamProvider() throws Exception, UnsupportedEncodingException {
		// Set up Kubernetes API parameters
        String namespace = getNamespace();

        if (logger.isDebugEnabled()) {
            logger.debug(String.format("Namespace [%s] set; clustering enabled", namespace));
        }

        String protocol = getEnv(CUSTOM_ENV_PREFIX + "MASTER_PROTOCOL", "KUBERNETES_MASTER_PROTOCOL");
        String masterHost = getEnv(CUSTOM_ENV_PREFIX + "MASTER_HOST", "KUBERNETES_SERVICE_HOST");
        String masterPort = getEnv(CUSTOM_ENV_PREFIX + "MASTER_PORT", "KUBERNETES_SERVICE_PORT");

        String clientCertificateFile = getEnv(CUSTOM_ENV_PREFIX + "CLIENT_CERT_FILE", "KUBERNETES_CLIENT_CERTIFICATE_FILE");
        String caCertFile = getEnv(CUSTOM_ENV_PREFIX + "CA_CERT_FILE", "KUBERNETES_CA_CERTIFICATE_FILE");
        if (caCertFile == null) {
            caCertFile = "/var/run/secrets/kubernetes.io/serviceaccount/ca.crt";
        }

        if (clientCertificateFile == null) {
            if (protocol == null) {
                protocol = "https";
            }
            String saTokenFile = getEnv(CUSTOM_ENV_PREFIX + "SA_TOKEN_FILE", "SA_TOKEN_FILE");
            if (saTokenFile == null) {
                saTokenFile = "/var/run/secrets/kubernetes.io/serviceaccount/token";
            }
            try {
                byte[] bytes = Files.readAllBytes(FileSystems.getDefault().getPath(saTokenFile));
                streamProvider = new TokenStreamProvider(new String(bytes, StandardCharsets.US_ASCII), caCertFile);
            } catch (IOException e) {
                logger.error("kubernetesMembershipProvider.streamError" + e.getMessage(), e);
            }
        } else {
            if (protocol == null) {
                protocol = "http";
            }
            String clientKeyFile = getEnv("KUBERNETES_CLIENT_KEY_FILE");
            String clientKeyPassword = getEnv("KUBERNETES_CLIENT_KEY_PASSWORD");
            String clientKeyAlgo = getEnv("KUBERNETES_CLIENT_KEY_ALGO");
            if (clientKeyAlgo == null) {
                clientKeyAlgo = "RSA";
            }
            streamProvider = new CertificateStreamProvider(clientCertificateFile, clientKeyFile, clientKeyPassword, clientKeyAlgo, caCertFile);
        }

        String ver = getEnv(CUSTOM_ENV_PREFIX + "API_VERSION", "KUBERNETES_API_VERSION");
        if (ver == null) {
            ver = "v1";
        }

        String labels = getEnv(CUSTOM_ENV_PREFIX + "LABELS", "KUBERNETES_LABELS");

        namespace = URLEncoder.encode(namespace, "UTF-8");
        labels = labels == null ? null : URLEncoder.encode(labels, "UTF-8");

        theUrl = String.format("%s://%s:%s/api/%s/namespaces/%s/pods", protocol, masterHost, masterPort, ver, namespace);
        if (labels != null && labels.length() > 0) {
        	theUrl = theUrl + "?labelSelector=" + labels;
        }
	}
	
	/**
     * <p>Metodo creado adhoc para obtener la respuesta del servicio kubernetes en modo texto</p>
     * @return
     */
    public String getAltServiceResponse() {
    	try {
			this.initializeStreamProvider();
		} catch (Exception e1) {
			logger.error("Error " + e1.getMessage());
			return e1.getMessage();
		}
    	if (streamProvider == null) {
            return "streamProvider is null";
        }

        try (InputStream stream = streamProvider.openStream(theUrl, new HashMap<>(), 1000, 1000);
                InputStreamReader reader = new InputStreamReader(stream, "UTF-8")) {
            return new BufferedReader(reader).lines().collect(Collectors.joining("\n"));
        } catch (IOException e) {
            logger.error("kubernetesMembershipProvider.streamError", e);
            return "kubernetesMembershipProvider.streamError: " + e.getMessage();
        }
    }

}
