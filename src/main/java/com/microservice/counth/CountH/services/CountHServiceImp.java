package com.microservice.counth.CountH.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.microservice.counth.CountH.model.ClientPerson;
import com.microservice.counth.CountH.model.CountH;
import com.microservice.counth.CountH.repository.ClientPersonRepository;
import com.microservice.counth.CountH.repository.CountHRepository;

import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@Service
public class CountHServiceImp implements CountHServices {
	@Autowired
	private CountHRepository countrepositry;
	
	@Autowired
	private ClientPersonRepository clientrerepository;

	@Override
	public Flux<CountH> findAll() {
		
		return countrepositry.findAll();
	}

	@Override
	public Mono<CountH> findById(String id) {
		
		return countrepositry.findById(id);
	}

	@Override
	public Mono<CountH> save(CountH counth) {
		
		return countrepositry.save(counth);
	}

	@Override
	public Mono<Void> delete(CountH counth) {
		
		return countrepositry.delete(counth);
	}


	@Override
	public Flux<ClientPerson> findAllClientPerson() {
		
		return clientrerepository.findAll();
	}

	@Override
	public Mono<ClientPerson> findClientPersonById(String id) {
	
		return clientrerepository.findById(id);
	}

	@Override
	public Mono<ClientPerson> saveClientPerson(ClientPerson clientperson) {
		
		return clientrerepository.save(clientperson);
	}

}
