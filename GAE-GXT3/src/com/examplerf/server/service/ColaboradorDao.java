package com.examplerf.server.service;

import java.util.List;

import com.examplerf.server.domain.Colaborador;
import com.examplerf.server.domain.ColaboradorListLoadResultBean;

public class ColaboradorDao extends ObjectifyDao<Colaborador> {
	
	public void save(Colaborador colaborador){
		this.put(colaborador);
	}
	public void remove(Colaborador colaborador){
		this.delete(colaborador);		
	}
	public ColaboradorListLoadResultBean list() {
		 List<Colaborador> list = listAll();  
		 return new ColaboradorListLoadResultBean(list);
	}
}