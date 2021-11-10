package br.com.alura.forum;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.data.web.config.EnableSpringDataWebSupport;

@EntityScan("br.com.alura.forum.modelo")
@SpringBootApplication
@EnableSpringDataWebSupport
@EnableCaching
public class ForumApplication {

	
	public static void main(String[] args) {
		SpringApplication.run(ForumApplication.class, args);
	}

}
