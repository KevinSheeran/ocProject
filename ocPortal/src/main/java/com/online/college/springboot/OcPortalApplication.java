package com.online.college.springboot;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;


@SpringBootApplication
@ComponentScan(basePackages={"*com.online.college"})
@MapperScan("com.online.college.core.*.dao")
public class OcPortalApplication {

	public static void main(String[] args) {
		SpringApplication.run(OcPortalApplication.class, args);
	}
}
