package java_web.online_shopping_mall.service;

import com.baomidou.mybatisplus.extension.service.IService;
import java_web.online_shopping_mall.entity.ProductCategory;

import java.util.List;

public interface ProductCategoryService extends IService<ProductCategory> {

    ProductCategory selectById(Long id);

    int insert(String name);

    List<ProductCategory> selectAll();
}
