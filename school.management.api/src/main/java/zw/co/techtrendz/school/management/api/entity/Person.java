package zw.co.techtrendz.school.management.api.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import org.hibernate.envers.Audited;
import zw.co.techtrendz.school.management.api.enums.Salutation;
import zw.co.techtrendz.school.management.api.enums.Status;

import java.time.LocalDateTime;

@EqualsAndHashCode(callSuper = true)
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

    private LocalDateTime dateOfBirth;
}
