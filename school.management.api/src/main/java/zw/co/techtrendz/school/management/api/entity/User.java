package zw.co.techtrendz.school.management.api.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Size;
import lombok.*;
import org.hibernate.annotations.Fetch;
import org.hibernate.annotations.FetchMode;
import org.hibernate.envers.Audited;
import zw.co.techtrendz.school.management.api.enums.Role;

import java.util.List;

@EqualsAndHashCode(callSuper = true)
@ToString(callSuper = true)
@Entity(name = "app_user")
@AllArgsConstructor
@NoArgsConstructor
@Data
@Audited
public class User extends Base {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotEmpty(message = "Username cannot be empty or null")
    @Column(nullable = false)
    private String username;

    @NotEmpty(message = "User must have a password")
    @Column(nullable = false)
    @Size(min = 8, message = "Password must be at least 8 characters long")
    private String password;

    private Boolean verified;

    private Boolean enabled;

    private Boolean changePassword;

    @ManyToOne
    @Fetch(FetchMode.JOIN)
    private Person person;

    List<Role> roles;

    public User(long id) {
        this.id = id;
    }
}
