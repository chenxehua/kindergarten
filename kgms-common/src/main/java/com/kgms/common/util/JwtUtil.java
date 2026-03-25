package com.kgms.common.util;

import io.jsonwebtoken.*;
import io.jsonwebtoken.security.Keys;
import org.springframework.util.StringUtils;

import javax.crypto.SecretKey;
import java.nio.charset.StandardCharsets;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

/**
 * JWT工具类
 */
public class JwtUtil {

    private static final String SECRET = "kgms@2024#kindergarten#growth#management#system";
    private static final Long EXPIRE_TIME = 7 * 24 * 60 * 60 * 1000L; // 7天
    private static final Long REFRESH_EXPIRE_TIME = 30 * 24 * 60 * 60 * 1000L; // 30天

    private static SecretKey getSecretKey() {
        return Keys.hmacShaKeyFor(SECRET.getBytes(StandardCharsets.UTF_8));
    }

    /**
     * 生成Token
     */
    public static String generateToken(String userId, Integer userType) {
        Map<String, Object> claims = new HashMap<>();
        claims.put("userId", userId);
        claims.put("userType", userType);
        return createToken(claims, userId, EXPIRE_TIME);
    }

    /**
     * 生成刷新Token
     */
    public static String generateRefreshToken(String userId) {
        Map<String, Object> claims = new HashMap<>();
        claims.put("userId", userId);
        claims.put("type", "refresh");
        return createToken(claims, userId, REFRESH_EXPIRE_TIME);
    }

    /**
     * 创建Token
     */
    private static String createToken(Map<String, Object> claims, String subject, Long expireTime) {
        Date now = new Date();
        Date expiryDate = new Date(now.getTime() + expireTime);

        return Jwts.builder()
                .claims(claims)
                .subject(subject)
                .issuedAt(now)
                .expiration(expiryDate)
                .signWith(getSecretKey())
                .compact();
    }

    /**
     * 解析Token
     */
    public static Claims parseToken(String token) {
        if (!StringUtils.hasText(token)) {
            return null;
        }
        try {
            return Jwts.parser()
                    .verifyWith(getSecretKey())
                    .build()
                    .parseSignedClaims(token)
                    .getPayload();
        } catch (JwtException e) {
            return null;
        }
    }

    /**
     * 获取用户ID
     */
    public static String getUserId(String token) {
        Claims claims = parseToken(token);
        if (claims == null) {
            return null;
        }
        return claims.get("userId", String.class);
    }

    /**
     * 获取用户类型
     */
    public static Integer getUserType(String token) {
        Claims claims = parseToken(token);
        if (claims == null) {
            return null;
        }
        return claims.get("userType", Integer.class);
    }

    /**
     * 验证Token是否有效
     */
    public static boolean validateToken(String token) {
        try {
            Claims claims = parseToken(token);
            if (claims == null) {
                return false;
            }
            return !claims.getExpiration().before(new Date());
        } catch (Exception e) {
            return false;
        }
    }

    /**
     * 判断是否为刷新Token
     */
    public static boolean isRefreshToken(String token) {
        Claims claims = parseToken(token);
        if (claims == null) {
            return false;
        }
        return "refresh".equals(claims.get("type", String.class));
    }
}
