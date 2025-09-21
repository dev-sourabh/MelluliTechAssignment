package com.mellulitech.security;

import com.auth0.jwk.Jwk;
import com.auth0.jwk.JwkProvider;
import com.auth0.jwt.interfaces.RSAKeyProvider;

import java.security.interfaces.RSAPrivateKey;
import java.security.interfaces.RSAPublicKey;

public class MyRSAKeyProvider implements RSAKeyProvider {

    private final JwkProvider provider;

    public MyRSAKeyProvider(JwkProvider provider) {
        this.provider = provider;
    }

    @Override
    public RSAPublicKey getPublicKeyById(String keyId) {
        try {
            Jwk jwk = provider.get(keyId);
            return (RSAPublicKey) jwk.getPublicKey();
        } catch (Exception e) {
            throw new RuntimeException("Failed to get public key for keyId: " + keyId, e);
        }
    }

    @Override
    public RSAPrivateKey getPrivateKey() {
        return null; // not needed for verification
    }

    @Override
    public String getPrivateKeyId() {
        return null; // not needed for verification
    }
}
