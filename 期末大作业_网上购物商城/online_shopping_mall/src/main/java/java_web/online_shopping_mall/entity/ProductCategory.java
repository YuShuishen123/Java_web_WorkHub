package java_web.online_shopping_mall.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Data;

import java.time.LocalDateTime;

@Data
@JsonInclude(JsonInclude.Include.NON_DEFAULT) // 默认值为 0 的字段不输出
@TableName("product_categories")
public class ProductCategory {
    @TableId(type = IdType.AUTO)
    private Long id; // 分类主键

    private String name; // 分类名称

    private LocalDateTime createdAt; // 创建时间

    private LocalDateTime updatedAt; // 更新时间

    public ProductCategory(String name) {
        this.name = name;
    }
}

