package com.smm.frame.authority;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;

import java.util.Date;

/**
 * @author jianxuan
 * @since 2019/11/27 10:03
 * 注释: JWT验证登录工具
 * <p>
 * iss: jwt 签发者
 * sub: jwt 所面向的用户
 * aud: 接收 jwt 的一方
 * exp: jwt 的过期时间，这个过期时间必须大于签发时间
 * nbf: 定义在什么时间之前，该 jwt 都不可用
 * iat: jwt 的签发时间
 * jti: jwt 的唯一标识
 */

public class JwtUtil {

    /**
     * token的header名称
     */
    public static final String TOKEN_HEADER = "Authorization";
    /**
     * token的前缀，注意空格
     */
    public static final String TOKEN_PREFIX = "Bearer ";

    /**
     * 过期时间是3600秒，既是1个小时
     */
    private static final long EXPIRATION = 3600L;

    private static final String SECRET = "jwt_secret";

    private static final String ISS = "Jianxuan";

    /**
     * 创建Token
     *
     * @param username 登录账号
     * @return
     */
    public static String createToken(String username) {
        return JwtUtil.TOKEN_PREFIX + Jwts.builder()
                .signWith(SignatureAlgorithm.HS512, SECRET)
                .setIssuer(ISS)
                .setSubject(username)
                .setExpiration(new Date(System.currentTimeMillis() + EXPIRATION * 8))
                .setIssuedAt(new Date())
                .compact();
    }

    /**
     * 获取设置的用户信息
     *
     * @param token
     * @return
     */
    public static String getSubject(String token) {
        return getTokenBody(token).getSubject();
    }

    /**
     * 解析token成可获取参数的对象
     *
     * @param token
     * @return
     */
    private static Claims getTokenBody(String token) {
        return Jwts.parser()
                .setSigningKey(SECRET)
                .parseClaimsJws(token)
                .getBody();
    }
}
