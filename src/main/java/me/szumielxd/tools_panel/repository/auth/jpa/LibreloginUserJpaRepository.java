package me.szumielxd.tools_panel.repository.auth.jpa;

import me.szumielxd.tools_panel.entity.auth.AuthUser;
import me.szumielxd.tools_panel.entity.auth.impl.LibreloginUser;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface LibreloginUserJpaRepository extends JpaRepository<LibreloginUser, UUID> {

	@Query("""
			FROM LibreloginUser
			WHERE lastNickname LIKE ?#{escape(#username)}% escape '\\'
			ORDER BY CASE WHEN lastNickname = :username THEN 1 ELSE 0 END DESC, lastSeen DESC
			""")
	List<AuthUser> findMatchingByLastNicknameStartingWith(@Param("username") String username, Pageable pageable);

	Optional<AuthUser> findByLastNickname(String lastNickname);

}
