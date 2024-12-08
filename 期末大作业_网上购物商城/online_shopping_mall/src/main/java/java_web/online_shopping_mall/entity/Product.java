package java_web.online_shopping_mall.entity;

import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.baomidou.mybatisplus.annotation.TableField;
import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Data;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Data
@TableName("products")
@JsonInclude(JsonInclude.Include.NON_DEFAULT) // 默认值为 0 的字段不输出
public class Product {

    @TableId
    private Long id; // 商品主键

    @TableField("name")
    private String name; // 商品名称

    @TableField("description")
    private String description; // 商品描述

    @TableField("price")
    private BigDecimal price; // 商品价格

    @TableField("stock")
    private Integer stock; // 商品库存

    @TableField("category_id")
    private Long category_id; // 商品分类 ID

    @TableField("image_url")
    private String image_url; // 商品图片路径

    @TableField("created_at")
    private LocalDateTime created_at; // 创建时间

    @TableField("updated_at")
    private LocalDateTime updated_at; // 更新时间

}

