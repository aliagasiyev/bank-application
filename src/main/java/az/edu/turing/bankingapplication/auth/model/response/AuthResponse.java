package az.edu.turing.bankingapplication.auth.model.response;

import az.edu.turing.bankingapplication.model.dto.response.RegisterResponse;
import lombok.*;

import java.util.HashSet;

@Getter
@Setter
@NoArgsConstructor

public class AuthResponse {

    private String message;
    private String token;
    private String refreshToken;
    private RegisterResponse user;

    public AuthResponse(String message) {
        this.message = message;
    }

    public AuthResponse(String message, String token, String refreshToken, RegisterResponse registerResponse) {
        this.message = message;
        this.token = token;
        this.refreshToken = refreshToken;
        this.user = registerResponse;

    }
    public AuthResponse(String message, String token, String refreshToken ){
        this.message = message;
        this.token = token;
        this.refreshToken = refreshToken;
    }


}
