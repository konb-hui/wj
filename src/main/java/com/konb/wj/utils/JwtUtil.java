package com.konb.wj.utils;

import com.konb.wj.pojo.User;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jws;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import org.springframework.util.StringUtils;

import javax.servlet.http.HttpServletRequest;
import java.util.Date;

/**
 * @author konb
 */
public class JwtUtil {

    public static final long EXPIRE = 1000 * 60 * 60 * 24;
    public static final String APP_SECRET = "konb";

    public static String getJwtToken(String id, String name) {
        String jwtToken = Jwts.builder()
                .setHeaderParam("typ", "JWT")
                .setHeaderParam("alg", "HS256")
                .setSubject("wj")
                .setIssuer("konb")
                .setIssuedAt(new Date())
                .setExpiration(new Date(System.currentTimeMillis() + EXPIRE))
                .claim("id", id)
                .claim("name", name)
                .signWith(SignatureAlgorithm.HS256, APP_SECRET)
                .compact();

        return jwtToken;

    }

    /**
     * 判断token是否有效
     */
    public static boolean checkToken(String jwtToken) {
        if (!StringUtils.hasText(jwtToken)) {
            return false;
        }
        try{
            Jwts.parser().setSigningKey(APP_SECRET).parseClaimsJws(jwtToken);
        }catch (Exception e) {
            e.printStackTrace();
            return false;
        }
        return true;
    }

    /**
     * 判断token是否有效
     */
    public static boolean checkToken(HttpServletRequest request) {
        String jwtToken = request.getHeader("token");
        return checkToken(jwtToken);
    }

    /**
     * 根据token获取用户信息
     */
    public static User getUserByJwtToken(HttpServletRequest request) {
        String jwtToken = request.getHeader("token");
        if (!StringUtils.hasText(jwtToken)) {
            return null;
        }
        Jws<Claims> claimsJws = Jwts.parser().setSigningKey(APP_SECRET).parseClaimsJws(jwtToken);
        Claims claims = claimsJws.getBody();
        User user = new User();
        user.setId((String) claims.get("id"));
        user.setUsername((String) claims.get("name"));
        return user;
    }

}
