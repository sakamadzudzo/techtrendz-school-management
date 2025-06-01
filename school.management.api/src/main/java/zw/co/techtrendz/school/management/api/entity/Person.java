package zw.co.techtrendz.school.management.api.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.*;
import org.hibernate.envers.Audited;
import zw.co.techtrendz.school.management.api.enums.Salutation;

import java.time.LocalDate;

@EqualsAndHashCode(callSuper = true)
@ToString(callSuper = true)
@Entity
@AllArgsConstructor
@NoArgsConstructor
@Data
@Audited
public class Person extends Base {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private Salutation salutation;

    private String firstName;

    private String otherNames;

    private String lastName;

    private String email;

    private String mobileNumber;

    private String mobileNumber2;

    private String telNumber;

    private String whatsapp;

    private String facebook;

    private String linkedin;

    private LocalDate dateOfBirth;

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
