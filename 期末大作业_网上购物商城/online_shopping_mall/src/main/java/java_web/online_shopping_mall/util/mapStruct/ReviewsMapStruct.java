package java_web.online_shopping_mall.util.mapStruct;

import java_web.online_shopping_mall.POJO.DTO.VO.ReviewsVO;
import java_web.online_shopping_mall.entity.Reviews;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

@Mapper
public interface ReviewsMapStruct {
    ReviewsMapStruct instance = Mappers.getMapper(ReviewsMapStruct.class);

    // 通过review返回reviewsVO
    ReviewsVO toReviewsVO(Reviews review);
}
