package me.szumielxd.tools_panel.controllers.api;

import jakarta.validation.Valid;
import jakarta.validation.constraints.Size;
import lombok.RequiredArgsConstructor;
import me.szumielxd.tools_panel.entity.PlayerInfo;
import me.szumielxd.tools_panel.entity.auth.AuthUser;
import me.szumielxd.tools_panel.exception.request.NonExistentEntityException;
import me.szumielxd.tools_panel.service.auth.PlayerAuthService;
import org.springframework.http.ResponseEntity;
import org.springframework.lang.NonNull;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/api/auth")
@RequiredArgsConstructor
public class AuthApiController {

	private final @NonNull PlayerAuthService playerAuthService;


	@GetMapping("user/{uuid}/name")
	public ResponseEntity<String> username(@PathVariable UUID uuid) {
		return ResponseEntity.of(playerAuthService.getInfoByUUID(uuid)
				.map(PlayerInfo::username));
	}

	@GetMapping("user/{uuid}")
	public ResponseEntity<AuthUserInfo> user(@PathVariable UUID uuid) {
		return ResponseEntity.of(playerAuthService.getByUUID(uuid)
				.map(AuthUserInfo::of));
	}

	@DeleteMapping("user/{uuid}")
	public ResponseEntity<Void> deleteUser(@PathVariable UUID uuid) {
		try {
			playerAuthService.deleteUser(uuid);
		} catch (NonExistentEntityException e) {
			return ResponseEntity.notFound().build();
		}
		return ResponseEntity.noContent().build();
	}

	@GetMapping("user")
	public ResponseEntity<List<PlayerAuthService.PlayerInfoSearchEntity>> searchUser(@RequestParam String query, @RequestParam(defaultValue = "false") boolean exact) {
		if (query.length() < 3) { // too many players can have matching usernames, which results in slower queries
			return ResponseEntity.ok(List.of());
		}
		if (exact) {
			return ResponseEntity.ok(playerAuthService.searchInfoExact(query));
		}
		return ResponseEntity.ok(playerAuthService.searchInfoByUsername(query));
	}


	@PostMapping("user/{uuid}/change-password")
	public ResponseEntity<Void> updateUserPassword(@PathVariable UUID uuid, @Valid @RequestBody PasswordUpdateRequest request) {
		try {
			playerAuthService.setPassword(uuid, request.newPassword());
		} catch (NonExistentEntityException e) {
			return ResponseEntity.notFound().build();
		}
		return ResponseEntity.noContent().build();
	}


	@PostMapping("user/{uuid}/enable-premium")
	public ResponseEntity<Void> enablePremiumUser(@PathVariable UUID uuid, @Valid @RequestBody EnablePremiumRequest request) {
		try {
			playerAuthService.enablePremium(uuid, request.premiumUUID());
		} catch (NonExistentEntityException e) {
			return ResponseEntity.notFound().build();
		}
		return ResponseEntity.noContent().build();
	}


	@GetMapping("user/{uuid}/disable-premium")
	public ResponseEntity<Void> enablePremiumUser(@PathVariable UUID uuid) {
		try {
			playerAuthService.disablePremium(uuid);
		} catch (NonExistentEntityException e) {
			return ResponseEntity.notFound().build();
		}
		return ResponseEntity.noContent().build();
	}


	public record AuthUserInfo(String username,
	                    UUID uniqueId,
	                    AuthUser.JoinData firstJoin,
	                    AuthUser.JoinData lastJoin,
	                    boolean isPremium) {

		static AuthUserInfo of(AuthUser user) {
			return new AuthUserInfo(
					user.getName(),
					user.getUniqueId(),
					user.getFirstJoin(),
					user.getLastJoin(),
					user.isPremium());
		}

	}

	public record PasswordUpdateRequest(
			@Size(min = 8, max = 64, message = "Password must be between 8 and 64 characters") String newPassword
	) {}

	public record EnablePremiumRequest (
			UUID premiumUUID
	) {}



}
