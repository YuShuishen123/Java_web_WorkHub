package jdbc.jwt_demo.Colltroe;


import io.jsonwebtoken.Claims;
import jdbc.jwt_demo.JwtUtil ;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("/auth")
public class AuthController {

    @Autowired
    private JwtUtil jwtUtil;

    // 模拟的登录方法
    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestParam String username, @RequestParam String password) {
        // 简单验证（通常会查数据库）
        if ("user123".equals(username) && "password".equals(password)) {
            String token = jwtUtil.generateToken(username);

            Map<String, String> response = new HashMap<>();
            response.put("token", token);
            return ResponseEntity.ok(response);
        } else {
            return ResponseEntity.status(401).body("Invalid credentials");
        }
    }

    // 受保护的资源接口
    @GetMapping("/profile")
    public ResponseEntity<?> profile(@RequestHeader(value = "Authorization", required = false) String authHeader) {
        if(authHeader==null){
            return ResponseEntity.status(401).body("未登录");
        }
        try {
            String token = authHeader.replace("Bearer ", "");
            if (jwtUtil.isTokenExpired(token)) {
                return ResponseEntity.status(401).body("Token expired");
            }
            Claims claims = jwtUtil.verifyToken(token);
            return ResponseEntity.ok("Welcome, " + ((Claims) claims).getSubject());
        } catch (Exception e) {
            return ResponseEntity.status(401).body("Invalid token");
        }
    }
}