package java_web.online_shopping_mall.entity;

import com.baomidou.mybatisplus.annotation.*;
import lombok.Data;

import java.time.LocalDateTime;

@Data
@TableName("users")
public class User {

    @TableId(type = IdType.AUTO)
    private Long id; // 用户主键

    @TableField(value = "username")
    private String username; // 用户名，唯一

    @TableField(value = "password")
    private String password; // 加密后的密码

    @TableField(value = "email")
    private String email; // 邮箱，唯一

    @TableField(value = "phone")
    private String phone; // 手机号，唯一

    @TableField(value = "nickname")
    private String nickname; // 昵称

    @TableField(value = "avatar")
    private String avatar;

    @TableField(fill = FieldFill.INSERT)
    private LocalDateTime createdtime; // 创建时间

    @TableField(fill = FieldFill.INSERT_UPDATE)
    private LocalDateTime updatedtime; // 更新时间

}

