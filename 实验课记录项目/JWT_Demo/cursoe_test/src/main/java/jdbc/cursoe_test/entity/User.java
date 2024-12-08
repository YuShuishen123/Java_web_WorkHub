package jdbc.cursoe_test.entity;

import lombok.Data;
import java.time.LocalDateTime;

@Data
public class User {
    private Long id;
    private String username;
    private String password;
    private String email;
    private String role;  // ROLE_STUDENT, ROLE_TEACHER, ROLE_ADMIN
    private String realName;
    private String avatar;
    private LocalDateTime createTime;
    private LocalDateTime updateTime;
    private Integer status; // 0-禁用 1-正常
} 