package zw.co.techtrendz.school.management.api.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import org.hibernate.envers.Audited;
import zw.co.techtrendz.school.management.api.enums.Status;

import java.time.LocalDateTime;

@EqualsAndHashCode(callSuper = true)
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

    private Boolean confirmed;

    private Boolean changePassword;
}
