package az.edu.turing.bankingapplication.auth.model.response;

import az.edu.turing.bankingapplication.auth.model.enums.Role;
import az.edu.turing.bankingapplication.model.dto.response.RegisterResponse;
import lombok.*;

import java.util.HashSet;
import java.util.Set;

@Getter
@Setter
@NoArgsConstructor
public class AuthResponse {

    private String message;
    private String token;
    private String refreshToken;
    private Set<Role> roles;
    private RegisterResponse user;

    public AuthResponse(String message) {
        this.message = message;
    }

    public AuthResponse(String message, String newToken) {
        this.message = message;
        this.token = newToken;
    }

    public AuthResponse(String userRegisteredSuccessfully, String token, String refreshToken, Set<Role> roles, RegisterResponse registerResponse) {
        this.message = userRegisteredSuccessfully;
        this.token = token;
        this.refreshToken = refreshToken;
        this.roles = roles;
    }

    public AuthResponse(String loginSuccessful, String token, String refreshToken, RegisterResponse registerResponse) {
        this.message = loginSuccessful;
        this.token = token;
        this.refreshToken = refreshToken;
        this.roles = new HashSet<>();
    }
}
