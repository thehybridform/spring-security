package com.kristinyoung.web.security;

public interface TokenFactory {
    String createToken(String userId);
    String getMessage(String token);

    class FailedToInitialize extends RuntimeException {
        FailedToInitialize(final Exception e) {
            super(e);
        }
    }

    class FailedToEncrypt extends RuntimeException {
        FailedToEncrypt(final Exception e) {
            super(e);
        }
    }

    class FailedToDecrypt extends RuntimeException {
        FailedToDecrypt(final Exception e) {
            super(e);
        }
    }

}
