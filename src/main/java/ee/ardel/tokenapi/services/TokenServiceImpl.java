package ee.ardel.tokenapi.services;

import com.auth0.jwt.JWT;
import com.auth0.jwt.JWTVerifier;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.exceptions.JWTCreationException;
import com.auth0.jwt.exceptions.JWTVerificationException;
import com.auth0.jwt.interfaces.DecodedJWT;
import ee.ardel.tokenapi.models.TokenRequest;
import ee.ardel.tokenapi.models.User;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.env.Environment;
import org.springframework.stereotype.Component;

@Component
public class TokenServiceImpl implements TokenService {

    @Value("${jwt.secret}")
    private String secret;

    private Algorithm algorithmHS;

    public TokenServiceImpl(Environment environment) {
        this.algorithmHS = Algorithm.HMAC256(environment.getProperty("jwt.secret"));
    }

    @Override
    public String generateToken(User user) {
        try {
            return JWT.create()
                    .withIssuer("janis")
                    .withClaim("role", user.getRole())
                    .withClaim("email", user.getEmail())
                    .withClaim("id", user.getId())
                    .withClaim("firstName", user.getFirstName())
                    .withClaim("lastName", user.getLastName())
                    .sign(algorithmHS);
        } catch (JWTCreationException exception) {
            //Invalid Signing configuration / Couldn't convert Claims.
        }
        return null;
    }

    @Override
    public Boolean validateToken(TokenRequest tokenRequest) {
        try {
            getToken(tokenRequest);

            return true;
        } catch (JWTVerificationException exception) {
            //Invalid signature/claims
        }
        return false;
    }

    @Override
    public String getUserName(TokenRequest tokenRequest) {
        DecodedJWT jwt = getToken(tokenRequest);
        return jwt.getClaim("id").asString();
    }

    private DecodedJWT getToken(TokenRequest tokenRequest) {
        JWTVerifier verifier = JWT.require(algorithmHS)
                .withIssuer("janis")
                .build();
        return verifier.verify(tokenRequest.getToken());
    }
}
