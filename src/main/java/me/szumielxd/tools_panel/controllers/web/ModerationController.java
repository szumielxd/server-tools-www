package me.szumielxd.tools_panel.controllers.web;

import org.springframework.lang.NonNull;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

@Controller
@RequestMapping("/moderators")
public class ModerationController {

	@ModelAttribute
	public void commonDataSetup(Model model) {
		model.addAttribute("currentPage", "moderators");
	}

	@GetMapping("/")
	public @NonNull ModelAndView home(Model model) {
		return new ModelAndView("moderators/home", model.asMap());
	}

}
