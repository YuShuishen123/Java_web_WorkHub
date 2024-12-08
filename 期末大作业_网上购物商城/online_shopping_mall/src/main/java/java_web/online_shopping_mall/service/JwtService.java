package java_web.online_shopping_mall.service;

import org.springframework.stereotype.Service;



@Service
public interface JwtService {
    // 将token加入黑名单
    void insertBlacklist(String token);
    // 验证token是否在黑名单中
    int selectByToken(String token);
}
