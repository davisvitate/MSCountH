package com.microservice.counth.CountH.controller;

import org.springframework.web.bind.annotation.DeleteMapping;
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
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;

import com.microservice.counth.CountH.model.ClientPerson;
import com.microservice.counth.CountH.model.CountH;
import com.microservice.counth.CountH.model.Firmante;
import com.microservice.counth.CountH.model.Movement;
import com.microservice.counth.CountH.model.Titular;
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
	
//	@GetMapping("/{id}")
//	public Mono<ResponseEntity<CountH>> ver(@PathVariable String id){
//		return service.findById(id).map(p -> ResponseEntity.ok()
//				.contentType(MediaType.APPLICATION_JSON_UTF8)
//				.body(p))
//				.defaultIfEmpty(ResponseEntity.notFound().build());
//	}
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
	
	//delete the saving count
	@DeleteMapping("/{id}")
	public Mono<ResponseEntity<Void>> eliminar(@PathVariable String id){
		return service.findById(id).flatMap(p ->{
			return service.delete(p).then(Mono.just(new ResponseEntity<Void>(HttpStatus.NO_CONTENT)));
		}).defaultIfEmpty(new ResponseEntity<Void>(HttpStatus.NOT_FOUND));
	}
	
	
//	@PostMapping("/client")
//	public Mono<Map<String, Object>> crearclientperson(@Valid @RequestBody Mono<ClientPerson> clientperson){
//		
//		Map<String, Object> respuesta = new HashMap<String, Object>();
//		
//		return clientperson.flatMap(clientp ->{
//			return service.saveClientPerson(clientp).map(c ->{
//				respuesta.put("clientperson", c);
//				respuesta.put("mensaje", "Clientperson creado con exito");
//				return respuesta;
//			});
//		});
//	}
	
	
	
	

	

	
	
//	@PostMapping
//	public Mono<ResponseEntity<Map<String, Object>>> crearTitular(@Valid @RequestBody Mono<Titular> titular){
//		
//		Map<String, Object> respuesta = new HashMap<String, Object>();
//		
//		return titular.flatMap(titularp ->{
//			return service.saveTitular(titularp).map(c ->{
//				respuesta.put("titular", c);
//				respuesta.put("mensaje", "Titular creado con exito");
//				return ResponseEntity
//						.created(URI.create("api/titular/".concat(c.getId())))
//						.contentType(MediaType.APPLICATION_JSON_UTF8)
//						.body(respuesta);
//			});
//		}).onErrorResume(t -> {
//			return Mono.just(t).cast(WebExchangeBindException.class)
//					.flatMap(e -> Mono.just(e.getFieldErrors()))
//					.flatMapMany(Flux::fromIterable)
//					.map(fieldError -> "El campo "+fieldError.getField() + " " + fieldError.getDefaultMessage())
//					.collectList()
//					.flatMap(list -> {
//						respuesta.put("errors", list);						
//						respuesta.put("status", HttpStatus.BAD_REQUEST.value());
//						return Mono.just(ResponseEntity.badRequest().body(respuesta));
//					});
//							
//		});
//		
//
//	}
	
	
//	@PostMapping
//	public Mono<ResponseEntity<Map<String, Object>>> crearFirmante(@Valid @RequestBody Mono<Firmante> firmante){
//		
//		Map<String, Object> respuesta = new HashMap<String, Object>();
//		
//		return firmante.flatMap(firmantep ->{
//			return service.saveFirmante(firmantep).map(c ->{
//				respuesta.put("titular", c);
//				respuesta.put("mensaje", "Firmante creado con exito");
//				return ResponseEntity
//						.created(URI.create("api/firmante/".concat(c.getId())))
//						.contentType(MediaType.APPLICATION_JSON_UTF8)
//						.body(respuesta);
//			});
//		}).onErrorResume(t -> {
//			return Mono.just(t).cast(WebExchangeBindException.class)
//					.flatMap(e -> Mono.just(e.getFieldErrors()))
//					.flatMapMany(Flux::fromIterable)
//					.map(fieldError -> "El campo "+fieldError.getField() + " " + fieldError.getDefaultMessage())
//					.collectList()
//					.flatMap(list -> {
//						respuesta.put("errors", list);						
//						respuesta.put("status", HttpStatus.BAD_REQUEST.value());
//						return Mono.just(ResponseEntity.badRequest().body(respuesta));
//					});
//							
//		});
//		
//
//	}
	
	@GetMapping("/listatitular")
	public Mono<ResponseEntity<Flux<Titular>>> listaTitular(){
		return Mono.just(
				ResponseEntity.ok()
				.contentType(MediaType.APPLICATION_JSON_UTF8)
				.body(service.findAllTitular())
				);
	}
	
	@GetMapping("/listafirmante")
	public Mono<ResponseEntity<Flux<Firmante>>> listaFirmante(){
		return Mono.just(
				ResponseEntity.ok()
				.contentType(MediaType.APPLICATION_JSON_UTF8)
				.body(service.findAllFirmante())
				);
	}
	
	@PutMapping("/retire/{id}")
	public Mono<ResponseEntity<CountH>> upadateretire(@RequestBody CountH counth, @PathVariable String id){
		
		Movement mov= new Movement();
		
		return service.findById(id).flatMap(c -> {
			if(c.getMonto()>counth.getMonto()) {
			c.setMonto(c.getMonto()-counth.getMonto());
			mov.setDescription("Retire");
			mov.setSaldo(counth.getMonto());
			mov.setDate(new Date());
			mov.setClient(counth.getClientperson());
			service.saveMove(mov);// registre of the movement
			}
			return service.save(c);
		}).map(c->ResponseEntity.created(URI.create("/api/counth/retire/".concat(c.getId())))
				.contentType(MediaType.APPLICATION_JSON_UTF8)
				.body(c))
		.defaultIfEmpty(ResponseEntity.notFound().build());
	}
	
	@PutMapping("/deposite/{id}")
	public Mono<ResponseEntity<CountH>> upadeposit(@RequestBody CountH counth, @PathVariable String id){
		Movement mov= new Movement();
		return service.findById(id).flatMap(c -> {
			
			c.setMonto(c.getMonto()+ counth.getMonto());
			c.setClientperson(counth.getClientperson());
			mov.setDescription("Deposite");
			mov.setSaldo(counth.getMonto());
			mov.setDate(new Date());
			mov.setClient(counth.getClientperson());
			return service.save(c);
		}).map(c->ResponseEntity.created(URI.create("/api/counth/deposite/".concat(c.getId())))
				.contentType(MediaType.APPLICATION_JSON_UTF8)
				.body(c))
		.defaultIfEmpty(ResponseEntity.notFound().build());
	}


}
