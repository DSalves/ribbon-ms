package br.com.ifc.ribbon.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.cloud.context.config.annotation.RefreshScope;
import org.springframework.core.env.Environment;
import org.springframework.web.bind.annotation.GetMapping;
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
	public String parser(@RequestParam(name="msg", required=false) String msg) {

		int porta = Integer.parseInt(environment.getProperty("local.server.port"));
		return proxy.parser(msg) + " {Ribbon Porta:" + porta + ", Mensagem: " + message + "}";
	}

}
