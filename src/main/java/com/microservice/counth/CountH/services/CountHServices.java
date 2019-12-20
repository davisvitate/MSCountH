package com.microservice.counth.CountH.services;


import com.microservice.counth.CountH.model.ClientPerson;
import com.microservice.counth.CountH.model.CountH;
import com.microservice.counth.CountH.model.Firmante;
import com.microservice.counth.CountH.model.Movement;
import com.microservice.counth.CountH.model.Titular;

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
	
	public Flux<Titular> findAllTitular(); 
	
	public Mono<Titular> findByIdTitular(String id);
	
	public Mono<Titular> saveTitular(Titular titular);
	
	public Mono<Void> deleteTitular(Titular titular);
	
public Flux<Firmante> findAllFirmante(); 
	
	public Mono<Firmante> findByIdFirmante(String id);
	
	public Mono<Firmante> saveFirmante(Firmante firmante);
	
	public Mono<Void> deleteFirmante(Firmante firmante);
	
public Flux<Movement> findAllMove(); 
	
	public Mono<Movement> findByIdMove(String id);
	
	public Mono<Movement> saveMove(Movement move);
	
	public Mono<Void> deleteMove(Movement move);


	

}
