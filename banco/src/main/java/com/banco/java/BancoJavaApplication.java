package com.banco.java;

import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.openfeign.EnableFeignClients;

import java.util.Optional;

@Slf4j
@SpringBootApplication
@EnableFeignClients
public class BancoJavaApplication {

	private static final int DEFAULT_PORT = 8080;

	public static void main(String[] args) {
		try {
			final int port = Optional.ofNullable(System.getProperty("app.port"))
					.filter(StringUtils::isNotBlank)
					.map(Integer::parseInt)
					.orElse(DEFAULT_PORT);

			SpringApplication.run(BancoJavaApplication.class, args);

			log.info("I'm Listening! - port: " + port);

		} catch (Exception e) {
			log.error("Application cannot boot up: " + e.getMessage(), e);
		}
	}
}
