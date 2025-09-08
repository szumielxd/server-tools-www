package me.szumielxd.tools_panel.utils;

import lombok.experimental.UtilityClass;
import org.springframework.lang.NonNull;

import java.nio.ByteBuffer;
import java.util.UUID;

@UtilityClass
public class UUIDUtils {

	public byte[] toByteArray(@NonNull UUID uuid) {
		var bytes = ByteBuffer.allocate(16);
		bytes.putLong(uuid.getMostSignificantBits());
		bytes.putLong(uuid.getLeastSignificantBits());
		return bytes.array();
	}

	public @NonNull UUID fromByteArray(byte[] uuid) {
		if (uuid.length != 16) {
			throw new IllegalArgumentException("Invalid uuid length. Expected 16, got %d".formatted(uuid.length));
		}
		var bytes = ByteBuffer.wrap(uuid);
		return new UUID(bytes.getLong(), bytes.getLong());
	}

}
