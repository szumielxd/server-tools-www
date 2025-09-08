package me.szumielxd.tools_panel.entity.log.dto;

import lombok.Getter;
import org.springframework.lang.NonNull;

@Getter
public class CommandPlayerLog extends PlayerLog {

	private final @NonNull String command;
	private final @NonNull String arguments;

	CommandPlayerLog(long id, long time, @NonNull LogType type, @NonNull String server, @NonNull String command, @NonNull String arguments) {
		super(id, time, type, server);
		this.command = command;
		this.arguments = arguments;
	}
}
