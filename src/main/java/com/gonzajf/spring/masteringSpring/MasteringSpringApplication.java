package com.gonzajf.spring.masteringSpring;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.EnableConfigurationProperties;

import com.gonzajf.spring.masteringSpring.config.PicturesUploadProperties;

@SpringBootApplication
@EnableConfigurationProperties({PicturesUploadProperties.class})
public class MasteringSpringApplication {

	public static void main(String[] args) {
		SpringApplication.run(MasteringSpringApplication.class, args);
	}

}
