package com.microservice.counth.CountH.repository;

import org.springframework.data.mongodb.repository.ReactiveMongoRepository;

import com.microservice.counth.CountH.model.ClientPerson;

public interface ClientPersonRepository  extends ReactiveMongoRepository<ClientPerson, String>{

}
