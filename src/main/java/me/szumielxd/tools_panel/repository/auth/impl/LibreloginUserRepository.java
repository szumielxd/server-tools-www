package me.szumielxd.tools_panel.repository.auth.impl;

import lombok.RequiredArgsConstructor;
import me.szumielxd.tools_panel.entity.auth.AuthUser;
import me.szumielxd.tools_panel.exception.request.NonExistentEntityException;
import me.szumielxd.tools_panel.repository.auth.AuthUserRepository;
import me.szumielxd.tools_panel.repository.auth.jpa.LibreloginUserJpaRepository;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.data.domain.Pageable;
import org.springframework.lang.NonNull;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Repository
@ConditionalOnProperty(name = "spring.datasource.auth.format", havingValue = "librelogin")
@RequiredArgsConstructor
public class LibreloginUserRepository implements AuthUserRepository {

	private final @NonNull LibreloginUserJpaRepository jpa;


	@Override
	public Optional<? extends AuthUser> findByUUID(UUID uuid) {
		return jpa.findById(uuid);
	}

	public Optional<? extends AuthUser> findByUsername(String username) {
		return jpa.findByLastNickname(username);
	}

	@Override
	public List<? extends AuthUser> searchUsersByUsername(String username, boolean exact, int limit) {
		if (exact) {
			return jpa.findByLastNickname(username).stream().toList();
		}
		return jpa.findMatchingByLastNicknameStartingWith(
				username,
				Pageable.ofSize(limit));
	}

	@Override
	@Transactional
	public void deleteUser(UUID uuid) {
		var user = jpa.findById(uuid).orElseThrow(NonExistentEntityException::new);
		jpa.delete(user);
	}

	@Transactional
	public void setPasswordSaltAndAlgo(UUID uuid, String hashedPassword, String salt, String algo) {
		var user = jpa.findById(uuid).orElseThrow(NonExistentEntityException::new);
		user.setAlgo(algo);
		user.setHashedPassword(hashedPassword);
		user.setSalt(salt);
		jpa.save(user);
	}

	@Transactional
	public void setPremiumUUID(UUID uuid, UUID premiumUUID) {
		var user = jpa.findById(uuid).orElseThrow(NonExistentEntityException::new);
		user.setPremiumUuid(premiumUUID);
		jpa.save(user);
	}
}
