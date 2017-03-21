package com.michal.springboot;

import com.michal.springboot.domain.User;
import com.michal.springboot.service.UserService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;


import javax.annotation.PostConstruct;

@SpringBootApplication
public class CarRentalLublinApplication {

	public static void main(String[] args) {
		SpringApplication.run(CarRentalLublinApplication.class, args);
	}

	private static final Logger log = LoggerFactory.getLogger(UserService.class);

	@PostConstruct
	public void print(){


	}
}
