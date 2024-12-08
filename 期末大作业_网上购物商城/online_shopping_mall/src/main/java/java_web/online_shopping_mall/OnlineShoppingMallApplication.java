package java_web.online_shopping_mall;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@MapperScan("java_web.online_shopping_mall.mapper") // 确保扫描到 mapper 包
@SpringBootApplication
public class OnlineShoppingMallApplication {

    public static void main(String[] args) {
        SpringApplication.run(OnlineShoppingMallApplication.class, args);
    }

}
