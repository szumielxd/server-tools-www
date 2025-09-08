package me.szumielxd.tools_panel.entity.log.dto;

import lombok.Getter;
import org.springframework.lang.NonNull;

@Getter
public class MessagePlayerLog extends PlayerLog {

	private final @NonNull String message;

	MessagePlayerLog(long id, long time, @NonNull LogType type, @NonNull String server, @NonNull String message) {
		super(id, time, type, server);
		this.message = message;
	}
}
