package com.examplerf.shared.service;

import java.util.List;

import com.examplerf.server.domain.ColaboradorListLoadResultBean;
import com.examplerf.server.locator.DaoServiceLocator;
import com.examplerf.server.service.ColaboradorDao;
import com.examplerf.shared.proxy.ColaboradorProxy;
import com.google.web.bindery.requestfactory.shared.ProxyFor;
import com.google.web.bindery.requestfactory.shared.Request;
import com.google.web.bindery.requestfactory.shared.RequestContext;
import com.google.web.bindery.requestfactory.shared.Service;
import com.google.web.bindery.requestfactory.shared.ValueProxy;
import com.sencha.gxt.data.shared.loader.ListLoadResult;

@Service(value = ColaboradorDao.class, locator = DaoServiceLocator.class)
public interface ColaboradorService extends RequestContext {
	Request<Void> save(ColaboradorProxy colaborador); 
	 Request<Void> remove(ColaboradorProxy colaborador);
	   
	 @ProxyFor(value = ColaboradorListLoadResultBean.class)
	 public interface ColaboradorListLoadResultProxy extends ValueProxy, ListLoadResult<ColaboradorProxy> {
	     @Override
	     public List<ColaboradorProxy> getData();
	 }
	  
	 Request<ColaboradorListLoadResultProxy> list();	
}
