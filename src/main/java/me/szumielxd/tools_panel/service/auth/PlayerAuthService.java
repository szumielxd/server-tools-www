package me.szumielxd.tools_panel.service.auth;

import lombok.AllArgsConstructor;
import me.szumielxd.tools_panel.entity.PlayerInfo;
import me.szumielxd.tools_panel.entity.auth.AuthUser;
import me.szumielxd.tools_panel.repository.auth.AuthUserRepository;
import org.springframework.lang.NonNull;
import org.springframework.lang.Nullable;

import java.util.List;
import java.util.Optional;
import java.util.UUID;
import java.util.function.Function;

@AllArgsConstructor
public abstract class PlayerAuthService {

	private final @NonNull AuthUserRepository authUserRepository;

	public abstract void setPassword(@NonNull UUID uuid, @NonNull String password);
	public abstract void enablePremium(@NonNull UUID uuid, @NonNull UUID premiumUUID);
	public abstract void disablePremium(@NonNull UUID uuid);

	public Optional<? extends AuthUser> getByUUID(@NonNull UUID uuid) {
		return authUserRepository.findByUUID(uuid);
	}

	public void deleteUser(@NonNull UUID uuid) {
		authUserRepository.deleteUser(uuid);
	}

	public @NonNull Optional<PlayerInfo> getInfo(@Nullable String query) {
		if (query == null) {
			return Optional.empty();
		}
		UUID uuid = null;
		try {
			uuid = UUID.fromString(query);
		} catch (IllegalArgumentException e) {
			return getInfoByUsername(query);
		}
		return getInfoByUUID(uuid);
	}

	public @NonNull Optional<PlayerInfo> getInfoByUUID(@NonNull UUID uuid) {
		return authUserRepository.findByUUID(uuid)
				.map(AuthUser::toPlayerInfo);
	}

	public @NonNull Optional<PlayerInfo> getInfoByUsername(@NonNull String username) {
		return authUserRepository.findByUsername(username)
				.map(AuthUser::toPlayerInfo);
	}

	public @NonNull List<PlayerInfoSearchEntity> searchInfoByUsername(String username) {
		return authUserRepository.searchUsersByUsername(username, false, 10).stream()
				.map(AuthUser::toPlayerInfo)
				.map(PlayerInfoSearchEntity.fromPlayerInfo(username))
				.toList();
	}

	public @NonNull List<PlayerInfoSearchEntity> searchInfoExact(String query) {
		return getInfo(query).stream()
				.map(PlayerInfoSearchEntity.fromPlayerInfo(query))
				.toList();
	}

	public record PlayerInfoSearchEntity(PlayerInfo user, String subject, String suffix) {

		public static @NonNull PlayerInfoSearchEntity fromPlayerInfo(@NonNull PlayerInfo user, String searchPhrase) {
			int start = user.username().toLowerCase().indexOf(searchPhrase.toLowerCase());
			if (start < 0) {
				throw new IllegalArgumentException("searchPhrase `%s` was not found in user's name `%s`".formatted(searchPhrase, user.username()));
			}
			int end = start + searchPhrase.length();
			return new PlayerInfoSearchEntity(
					new PlayerInfo(
							user.uuid(),
							user.username()),
					user.username().substring(start, end),
					user.username().substring(end));
		}

		public static @NonNull Function<PlayerInfo, PlayerInfoSearchEntity> fromPlayerInfo(String searchPhrase) {
			return user -> fromPlayerInfo(user, searchPhrase);
		}
	}

}
