package az.edu.turing.bankingapplication.model.dto.response;

import lombok.*;

import java.util.Set;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class RegisterResponse{
        String message;
        Long id;
        String username;
        String email;


    public RegisterResponse(String accountRegisteredSuccessfully, RegisterResponse registerResponse) {
        this.message = accountRegisteredSuccessfully;
        this.id = registerResponse.id;
        this.username = registerResponse.username;
        this.email = registerResponse.email;
    }

    public RegisterResponse(String usernameAlreadyExists) {
        this.message = usernameAlreadyExists;
    }
}
