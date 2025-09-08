package me.szumielxd.tools_panel.entity.log.dto;

import org.springframework.lang.NonNull;

public enum LogType {
	MESSAGE,
	COMMAND,
	JOIN,
	QUIT,
	KICK,
	SERVER_CHANGE;

	public static @NonNull LogType fromMagicId(int magicId) {
		var arr = values();
		if (magicId < 1 || magicId > arr.length) {
			throw new IllegalArgumentException("Invalid magic ID: %d".formatted(magicId));
		}
		return arr[magicId - 1];
	}

}
