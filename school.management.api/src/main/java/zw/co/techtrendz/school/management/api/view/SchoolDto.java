package zw.co.techtrendz.school.management.api.view;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

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
}
