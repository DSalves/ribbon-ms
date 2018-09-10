package br.com.ifc.ribbon.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import br.com.ifc.ribbon.client.PagarMe;

@RestController
@RequestMapping("/ribbon")
public class RibbonController {

	@Autowired
	private PagarMe proxy;
	
	@Value("${message:Default}")
    private String message;

	@GetMapping("/parser")
	public String parser(@RequestParam(name="msg", required=false) String msg) {

		return proxy.parser(msg) + " Ribbon:" + message;
	}

}
