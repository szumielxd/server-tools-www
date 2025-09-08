package me.szumielxd.tools_panel.repository.portfel;

import me.szumielxd.tools_panel.entity.portfel.PortfelUser;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.UUID;

@Repository
public interface PortfelUserRepository extends JpaRepository<PortfelUser, UUID> {
}
