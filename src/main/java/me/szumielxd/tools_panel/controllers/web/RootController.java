package me.szumielxd.tools_panel.controllers.web;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

@Controller
@RequestMapping("/")
public class RootController {

	@RequestMapping("/")
	public ModelAndView home() {
		return new ModelAndView(PlayerInfoController.searchRedirect());
	}

}
