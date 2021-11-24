/**
 * 
 */
package net.iberdok.bsmvcw.controller;

import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import net.iberdok.bsmvcw.model.StringJsonResponse;
import net.iberdok.bsmvcw.service.DnsMembershipProviderAdapter;
import net.iberdok.collections.SingleMap;

/**
 * @author DOIBALMI
 *
 */
@Controller
@RequestMapping({"/services/test/system/cloud/dns","/services/system/cloud/dns"})
public class DnsCloudMembershipProviderController {

	@Autowired
	private DnsMembershipProviderAdapter dnsServiceAdapter;
	
	@RequestMapping("/name.service")
	public HttpEntity<Map<String,String>> getDnsServiceName() {
		return new HttpEntity<Map<String,String>>(
				new SingleMap<String,String>(
					"dns-service-name", this.dnsServiceAdapter.getDnsServiceName()
				)
		);
	}
	
	@RequestMapping(value="/members.service", produces = MediaType.APPLICATION_JSON_VALUE)
	public HttpEntity<StringJsonResponse> getDnsMembers() {
		return new HttpEntity<StringJsonResponse>(
				new StringJsonResponse(
					this.dnsServiceAdapter.getDnsMembers()
				)
		);
	}
	
}
