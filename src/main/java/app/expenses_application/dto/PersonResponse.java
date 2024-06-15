package app.expenses_application.dto;

import app.expenses_application.model.Role;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.stereotype.Component;

/**
 * Data Transfer Object for person responses.
 * This class encapsulates the details of a person, including their ID, email, password, name, and role.
 */

@Data
@Component
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class PersonResponse {

    private Long id;
    private String email;
    private String password;
    private String name;
    private Role role;
}
