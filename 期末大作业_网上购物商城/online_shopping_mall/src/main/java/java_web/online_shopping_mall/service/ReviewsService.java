package java_web.online_shopping_mall.service;

import java_web.online_shopping_mall.POJO.DTO.ReviewsDto;
import java_web.online_shopping_mall.POJO.DTO.VO.ReviewsVO;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public interface ReviewsService {
    // 查询某个商品的平均分
    Double selectAvgRating(int product_id);
    // 查询某个商品的所有评论和对应的分数
    List<ReviewsVO> selectAllReviews(int product_id);
    // 插入评论
    void insertReviews(ReviewsDto reviewsDto,Long userId);
}
