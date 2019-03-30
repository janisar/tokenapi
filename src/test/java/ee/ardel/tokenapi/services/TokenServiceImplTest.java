package ee.ardel.tokenapi.services;

import ee.ardel.tokenapi.models.LoginRequest;
import ee.ardel.tokenapi.models.Role;
import ee.ardel.tokenapi.models.User;
import ee.ardel.tokenapi.repositories.UserRepository;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mockito;
import org.mockito.junit.MockitoJUnitRunner;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.mock;

@RunWith(MockitoJUnitRunner.class)
public class TokenServiceImplTest {

    private final TokenService tokenService;

    private final UserRepository userRepository = mock(UserRepository.class);
    private final TokenKeyProvider tokenKeyProvider = mock(TokenKeyProvider.class);

    public TokenServiceImplTest() {
        this.tokenService = new TokenServiceImpl(userRepository, tokenKeyProvider, "Salt", 6);
    }

    @Test
    public void shouldLoginUserAndReturnJwtToken() {
        var loginRequest = new LoginRequest();
        loginRequest.setEmail("jon@winterfell.com");
        loginRequest.setPassword("Ygritte");

        User testUser = getTestUser("test1");
        Mockito.when(userRepository.findByEmailAndPassword(any(), any())).thenReturn(testUser);
        Mockito.when(tokenKeyProvider.getPrivateKey()).thenReturn(new byte[100]);

        var token = tokenService.login(loginRequest);

        Assert.assertNotNull(token);
        Assert.assertTrue(token.contains("ey"));
    }

    private User getTestUser(String id) {
        return User.builder()
                .email("jon@winterfell.com")
                .role(Role.EMPLOYEE)
                .firstName("Jon")
                .lastName("Snow")
                .password("Ygritte")
                .id(id)
                .build();
    }

    private byte[] getPrivateKey() throws IOException {
        File privateKeyFile = new File("src/test/resources/token");
        FileInputStream stream = new FileInputStream(privateKeyFile);
        return stream.readAllBytes();
    }

    private byte[] getPublicKey() throws IOException {
        File privateKeyFile = new File("src/test/resources/token.pub");
        FileInputStream stream = new FileInputStream(privateKeyFile);
        return stream.readAllBytes();
    }
}
