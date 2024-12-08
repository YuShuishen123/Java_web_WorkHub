package java_web.online_shopping_mall.util;

import com.github.pagehelper.PageInfo;

import java.util.List;

public class PageUtils {
    /**
     * 构建分页结果
     *
     * @param list 查询结果列表
     * @param <T>  数据类型
     * @return 分页结果
     */
    public static <T> PageResult<T> createPageResult(List<T> list) {
        // 使用 PageInfo 包装结果
        PageInfo<T> pageInfo = new PageInfo<>(list);
        // 构建分页结果
        PageResult<T> pageResult = new PageResult<>();
        pageResult.setTotal(pageInfo.getTotal());
        pageResult.setPageNum(pageInfo.getPageNum());
        pageResult.setPageSize(pageInfo.getPageSize());
        pageResult.setList(list);
        pageResult.setTotalPages(pageInfo.getPages()); // 设置总页数
        System.out.println(pageInfo.getPages());
        return pageResult;
    }
}