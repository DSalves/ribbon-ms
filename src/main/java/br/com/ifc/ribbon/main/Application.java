package br.com.ifc.ribbon.main;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;
import org.springframework.cloud.openfeign.EnableFeignClients;


@EnableFeignClients("br.com.ifc.ribbon.client")
@EnableDiscoveryClient
@EnableEurekaClient
@SpringBootApplication(scanBasePackages={"br.com.ifc.ribbon.config", "br.com.ifc.ribbon.controller", "br.com.ifc.ribbon.filter"})
public class Application {

	public static void main(String[] args) {
		SpringApplication.run(Application.class, args);
	}
}
