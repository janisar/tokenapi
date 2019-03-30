package ee.ardel.tokenapi.services;

import org.junit.Assert;
import org.junit.Test;

public class TokenFileProviderTest {

    private final TokenKeyProvider tokenKeyProvider;

    public TokenFileProviderTest() {
        this.tokenKeyProvider = new TokenKeyProviderImpl("src/test/resources/token");
    }

    @Test
    public void shouldReadTestTokenFile() {
        var tokenByteArray = tokenKeyProvider.getPrivateKey();

        Assert.assertNotNull(tokenByteArray);
    }

    @Test(expected = TokenApiException.class)
    public void shouldThrowExceptionWhenFileNotFound() {
        new TokenKeyProviderImpl("wrongToken").getPrivateKey();
    }
}
