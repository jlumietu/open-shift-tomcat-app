/**
 * 
 */
package net.iberdok.bsmvcw.controller;

import java.io.IOException;
import java.io.Serializable;
import java.util.Map;

import org.apache.log4j.Logger;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import net.iberdok.bsmvcw.util.env.EnvironmentVariableReader;
import net.iberdok.collections.SingleMap;

/**
 * @author DOIBALMI
 *
 */
@Controller
@RequestMapping({"/services/test/system/environment","/services/system/environment"})
public class EnvironmentVariableController {

	private Logger logger = Logger.getLogger(this.getClass());
	
	private EnvironmentVariableReader environmentVariableReader;
	
	/**
	 * 
	 */
	public EnvironmentVariableController() {
		super();
		this.environmentVariableReader = new EnvironmentVariableReader();
		this.logger.info("Starting " + this.getClass().getName());
	}

	@RequestMapping("/variables.service")
	public HttpEntity<Map<String,String>> getEnvironmentVariables() {
		return new HttpEntity<Map<String,String>>(environmentVariableReader.readEnvironmentVariables());
	}
	
	
	
}
