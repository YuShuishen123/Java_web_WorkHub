package java_web.online_shopping_mall.util;

import lombok.Data;

import java.util.List;

@Data
public class PageResult<T> {
    private long total;     // 总记录数
    private int pageNum;    // 当前页码
    private int pageSize;   // 每页记录数
    private int totalPages;    // 总页数
    private List<T> list;   // 当前页数据
}
