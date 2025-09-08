package me.szumielxd.tools_panel.service;

import lombok.AllArgsConstructor;
import me.szumielxd.tools_panel.entity.log.dto.PlayerLog;
import me.szumielxd.tools_panel.repository.log.PlayerLogRepository;
import me.szumielxd.tools_panel.utils.UUIDUtils;
import org.springframework.data.domain.Limit;
import org.springframework.lang.NonNull;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@Service
@AllArgsConstructor
public class PlayerLogService {

	private final @NonNull PlayerLogRepository playerLogRepository;

	public List<PlayerLog> fetchLogs(@NonNull UUID userId, long lastLogId) {
		return playerLogRepository.getLogs(UUIDUtils.toByteArray(userId), lastLogId, 50).stream()
				.map(PlayerLog::from)
				.toList();
	}


}
