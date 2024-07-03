package com.techbro.sammychatbot;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@SpringBootApplication
@RestController
public class SammychatbotApplication {

	public static void main(String[] args) {
		SpringApplication.run(SammychatbotApplication.class, args);
	}

	@GetMapping("/api/v1/auth")
	public ResponseEntity<?> testing(@RequestParam("name") String name){
		return ResponseEntity.ok("Hello World");
	}

}
