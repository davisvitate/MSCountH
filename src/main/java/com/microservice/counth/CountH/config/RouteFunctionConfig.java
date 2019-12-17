package com.microservice.counth.CountH.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.reactive.function.server.RouterFunction;
import org.springframework.web.reactive.function.server.ServerResponse;

import com.microservice.counth.CountH.model.CountHHandler;

import static org.springframework.web.reactive.function.server.RouterFunctions.route;
import static org.springframework.web.reactive.function.server.RequestPredicates.*;




@Configuration
public class RouteFunctionConfig {
	
	@Bean
	public RouterFunction<ServerResponse> routes(CountHHandler handler){
		
		return route(GET("/api/v2/counth").or(GET("/api/v3/counth")), handler::listar)
				.andRoute(GET("/api/v2/counth/{id}"), handler::ver)
				.andRoute(POST("/api/v2/counth"), handler::crear);
				//.andRoute(PUT("/api/v2/counth/{id}"), handler::editar);
				//.andRoute(DELETE("/api/v2/productos/{id}"), handler::eliminar)
				//.andRoute(POST("/api/v2/productos/upload/{id}"), handler::upload)
				//.andRoute(POST("/api/v2/productos/crear"), handler::crearConFoto);
	}

}
