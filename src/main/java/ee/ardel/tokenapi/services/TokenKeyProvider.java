package ee.ardel.tokenapi.services;

import org.springframework.stereotype.Service;

@Service
public interface TokenKeyProvider {

    byte[] getPrivateKey();
}
