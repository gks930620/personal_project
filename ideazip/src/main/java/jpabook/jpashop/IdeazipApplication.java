package jpabook.jpashop;

import com.querydsl.jpa.impl.JPAQueryFactory;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.PropertySource;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;

import javax.persistence.EntityManager;




@PropertySource("file.properties")
@PropertySource("herokudb.properties")
@EnableJpaAuditing //auditing기능 활성화.  BaseTimeEntity 참고
@SpringBootApplication
public class IdeazipApplication {

	public static void main(String[] args) {
		SpringApplication.run(IdeazipApplication.class, args);
	}


	@Bean
	JPAQueryFactory jpaQueryFactory(EntityManager em){   //이렇게 빈으로 할려면 em이 빈으로 등록되어있어야함.
		return new JPAQueryFactory(em);
	}




}
