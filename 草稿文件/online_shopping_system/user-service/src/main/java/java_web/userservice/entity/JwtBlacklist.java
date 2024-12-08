package java_web.userservice.entity;


import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

import java.time.LocalDateTime;

@Data
@TableName("jwt_blacklist") // 对应数据库表名
public class JwtBlacklist {

    @TableId(value = "id", type = IdType.AUTO) // 指定主键字段和自增策略
    private Long id;

    private String token; // JWT 令牌

    private LocalDateTime expirationTime; // 令牌过期时间

    private LocalDateTime createdAt; // 创建时间

    // 重写 toString 方法（便于日志和调试）
    @Override
    public String toString() {
        return "JwtBlacklist{" +
                "id=" + id +
                ", token='" + token + '\'' +
                ", expirationTime=" + expirationTime +
                ", createdAt=" + createdAt +
                '}';
    }
}

