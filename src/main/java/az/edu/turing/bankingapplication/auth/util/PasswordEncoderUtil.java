package az.edu.turing.bankingapplication.auth.util;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Component;

@Component
public class PasswordEncoderUtil {

    private final BCryptPasswordEncoder passwordEncoder;

    public PasswordEncoderUtil() {
        this.passwordEncoder = new BCryptPasswordEncoder();
    }

    /**
     * Encodes the given plain text password.
     *
     * @param plainPassword the plain text password
     * @return the encoded password
     */
    public String encodePassword(String plainPassword) {
        return passwordEncoder.encode(plainPassword);
    }

    /**
     * Checks if the given plain text password matches the encoded password.
     *
     * @param plainPassword the plain text password
     * @param encodedPassword the encoded password
     * @return true if the passwords match, false otherwise
     */
    public boolean matchesPassword(String plainPassword, String encodedPassword) {
        return passwordEncoder.matches(plainPassword, encodedPassword);
    }
}
