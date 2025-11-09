package zw.co.techtrendz.school.management.api.user.view;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import zw.co.techtrendz.school.management.api.academic.entity.ActivityPoint;
import zw.co.techtrendz.school.management.api.academic.view.DormYearDto;
import zw.co.techtrendz.school.management.api.academic.view.SchoolDto;
import zw.co.techtrendz.school.management.api.academic.view.StudentClassDto;
import zw.co.techtrendz.school.management.api.user.enums.Salutation;
import zw.co.techtrendz.school.management.api.common.enums.Status;
import zw.co.techtrendz.school.management.api.common.utility.AppUtils;

import java.time.LocalDate;
import java.time.LocalDateTime;

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

    private DormYearDto dorm;

    private SchoolDto school;

    private Integer startYear;

    private Integer endYear;

    private Long extraCurricularYearId;

    private Long dormId;

    private ActivityPoint activityPoint;

    private Long parentId;

    public String getFullname() {
        return AppUtils.getFullname(this.salutation, this.firstName, this.otherNames, this.lastName);
    }

    public String getName() {
        return AppUtils.getName(this.firstName, this.otherNames, this.lastName);
    }
}
