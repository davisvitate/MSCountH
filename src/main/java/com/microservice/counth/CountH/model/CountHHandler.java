package com.microservice.counth.CountH.model;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.MediaType;
import org.springframework.validation.BeanPropertyBindingResult;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;

import static org.springframework.web.reactive.function.BodyInserters.*;

import org.springframework.web.reactive.function.server.ServerRequest;
import org.springframework.web.reactive.function.server.ServerResponse;

import com.microservice.counth.CountH.services.CountHServices;

import reactor.core.publisher.Mono;
import reactor.core.publisher.Flux;



public class CountHHandler {
	
	@Autowired
	private CountHServices service;
	
	@Value("${config.uploads.path}")
	private String path;
	
	@Autowired
	//private Validator validator;
	
	public Mono<ServerResponse> listar(ServerRequest request){
		return ServerResponse.ok()
				.contentType(MediaType.APPLICATION_JSON_UTF8)
				.body(service.findAll(), CountH.class);
	}



}
