package com.marx.security;

import com.wobangkj.api.StorageJwt;
import com.wobangkj.exception.SecretException;

import javax.crypto.KeyGenerator;
import java.nio.charset.StandardCharsets;
import java.security.NoSuchAlgorithmException;

public class StrJwt extends StorageJwt {
    protected StrJwt() throws NoSuchAlgorithmException {
    }

    public StrJwt(KeyGenerator generator) throws NoSuchAlgorithmException {
        super(generator);
    }

    @Override
    protected byte[] getSecret() throws SecretException {
        return "".getBytes(StandardCharsets.UTF_8);
    }

    @Override
    protected void setSecret(byte[] data) throws SecretException {

    }
}
