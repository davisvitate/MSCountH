package com.microservice.counth.CountH.repository;

import org.springframework.data.mongodb.repository.ReactiveMongoRepository;

import com.microservice.counth.CountH.model.Titular;

public interface TitularRepository extends ReactiveMongoRepository<Titular, String>{

}
