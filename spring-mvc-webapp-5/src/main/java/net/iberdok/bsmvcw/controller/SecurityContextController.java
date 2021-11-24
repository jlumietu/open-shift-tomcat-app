/**
 * 
 */
package net.iberdok.bsmvcw.controller;

import java.security.Principal;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.Filter;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.FilterChainProxy;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

/**
 * @author jlumietu
 *
 */
@Controller
@RequestMapping("/secured")
public class SecurityContextController{
	
	@Autowired
	private FilterChainProxy filterChainProxy;
	
	@RequestMapping("/authentication.service")
	public @ResponseBody HttpEntity<Authentication> getSecurityContextAuthentication(Principal principal){
		return new HttpEntity<Authentication>(SecurityContextHolder.getContext().getAuthentication());
	} 
	
	@RequestMapping("/filterchain.service")
	public @ResponseBody HttpEntity<Map<Integer, Map<Integer, String>>> getSecurityFilterChainProxy(){
		Map<Integer, Map<Integer, String>> filterChains= new HashMap<Integer, Map<Integer, String>>();
		int i = 1;
		for(SecurityFilterChain secfc :  this.filterChainProxy.getFilterChains()){
			//filters.put(i++, secfc.getClass().getName());
			Map<Integer, String> filters = new HashMap<Integer, String>();
			int j = 1;
			for(Filter filter : secfc.getFilters()){
				filters.put(j++, filter.getClass().getName());
			}
			filterChains.put(i++, filters);
		}
		return new HttpEntity<Map<Integer, Map<Integer, String>>>(filterChains);
	}

}
