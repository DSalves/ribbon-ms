package br.com.ifc.ribbon.client;

import org.springframework.cloud.netflix.ribbon.RibbonClient;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestParam;

import feign.Headers;

@FeignClient(name="pagarme-service")
@RibbonClient(name="pagarme-service")
public interface PagarMe {

	@GetMapping(value="/pagarme/servico")
	@Headers("Authorization: {authToken}")
	public String parser(@RequestHeader(value="Authorization") String authToken, @RequestParam(name="msg", required=false) String msg) ;
}