package java_web.online_shopping_mall.entity;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Data;

import java.time.LocalDateTime;

@Data
@TableName("reviews") // 表名与数据库一致
// 为空的字段不发送
@JsonInclude(JsonInclude.Include.NON_NULL)
public class Reviews {

    @TableId
    private Long id; // 主键 ID

    @TableField("product_id")
    private Long product_id; // 商品 ID，关联 products 表

    @TableField("user_id")
    private Long user_id; // 用户 ID，关联 users 表

    @TableField("rating")
    private Integer rating; // 评分

    @TableField("comment")
    private String comment; // 评价内容

    @TableField("created_at")
    private LocalDateTime created_at; // 创建时间

    @TableField("updated_at")
    private LocalDateTime updated_at; // 更新时间
}

