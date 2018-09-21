package br.com.ifc.ribbon.controller;

import java.net.InetAddress;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.cloud.context.config.annotation.RefreshScope;
import org.springframework.core.env.Environment;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import br.com.ifc.ribbon.client.PagarMe;

@RestController
@RefreshScope
@RequestMapping("/ribbon")
public class RibbonController {

	@Autowired
	private PagarMe proxy;
	
	@Autowired
	private Environment environment;
	
	@Value("${message:Default}")
    private String message;

	@GetMapping("/parser")
	@PreAuthorize("hasRole('USER')")
	public String parser(@RequestParam(name="msg", required=false) String msg, @RequestHeader(value="Authorization") String authToken) {

		int porta = Integer.parseInt(environment.getProperty("local.server.port"));
		String ip = environment.getProperty("local.server.ip");
		
		if(ip == null) {
			try {
				ip = InetAddress.getLocalHost().getHostName();			
			} catch (Exception e) {
				// TODO: handle exception
			}
		}	
		
		return proxy.parser(authToken, msg) + " {Ribbon Ip:" + ip + ", Porta:" + porta + ", Mensagem: " + message + "}";
	}

}
