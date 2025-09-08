package me.szumielxd.tools_panel.repository.auth;

import me.szumielxd.tools_panel.entity.auth.AuthUser;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface AuthUserRepository {

	Optional<? extends AuthUser> findByUUID(UUID uuid);

	Optional<? extends AuthUser> findByUsername(String username);

	List<? extends AuthUser> searchUsersByUsername(String username, boolean exact, int limit);

	void deleteUser(UUID uuid);


}
