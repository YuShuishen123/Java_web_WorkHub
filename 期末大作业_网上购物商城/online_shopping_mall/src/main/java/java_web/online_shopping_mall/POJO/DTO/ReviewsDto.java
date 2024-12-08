package java_web.online_shopping_mall.POJO.DTO;

import jakarta.validation.Valid;
import jakarta.validation.constraints.NotNull;
import lombok.Data;
import org.springframework.validation.annotation.Validated;

@Valid
@Validated
@Data
public class ReviewsDto {

    @NotNull(message = "商品ID不能为空")
    private Long product_id; // 商品 ID，关联 products 表

    private Integer rating; // 评分

    private String comment; // 评价内容
}
