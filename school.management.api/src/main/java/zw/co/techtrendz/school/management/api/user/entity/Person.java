package zw.co.techtrendz.school.management.api.user.entity;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.persistence.*;
import lombok.*;
import org.hibernate.envers.Audited;
import zw.co.techtrendz.school.management.api.common.entity.Base;
import zw.co.techtrendz.school.management.api.user.enums.Salutation;

import java.time.LocalDate;

@EqualsAndHashCode(callSuper = true)
@ToString(callSuper = true)
@Entity
@AllArgsConstructor
@NoArgsConstructor
@Data
@Audited
@Schema(description = "Entity representing personal information for individuals")
public class Person extends Base {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Schema(description = "Unique identifier for the person", example = "1")
    private Long id;

    @Schema(description = "Salutation for the person", example = "MR")
    private Salutation salutation;

    @Schema(description = "First name", example = "John")
    private String firstName;

    @Schema(description = "Middle or other names", example = "William")
    private String otherNames;

    @Schema(description = "Last name or surname", example = "Doe")
    private String lastName;

    @Schema(description = "Email address", example = "john.doe@example.com")
    private String email;

    @Schema(description = "Primary mobile number", example = "+1234567890")
    private String mobileNumber;

    @Schema(description = "Secondary mobile number", example = "+0987654321")
    private String mobileNumber2;

    @Schema(description = "Telephone number", example = "+1234567890")
    private String telNumber;

    @Schema(description = "WhatsApp contact", example = "+1234567890")
    private String whatsapp;

    @Schema(description = "Facebook profile URL", example = "https://facebook.com/john.doe")
    private String facebook;

    @Schema(description = "LinkedIn profile URL", example = "https://linkedin.com/in/john-doe")
    private String linkedin;

    @Schema(description = "Date of birth", example = "1990-05-15")
    private LocalDate dateOfBirth;

    @Column(nullable = false)
    @Schema(description = "Any form of identification e.g. passport number, ID number", example = "90-334352k49", requiredMode = Schema.RequiredMode.REQUIRED)
    private String identification;

    public Person(long id) {
        this.id = id;
    }

    public String getFullname() {
        StringBuilder fullname = new StringBuilder();
        if (this.salutation != null) {
            fullname.append(this.salutation.getValue()).append(".");
        }
        if (this.firstName != null && !this.firstName.isBlank()) {
            fullname.append(fullname.isEmpty() ? this.firstName : " " + this.firstName);
        }
        if (this.otherNames != null && !this.otherNames.isBlank()) {
            fullname.append(fullname.isEmpty() ? this.otherNames : " " + this.otherNames);
        }
        if (this.lastName != null && !this.lastName.isBlank()) {
            fullname.append(fullname.isEmpty() ? this.lastName : " " + this.lastName);
        }

        return fullname.toString();
    }

    public String getName() {
        StringBuilder name = new StringBuilder();
        if (this.firstName != null && !this.firstName.isBlank()) {
            name.append(name.isEmpty() ? this.firstName : " " + this.firstName);
        }
        if (this.otherNames != null && !this.otherNames.isBlank()) {
            name.append(name.isEmpty() ? this.otherNames : " " + this.otherNames);
        }
        if (this.lastName != null && !this.lastName.isBlank()) {
            name.append(name.isEmpty() ? this.lastName : " " + this.lastName);
        }

        return name.toString();
    }
}
