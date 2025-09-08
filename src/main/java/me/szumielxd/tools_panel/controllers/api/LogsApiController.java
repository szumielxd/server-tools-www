package me.szumielxd.tools_panel.controllers.api;

import lombok.RequiredArgsConstructor;
import me.szumielxd.tools_panel.entity.log.dto.PlayerLog;
import me.szumielxd.tools_panel.service.PlayerLogService;
import org.springframework.http.ResponseEntity;
import org.springframework.lang.NonNull;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/api/logs")
@RequiredArgsConstructor
public class LogsApiController {

	private final @NonNull PlayerLogService playerLogService;

	@GetMapping("user/{uuid}")
	public ResponseEntity<List<PlayerLog>> user(@PathVariable UUID uuid, @RequestParam(defaultValue = "-1") long lastId) {
		if (lastId < 0) {
			lastId = Long.MAX_VALUE;
		}
		return ResponseEntity.ok(playerLogService.fetchLogs(uuid, lastId));
	}

}
