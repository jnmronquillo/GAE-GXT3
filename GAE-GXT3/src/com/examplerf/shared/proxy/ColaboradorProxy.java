package com.examplerf.shared.proxy;

import com.examplerf.server.domain.Colaborador;
import com.examplerf.server.locator.ObjectifyLocator;
import com.google.web.bindery.requestfactory.shared.EntityProxy;
import com.google.web.bindery.requestfactory.shared.ProxyFor;

@ProxyFor(value = Colaborador.class, locator = ObjectifyLocator.class)
public interface ColaboradorProxy extends EntityProxy {
	String getNombres();
	void setNombres(String nombres);
	String getApellidos();
	void setApellidos(String apellidos);
	Integer getEdad();
	void setEdad(Integer edad);
	Long getId();
	void setId(Long id);
}
