package java_web.online_shopping_mall.POJO.DTO;

import jakarta.validation.Valid;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import lombok.Data;

@Valid
@Data
public class OrdersNewStatusDTO {
    @NotNull(message = "订单ID不能为空")
    private long order_id;
    @NotBlank(message = "订单状态不能为空")
    @Pattern(regexp = "^(未支付|已支付|已取消)$", message = "订单状态只能是 '未支付' 或 '已支付'或'已取消'")
    private String order_status;
}
