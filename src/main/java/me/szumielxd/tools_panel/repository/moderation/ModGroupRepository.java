package me.szumielxd.tools_panel.repository.moderation;


import me.szumielxd.tools_panel.entity.moderation.ModGroup;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ModGroupRepository extends JpaRepository<ModGroup, Long> {

}
