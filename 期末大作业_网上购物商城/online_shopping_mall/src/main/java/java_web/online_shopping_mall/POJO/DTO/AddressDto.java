package java_web.online_shopping_mall.POJO.DTO;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

import java.io.Serializable;
import java.time.LocalDateTime;

/**
 * 收货地址实体类
 * 对应数据库表 `addresses`
 * 用于更新和插入地址信息
 */
@Data
public class AddressDto implements Serializable {
    @TableId(type = IdType.AUTO)
    private Long id;
    private String receiver_name;
    private String phone;
    private String province;
    private String city;
    private String district;
    private String detail;
    //    默认为False
    private Boolean is_default;
}

