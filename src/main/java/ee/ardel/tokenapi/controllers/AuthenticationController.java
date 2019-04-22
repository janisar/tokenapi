package ee.ardel.tokenapi.controllers;

import ee.ardel.tokenapi.models.LoginRequest;
import ee.ardel.tokenapi.models.RegisterRequest;
import ee.ardel.tokenapi.models.TokenResponse;
import ee.ardel.tokenapi.services.TokenService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import static org.springframework.http.HttpStatus.CREATED;

@RestController
public class AuthenticationController {

    private final TokenService tokenService;

    public AuthenticationController(TokenService tokenService) {
        this.tokenService = tokenService;
    }

    @PostMapping(value = "/login")
    public ResponseEntity<TokenResponse> login(@RequestBody LoginRequest loginRequest) {
        return ResponseEntity.ok(toTokenResponse(tokenService.login(loginRequest)));
    }

    private TokenResponse toTokenResponse(String token) {
        return new TokenResponse(token);
    }


    @PostMapping(value = "/register")
    public ResponseEntity<Void> register(@RequestBody RegisterRequest registerRequest) {
        tokenService.register(registerRequest);
        return new ResponseEntity<>(CREATED);
    }
}
