package java_web.online_shopping_mall.controller;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.Valid;
import java_web.online_shopping_mall.POJO.DTO.ReviewsDto;
import java_web.online_shopping_mall.POJO.DTO.VO.ReviewsVO;
import java_web.online_shopping_mall.util.Response;
import org.springframework.web.bind.annotation.*;
import java_web.online_shopping_mall.service.ReviewsService;

import java.util.List;

@RestController
@RequestMapping("/reviews")
public class ReviewsController {
    private final ReviewsService reviewsService;
    public ReviewsController(ReviewsService reviewsService){
        this.reviewsService = reviewsService;
    }

    // 查询某个商品的平均分
    @GetMapping("/avg_rating/{product_id}")
    public Response<Double> getAvgRating(@PathVariable int product_id){
        return Response.success(reviewsService.selectAvgRating(product_id));
    }

    // 查询某个商品的全部评价以及分数
    @GetMapping("/all_reviews/{product_id}")
    public Response<List<ReviewsVO>> getAllReviews(@PathVariable int product_id){
        return Response.success(reviewsService.selectAllReviews(product_id));
    }

    // 插入评价和分数
    @PostMapping("/add_reviews")
    public Response<String> addReviews(@Valid @RequestBody ReviewsDto reviewsDto, HttpServletRequest request){
        // 从请求中获取用户id
        Long userId = (Long) request.getAttribute("userId");
        System.out.println(reviewsDto);
        reviewsService.insertReviews(reviewsDto,userId);
        System.out.println(reviewsDto);
        return Response.success("评价成功");
    }
}
