package java_web.userservice.config.service;

import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;


@Service
public interface JwtService {
    // 将token加入黑名单
    void addToBlacklist(String token, LocalDateTime expirationTime);
    // 验证token是否在黑名单中
    boolean isInBlacklist(String token);
}
