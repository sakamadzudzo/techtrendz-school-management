package zw.co.techtrendz.school.management.api.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import zw.co.techtrendz.school.management.api.enums.Role;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Schema(description = "DTO for user view information")
public class UserViewDto {

    @Schema(description = "Username for login", example = "john.doe")
    private String username;

    @Schema(description = "Email address", example = "john.doe@example.com")
    private String email;

    @Schema(description = "Full name of the user", example = "John Doe")
    private String name;

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

    @Schema(description = "Whether the user account is verified", example = "true")
    private Boolean verified;

    @Schema(description = "Whether the user account is enabled", example = "true")
    private Boolean enabled;

    @Schema(description = "Whether the user needs to change password", example = "false")
    private Boolean changePassword;

    @Schema(description = "List of roles assigned to the user")
    List<Role> roles;
}
