package zw.co.techtrendz.school.management.api.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import zw.co.techtrendz.school.management.api.enums.Role;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class UserViewDto {

    private String username;

    private String email;

    private String name;

    private String mobileNumber;

    private String mobileNumber2;

    private String telNumber;

    private String whatsapp;

    private String facebook;

    private String linkedin;

    private Boolean verified;

    private Boolean enabled;

    private Boolean changePassword;

    List<Role> roles;
}
