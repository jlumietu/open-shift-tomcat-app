package net.iberdok.bsmvcw.controller;

import java.io.IOException;
import java.util.Map;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import net.iberdok.bsmvcw.model.StringJsonResponse;
import net.iberdok.bsmvcw.service.KubernetesMembershipProviderAdapter;
import net.iberdok.collections.SingleMap;

/**
 * 
 * @author DOIBALMI
 *
 */
@Controller
@RequestMapping({"/services/test/system/cloud/kubernetes","/services/system/cloud/kubernetes"})
public class KubernetesCloudMembershipProviderController {
	
	private Logger logger = Logger.getLogger(this.getClass());
	
	@Autowired
	private KubernetesMembershipProviderAdapter kubernetesAdapter;
	
	@RequestMapping("/core/url.service")
	public HttpEntity<Map<String,String>> getKubernetesUrl() {
		try {
			return new HttpEntity<Map<String,String>>(
					new SingleMap<String,String>(
						"url", kubernetesAdapter.getKubernetesMembershipServiceUrl()
					)
			);
		} catch (IOException e) {
			logger.error("Error " + e.getMessage(), e);
			return new ResponseEntity<Map<String,String>>(
					new SingleMap<String,String>(
							"message", e.getMessage()
					),
					HttpStatus.SERVICE_UNAVAILABLE
			);
					
		}
	}
	
	@RequestMapping("/alt/url.service")
	public HttpEntity<Map<String,String>> getKubernetesAltUrl() {
		return new HttpEntity<Map<String,String>>(
				new SingleMap<String,String>(
					"url", kubernetesAdapter.getKubernetesMembershipServiceUrlAlternative()
				)
		);
	}
	
	@RequestMapping(value="/core/response.service", produces = MediaType.APPLICATION_JSON_VALUE)
	public @ResponseBody HttpEntity<StringJsonResponse> getKubernetesServiceResponse() {
		return new HttpEntity<StringJsonResponse>(
				new StringJsonResponse(this.kubernetesAdapter.getServiceResponse())
		);
	}
	
	@RequestMapping(value="/alt/response.service", produces = MediaType.APPLICATION_JSON_VALUE)
	public @ResponseBody HttpEntity<StringJsonResponse> getKubernetesAltServiceResponse() {
		return new HttpEntity<StringJsonResponse>(
				new StringJsonResponse(this.kubernetesAdapter.getAltServiceResponse())
		);
	}
	
	@RequestMapping(value="/string/json/response/test.service", produces = MediaType.APPLICATION_JSON_VALUE)
	public @ResponseBody HttpEntity<StringJsonResponse> stringJsonResponseTest() {
		return new HttpEntity<StringJsonResponse>(
				new StringJsonResponse("{\"test\":\"ok\"}")
		);
	}
	
	

}
