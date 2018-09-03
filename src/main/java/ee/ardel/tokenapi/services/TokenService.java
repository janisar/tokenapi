package ee.ardel.tokenapi.services;

import ee.ardel.tokenapi.models.TokenRequest;
import ee.ardel.tokenapi.models.User;
import org.springframework.stereotype.Service;

@Service
public interface TokenService {

    String generateToken(User user);

    Boolean validateToken(TokenRequest tokenRequest);

    String getUserName(TokenRequest tokenRequest);
}
