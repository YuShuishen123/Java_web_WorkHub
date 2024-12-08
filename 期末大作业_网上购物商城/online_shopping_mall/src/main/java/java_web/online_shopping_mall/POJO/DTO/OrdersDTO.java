package java_web.online_shopping_mall.POJO.DTO;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import lombok.Data;

import java.math.BigDecimal;

// 该类用于接收前端发送的订单创建请求
@Valid
@Data
public class OrdersDTO {
    @TableId(type = IdType.AUTO)
    @NotNull(message = "地址ID不能为空")
    private int address_id;
    @NotNull(message = "商品ID不能为空")
    private int product_id;
    @NotNull(message = "数量不能为空")
    private int quantity;
    private BigDecimal total_amount;
    //订单状态只能从已支付和未支付两种之间选择
    @Pattern(regexp = "^(未支付|已支付|已取消)$", message = "订单状态只能是 '未支付' 或 '已支付'或'已取消'")
    private String order_status;
    //如果为空，则默认订单未备注
    private String order_note;
}