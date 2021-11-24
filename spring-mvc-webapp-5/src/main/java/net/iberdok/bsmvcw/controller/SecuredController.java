/**
 * 
 */
package net.iberdok.bsmvcw.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;

import net.iberdok.util.IberdokVersionUtil;

/**
 * @author doibalmi
 *
 */
@Controller
@RequestMapping("/secured")
public class SecuredController {
	
	@Autowired
	private IberdokVersionUtil versionUtil;
	
	@RequestMapping("/home.htm")
	public String securedHome(ModelMap model){
		return "redirect: test.htm"; // Toma el path secured desde su propio nivel
	}
	
	@RequestMapping("/test.htm")
	public String securedTest(ModelMap model){
		model.put("versionInfo", this.versionUtil.getVersion());
		return "secured/test";
	}

}
