package app.expenses_application.dto;

import app.expenses_application.model.Person;
import lombok.*;

/**
 * Data Transfer Object for login responses.
 * This class encapsulates the authentication token and the associated person details.
 */

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class LoginResponse {

    private String token;
    private Person person;
}