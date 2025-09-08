package me.szumielxd.tools_panel.entity.moderation;

import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.JdbcTypeCode;
import org.springframework.lang.NonNull;
import org.springframework.lang.Nullable;

import java.sql.Types;
import java.util.UUID;

@Entity
@Table(name = "users")
@Data
@NoArgsConstructor
public class Moderator {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(nullable = false)
	@Setter(AccessLevel.NONE)
	private @NonNull Long id;

	@Column(length = 36, nullable = false, unique = true)
	@JdbcTypeCode(Types.VARCHAR)
	private @NonNull UUID uuid;

	@Column(name = "forum_id")
	private @Nullable Integer forumId;

	@Column(name = "discord_id")
	private @Nullable Long discordId;

	@ManyToOne(fetch = FetchType.EAGER)
	private @Nullable ModGroup group;

	@Column(nullable = false)
	private boolean frozen;

}
