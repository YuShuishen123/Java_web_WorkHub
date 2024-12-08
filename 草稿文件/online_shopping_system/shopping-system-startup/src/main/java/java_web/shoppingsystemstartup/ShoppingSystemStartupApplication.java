package java_web.shoppingsystemstartup;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;

@SpringBootApplication
@ComponentScan(basePackages = {"java_web.userservice", "java_web.common"})
@MapperScan("java_web.userservice.mapper") // 指定 Mapper 接口所在的包
public class ShoppingSystemStartupApplication {

    public static void main(String[] args) {
        SpringApplication.run(ShoppingSystemStartupApplication.class, args);
    }
}
