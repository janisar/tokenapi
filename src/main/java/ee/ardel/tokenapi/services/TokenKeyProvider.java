package ee.ardel.tokenapi.services;

import org.springframework.stereotype.Service;

import java.security.interfaces.RSAPrivateKey;

@Service
public interface TokenKeyProvider {

    RSAPrivateKey getPrivateKey();
}
