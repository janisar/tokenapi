package ee.ardel.tokenapi.services;

import org.apache.tomcat.util.codec.binary.Base64;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.security.GeneralSecurityException;
import java.security.KeyFactory;
import java.security.interfaces.RSAPrivateKey;
import java.security.spec.PKCS8EncodedKeySpec;

@Component
public class TokenKeyProviderImpl implements TokenKeyProvider {

    private final String fileLocation;

    public TokenKeyProviderImpl(@Value("${jwt.privateKey.location}") String fileLocation) {
        this.fileLocation = fileLocation;
    }

    @Override
    public RSAPrivateKey getPrivateKey() {
        try (FileInputStream stream = new FileInputStream(new File(fileLocation))) {

            byte[] privateKeyBytes = stream.readAllBytes();
            return getPrivateKeyFromString(getPrivateKeyPem(privateKeyBytes));
        } catch (IOException | GeneralSecurityException e) {
            throw new TokenApiException("Failed to load the secret key!");
        }
    }

    private static RSAPrivateKey getPrivateKeyFromString(String key) throws GeneralSecurityException {
        var keyFactory = KeyFactory.getInstance("RSA");
        var keySpec = new PKCS8EncodedKeySpec(Base64.decodeBase64(key));
        return (RSAPrivateKey) keyFactory.generatePrivate(keySpec);
    }

    private static String getPrivateKeyPem(byte[] privateKeyBytes) {
        var privateKeyPem = new String(privateKeyBytes);
        privateKeyPem = privateKeyPem.replace("-----BEGIN PRIVATE KEY-----\n", "");
        privateKeyPem = privateKeyPem.replace("-----END PRIVATE KEY-----", "");
        return privateKeyPem;
    }
}
