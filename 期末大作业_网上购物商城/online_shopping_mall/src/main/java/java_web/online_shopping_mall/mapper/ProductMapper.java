package java_web.online_shopping_mall.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import java_web.online_shopping_mall.POJO.DTO.ProductsDTO;
import java_web.online_shopping_mall.entity.Product;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;

import java.util.List;

@Mapper
public interface ProductMapper extends BaseMapper<Product> {
    // 添加商品
    @Insert("INSERT INTO products (name, description, price, stock, category_id, image_url, created_at, updated_at) VALUES (#{name}, #{description}, #{price}, #{stock}, #{category_id}, #{image_url}, NOW(), NOW());")
    int insert(ProductsDTO productDTO);

    // 获取全部商品的全部信息
    @Select("SELECT id, name, description, price, stock, category_id, image_url, created_at, updated_at \n" +
            "FROM products;")
    List<Product> selectAll();

    // 获取全部商品名称图片和价格
    @Select("SELECT id, name, image_url, price FROM products")
    List<Product> selectNameImageAndPrice();

    // 根据名称模糊搜索商品，返回名称图片和价格
    @Select("SELECT id, name, image_url, price FROM products WHERE name LIKE CONCAT('%',#{keyword},'%')")
    List<Product> selectByName(String keyword);

    // 根据商品id获取单个商品全部信息
    @Select("SELECT * FROM products WHERE id = #{id}")
    Product selectById(Long id);

    // 根据分类id搜索商品，返回名称图片和价格
    @Select("SELECT id, name, image_url, price FROM products WHERE category_id = #{category_id}")
    List<Product> selectByCategoryId(Long categoryId);

    // 更新商品库存
    @Update("UPDATE products SET stock = #{stock} WHERE id = #{productID}")
    int updateProductStock(Long productID, Integer stock);

}
