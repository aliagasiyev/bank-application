package az.edu.turing.bankingapplication.auth.model.response;

import az.edu.turing.bankingapplication.model.dto.response.RegisterResponse;
import az.edu.turing.bankingapplication.model.dto.response.UserResponse;
import lombok.*;


@Getter
@Setter
@NoArgsConstructor
@Builder
public class AuthResponse {

    private String message;
    private String token;
    private String refreshToken;
    private UserResponse user;

    public AuthResponse(String message) {
        this.message = message;
    }

    public AuthResponse(String message, String token, String refreshToken, UserResponse userResponse) {
        this.message = message;
        this.token = token;
        this.refreshToken = refreshToken;
        this.user = userResponse;

    }
    public AuthResponse(String message, String token, String refreshToken ){
        this.message = message;
        this.token = token;
        this.refreshToken = refreshToken;
    }
}
