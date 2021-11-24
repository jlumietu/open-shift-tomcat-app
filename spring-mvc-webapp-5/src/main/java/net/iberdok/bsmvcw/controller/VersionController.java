/**
 * 
 */
package net.iberdok.bsmvcw.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import net.iberdok.model.io.VersionInfo;
import net.iberdok.util.IberdokVersionUtil;

/**
 * @author DOIBALMI
 *
 */
@Controller
public class VersionController {

	@Autowired
	private IberdokVersionUtil versionUtil;
	
	@RequestMapping("/services/test/system/version.service")
	public @ResponseBody HttpEntity<VersionInfo> getVersion(){
		return new ResponseEntity<VersionInfo>(
				this.versionUtil.getVersion(),
				HttpStatus.OK
			);
	}
	
	@RequestMapping("/version.htm")
	public String getVersion(ModelMap model){
		model.put("versionInfo", this.versionUtil.getVersion());
		return "test";
	}
	
		
}
