package java_web.online_shopping_mall.POJO.DTO;

import jakarta.validation.Valid;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

import java.math.BigDecimal;

@Data
@Valid
public class ProductsDTO {

    // 此类型用于接收前端提交的商品数据，包括商品名称、描述、价格、库存等字段。

    @NotNull(message = "商品名称不能为空")
    private String name; // 商品名称

    @NotNull(message = "商品描述不能为空")
    private String description; // 商品描述

    @NotNull(message = "商品价格不能为空")
    private BigDecimal price; // 商品价格

    @NotNull(message = "商品库存不能为空")
    @Min(value = 0, message = "商品库存必须大于等于0")
    private Integer stock; // 商品库存

    @NotNull(message = "商品分类ID不能为空")
    private Long category_id; // 商品分类 ID

    private String image_url; // 商品图片路径
}
