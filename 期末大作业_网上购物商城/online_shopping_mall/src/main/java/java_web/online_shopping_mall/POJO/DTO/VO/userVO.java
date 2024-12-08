package java_web.online_shopping_mall.POJO.DTO.VO;

import com.baomidou.mybatisplus.annotation.FieldFill;
import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import lombok.Data;

import java.time.LocalDateTime;

@Data
public class userVO {
    private String username;
    private String email;
    private String phone;
    private String nickname;
    private String avatar;
    private LocalDateTime createdtime;
    private LocalDateTime updatedtime;
}
