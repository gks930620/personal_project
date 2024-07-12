package com.prac.prac;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class PracApplication {
	//항상 하나 만들면 테스트 만들면서 해야된다.

	public static void main(String[] args) {
		SpringApplication.run(PracApplication.class, args);
		System.out.println("프로젝트 위치 이동은 별 영향이없다.");
	}


}
