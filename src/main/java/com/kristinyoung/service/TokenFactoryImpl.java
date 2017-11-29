package com.kristinyoung.service;

import com.google.common.collect.Maps;
import com.google.common.io.BaseEncoding;
import com.kristinyoung.TokenFactory;
import org.apache.commons.lang3.RandomStringUtils;
import org.springframework.stereotype.Service;

import javax.crypto.Cipher;
import javax.crypto.NoSuchPaddingException;
import javax.crypto.spec.SecretKeySpec;
import java.nio.charset.Charset;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Map;

@Service
final class TokenFactoryImpl implements TokenFactory {

    private static final Charset UTF_8 = Charset.forName("UTF-8");
    private static final String CYPHER = "AES/ECB/PKCS5Padding";
    private static final int LENGTH = 16;
    private static final String SPACER = "+++kristinyoung+++";

    private final Map<String, KeyHolder> keys = Maps.newHashMap();

    @Override
    public String createToken(final String passPhrase, final String message) {
        return BaseEncoding.base64Url().omitPadding().encode(encrypt(getKeyHolder(passPhrase), message + SPACER + RandomStringUtils.randomAlphabetic(16)));
    }

    @Override
    public String getMessage(final String passPhrase, final String token) {
        final KeyHolder keyHolder = getKeyHolder(passPhrase);
        return parse(new String(decrypt(keyHolder, BaseEncoding.base64Url().decode(token)), UTF_8));
    }

    private String parse(final String s) {
        if (s != null) {
            final int i = s.indexOf(SPACER);
            if (i > 0) {
                return s.substring(0, i);
            }
        }
        return null;
    }

    private byte[] encrypt(final KeyHolder keyHolder, final String message) {
        try {
            final Cipher aes = createCipher();
            aes.init(Cipher.ENCRYPT_MODE, keyHolder.key);
            return aes.doFinal(message.getBytes(UTF_8));
        } catch (final Exception e) {
            throw new FailedToEncrypt(e);
        }
    }

    private byte[] decrypt(final KeyHolder keyHolder, final byte[] token) {
        try {
            final Cipher aes = createCipher();
            aes.init(Cipher.DECRYPT_MODE, keyHolder.key);
            return aes.doFinal(token);
        } catch (final Exception e) {
            throw new FailedToDecrypt(e);
        }
    }

    private Cipher createCipher() throws NoSuchPaddingException, NoSuchAlgorithmException {
        return Cipher.getInstance(CYPHER);
    }

    private KeyHolder getKeyHolder(final String passPhrase) {
        if (!keys.containsKey(passPhrase)) {
            keys.put(passPhrase, new KeyHolder(passPhrase));
        }

        return keys.get(passPhrase);
    }

    private static class KeyHolder {
        private final SecretKeySpec key;

        KeyHolder(final String passPhrase) {
            try {
                final MessageDigest digest = MessageDigest.getInstance("SHA");
                digest.update(("metachi auth token " + passPhrase).getBytes(UTF_8));
                key = new SecretKeySpec(digest.digest(), 0, LENGTH, "AES");
            } catch (final Exception e) {
                throw new FailedToInitialize(e);
            }
        }
    }
}
