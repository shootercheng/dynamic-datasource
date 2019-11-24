package com.scd;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

@SpringBootApplication
@ComponentScan(basePackages = {"com.scd"})
@MapperScan(basePackages = {"com.scd.mapper"})
@EnableSwagger2
public class DynamicdatasourceApplication {

	public static void main(String[] args) {
		SpringApplication.run(DynamicdatasourceApplication.class, args);
	}

}
