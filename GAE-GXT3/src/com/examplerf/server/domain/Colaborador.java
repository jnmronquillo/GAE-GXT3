package com.examplerf.server.domain;

import com.googlecode.objectify.annotation.Entity;

@Entity
public class Colaborador extends DatastoreObject {
	private String nombres;
	private String apellidos;
	private Integer edad;
	public String getNombres() {
		return nombres;
	}
	public void setNombres(String nombres) {
		this.nombres = nombres;
	}
	public String getApellidos() {
		return apellidos;
	}
	public void setApellidos(String apellidos) {
		this.apellidos = apellidos;
	}
	public Integer getEdad() {
		return edad;
	}
	public void setEdad(Integer edad) {
		this.edad = edad;
	}	
}