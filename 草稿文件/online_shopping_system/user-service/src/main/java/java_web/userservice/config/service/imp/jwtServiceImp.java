package java_web.userservice.config.service.imp;
import java_web.common.exception.CustomException;
import java_web.userservice.config.service.JwtService;
import java_web.userservice.mapper.JwtBlacklistMapper;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

@Service
public class jwtServiceImp implements JwtService {

    private final JwtBlacklistMapper jwtBlacklistMapper;
    public jwtServiceImp(JwtBlacklistMapper jwtBlacklistMapper){
        this.jwtBlacklistMapper=jwtBlacklistMapper;
    }

    @Override
    public void addToBlacklist(String token, LocalDateTime expirationTime) {
        int result = jwtBlacklistMapper.insertToken(token, expirationTime);
        if (result == 0) {
            // 插入失败时抛出自定义异常，包含错误信息和错误码
            throw new CustomException("Failed to add token to blacklist", "DB_INSERT_FAILED");
        }
    }


    @Override
       // 检查token是否在黑名单中
    public boolean isInBlacklist(String token) {
        // 查询token是否在黑名单中
        //返回1则说明在黑名单中
        return jwtBlacklistMapper.isTokenBlacklisted(token) > 0;
    }



}
