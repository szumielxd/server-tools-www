package me.szumielxd.tools_panel.entity.moderation;

import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import jakarta.persistence.*;
import lombok.*;
import me.szumielxd.tools_panel.persistence.converters.ColorAttributeConverter;
import me.szumielxd.tools_panel.persistence.serializers.ColorHexSerializer;

import java.awt.*;

@Entity
@Table(name = "groups")
@Data
@NoArgsConstructor
public class ModGroup {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(nullable = false)
	@Setter(AccessLevel.NONE)
	private @NonNull Long id;

	@Column(length = 32, nullable = false, unique = true)
	private @NonNull String name;

	@Column(nullable = false)
	@JsonSerialize(using = ColorHexSerializer.class)
	@Convert(converter = ColorAttributeConverter.class)
	private @NonNull Color color;

	@Column(nullable = false)
	private @NonNull Integer weight;

	@Column(name = "discord_id", nullable = false)
	private @NonNull Long discordId;

	@Column(name = "forum_id", nullable = false)
	private @NonNull Integer forumId;

	@Column(name = "minecraft_id", length = 36, nullable = false)
	private @NonNull String minecraftId;

}
