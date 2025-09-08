package me.szumielxd.tools_panel.entity.portfel;

import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.JdbcTypeCode;
import org.springframework.lang.NonNull;

import java.sql.Types;
import java.util.UUID;

@Entity
@Table(name = "walutaprawdziwa_mf")
@Data
@NoArgsConstructor
public class PortfelUser {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(length = 36, nullable = false, unique = true)
	@JdbcTypeCode(Types.VARCHAR)
	@Setter(AccessLevel.NONE)
	private @NonNull UUID uuid;

	@Column(name = "Konto")
	private long balance;
	private long minorBalance;
	private boolean ignoreTop;

}
