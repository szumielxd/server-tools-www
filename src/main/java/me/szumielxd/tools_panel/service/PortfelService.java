package me.szumielxd.tools_panel.service;

import lombok.AllArgsConstructor;
import me.szumielxd.tools_panel.entity.portfel.PortfelUser;
import me.szumielxd.tools_panel.repository.portfel.PortfelUserRepository;
import org.springframework.lang.NonNull;
import org.springframework.stereotype.Service;

import java.util.Optional;
import java.util.UUID;

@Service
@AllArgsConstructor
public class PortfelService {

	private final @NonNull PortfelUserRepository portfelUserRepository;

	public Optional<PortfelUser> getByUUID(@NonNull UUID uuid) {
		return portfelUserRepository.findById(uuid);
	}

}
