package java_web.online_shopping_mall.util;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import java_web.online_shopping_mall.service.JwtService;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.util.Date;

@SuppressWarnings("ALL")
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

    // 根据用户名和用户ID生成JWT
    public String generateToken(String username, Long userId) {
        return Jwts.builder()
                .setSubject(username) // 设置主题（一般为用户名）
                .claim("userId", userId) // 将用户ID添加到JWT的claims中
                .setIssuedAt(new Date()) // 签发时间
                .setExpiration(new Date(System.currentTimeMillis() + expirationTime)) // 过期时间
                .signWith(SignatureAlgorithm.HS256, secretKey) // 使用HS256算法加密并设置密钥
                .compact(); // 压缩生成JWT字符串
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
        return verifyToken(token).getExpiration().before(new Date()) || jwtService.selectByToken(token) != 0;
    }

    // 模拟销毁 token，加入黑名单
    public void destroyToken(String token) {
        System.out.println("销毁JWT");
        jwtService.insertBlacklist(token);
        // 将 token 加入黑名单
    }
}
