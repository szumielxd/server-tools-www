package me.szumielxd.tools_panel.entity.auth.impl;

import jakarta.persistence.*;
import lombok.*;
import me.szumielxd.tools_panel.entity.auth.AuthUser;
import org.hibernate.annotations.JdbcTypeCode;
import org.springframework.lang.NonNull;
import org.springframework.lang.Nullable;

import java.sql.Types;
import java.time.Instant;
import java.util.UUID;

@Table(name = "librepremium_data")
@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
public class LibreloginUser implements AuthUser {

	@Id
	@Column(length = 36, nullable = false)
	@JdbcTypeCode(Types.VARCHAR)
	@Setter(AccessLevel.NONE)
	private @NonNull UUID uuid;

	@Column(name = "premium_uuid", unique = true)
	@JdbcTypeCode(Types.VARCHAR)
	private @Nullable UUID premiumUuid;

	@Column(name = "hashed_password")
	private @Nullable String hashedPassword;

	@Column
	private @Nullable String salt;

	@Column
	private @Nullable String algo;

	@Column(name = "last_nickname", length = 32, nullable = false, unique = true)
	private @NonNull String lastNickname;

	@Column
	private @Nullable Instant joined;

	@Column(name = "last_seen")
	private @Nullable Instant lastSeen;

	@Column(name = "last_server")
	private @Nullable String lastServer;

	@Column
	private @Nullable String secret;

	@Column
	private @Nullable String ip;

	@Column(name = "last_authentication")
	private @Nullable Instant lastAuthentication;

	@Column
	private @Nullable String email;


	@NonNull
	@Override
	public String getName() {
		return this.lastNickname;
	}

	@NonNull
	@Override
	public UUID getUniqueId() {
		return this.uuid;
	}

	@Nullable
	@Override
	public JoinData getFirstJoin() {
		if (this.joined != null) {
			return new JoinData(null, joined.toEpochMilli());
		}
		return null;
	}

	@Nullable
	@Override
	public JoinData getLastJoin() {
		if (this.lastSeen != null) {
			return new JoinData(this.ip, this.lastSeen.toEpochMilli());
		}
		return null;
	}

	@Override
	public boolean isPremium() {
		return this.premiumUuid != null;
	}
}
