package java_web.online_shopping_mall.service.imp;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.github.pagehelper.PageHelper;
import java_web.online_shopping_mall.POJO.DTO.ProductsDTO;
import java_web.online_shopping_mall.entity.Product;
import java_web.online_shopping_mall.exception.CustomException;
import java_web.online_shopping_mall.mapper.ProductMapper;
import java_web.online_shopping_mall.service.ProductService;
import java_web.online_shopping_mall.util.PageResult;
import org.springframework.stereotype.Service;
import java.util.List;
import java_web.online_shopping_mall.util.PageUtils; // 导入分页工具类

@Service
public class ProductServiceImpl extends ServiceImpl<ProductMapper, Product> implements ProductService {

    private final ProductMapper productMapper;

    public ProductServiceImpl(ProductMapper productMapper) {
        this.productMapper = productMapper;
    }

    // 插入商品
    @Override
    public void insertProduct(ProductsDTO productDTO) {
        int result = productMapper.insert(productDTO);
        if(result == 0 ){
            throw new CustomException("添加商品失败", "INSERT_FAILED");
        }
    }

    // 获取所有商品的商品名称、图片和价格
    @Override
    public PageResult<Product> getProductNameImageAndPrice(int page, int size) {
        // 设置分页参数
        PageHelper.startPage(page, size);
        // 查询商品信息
        List<Product> products = productMapper.selectNameImageAndPrice();
        // 使用PageInfo包装结果（包含分页信息）并且返回
        return PageUtils.createPageResult(products);
    }

    @Override
    public PageResult<Product> searchProductByName(String keyword,int page,int size) {
        //        1,设置分页参数
        PageHelper.startPage(page,size);
        //         2,获取所有商品信息
        List<Product> products = productMapper.selectByName(keyword);
        if(products == null){
            throw new CustomException("没有找到符合条件的商品", "NOT_FOUND");
        }
        //        3,使用Page info包装结果和分页信息
        return PageUtils.createPageResult(products);
    }

    @Override
    public Product getProductById(Long id) {

        return productMapper.selectById(id);
    }

    @Override
    public PageResult<Product> getProductsByCategoryId(Long categoryId, int page, int size) {
//        1,设置分页参数
        PageHelper.startPage(page,size);
//         2,获取所有商品信息
        List<Product> products = productMapper.selectByCategoryId(categoryId);
        System.out.println(products);
//        3,使用Page info包装结果和分页信息
        return PageUtils.createPageResult(products);

    }

    @Override
    public void updateProductStock(Long productId, Integer stock) {
        int result = productMapper.updateProductStock(productId, stock);
        if (result == 0) {
            throw new CustomException("库存更新失败", "UPDATE_FAILED");
        }
    }



}

