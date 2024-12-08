package java_web.online_shopping_mall.mapper;

import java_web.online_shopping_mall.POJO.DTO.ReviewsDto;
import java_web.online_shopping_mall.entity.Reviews;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import java.util.List;

@Mapper
public interface ReviewsMapper {

    // 查询某个商品的平均分
    @Select("SELECT AVG(rating) FROM reviews WHERE product_id = #{product_id};")
    Double selectAvgRating(int product_id);

    // 查询某个商品的全部评价及对应的分数
    @Select("SELECT * FROM reviews WHERE product_id = #{product_id};")
    List<Reviews> selectAllReviews(int product_id);

    // 插入评价
    @Insert("INSERT INTO reviews (product_id, user_id, rating, comment) VALUES (#{reviewsDto.product_id}, #{user_Id}, #{reviewsDto.rating}, #{reviewsDto.comment});")
    int insertReviews(@Param("reviewsDto") ReviewsDto reviewsDto, @Param("user_Id") Long user_Id);
}

