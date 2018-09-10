package br.com.ifc.ribbon.client;

import org.springframework.cloud.netflix.ribbon.RibbonClient;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

@FeignClient(name="pagarme-service")
@RibbonClient(name="pagarme-service")
public interface PagarMe {

	@GetMapping(value="/pagarme/servico")
	public String parser(@RequestParam("msg") String msg) ;
}