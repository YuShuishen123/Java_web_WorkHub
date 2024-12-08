package java_web.online_shopping_mall.config;


import java_web.online_shopping_mall.filter.JwtFilter ;
import java_web.online_shopping_mall.util.JwtUtil ;
import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class FilterConfig {

    // 注册 JwtFilter 过滤器
    @Bean
    public FilterRegistrationBean<JwtFilter> jwtFilter(JwtUtil jwtUtil) {
        FilterRegistrationBean<JwtFilter> registrationBean = new FilterRegistrationBean<>();

        // 使用构造函数初始化 JwtFilter，注入 JwtUtil 和 TokenCheck
        JwtFilter jwtFilter = new JwtFilter(jwtUtil);

        // 将过滤器设置到注册类中
        registrationBean.setFilter(jwtFilter);  // 创建 JwtFilter 并注入 JwtUtil 和 TokenCheck
        registrationBean.addUrlPatterns("/*");  // 过滤器作用的 URL 模式，应用到所有路径
        registrationBean.setOrder(1);  // 设置过滤器的执行顺序，数字越小，优先级越高
        return registrationBean;
    }
}
