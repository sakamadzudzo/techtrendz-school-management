package zw.co.techtrendz.school.management.api.view;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import zw.co.techtrendz.school.management.api.enums.Salutation;
import zw.co.techtrendz.school.management.api.enums.Status;
import zw.co.techtrendz.school.management.api.utility.AppUtils;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class StudentDto {

    private Long id;

    private Salutation salutation;

    private String firstName;

    private String otherNames;

    private String lastName;

    private LocalDate dateOfBirth;

    private String email;

    private String mobileNumber;

    private String mobileNumber2;

    private String telNumber;

    private String whatsapp;

    private String facebook;

    private String linkedin;

    private Status status;

    private LocalDateTime datestamp;

    private StudentClassDto classroom;

    private DormDto dorm;

    private SchoolDto school;

    private Integer startYear;

    private Integer endYear;

    public StudentDto(
            Long id,
            Salutation salutation,
            String firstName,
            String otherNames,
            String lastName,
            LocalDate dateOfBirth,
            String email,
            String mobileNumber,
            String mobileNumber2,
            String telNumber,
            String whatsapp,
            String facebook,
            String linkedin,
            Status status,
            LocalDateTime datestamp
    ){
        this.id = id;
        this.salutation = salutation;
        this.firstName = firstName;
        this.otherNames = otherNames;
        this.lastName = lastName;
        this.dateOfBirth = dateOfBirth;
        this.email = email;
        this.mobileNumber = mobileNumber;
        this.mobileNumber2 = mobileNumber2;
        this.telNumber = telNumber;
        this.whatsapp = whatsapp;
        this.facebook = facebook;
        this.linkedin = linkedin;
        this.status = status;
        this.datestamp = datestamp;
    }

    public String getFullname() {
        return AppUtils.getFullname(this.salutation, this.firstName, this.otherNames, this.lastName);
    }

    public String getName() {
        return AppUtils.getName(this.firstName, this.otherNames, this.lastName);
    }
}
