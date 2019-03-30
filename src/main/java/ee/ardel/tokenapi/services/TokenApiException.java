package ee.ardel.tokenapi.services;

class TokenApiException extends RuntimeException {
    TokenApiException(String message) {
        super(message);
    }
}
