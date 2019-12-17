package com.microservice.counth.CountH.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.support.WebExchangeBindException;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;

import java.net.URI;
import java.util.HashMap;
import java.util.Map;

import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;

import com.microservice.counth.CountH.model.CountH;
import com.microservice.counth.CountH.services.CountHServices;

import lombok.Value;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@RestController
@RequestMapping("/api/counth")	
public class CountHController {
	
	@Autowired
	private CountHServices service;
	
	
	//private String path;
	
	@GetMapping
	public Mono<ResponseEntity<Flux<CountH>>> lista(){
		return Mono.just(
				ResponseEntity.ok()
				.contentType(MediaType.APPLICATION_JSON_UTF8)
				.body(service.findAll())
				);
	}
	
	@GetMapping("/{id}")
	public Mono<ResponseEntity<CountH>> ver(@PathVariable String id){
		return service.findById(id).map(p -> ResponseEntity.ok()
				.contentType(MediaType.APPLICATION_JSON_UTF8)
				.body(p))
				.defaultIfEmpty(ResponseEntity.notFound().build());
	}
	
	@PostMapping
	public Mono<ResponseEntity<Map<String, Object>>> crear(@Valid @RequestBody Mono<CountH> monoCountH){
		
		Map<String, Object> respuesta = new HashMap<String, Object>();
		
		return monoCountH.flatMap(counth ->{
			return service.save(counth).map(c ->{
				respuesta.put("counth", c);
				respuesta.put("mensaje", "Cuenta de ahorro creado con exito");
				return ResponseEntity
						.created(URI.create("api/counth/".concat(c.getId())))
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
	
	@PutMapping("/{id}")
	public Mono<ResponseEntity<CountH>> editar(@RequestBody CountH counth, @PathVariable String id){
		return service.findById(id).flatMap(c -> {
			c.setNum(counth.getNum());
			c.setMonto(counth.getMonto());
			c.setClientperson(counth.getClientperson());
			return service.save(c);
		}).map(c->ResponseEntity.created(URI.create("/api/counth/".concat(c.getId())))
				.contentType(MediaType.APPLICATION_JSON_UTF8)
				.body(c))
		.defaultIfEmpty(ResponseEntity.notFound().build());
	}


}
