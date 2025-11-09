package zw.co.techtrendz.school.management.api.auth.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Schema(description = "DTO for password change operations")
public class PasswordDto {

    @Schema(description = "Current password for verification", example = "oldPassword123")
    private String currentPassword;

    @Schema(description = "New password to set", example = "newPassword456")
    private String newPassword;
}
