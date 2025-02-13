package com.sakurapuare.boatmanagement.utils;

import cn.hutool.core.date.DateField;
import cn.hutool.core.date.DateTime;
import cn.hutool.json.JSONObject;
import cn.hutool.jwt.JWT;
import cn.hutool.jwt.JWTPayload;
import cn.hutool.jwt.JWTUtil;
import com.sakurapuare.boatmanagement.config.JWTConfig;
import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

@Component
public class JWTUtils {

    private static JWTConfig jwtConfig;

    public JWTUtils(JWTConfig jwtConfig) {
        JWTUtils.jwtConfig = jwtConfig;
    }

    public static String generateToken(Map<String, Object> claims) {

        Map<String, Object> map = new HashMap<>();

        DateTime now = DateTime.now();
        DateTime newTime = now.offsetNew(DateField.SECOND, (int) jwtConfig.getExpire());

        map.put(JWTPayload.ISSUED_AT, now);
        map.put(JWTPayload.EXPIRES_AT, newTime);
        map.put(JWTPayload.NOT_BEFORE, now);

        map.putAll(claims);

        return JWTUtil.createToken(map, jwtConfig.getSignKey().getBytes());
    }

    public static JSONObject parseToken(String token) {
        return JWTUtil.parseToken(token).getPayloads();
    }


    public static boolean verifyToken(String token) {
        try {
            JWT jwt = JWTUtil.parseToken(token);
            jwt.setKey(jwtConfig.getSignKey().getBytes());
            return jwt.validate(0);
        } catch (Exception e) {
            return false;
        }

    }

    public static String getKey(String token, String keyName) {
        try {
            JWT jwt = JWTUtil.parseToken(token);
            jwt.setKey(jwtConfig.getSignKey().getBytes());
            return jwt.getPayload(keyName).toString();
        } catch (Exception e) {
            return null;
        }
    }

    public static Long getUserId(String token) {
        return Long.parseLong(Objects.requireNonNull(getKey(token, "userId")));
    }
}
