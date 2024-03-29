package com.microservice.counth.CountH.controller;

import java.net.URI;
import java.util.HashMap;
import java.util.Map;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.support.WebExchangeBindException;


import com.microservice.counth.CountH.model.ClientPerson;
import com.microservice.counth.CountH.model.CountH;
import com.microservice.counth.CountH.services.CountHServices;

import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@RestController
@RequestMapping("/api/client")	
public class ClientPersonController {
	
	@Autowired
	private CountHServices service;
	
	@GetMapping
	public Mono<ResponseEntity<Flux<ClientPerson>>> listaClient(){
		return Mono.just(
				ResponseEntity.ok()
				.contentType(MediaType.APPLICATION_JSON_UTF8)
				.body(service.findAllClientPerson())
				);
	}
	
	@GetMapping("/{id}")
	public Mono<ResponseEntity<ClientPerson>> verClient(@PathVariable String id){
		return service.findClientPersonById(id).map(p -> ResponseEntity.ok()
				.contentType(MediaType.APPLICATION_JSON_UTF8)
				.body(p))
				.defaultIfEmpty(ResponseEntity.notFound().build());
	}
	
//	@GetMapping("/{id}")
//	public Mono<ClientPerson> verClient(@PathVariable String id){
//		return service.findClientPersonById(id)
//		.switchIfEmpty(Mono.just("").flatMap(r->{
//			ClientPerson cta2=new ClientPerson();
//			return Mono.just(cta2);
//		})
//		);
//	}
	
	@PostMapping
	public Mono<ResponseEntity<Map<String, Object>>> crearclientperson(@Valid @RequestBody Mono<ClientPerson> monoClient){
		
		Map<String, Object> respuesta = new HashMap<String, Object>();
		
		return monoClient.flatMap(clientperson ->{
			return service.saveClientPerson(clientperson).map(c ->{
				respuesta.put("client", c);
				respuesta.put("mensaje", "Cliente creado con exito");
				return ResponseEntity
						.created(URI.create("api/client/".concat(c.getId())))
						.contentType(MediaType.APPLICATION_JSON_UTF8)
						.body(respuesta);
			});
		}).onErrorResume(t -> {
			return Mono.just(t).cast(WebExchangeBindException.class)
					.flatMap(e -> Mono.just(e.getFieldErrors()))
					.flatMapMany(Flux::fromIterable)
					.map(fieldError -> "El campo "+fieldError.getField() + " " + fieldError.getDefaultMessage())
					.collectList()
					.flatMap(list -> {
						respuesta.put("errors", list);						
						respuesta.put("status", HttpStatus.BAD_REQUEST.value());
						return Mono.just(ResponseEntity.badRequest().body(respuesta));
					});
							
		});
		

	}

}
