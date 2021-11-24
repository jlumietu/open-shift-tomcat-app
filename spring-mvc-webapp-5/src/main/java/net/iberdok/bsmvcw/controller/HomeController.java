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
public class HomeController {
	
	@RequestMapping("/home.htm")
	public String home(ModelMap model){
		// TODO: build your own home.htm
		return "redirect:version.htm";
	}	
	

}
