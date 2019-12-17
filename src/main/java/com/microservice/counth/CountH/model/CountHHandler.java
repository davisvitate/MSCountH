package com.microservice.counth.CountH.model;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Component;
import org.springframework.validation.BeanPropertyBindingResult;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;

import static org.springframework.web.reactive.function.BodyInserters.*;

import java.net.URI;

import org.springframework.web.reactive.function.server.ServerRequest;
import org.springframework.web.reactive.function.server.ServerResponse;

import com.microservice.counth.CountH.services.CountHServices;

import reactor.core.publisher.Mono;
import reactor.core.publisher.Flux;


@Component
public class CountHHandler {
	
	@Autowired
	private CountHServices service;
	
	//@Value("${config.uploads.path}")
	private String path;
	
	@Autowired
	private Validator validator;
	
	public Mono<ServerResponse> listar(ServerRequest request){
		return ServerResponse.ok()
				.contentType(MediaType.APPLICATION_JSON_UTF8)
				.body(service.findAll(), CountH.class);
	}
	
public Mono<ServerResponse> ver(ServerRequest request){
		
		String id = request.pathVariable("id");
		return service.findById(id).flatMap( c -> ServerResponse
				.ok()
				.contentType(MediaType.APPLICATION_JSON_UTF8)
				.body(fromObject(c)))
				.switchIfEmpty(ServerResponse.notFound().build());
	}

public Mono<ServerResponse> crear(ServerRequest request){
	Mono<CountH> counth = request.bodyToMono(CountH.class);
	
	return counth.flatMap(c -> {
		
		Errors errors = new BeanPropertyBindingResult(c, CountH.class.getName());
		validator.validate(c, errors);
		if(errors.hasErrors()) {
			return Flux.fromIterable(errors.getFieldErrors())
					.map(fieldError -> "El campo " + fieldError.getField() + " " + fieldError.getDefaultMessage())
					.collectList()
					.flatMap(list -> ServerResponse.badRequest().body(fromObject(list)));
		} else {
			return service.save(c).flatMap(cdb -> ServerResponse
					.created(URI.create("api/v2/counth/".concat(cdb.getId())))
					.contentType(MediaType.APPLICATION_JSON_UTF8)
					.body(fromObject(cdb)));
		}		
	});
}

}
