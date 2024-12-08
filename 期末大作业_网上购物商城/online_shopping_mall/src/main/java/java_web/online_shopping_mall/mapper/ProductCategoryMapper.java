package java_web.online_shopping_mall.mapper;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import java_web.online_shopping_mall.entity.ProductCategory;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;

import java.util.List;

@Mapper
public interface ProductCategoryMapper extends BaseMapper<ProductCategory> {

    @Insert("INSERT INTO product_categories (name, created_at, updated_at) VALUES (#{name},NOW(), NOW());")
    int insert(String name);

    @Select("Select * from product_categories")
    List<ProductCategory> selectAll();

    // 根据id查询分类
    @Select("Select * from product_categories where id = #{id}")
    ProductCategory selectById(Long id);

}
