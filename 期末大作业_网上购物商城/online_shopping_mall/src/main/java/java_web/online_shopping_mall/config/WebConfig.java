package java_web.online_shopping_mall.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class WebConfig implements WebMvcConfigurer {

    @Override
    public void addCorsMappings(CorsRegistry registry) {
        registry.addMapping("/**")  // 允许所有路径跨域
                .allowedOrigins("http://localhost:5173")  // 前端的地址
                .allowedMethods("GET", "POST", "PUT", "DELETE", "OPTIONS")  // 允许的请求方法
                .allowedHeaders("Authorization", "Content-Type")  // 允许的请求头
                .allowCredentials(true)  // 允许发送 Cookie
                .maxAge(3600);  // 缓存预检请求的最大时长（单位：秒）
    }
}
