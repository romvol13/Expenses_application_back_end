package app.expenses_application.controller;

import app.expenses_application.dto.LoginRequest;
import app.expenses_application.dto.LoginResponse;
import app.expenses_application.service.AuthenticationService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

/**
 * Controller class for handling authentication-related requests.
 * This class provides an endpoint for user authentication.
 */

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/auth")
public class AuthenticationController {
    private final AuthenticationService authenticationService;

    /**
     * Authenticates a user based on the provided login credentials.
     *
     * @param request the login request containing the username and password.
     * @return a ResponseEntity containing the authentication response.
     */
    @PostMapping("/authenticate")
    @CrossOrigin(origins = "http://localhost:4200")
    //@CrossOrigin(origins = "http://myngcode.s3-website.eu-central-1.amazonaws.com")
    public ResponseEntity<LoginResponse> authenticate(@RequestBody final LoginRequest request) {
        return ResponseEntity.ok(authenticationService.authenticate(request));
    }
}
