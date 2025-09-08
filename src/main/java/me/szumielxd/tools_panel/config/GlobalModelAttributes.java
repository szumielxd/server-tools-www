package me.szumielxd.tools_panel.config;

import me.szumielxd.tools_panel.utils.CollectionUtils;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ModelAttribute;

@ControllerAdvice
public class GlobalModelAttributes {

	@ModelAttribute
	public void addGlobalAttributes(Model model) {
		model.addAttribute("pages", CollectionUtils.insertOrderedMap(
				"info", "Przegląd profilu",
				"payments", "Płatności",
				"moderators", "Moderatorzy"));
		model.addAttribute("siteURL", "");
		model.addAttribute("siteIcon", "");
		model.addAttribute("serverName", "Example");
	}

}
