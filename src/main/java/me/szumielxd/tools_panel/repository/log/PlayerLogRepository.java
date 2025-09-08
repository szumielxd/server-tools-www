package me.szumielxd.tools_panel.repository.log;

import lombok.RequiredArgsConstructor;
import me.szumielxd.tools_panel.entity.log.FullPlayerLogEntry;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.data.repository.query.Param;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class PlayerLogRepository {

	private final JdbcTemplate jdbcTemplate;

	public PlayerLogRepository(@Qualifier("playerLogJdbcTemplate") JdbcTemplate jdbcTemplate) {
		this.jdbcTemplate = jdbcTemplate;
	}


	public List<FullPlayerLogEntry> getLogs(@Param("uuid") byte[] uuid, @Param("lastId") long lastId, int limit) {
		return jdbcTemplate.query("""
					SELECT `l`.`id`, `time`, `type`, `s`.`name` as `server`, `message`, `command`, `arguments`, `target_server`, `extra` FROM `bungeeweb_logs` as `l`
					        LEFT JOIN `bungeeweb_servers` as `s` ON `l`.`server_id` = `s`.`id`
					        LEFT JOIN `bungeeweb_player_sessions` as `ps` ON `l`.`session_id` = `ps`.`id`
					        LEFT JOIN `bungeeweb_players` as `p` ON `ps`.`player_id` = `p`.`id`
					        LEFT JOIN `bungeeweb_chat` as `ch` ON `l`.`id` = `ch`.`id`
					        LEFT JOIN `bungeeweb_commands` as `cmd` ON `l`.`id` = `cmd`.`id`
					        LEFT JOIN  (SELECT `sc`.`id`, `name` as `target_server`, `extra` FROM `bungeeweb_serverchanges` as `sc`
					            LEFT JOIN `bungeeweb_servers` as `s` ON `sc`.`target_server` = `s`.`id`) as `sc` ON `l`.`id` = `sc`.`id`
					        WHERE `uuid` = ? AND `l`.`id` < ?
					        ORDER BY `l`.`id` DESC
					        LIMIT ?;
					""",
				(rs, rn) -> FullPlayerLogEntry.fromResultSet(rs),
				uuid, lastId, limit);
	}

}
