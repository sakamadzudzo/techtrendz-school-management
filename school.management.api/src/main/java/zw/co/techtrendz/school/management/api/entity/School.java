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
@Entity
@AllArgsConstructor
@NoArgsConstructor
@Data
@Audited
public class School extends Base {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotEmpty(message = "Name cannot be empty or null")
    @Column(nullable = false)
    private String name;

    private String email;

    private String telNumber;

    private String mobileNumber;

    private String mobileNumber2;

    @Size(max = 255, message = "Address cannot exceed 255 characters")
    private String address;

    private String website;
}
