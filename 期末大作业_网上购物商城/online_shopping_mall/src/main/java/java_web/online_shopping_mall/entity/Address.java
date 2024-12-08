package java_web.online_shopping_mall.entity;

import com.baomidou.mybatisplus.annotation.*;
import lombok.Data;

import java.io.Serializable;
import java.time.LocalDateTime;

/**
 * 收货地址实体类
 * 对应数据库表 `addresses`
 */

@Data
@TableName("addresses")
public class Address implements Serializable {
//    ID自增
    @TableId(type = IdType.AUTO)
    private Long id;
    private Long user_id;
    private String receiver_name;
    private String phone;
    private LocalDateTime created_at;
    private LocalDateTime updated_at;
    private String province;
    private String city;
    private String district;
    private String detail;
    private Boolean is_default;

}

