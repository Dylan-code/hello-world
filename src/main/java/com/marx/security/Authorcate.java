package com.marx.security;

import com.marx.util.IocUtils;
import com.wobangkj.api.DBStorageJwt;
import com.wobangkj.api.Serializer;
import com.wobangkj.auth.Authenticate;
import com.wobangkj.auth.Authenticator;
import com.wobangkj.cache.LruMemCacheImpl;
import com.wobangkj.utils.KeyUtils;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import java.security.NoSuchAlgorithmException;

@Component
public class Authorcate extends Authenticator {

    public Authorcate(MyMemCache myMemCache) throws NoSuchAlgorithmException {
        super(myMemCache, new DBStorageJwt().instance("root","123456","8.136.6.137","3306","marx","jwt_entity"));
    }

    @Override
    protected String createToken(Serializer author) {
        return KeyUtils.md5Hex(String.format("%d;%d", System.currentTimeMillis(), author.hashCode()));
    }
}
