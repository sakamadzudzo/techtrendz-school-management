package zw.co.techtrendz.school.management.api.user.entity;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Size;
import lombok.*;
import org.hibernate.annotations.Fetch;
import org.hibernate.annotations.FetchMode;
import org.hibernate.envers.Audited;
import zw.co.techtrendz.school.management.api.common.entity.Base;
import zw.co.techtrendz.school.management.api.user.enums.Role;

import java.util.List;

@EqualsAndHashCode(callSuper = true)
@ToString(callSuper = true)
@Entity(name = "app_user")
@AllArgsConstructor
@NoArgsConstructor
@Data
@Audited
@Schema(description = "Entity representing a system user account")
public class User extends Base {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Schema(description = "Unique identifier for the user", example = "1")
    private Long id;

    @NotEmpty(message = "Username cannot be empty or null")
    @Column(nullable = false)
    @Schema(description = "Username for login", example = "john.doe", required = true)
    private String username;

    @NotEmpty(message = "User must have a password")
    @Column(nullable = false)
    @Size(min = 8, message = "Password must be at least 8 characters long")
    @Schema(description = "Hashed password for authentication", example = "$2a$10...", required = true)
    private String password;

    @Schema(description = "Whether the user account is verified", example = "true")
    private Boolean verified;

    @Schema(description = "Whether the user account is enabled", example = "true")
    private Boolean enabled;

    @Schema(description = "Whether the user needs to change password on next login", example = "false")
    private Boolean changePassword;

    @ManyToOne
    @Fetch(FetchMode.JOIN)
    @Schema(description = "Person details associated with this user account")
    private Person person;

    @Schema(description = "List of roles assigned to this user")
    List<Role> roles;

    public User(long id) {
        this.id = id;
    }
}
