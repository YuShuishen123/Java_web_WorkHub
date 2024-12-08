package java_web.online_shopping_mall.service.imp;

import java_web.online_shopping_mall.POJO.DTO.ReviewsDto;
import java_web.online_shopping_mall.POJO.DTO.VO.ReviewsVO;
import java_web.online_shopping_mall.entity.Reviews;
import java_web.online_shopping_mall.exception.CustomException;
import java_web.online_shopping_mall.mapper.ReviewsMapper;
import java_web.online_shopping_mall.service.ReviewsService;
import org.springframework.stereotype.Service;
import java_web.online_shopping_mall.service.UserService;
import java_web.online_shopping_mall.util.mapStruct.ReviewsMapStruct;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

@Service
public class ReviewsServiceImp implements ReviewsService {

    private final ReviewsMapper reviewsMapper;
    private final UserService userService;
    public ReviewsServiceImp(ReviewsMapper reviewsMapper, UserService userService) {
        this.reviewsMapper = reviewsMapper;
        this.userService = userService;
    }

    @Override
    public Double selectAvgRating(int product_id) {
        return reviewsMapper.selectAvgRating(product_id);
    }

    public List<ReviewsVO> selectAllReviews(int product_id) {
        // 查询所有评论
        List<Reviews> reviews = reviewsMapper.selectAllReviews(product_id);

        // 创建 ReviewsVO 列表用于存放映射后的结果
        List<ReviewsVO> reviewsVOList = new ArrayList<>();
        // 遍历每一条评论
        for (Reviews review : reviews) {
            // 创建一个新的 ReviewsVO 对象
            ReviewsVO reviewsVO = ReviewsMapStruct.instance.toReviewsVO(review);
            // 查找对应的 username
            String username = userService.findUsernameById(review.getUser_id());
            reviewsVO.setUsername(Objects.requireNonNullElse(username, "该用户已注销"));
            // 将映射后的 ReviewsVO 添加到列表中
            reviewsVOList.add(reviewsVO);
        }

        // 返回映射后的 ReviewsVO 列表
        return reviewsVOList;
    }

    @Override
    public void insertReviews(ReviewsDto reviewsDto, Long userId) {
        int result = reviewsMapper.insertReviews(reviewsDto,userId);
        if(result == 0){
            throw new CustomException("添加评价失败", "NOT_FOUND");
        }
    }


}
