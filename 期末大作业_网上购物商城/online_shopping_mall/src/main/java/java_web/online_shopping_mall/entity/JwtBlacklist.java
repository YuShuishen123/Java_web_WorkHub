package java_web.online_shopping_mall.entity;


import com.baomidou.mybatisplus.annotation.TableName;
import com.fasterxml.jackson.annotation.JsonInclude;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.Data;
@Entity
@Data
@JsonInclude(JsonInclude.Include.NON_DEFAULT) // 默认值为 0 的字段不输出
@TableName("jwt_blacklist")  // 指定表名
public class JwtBlacklist {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    int id;
    String token;

    // 构造器
    public JwtBlacklist(String token) {
        this.token = token;
    }

    public JwtBlacklist() {
    }
}


