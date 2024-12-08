package java_web.userservice.util;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import java_web.userservice.config.service.JwtService;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;
import java.util.Date;

@Component
public class JwtUtil {

    // 注入黑名单服务
    private final JwtService jwtService;

    public JwtUtil(JwtService jwtService) {
        this.jwtService = jwtService;
    }

    @Value("${jwt.secret}")
    private String secretKey;

    @Value("${jwt.expiration}")
    private long expirationTime;

    //根据用户名 生成 JWT
    public String generateToken(String username) {
        return Jwts.builder()
                .setSubject(username)
                .setIssuedAt(new Date())
                .setExpiration(new Date(System.currentTimeMillis() + expirationTime))
                .signWith(SignatureAlgorithm.HS256, secretKey)
                .compact();
    }

    // 验证和解析 JWT 是否正确
    public Claims verifyToken(String token) {
        return Jwts.parser()
                .setSigningKey(secretKey)
                .parseClaimsJws(token)
                .getBody();
    }

    // 检查 JWT 是否过期
    public boolean isTokenExpired(String token) {
        return verifyToken(token).getExpiration().before(new Date()) || jwtService.isInBlacklist(token);
    }

    // 模拟销毁 token，加入黑名单
    public void destroyToken(String token,  LocalDateTime expirationTime) {
        jwtService.addToBlacklist(token,expirationTime);
        // 将 token 加入黑名单
    }
}
