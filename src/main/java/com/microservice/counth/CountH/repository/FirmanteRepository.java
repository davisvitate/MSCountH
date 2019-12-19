package com.microservice.counth.CountH.repository;

import org.springframework.data.mongodb.repository.ReactiveMongoRepository;
import com.microservice.counth.CountH.model.Firmante;

public interface FirmanteRepository extends ReactiveMongoRepository<Firmante, String>{

}
