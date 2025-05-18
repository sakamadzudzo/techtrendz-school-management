package zw.co.techtrendz.school.management.api.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class AuthDto {

    private String username;

    private String password;

    private boolean rememberMe;

    public AuthDto(String username, String password) {

        this.username = username;

        this.password = password;
    }
}
