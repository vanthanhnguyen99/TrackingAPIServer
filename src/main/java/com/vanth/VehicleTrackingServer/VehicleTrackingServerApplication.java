package com.vanth.VehicleTrackingServer;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

import com.vanth.tcpserver.TCPServer;

@SpringBootApplication
@EntityScan("com.vanth.entity")
@ComponentScan("com.vanth.controller, com.vanth.tcpserver")
@EnableJpaRepositories("com.vanth.repository")
public class VehicleTrackingServerApplication {

	public static void main(String[] args) {
		TCPServer tcpServer = new TCPServer();
		SpringApplication.run(VehicleTrackingServerApplication.class, args);
		tcpServer.start();
	}

}
