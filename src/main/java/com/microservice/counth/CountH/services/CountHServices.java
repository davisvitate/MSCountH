package com.microservice.counth.CountH.services;


import com.microservice.counth.CountH.model.ClientPerson;
import com.microservice.counth.CountH.model.CountH;

import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

public interface CountHServices {

	public Flux<CountH> findAll(); 
	
	public Mono<CountH> findById(String id);
	
	public Mono<CountH> save(CountH counth);
	
	public Mono<Void> delete(CountH counth);
	
	public Flux<ClientPerson> findAllClientPerson();
	
	public Mono<ClientPerson> findClientPersonById(String id);
	
	public Mono<ClientPerson> saveClientPerson(ClientPerson clientperson);

	

}
