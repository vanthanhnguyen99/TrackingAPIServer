package com.vanth.VehicleTrackingServer;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.stereotype.Controller;

import com.vanth.tcpserver.TCPServer;

@SpringBootApplication
@EntityScan("com.vanth.entity")
@ComponentScan("com.vanth.controller, com.vanth.tcpserver,com.vanth.jwt.configs")
@EnableJpaRepositories("com.vanth.repository")

public class VehicleTrackingServerApplication {

	public static void main(String[] args) {
		
		TCPServer tcpServer = new TCPServer();

		SpringApplication.run(VehicleTrackingServerApplication.class, args);
		tcpServer.start();
	}

}
