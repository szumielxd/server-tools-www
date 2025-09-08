package me.szumielxd.tools_panel.entity.log.dto;

import lombok.Getter;
import org.springframework.lang.NonNull;

@Getter
public class KickPlayerLog extends ServerChangePlayerLog {

	private final @NonNull String reason;

	KickPlayerLog(long id, long time, @NonNull LogType type, @NonNull String server, String targetServer, String reason) {
		super(id, time, type, server, targetServer);
		this.reason = reason;
	}
}
