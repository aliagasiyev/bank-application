package az.edu.turing.bankingapplication.model.dto.response;

import lombok.*;

@Getter
@Setter
@RequiredArgsConstructor
@AllArgsConstructor
@Builder
public class LoginResponse {
    String message;
    String username;
    String email;
    String accessToken;
    String refreshToken;

    public LoginResponse(String message) {
        this.message = message;
    }
}
