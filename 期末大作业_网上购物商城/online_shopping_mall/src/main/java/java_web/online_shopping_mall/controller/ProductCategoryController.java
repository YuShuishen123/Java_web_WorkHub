package java_web.online_shopping_mall.controller;


import jakarta.validation.Valid;
import jakarta.validation.constraints.NotBlank;
import java_web.online_shopping_mall.entity.ProductCategory;
import java_web.online_shopping_mall.service.ProductCategoryService;
import java_web.online_shopping_mall.util.Response;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/categories")
@Valid
@Validated
public class ProductCategoryController {

    // 注入服务
    private final ProductCategoryService productCategoryService;

    public ProductCategoryController(ProductCategoryService productCategoryService) {
        this.productCategoryService = productCategoryService;
    }

    // 新增分类
    @PostMapping("/add")
    public Response<String> createCategory(@RequestParam @NotBlank(message = "分类名称不能为空") String name) {
        productCategoryService.insert(name);
        return Response.success("添加成功");
    }

    // 根据 ID 查询分类
    @GetMapping("/byId/{id}")
    public Response<ProductCategory> getCategoryById(@PathVariable Long id) {
        return Response.success(productCategoryService.getById(id));
    }

    // 查询所有分类
    @GetMapping
    public Response<List<ProductCategory>> getAllCategories() {
        return Response.success(productCategoryService.selectAll());
    }
}
