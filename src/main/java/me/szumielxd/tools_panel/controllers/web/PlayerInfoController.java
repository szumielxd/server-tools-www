package me.szumielxd.tools_panel.controllers.web;

import lombok.RequiredArgsConstructor;
import me.szumielxd.tools_panel.service.auth.PlayerAuthService;
import org.springframework.lang.NonNull;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.method.annotation.MvcUriComponentsBuilder;
import org.springframework.web.servlet.view.RedirectView;

import java.util.UUID;

@RequiredArgsConstructor
@Controller
@RequestMapping("/info")
public class PlayerInfoController {

	private final @NonNull PlayerAuthService playerAuthService;

	@ModelAttribute
	public void commonDataSetup(Model model) {
		model.addAttribute("currentPage", "info");
	}

	@RequestMapping("")
	public @NonNull ModelAndView info(Model model, @RequestParam(required = false) UUID user) {
		var info = playerAuthService.getInfo(String.valueOf(user));
		if (info.isEmpty()) {
			return new ModelAndView(searchRedirect());
		}
		model.addAttribute("username", info.get().username());
		model.addAttribute("uuid", info.get().uuid());
		return new ModelAndView("info/home");
	}

	@GetMapping("/search")
	public @NonNull ModelAndView search(Model model, @RequestParam(required = false) String query) {
		if (query != null) {
			var user = playerAuthService.getInfo(query);
			if (user.isPresent()) {
				return new ModelAndView(infoRedirect(user.get().uuid()));
			}
		}
		model.addAttribute("search_invalid_user", query != null);
		model.addAttribute("search_query", query != null ? query : "");
		return new ModelAndView("info/search", model.asMap());
	}

	public static RedirectView searchRedirect() {
		return new RedirectView(
				MvcUriComponentsBuilder
						.fromMethodCall(
								MvcUriComponentsBuilder.on(PlayerInfoController.class).search(null, null)
						)
						.build()
						.toUriString());
	}

	public static RedirectView infoRedirect(@NonNull UUID uuid) {
		return new RedirectView(
				MvcUriComponentsBuilder
						.fromMethodCall(
								MvcUriComponentsBuilder.on(PlayerInfoController.class).info(null, null)
						)
						.queryParam("user", uuid)
						.build()
						.toUriString());
	}

}
