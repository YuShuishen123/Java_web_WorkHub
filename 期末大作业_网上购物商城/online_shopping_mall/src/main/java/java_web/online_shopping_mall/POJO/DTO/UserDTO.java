package java_web.online_shopping_mall.POJO.DTO;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Data;
import org.springframework.validation.annotation.Validated;



@Validated
@Data
public class UserDTO {
    @NotNull(message = "用户名不能为空")
    @Size(min = 6, max = 20, message = "用户名长度必须在6到20之间")
    private String username;
    @NotNull(message = "密码不能为空")
    @Size(min = 6, message = "密码长度不能小于6位")
    private String password;
}
