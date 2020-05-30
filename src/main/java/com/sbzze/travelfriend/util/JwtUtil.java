package com.sbzze.travelfriend.util;

import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import com.sbzze.travelfriend.entity.User;

import java.util.Date;

public class JwtUtil {

    // 过期时间30分钟
    private static final long EXPIRE_TIME = 30 * 60 * 1000;

    public static String getToken(User user) {

        Date date = new Date(System.currentTimeMillis() + EXPIRE_TIME);

        String token = "";

        token = JWT.create()
                .withAudience(user.getId())
                .withExpiresAt(date)
                .sign(Algorithm.HMAC256(user.getPassword()));

        return token;
    }
}
