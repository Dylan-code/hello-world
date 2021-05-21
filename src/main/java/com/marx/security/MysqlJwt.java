package com.marx.security;

import com.marx.dao.JwtMapper;
import com.marx.entity.JwtEntity;
import com.wobangkj.api.StorageJwt;
import com.wobangkj.auth.Authenticate;
import com.wobangkj.exception.SecretException;
import com.wobangkj.utils.HexUtils;
import com.wobangkj.utils.KeyUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import java.nio.charset.StandardCharsets;
import java.security.NoSuchAlgorithmException;
import java.util.Optional;

@Component
public class MysqlJwt extends StorageJwt {

    @Autowired
    private JwtMapper jwtMapper;

    protected MysqlJwt() throws NoSuchAlgorithmException {
    }

    /**
     * 给外界提供密钥
     */
    protected byte[] getSecret() throws SecretException {
        if (jwtMapper == null) {
            return new byte[0];
        }
        JwtEntity entity = jwtMapper.queryOne(null);
        if (entity != null && entity.getSecret_key() != null && !"".equals(entity.getSecret_key())) {
            return HexUtils.hex2Bytes(entity.getSecret_key());
        }
        return null;
    }

    @Override
    protected void setSecret(byte[] data) throws SecretException {
        if (jwtMapper == null) {
            return;
        }
        //获取密钥
        jwtMapper.insert(new JwtEntity(KeyUtils.byte2Hex(data)));
    }

    @PostConstruct
    protected void init() {
        try {
            this.initialize();
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        }
    }
}
