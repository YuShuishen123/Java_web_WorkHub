package java_web.online_shopping_mall.POJO.DTO;

import jakarta.validation.Valid;
import jakarta.validation.constraints.Email;
import lombok.Data;
import org.springframework.validation.annotation.Validated;

@Validated
@Valid
@Data
public class userUpdateDTO {

    private String password;
    @Email(message = "邮箱格式不正确")
    private String email;
    private String phone;
    private String nickname;

}
