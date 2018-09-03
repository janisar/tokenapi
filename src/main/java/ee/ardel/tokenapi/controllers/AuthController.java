package ee.ardel.tokenapi.controllers;

import ee.ardel.tokenapi.models.TokenRequest;
import ee.ardel.tokenapi.models.User;
import ee.ardel.tokenapi.services.TokenService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(value = "/token")
public class AuthController {

    private final TokenService tokenService;

    @Autowired
    public AuthController(TokenService tokenService) {
        this.tokenService = tokenService;
    }

    @RequestMapping
    public ResponseEntity<String> generateToken(@RequestBody User user) {
        return new ResponseEntity<>(tokenService.generateToken(user), HttpStatus.OK);
    }

    @RequestMapping("/user")
    public ResponseEntity<String> getUser(@RequestBody TokenRequest tokenRequest) {
        return new ResponseEntity<>(tokenService.getUserName(tokenRequest), HttpStatus.OK);
    }

    @RequestMapping("/validate")
    public ResponseEntity<Boolean> validateToken(@RequestBody TokenRequest tokenRequest) {
        return new ResponseEntity<>(tokenService.validateToken(tokenRequest), HttpStatus.OK);
    }
}
