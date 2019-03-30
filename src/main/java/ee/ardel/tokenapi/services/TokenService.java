package ee.ardel.tokenapi.services;

import ee.ardel.tokenapi.models.LoginRequest;
import ee.ardel.tokenapi.models.RegisterRequest;
import org.springframework.stereotype.Service;

@Service
public interface TokenService {

    String login(LoginRequest user);

    void register(RegisterRequest registerRequest);
}
