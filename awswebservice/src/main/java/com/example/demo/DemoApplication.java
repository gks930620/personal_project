package com.example.demo;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;



// naver에서 EC2 네이버로그인 설정했으면 local에서는 안된다..  p318.
@SpringBootApplication
public class DemoApplication {
	//H2를 실행하세요
	public static void main(String[] args) {
		SpringApplication.run(DemoApplication.class, args);
		System.out.println("잘되야할텐데...");
	}

}
