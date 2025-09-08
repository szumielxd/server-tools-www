package me.szumielxd.tools_panel.entity.log;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

import java.sql.ResultSet;
import java.sql.SQLException;

public record FullPlayerLogEntry(
		long id,
		long time,
		int type,
		String server,
		String message,
		String command,
		String commandArguments,
		String targetServer,
		String kickReason
) {

	public static FullPlayerLogEntry fromResultSet(ResultSet rs) throws SQLException {
		return new FullPlayerLogEntry(
				rs.getLong("id"),
				rs.getTimestamp("time").getTime(),
				rs.getInt("type"),
				rs.getString("server"),
				rs.getString("message"),
				rs.getString("command"),
				rs.getString("arguments"),
				rs.getString("target_server"),
				rs.getString("extra")
		);
	}

}
