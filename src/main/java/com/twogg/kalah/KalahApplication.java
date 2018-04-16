package com.twogg.kalah;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.web.bind.annotation.RequestMapping;

@SpringBootApplication
@ComponentScan(basePackages = "com.twogg.kalah.*")
public class KalahApplication {

	public static void main(String[] args) {
		SpringApplication.run(KalahApplication.class, args);
	}

	@RequestMapping("/")
	public String index() {
		return "index.html";
	}
}
