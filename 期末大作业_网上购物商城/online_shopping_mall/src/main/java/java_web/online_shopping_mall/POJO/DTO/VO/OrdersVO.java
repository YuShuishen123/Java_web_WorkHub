package java_web.online_shopping_mall.POJO.DTO.VO;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import jakarta.validation.Valid;
import jakarta.validation.constraints.Pattern;
import lombok.Data;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Data
@Valid
public class OrdersVO {
    @TableId(type = IdType.AUTO)
    private int order_id;
    private String order_number;
    private String address;
    private String productName;
    private int quantity;
    private BigDecimal total_amount;
    //订单状态只能从已支付和未支付两种之间选择
    @Pattern(regexp = "^(未支付|已支付|已取消)$", message = "订单状态只能是 '未支付' 或 '已支付'或'已取消'")
    private String order_status;
    //如果为空，则默认订单未备注
    private String order_note;
    private LocalDateTime created_at;
    private LocalDateTime updated_at;
}