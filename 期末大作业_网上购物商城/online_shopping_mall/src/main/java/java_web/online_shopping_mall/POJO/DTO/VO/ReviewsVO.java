package java_web.online_shopping_mall.POJO.DTO.VO;

import lombok.Data;

import java.time.LocalDateTime;

@Data
public class ReviewsVO {
    private Long id;
    private String username;
    private Integer rating;
    private String comment;
    private LocalDateTime created_at;
    private LocalDateTime updated_at;
}
