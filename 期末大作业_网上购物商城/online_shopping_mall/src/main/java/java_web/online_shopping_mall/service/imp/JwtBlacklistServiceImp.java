package java_web.online_shopping_mall.service.imp;


import java_web.online_shopping_mall.mapper.JwtBlacklistMapper;
import java_web.online_shopping_mall.service.JwtService;
import org.springframework.stereotype.Service;

@Service  // 标记为服务类
public class JwtBlacklistServiceImp implements JwtService {

    // 注入JwtBlacklistMapper对象
    private final JwtBlacklistMapper jwtBlacklistMapper;

    public JwtBlacklistServiceImp(java_web.online_shopping_mall.mapper.JwtBlacklistMapper jwtBlacklistMapper) {
        this.jwtBlacklistMapper = jwtBlacklistMapper;
    }


    @Override
    public void insertBlacklist(String token) {
        jwtBlacklistMapper.insertBlacklist(token);
    }

    @Override
    public int selectByToken(String token) {
        return jwtBlacklistMapper.selectByToken(token);
    }
}

