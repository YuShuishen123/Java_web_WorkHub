package java_web.online_shopping_mall.service;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.IService;
import java_web.online_shopping_mall.POJO.DTO.ProductsDTO;
import java_web.online_shopping_mall.entity.Product;
import java_web.online_shopping_mall.util.PageResult;

import java.util.List;

public interface ProductService extends IService<Product> {

    // 添加商品
    void insertProduct(ProductsDTO productDTO);

    // 获取所有商品名称、图片和价格
    PageResult<Product> getProductNameImageAndPrice(int page, int size);

    // 根据商品名称模糊搜索商品
    PageResult<Product> searchProductByName(String keyword,int page, int size);

    // 根据商品ID查询商品详情
    Product getProductById(Long id);

    // 根据分类ID搜索商品
    PageResult<Product> getProductsByCategoryId(Long categoryId,int page, int size);

    // 更新商品库存
    void updateProductStock(Long productId, Integer stock);
}

