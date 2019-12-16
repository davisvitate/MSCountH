package com.microservice.counth.CountH.repository;

import org.springframework.data.mongodb.repository.ReactiveMongoRepository;

import com.microservice.counth.CountH.model.CountH;

public interface CountHRepository extends ReactiveMongoRepository<CountH, String> {

}
