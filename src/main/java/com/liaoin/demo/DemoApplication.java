package com.liaoin.demo;
import com.spring4all.swagger.EnableSwagger2Doc;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;
import springfox.documentation.swagger2.annotations.EnableSwagger2;
import tk.mybatis.spring.annotation.MapperScan;

/**
 * @author mc
 * Create date 2019/2/16 14:44
 * Version 1.0
 * Description
 */

@ComponentScan({"com.liaoin.demo","com.github.surpassm.security","com.github.surpassm.config"})
@MapperScan(basePackages = "com.liaoin.demo.mapper")
@SpringBootApplication
@EnableSwagger2Doc
public class DemoApplication {

	public static void main(String[] args) {
		SpringApplication.run(DemoApplication.class, args);
	}

}

