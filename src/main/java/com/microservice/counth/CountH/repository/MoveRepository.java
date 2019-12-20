package com.microservice.counth.CountH.repository;

import org.springframework.data.mongodb.repository.ReactiveMongoRepository;

import com.microservice.counth.CountH.model.Movement;

public interface MoveRepository extends ReactiveMongoRepository<Movement, String> {
	
	

}
