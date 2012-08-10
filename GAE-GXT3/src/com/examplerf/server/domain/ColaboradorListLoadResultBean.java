package com.examplerf.server.domain;

import java.util.List;

import com.sencha.gxt.data.shared.loader.ListLoadResultBean;
 
@SuppressWarnings("serial")
public class ColaboradorListLoadResultBean extends ListLoadResultBean<Colaborador> {
    public ColaboradorListLoadResultBean(List<Colaborador> list) {
      super(list);
    }
    public ColaboradorListLoadResultBean() {
        super();
    }
}