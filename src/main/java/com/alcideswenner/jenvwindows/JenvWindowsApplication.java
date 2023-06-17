package com.alcideswenner.jenvwindows;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication(proxyBeanMethods = false)
public class JenvWindowsApplication {

	public static void main(String[] args) {
		SpringApplication.run(JenvWindowsApplication.class, args);
	}

}
