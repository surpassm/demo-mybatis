package com.liaoin.demo;
import com.spring4all.swagger.EnableSwagger2Doc;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cache.annotation.EnableCaching;
import tk.mybatis.spring.annotation.MapperScan;

/**
 * @author mc
 * Create date 2019/2/13 10:35
 * Version 1.0
 * Description 启动类
 */

@EnableCaching
@EnableSwagger2Doc
@SpringBootApplication
@MapperScan(basePackages = "com.liaoin.demo.mapper")
public class DemoApplication {

	public static void main(String[] args) {
		SpringApplication.run(DemoApplication.class, args);
	}

}

