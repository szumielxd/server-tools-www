package me.szumielxd.tools_panel.entity.moderation;

import jakarta.persistence.*;
import lombok.*;
import org.springframework.lang.NonNull;
import org.springframework.lang.Nullable;

import java.time.Instant;

@Entity
@Table(name = "actions")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class ModLog {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(nullable = false)
	@Setter(AccessLevel.NONE)
	private @NonNull Long id;

	@Column(nullable = false)
	private @NonNull Instant time;

	@ManyToOne
	@JoinColumn(nullable = false)
	private @NonNull Moderator user;

	@Column(nullable = false, length = 32)
	private @NonNull String type;

	@ManyToOne
	@JoinColumn(name = "group_from")
	private @Nullable ModGroup groupFrom;

	@ManyToOne
	@JoinColumn(name = "group_to")
	private @Nullable ModGroup groupTo;

	@Column(length = 400, nullable = false)
	private @NonNull String description;

	@Column(nullable = false)
	private boolean silent;

}
