package me.szumielxd.tools_panel.entity.auth;

import me.szumielxd.tools_panel.entity.PlayerInfo;
import org.springframework.lang.NonNull;
import org.springframework.lang.Nullable;

import java.util.UUID;

public interface AuthUser {

	@NonNull String getName();
	@NonNull UUID getUniqueId();
	@Nullable String getEmail();
	@Nullable JoinData getFirstJoin();
	@Nullable JoinData getLastJoin();
	boolean isPremium();

	record JoinData(@Nullable String ip, long date) {}

	default PlayerInfo toPlayerInfo() {
		return new PlayerInfo(getUniqueId(), getName());
	}

}
