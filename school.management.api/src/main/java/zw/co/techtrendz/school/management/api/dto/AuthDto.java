package zw.co.techtrendz.school.management.api.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Schema(description = "DTO for user authentication credentials")
public class AuthDto {

    @Schema(description = "Username for authentication", example = "john.doe")
    private String username;

    @Schema(description = "Password for authentication", example = "password123")
    private String password;

    @Schema(description = "Whether to remember the user login", example = "true")
    private boolean rememberMe;

    public AuthDto(String username, String password) {

        this.username = username;

        this.password = password;
    }
}
