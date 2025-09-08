package me.szumielxd.tools_panel.service.auth;

import at.favre.lib.crypto.bcrypt.BCrypt;
import me.szumielxd.tools_panel.repository.auth.impl.LibreloginUserRepository;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.lang.NonNull;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
@ConditionalOnProperty(name = "spring.datasource.auth.format", havingValue = "librelogin")
public class PlayerLibreloginService extends PlayerAuthService {

	private static final BCrypt.Hasher HASHER = BCrypt
			.with(BCrypt.Version.VERSION_2A);

	private final LibreloginUserRepository authUserRepository;

	public PlayerLibreloginService(LibreloginUserRepository authUserRepository) {
		super(authUserRepository);
		this.authUserRepository = authUserRepository;
	}

	@Override
	public void setPassword(@NonNull UUID uuid, @NonNull String password) {
		var bcrypt = HASHER.hashToString(10, password.toCharArray()).split("\\$");
		authUserRepository.setPasswordSaltAndAlgo(uuid,
				bcrypt[2] + "$" + bcrypt[3].substring(22),
				bcrypt[3].substring(0, 22),
				"BCrypt-" + bcrypt[1].toUpperCase());
	}

	@Override
	public void enablePremium(@NonNull UUID uuid, @NonNull UUID premiumUUID) {
		authUserRepository.setPremiumUUID(uuid, premiumUUID);
	}

	@Override
	public void disablePremium(@NonNull UUID uuid) {
		authUserRepository.setPremiumUUID(uuid, null);
	}

}
