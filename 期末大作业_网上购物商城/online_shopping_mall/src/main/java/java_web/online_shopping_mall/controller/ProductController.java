package java_web.online_shopping_mall.controller;

import jakarta.validation.Valid;
import java_web.online_shopping_mall.POJO.DTO.ProductsDTO;
import java_web.online_shopping_mall.entity.Product;
import java_web.online_shopping_mall.service.ProductService;
import java_web.online_shopping_mall.util.PageResult;
import java_web.online_shopping_mall.util.Response;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;


@Validated
@Valid
@RestController
@RequestMapping("/products")
public class ProductController {

    private final ProductService productService;

    public ProductController(ProductService productService) {
        this.productService = productService;
    }

    // 添加商品
    @PostMapping
    public Response<String> addProduct(@Valid @RequestBody ProductsDTO productDTO) {
        // 插入商品
        productService.insertProduct(productDTO);
        return Response.success("添加成功");
    }


    // 获取商品名称、图片和价格，支持分页
    @GetMapping
    public Response<PageResult<Product>> getProductNameImageAndPrice(
            @RequestParam(defaultValue = "1") int page,
            @RequestParam(defaultValue = "10") int size) {
        PageResult<Product> productPage = productService.getProductNameImageAndPrice(page, size);
        return Response.success(productPage);
    }

    // 根据商品名称模糊搜索商品
    @GetMapping("/search")
    public Response<PageResult<Product>> search(
            @RequestParam String keyword,
            @RequestParam(defaultValue = "1") int page,
            @RequestParam(defaultValue = "10") int size) {
        PageResult<Product> pageResult =productService.searchProductByName(keyword,page,size);

        return Response.success(pageResult);
    }

    // 根据商品ID查询商品详情
    @GetMapping("/detail")
    public Response<Product> getProductById(@RequestParam Long id) {
        Product product = productService.getProductById(id);
        return Response.success(product);
    }

    // 根据分类获取商品列表
    @GetMapping("/category")
    public Response<PageResult<Product>> getProductsByCategoryId(@RequestParam Long categoryId,
                                                                 @RequestParam(defaultValue = "1") int page,
                                                                 @RequestParam(defaultValue = "10") int size) {
        PageResult<Product> pageResult = productService.getProductsByCategoryId(categoryId,page,size);
        return Response.success(pageResult);
    }

}

