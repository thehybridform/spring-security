package com.kristinyoung.web.security;

public interface TokenFactory {

    String createToken(String userId);
    String getMessage(String token);

    class FailedToInitialize extends RuntimeException {
        public FailedToInitialize(final Exception e) {
            super(e);
        }
    }

    class FailedToEncrypt extends RuntimeException {
        public FailedToEncrypt(final Exception e) {
            super(e);
        }
    }

    class FailedToDecrypt extends RuntimeException {
        public FailedToDecrypt(final Exception e) {
            super(e);
        }
    }

}
