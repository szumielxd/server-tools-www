package me.szumielxd.tools_panel.repository.moderation;

import me.szumielxd.tools_panel.entity.moderation.Moderator;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Repository
public interface ModeratorRepository extends JpaRepository<Moderator, Long> {

	List<Moderator> findAllByGroupId(int groupId);

	Optional<Moderator> findByUuid(UUID uuid);


}
