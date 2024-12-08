package java_web.main_modle;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;

@SpringBootApplication
@ComponentScan(basePackages = {"java_web.main_modle", "java_web.user_modle"})
public class MainModleApplication {

	public static void main(String[] args) {
		SpringApplication.run(MainModleApplication.class, args);
	}

}
