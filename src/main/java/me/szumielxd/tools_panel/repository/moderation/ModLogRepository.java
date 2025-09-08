package me.szumielxd.tools_panel.repository.moderation;

import me.szumielxd.tools_panel.entity.moderation.ModLog;
import org.springframework.data.domain.Limit;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.awt.print.Pageable;
import java.util.List;

@Repository
public interface ModLogRepository extends JpaRepository<ModLog, Long> {

	List<ModLog> findByIdLessThanOrderByIdDesc(Long id, Limit limit);

}
