package com.example.NetconfClient1;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;

@SpringBootApplication
@ComponentScan(basePackages = {"com.example.netconf.model","com.example.NetconfClient1"})
public class NetconfClient1Application {

	public static void main(String[] args) {
		SpringApplication.run(NetconfClient1Application.class, args);
	}

}
