/**
 * 
 */
package net.iberdok.bsmvcw.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 * @author doibalmi
 *
 */
@Controller
public class SecurityAccessController {
	
	@RequestMapping("/login.htm")
	public String getLogin(ModelMap model){
		return "login";
	}
	
	@RequestMapping("/loginfailed.htm")
	public String getLoginfailed(ModelMap model){
		model.addAttribute("message", "loginfailed");
		return "login";
	}
	
	@RequestMapping("/logout.htm")
	public String getLogout(ModelMap model){
		model.addAttribute("message", "logout done");
		return "login";
	}

}
