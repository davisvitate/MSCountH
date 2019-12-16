package com.microservice.counth.CountH.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.beans.factory.annotation.Autowired;
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
	
	
	private String path;
	
	@GetMapping
	public Mono<ResponseEntity<Flux<CountH>>> lista(){
		return Mono.just(
				ResponseEntity.ok()
				.contentType(MediaType.APPLICATION_JSON_UTF8)
				.body(service.findAll())
				);
	}


}
