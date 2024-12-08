package java_web.userservice.mapper;


import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;

@Component
@Mapper
@ComponentScan(basePackages = "java_web.userservice.mapper")
public interface JwtBlacklistMapper{

    // 插入 token
    @Insert("INSERT INTO jwt_blacklist (token, expiration_time, created_at) " +
            "VALUES (#{token}, #{expirationTime}, NOW())")
    int insertToken(@Param("token") String token,
                     @Param("expirationTime") LocalDateTime expirationTime);

    // 检测 token 是否在黑名单中
    @Select("SELECT COUNT(1) FROM jwt_blacklist WHERE token = #{token}")
    int isTokenBlacklisted(@Param("token") String token);
}


