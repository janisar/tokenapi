package ee.ardel.tokenapi.services;

import ee.ardel.tokenapi.controllers.AuthenticationController;
import ee.ardel.tokenapi.models.LoginRequest;
import ee.ardel.tokenapi.models.TokenResponse;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;
import org.springframework.http.ResponseEntity;

import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.class)
public class AuthenticationControllerTest {

    @InjectMocks
    private AuthenticationController authenticationController;
    @Mock
    private TokenService tokenService;

    @Test
    public void ShouldReturnJwtTokenResponse() {
        var loginRequest = new LoginRequest();
        var token = "token";

        when(tokenService.login(loginRequest)).thenReturn(token);

        ResponseEntity<TokenResponse> response = authenticationController.login(loginRequest);

        var tokenResponse = response.getBody();

        Assert.assertEquals(tokenResponse.getToken(), token);
    }
}
