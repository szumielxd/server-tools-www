package me.szumielxd.tools_panel.controllers.api;

import lombok.RequiredArgsConstructor;
import me.szumielxd.tools_panel.entity.portfel.PortfelUser;
import me.szumielxd.tools_panel.service.PortfelService;
import org.springframework.http.ResponseEntity;
import org.springframework.lang.NonNull;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.UUID;

@RestController
@RequestMapping("/api/portfel")
@RequiredArgsConstructor
public class PortfelApiController {

	private final @NonNull PortfelService portfelService;

	@GetMapping("user/{uuid}")
	public ResponseEntity<PortfelUserInfo> user(@PathVariable UUID uuid) {
		return ResponseEntity.of(portfelService.getByUUID(uuid)
				.map(PortfelUserInfo::of));
	}

	public record PortfelUserInfo(UUID uniqueId,
	                              long balance,
	                              long minorBalance,
	                              boolean ignoreTop) {

		public static PortfelUserInfo of(PortfelUser user) {
			return new PortfelUserInfo(
					user.getUuid(),
					user.getBalance(),
					user.getMinorBalance(),
					user.isIgnoreTop());
		}
	}


}
