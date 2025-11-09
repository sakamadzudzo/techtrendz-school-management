package zw.co.techtrendz.school.management.api.academic.view;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import zw.co.techtrendz.school.management.api.common.enums.Status;

import java.time.LocalDateTime;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class SchoolDto {

    private Long id;

    private String name;

    private String email;

    private String telNumber;

    private String mobileNumber;

    private String mobileNumber2;

    private String address;

    private String website;

    private Status status;

    private LocalDateTime datestamp;
}
