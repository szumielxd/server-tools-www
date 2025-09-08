package me.szumielxd.tools_panel.entity.log.dto;

import lombok.Getter;

import org.springframework.lang.NonNull;

@Getter
public class ServerChangePlayerLog extends PlayerLog {

	private final String targetServer;

	ServerChangePlayerLog(long id, long time, @NonNull LogType type, @NonNull String server, String targetServer) {
		super(id, time, type, server);
		this.targetServer = targetServer;
	}
}
