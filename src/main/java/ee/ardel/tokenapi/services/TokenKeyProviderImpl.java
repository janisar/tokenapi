package ee.ardel.tokenapi.services;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;

@Component
public class TokenKeyProviderImpl implements TokenKeyProvider {

    private final String fileLocation;

    public TokenKeyProviderImpl(@Value("${jwt.privateKey.location}") String fileLocation) {
        this.fileLocation = fileLocation;
    }

    @Override
    public byte[] getPrivateKey() {
        final File file = new File(fileLocation);
        try (FileInputStream stream = new FileInputStream(file)) {

            return stream.readAllBytes();
        } catch (IOException e) {
            throw new TokenApiException("Failed to load the secret key!");
        }
    }
}
