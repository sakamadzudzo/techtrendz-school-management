package zw.co.techtrendz.school.management.api.user.view;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import zw.co.techtrendz.school.management.api.academic.view.ClassroomYearDto;
import zw.co.techtrendz.school.management.api.academic.view.SubjectYearDto;
import zw.co.techtrendz.school.management.api.user.enums.Salutation;
import zw.co.techtrendz.school.management.api.common.enums.Status;
import zw.co.techtrendz.school.management.api.common.utility.AppUtils;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class TeacherDto {

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

    private String identification;

    private Status status;

    private LocalDateTime datestamp;

    private List<ClassroomYearDto> classrooms;

    private List<SubjectYearDto> subjects;

    private Long extraCurricularYearId;

    public String getFullname() {
        return AppUtils.getFullname(this.salutation, this.firstName, this.otherNames, this.lastName);
    }

    public String getName() {
        return AppUtils.getName(this.firstName, this.otherNames, this.lastName);
    }
}