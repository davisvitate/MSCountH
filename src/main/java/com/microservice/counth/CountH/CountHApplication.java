package com.microservice.counth.CountH;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.mongodb.core.ReactiveMongoTemplate;

import com.microservice.counth.CountH.model.ClientPerson;
import com.microservice.counth.CountH.model.CountH;
import com.microservice.counth.CountH.services.CountHServices;

import reactor.core.publisher.Flux;

@SpringBootApplication
public class CountHApplication implements CommandLineRunner{
	
	@Autowired
	private CountHServices service;
	
	@Autowired
	private ReactiveMongoTemplate mongoTemplate;
	
	private static final Logger log = LoggerFactory.getLogger(CountHApplication.class);
	
	

	public static void main(String[] args) {
		SpringApplication.run(CountHApplication.class, args);
	}
	
	@Override
	public void run(String... args) throws Exception {
		mongoTemplate.dropCollection("counth").subscribe();
		mongoTemplate.dropCollection("clientperson").subscribe();
		
		ClientPerson antonio = new ClientPerson("Antonio");
		ClientPerson andrea = new ClientPerson("Andrea");
		ClientPerson federico = new ClientPerson("Federico");
		ClientPerson luis = new ClientPerson("Luis");
		
		Flux.just(antonio, andrea, federico, luis)
		.flatMap(service::saveClientPerson)
		.doOnNext(c ->{
			log.info("ClientesPerson creada: " + c.getName() + ", Id: " + c.getId());
		}).thenMany(
				Flux.just(new CountH("43435", 434.2, antonio),
						new CountH("54634", 177.89, andrea),
						new CountH("3656", 534.4, federico),
						new CountH("1453", 656.7, luis)
				)
					
		).flatMap(counth ->{return service.save(counth);})
		.subscribe(counth -> log.info("Insert: " + counth.getId() + " " + counth.getNum()));
		
	}

}
