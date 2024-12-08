package java_web.online_shopping_mall.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.baomidou.mybatisplus.annotation.TableField;
import jakarta.validation.constraints.Pattern;
import lombok.Data;

import java.beans.Transient;
import java.math.BigDecimal;
import java.time.LocalDateTime;

/**
 * 订单实体类
 */
@TableName("orders")
@Data
public class Order {
    /**
     * 订单ID
     */
    @TableId(type = IdType.AUTO)
    @TableField("order_id")
    private Long order_id;

    /**
     * 用户ID
     */
    @TableField("user_id")
    private Long user_id;

    /**
     * 地址ID
     */
    @TableField("address_id")
    private Long address_id;

    /**
     * 商品ID
     */
    @TableField("product_id")
    private Long product_id;

    /**
     * 数量
     */
    @TableField("quantity")
    private int quantity;

    /**
     * 总价
     */
    @TableField("total_amount")
    private BigDecimal total_amount;

    /**
     * 订单状态（只能从已支付和未支付两种之间选择）
     */
    @TableField("order_status")
    @Pattern(regexp = "^(未支付|已支付|已取消)$", message = "订单状态只能是 '未支付' 或 '已支付'或'已取消'")
    private String order_status;

    /**
     * 订单备注（如果为空，则默认订单未备注）
     */
    @TableField("order_note")
    private String order_note;

    /**
     * 创建时间
     */
    @TableField("created_at")
    private LocalDateTime created_at;

    /**
     * 更新时间
     */
    @TableField("updated_at")
    private LocalDateTime updated_at;

    /**
     * 订单号
     */
    @TableField("order_number")
    private String order_number;
}