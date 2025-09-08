package me.szumielxd.tools_panel.controllers.api;

import lombok.RequiredArgsConstructor;
import me.szumielxd.tools_panel.entity.moderation.ModGroup;
import me.szumielxd.tools_panel.entity.moderation.ModLog;
import me.szumielxd.tools_panel.entity.moderation.Moderator;
import me.szumielxd.tools_panel.exception.request.InvalidParameterFormatException;
import me.szumielxd.tools_panel.repository.moderation.ModGroupRepository;
import me.szumielxd.tools_panel.repository.moderation.ModLogRepository;
import me.szumielxd.tools_panel.repository.moderation.ModeratorRepository;
import org.springframework.data.domain.*;
import org.springframework.http.ResponseEntity;
import org.springframework.lang.NonNull;
import org.springframework.lang.Nullable;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/api/moderation")
@RequiredArgsConstructor
public class ModeratorsApiController {

	private final @NonNull ModGroupRepository modGroupRepository;
	private final @NonNull ModeratorRepository moderatorRepository;
	private final @NonNull ModLogRepository modLogRepository;

	@GetMapping("/groups")
	public ResponseEntity<List<ModGroup>> groups() {
		return ResponseEntity.ok(
				modGroupRepository.findAll(
						Sort.by(Sort.Order.desc("weight"))));
	}

	@GetMapping("/groups/{id}")
	public ResponseEntity<ModGroup> group(@PathVariable Long id) {
		return ResponseEntity.of(modGroupRepository.findById(id));
	}

	@GetMapping("/users")
	public ResponseEntity<List<Moderator>> users(@RequestParam(required = false) @Nullable Integer groupId) {
		if (groupId != null) {
			return ResponseEntity.ok(moderatorRepository.findAllByGroupId(groupId));
		}
		return ResponseEntity.ok(moderatorRepository.findAll());
	}

	@GetMapping("/users/{id}")
	public ResponseEntity<Moderator> user(@PathVariable String id) {
		UUID uuid;
		try {
			uuid = UUID.fromString(id);
		} catch (IllegalArgumentException e) {
			long userId;
			try {
				userId = Long.parseLong(id);
			} catch (NumberFormatException ex) {
				throw new InvalidParameterFormatException("ID");
			}
			return ResponseEntity.of(moderatorRepository.findById(userId));
		}
		return ResponseEntity.of(moderatorRepository.findByUuid(uuid));
	}

	@GetMapping("/logs")
	public ResponseEntity<List<ModLog>> logs(@RequestParam long lastId, @RequestParam(defaultValue = "50") int amount) {
		amount = amount > 0 && amount < 50 ? amount : 50;
		return ResponseEntity.ok(modLogRepository.findByIdLessThanOrderByIdDesc(lastId, Limit.of(amount)));
	}


}
