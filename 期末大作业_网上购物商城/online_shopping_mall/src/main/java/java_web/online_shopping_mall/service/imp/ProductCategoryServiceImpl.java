package java_web.online_shopping_mall.service.imp;


import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import java_web.online_shopping_mall.entity.ProductCategory;
import java_web.online_shopping_mall.exception.CustomException;
import java_web.online_shopping_mall.mapper.ProductCategoryMapper ;
import java_web.online_shopping_mall.service.ProductCategoryService ;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ProductCategoryServiceImpl extends ServiceImpl<ProductCategoryMapper, ProductCategory> implements ProductCategoryService {

    private final ProductCategoryMapper ProductCategoryMapper;
    public ProductCategoryServiceImpl(ProductCategoryMapper productCategoryMapper) {
        this.ProductCategoryMapper = productCategoryMapper;
    }
    @Override
    public ProductCategory selectById(Long id) {
        ProductCategory productCategory = ProductCategoryMapper.selectById(id);
        if (productCategory == null){
            throw new CustomException("不存在该分类", "NOT_FOUND");
        }
        return productCategory;
    }

    @Override
    public int insert(String name) {
        int result = ProductCategoryMapper.insert(name);
        if (result == 0){
            throw new CustomException("添加失败", "INSERT_FAILED");
        }
        return result;
    }

    @Override
    public List<ProductCategory> selectAll() {
        return ProductCategoryMapper.selectAll();
    }
}

