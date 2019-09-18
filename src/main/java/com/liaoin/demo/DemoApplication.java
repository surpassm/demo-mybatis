package com.liaoin.demo;
import com.github.surpassm.EnableSecurity;
import com.spring4all.swagger.EnableSwagger2Doc;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;
import tk.mybatis.spring.annotation.MapperScan;

@ComponentScan({"com.liaoin.demo","com.github.surpassm"})
@MapperScan(basePackages = "com.liaoin.demo.mapper")
@SpringBootApplication
@EnableSwagger2Doc
public class DemoApplication {

	public static void main(String[] args) {
		SpringApplication.run(DemoApplication.class, args);
	}

}

