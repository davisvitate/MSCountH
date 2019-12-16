package com.microservice.counth.CountH.model;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import javax.validation.Valid;
import javax.validation.constraints.NotNull;



@Document(collection="counth")
public class CountH {

		@Id
		private String id;
		
		
		private String num;
		
	
		private Double monto;
		
		@Valid
		@NotNull
		private ClientPerson clientperson;
		

		public CountH() {}
		
		public CountH(String num, Double monto) {
			this.num = num;
			this.monto = monto;
		}


		public CountH(String num, Double monto, ClientPerson clientperson) {
			this(num, monto);
			this.clientperson = clientperson;
		}


		public String getId() {
			return id;
		}


		public void setId(String id) {
			this.id = id;
		}


		public String getNum() {
			return num;
		}


		public void setNum(String num) {
			this.num = num;
		}


		public Double getMonto() {
			return monto;
		}


		public void setMonto(Double monto) {
			this.monto = monto;
		}
		
		public ClientPerson getClientperson() {
			return clientperson;
		}


		public void setClientperson(ClientPerson clientperson) {
			this.clientperson = clientperson;
		}
		
}
