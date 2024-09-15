package az.edu.turing.bankingapplication.auth.model.enums;

import lombok.Getter;

@Getter
public enum Role {
    USER("ROLE_USER"),
    ADMIN("ROLE_ADMIN"),
    MODERATOR("ROLE_MODERATOR");

    private final String roleName;

    Role(String roleName) {
        this.roleName = roleName;
    }

}