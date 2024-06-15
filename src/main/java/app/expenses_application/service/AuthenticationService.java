package app.expenses_application.service;

import app.expenses_application.dto.LoginRequest;
import app.expenses_application.dto.LoginResponse;
import app.expenses_application.model.Person;
import app.expenses_application.repository.PersonRepository;
import app.expenses_application.util.JwtService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.stereotype.Service;

/**
 * Service class for handling authentication-related operations.
 */
@Service
@RequiredArgsConstructor
public class AuthenticationService {
    private final PersonRepository personRepository;
    private final JwtService jwtService;
    private final AuthenticationManager authenticationManager;

    /**
     * Authenticates a user based on the provided login credentials.
     *
     * @param request the login request containing the email and password.
     * @return a LoginResponse containing the authentication token and the authenticated person.
     */
    public LoginResponse authenticate(final LoginRequest request) {
        authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(request.getEmail(), request.getPassword()));
        Person person = personRepository.findByEmail(request.getEmail())
                .orElseThrow(); // handle appropriately if person not found
        String jwtToken = jwtService.generateToken(person);
        return LoginResponse.builder()
                .token(jwtToken)
                .person(person)
                .build();
    }
}
