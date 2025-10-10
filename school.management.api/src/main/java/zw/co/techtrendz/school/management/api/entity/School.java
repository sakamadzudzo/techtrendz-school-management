package zw.co.techtrendz.school.management.api.entity;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Size;
import lombok.*;
import org.hibernate.envers.Audited;

@EqualsAndHashCode(callSuper = true)
@ToString(callSuper = true)
@Entity
@AllArgsConstructor
@NoArgsConstructor
@Data
@Audited
@Schema(description = "Entity representing a school institution")
public class School extends Base {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Schema(description = "Unique identifier for the school", example = "1")
    private Long id;

    @NotEmpty(message = "Name cannot be empty or null")
    @Column(nullable = false)
    @Schema(description = "Name of the school", example = "Springfield High School", required = true)
    private String name;

    @Schema(description = "Email address of the school", example = "info@springfield.edu")
    private String email;

    @Schema(description = "Telephone number", example = "+1234567890")
    private String telNumber;

    @Schema(description = "Primary mobile number", example = "+1234567890")
    private String mobileNumber;

    @Schema(description = "Secondary mobile number", example = "+0987654321")
    private String mobileNumber2;

    @Size(max = 255, message = "Address cannot exceed 255 characters")
    @Schema(description = "Physical address of the school", example = "123 Education Street, Springfield")
    private String address;

    @Schema(description = "Website URL", example = "https://www.springfield.edu")
    private String website;

    public School(long id) {
        this.id = id;
    }
}
