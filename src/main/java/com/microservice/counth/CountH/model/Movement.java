package com.microservice.counth.CountH.model;

import java.util.Date;

import javax.validation.constraints.NotNull;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.format.annotation.DateTimeFormat;

@Document(collection = "movement")
public class Movement {

	@Id
	private String id;

	@NotNull
	private double saldo;

	@DateTimeFormat(pattern = "yyyy-MM-dd")
	private Date date;

	@NotNull
	private String description;

	@NotNull
	private ClientPerson client;

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public double getSaldo() {
		return saldo;
	}

	public void setSaldo(double saldo) {
		this.saldo = saldo;
	}

	public Date getDate() {
		return date;
	}

	public void setDate(Date date) {
		this.date = date;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public ClientPerson getClient() {
		return client;
	}

	public void setClient(ClientPerson client) {
		this.client = client;
	}

}
