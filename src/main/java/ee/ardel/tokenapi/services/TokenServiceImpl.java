package ee.ardel.tokenapi.services;

import ee.ardel.tokenapi.models.LoginRequest;
import ee.ardel.tokenapi.models.RegisterRequest;
import ee.ardel.tokenapi.models.Role;
import ee.ardel.tokenapi.models.User;
import ee.ardel.tokenapi.repositories.UserRepository;
import ee.ardel.tokenapi.utils.PasswordUtil;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.util.Date;
import java.util.UUID;

@Component
public class TokenServiceImpl implements TokenService {

    private final String passwordSalt;
    private int tokenExpiryInHours;

    private final UserRepository userRepository;
    private final TokenKeyProvider tokenKeyProvider;

    public TokenServiceImpl(UserRepository userRepository,
                            TokenKeyProvider tokenKeyProvider,
                            @Value("${password.hash}") String passwordSalt,
                            @Value("${jwt.expiry.hours}") int expiryHours) {
        this.userRepository = userRepository;
        this.passwordSalt = passwordSalt;
        this.tokenKeyProvider = tokenKeyProvider;
        this.tokenExpiryInHours = expiryHours;
    }

    @Override
    public String login(LoginRequest loginRequest) {
        User loginUser = userRepository.findByEmailAndPassword(loginRequest.getEmail(),
                hashPassword(loginRequest.getPassword()));

        if (loginUser == null) {
            throw new UnAuthorizedException();
        }
        return generateToken(loginUser);
    }

    @Override
    public void register(RegisterRequest registerRequest) {
        userRepository.insert(createUser(registerRequest));
    }

    private User createUser(RegisterRequest registerRequest) {
        return User.builder()
                .email(registerRequest.getEmail())
                .password(hashPassword(registerRequest.getPassword()))
                .firstName(registerRequest.getFirstName())
                .lastName(registerRequest.getLastName())
                .id(UUID.randomUUID().toString())
                .role(Role.COMPANY)
                .build();
    }

    private String hashPassword(String password) {
        return PasswordUtil.hashPassword(password.toCharArray(), getSalt());
    }

    private byte[] getSalt() {
        return passwordSalt != null ? passwordSalt.getBytes() : null;
    }

    private String generateToken(User user) {
        return Jwts.builder()
                .signWith(SignatureAlgorithm.RS512, tokenKeyProvider.getPrivateKey())
                .claim(Claims.ID, user.getId())
                .claim(Claims.ISSUED_AT, new Date())
                .claim(Claims.EXPIRATION, getExpiry())
                .claim("role", "ROLE_" + user.getRole())
                .compact();
    }

    private Date getExpiry() {
        return new Date();
    }
}
