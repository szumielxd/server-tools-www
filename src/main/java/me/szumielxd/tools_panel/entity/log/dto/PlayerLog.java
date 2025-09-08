package me.szumielxd.tools_panel.entity.log.dto;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import me.szumielxd.tools_panel.entity.log.FullPlayerLogEntry;
import org.springframework.lang.NonNull;

@Getter
@RequiredArgsConstructor(access = AccessLevel.PACKAGE)
public class PlayerLog {
	protected final long id;
	protected final long time;
	protected final @NonNull LogType type;
	protected final @NonNull String server;

	public static PlayerLog from(FullPlayerLogEntry logEntry) {
		var type = LogType.fromMagicId(logEntry.type());
		return switch (type) {
			case COMMAND -> new CommandPlayerLog(logEntry.id(), logEntry.time(), type, logEntry.server(), logEntry.command(), logEntry.commandArguments());
			case MESSAGE -> new MessagePlayerLog(logEntry.id(), logEntry.time(), type, logEntry.server(), logEntry.message());
			case KICK -> new KickPlayerLog(logEntry.id(), logEntry.time(), type, logEntry.server(), logEntry.server(), logEntry.kickReason());
			case SERVER_CHANGE -> new ServerChangePlayerLog(logEntry.id(), logEntry.time(), type, logEntry.server(), logEntry.server());
			default -> new PlayerLog(logEntry.id(), logEntry.time(), type, logEntry.server());
		};
	}

}
