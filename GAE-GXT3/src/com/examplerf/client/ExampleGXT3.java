package com.examplerf.client;
 
import java.util.ArrayList;
import java.util.List;
 
import com.examplerf.client.events.SaveEvent;
import com.examplerf.client.events.SaveEventHandler;
import com.examplerf.shared.proxy.ColaboradorProxy;
import com.examplerf.shared.service.ColaboradorService;
import com.examplerf.shared.service.ExampleRFRequestFactory;
import com.google.gwt.core.client.EntryPoint;
import com.google.gwt.core.client.GWT;
import com.google.gwt.core.client.Scheduler;
import com.google.gwt.core.client.Scheduler.ScheduledCommand;
import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.uibinder.client.UiField;
import com.google.gwt.uibinder.client.UiHandler;
import com.google.gwt.user.client.ui.IsWidget;
import com.google.gwt.user.client.ui.RootPanel;
import com.google.gwt.user.client.ui.Widget;
import com.google.web.bindery.event.shared.SimpleEventBus;
import com.google.web.bindery.requestfactory.gwt.client.RequestFactoryEditorDriver;
import com.google.web.bindery.requestfactory.shared.Receiver;
import com.google.web.bindery.requestfactory.shared.RequestContext;
import com.sencha.gxt.core.client.Style.SelectionMode;
import com.sencha.gxt.core.client.ValueProvider;
import com.sencha.gxt.data.shared.ListStore;
import com.sencha.gxt.data.shared.ModelKeyProvider;
import com.sencha.gxt.data.shared.PropertyAccess;
import com.sencha.gxt.data.shared.loader.ListLoadConfig;
import com.sencha.gxt.data.shared.loader.ListLoadResult;
import com.sencha.gxt.data.shared.loader.ListLoader;
import com.sencha.gxt.data.shared.loader.LoadResultListStoreBinding;
import com.sencha.gxt.data.shared.loader.RequestFactoryProxy;
import com.sencha.gxt.widget.core.client.Dialog;
import com.sencha.gxt.widget.core.client.box.ConfirmMessageBox;
import com.sencha.gxt.widget.core.client.button.TextButton;
import com.sencha.gxt.widget.core.client.event.HideEvent;
import com.sencha.gxt.widget.core.client.event.HideEvent.HideHandler;
import com.sencha.gxt.widget.core.client.event.SelectEvent;
import com.sencha.gxt.widget.core.client.grid.ColumnConfig;
import com.sencha.gxt.widget.core.client.grid.ColumnModel;
import com.sencha.gxt.widget.core.client.grid.Grid;
import com.sencha.gxt.widget.core.client.info.Info;
import com.sencha.gxt.widget.core.client.selection.SelectionChangedEvent;
import com.sencha.gxt.widget.core.client.selection.SelectionChangedEvent.SelectionChangedHandler;
 
public class ExampleGXT3 implements EntryPoint, IsWidget {
 interface ColaboradorProxyProperties extends PropertyAccess<ColaboradorProxy> {
     ModelKeyProvider<ColaboradorProxy> id();
     ValueProvider<ColaboradorProxy, String> nombres();
     ValueProvider<ColaboradorProxy, String> apellidos();
     ValueProvider<ColaboradorProxy, Integer> edad();
 }
  
 interface MyUiBinder extends UiBinder<Widget, ExampleGXT3> {
 }
  
 private static MyUiBinder uiBinder = GWT.create(MyUiBinder.class);
  
 private final ExampleRFRequestFactory myRF = GWT.create(ExampleRFRequestFactory.class);
   
 interface Driver extends RequestFactoryEditorDriver<ColaboradorProxy, ColaboradorEditor>{  
 }
 Driver driver = GWT.create(Driver.class);
  
 private ColaboradorEditor editor;
  
 private ColaboradorService cs;
 private ColaboradorProxy colaborador;
 private ListStore<ColaboradorProxy> store;
 private ListLoader<ListLoadConfig, ListLoadResult<ColaboradorProxy>> loader; 
  
 @UiField(provided = true)
 Grid<ColaboradorProxy> grid;
  
 @UiField
 TextButton edit;
  
 @UiField
 TextButton delete;
  
 public void onModuleLoad() {
  myRF.initialize(new SimpleEventBus());
  editor = new ColaboradorEditor();  
  driver.initialize(myRF, editor);
  editor.addSaveEventHandler(new SaveEventHandler() {
    
   @Override
   public void onSave(SaveEvent saveEvent) {
     
    RequestContext context = driver.flush();
    if(!driver.hasErrors()&&editor.isValid()){
     editor.hide();
     context.fire(new Receiver<Void>() {
       
      @Override
      public void onSuccess(Void response) {
       Info.display("ExampleRF", "Se guardo correctamente");      
       loader.load();
      }
     });
    }
       
   }
  });  
   
  RootPanel.get().add(this);  
   
 } 
 
 @Override
 public Widget asWidget() {
  RequestFactoryProxy<ListLoadConfig, ListLoadResult<ColaboradorProxy>> proxy = new RequestFactoryProxy<ListLoadConfig, ListLoadResult<ColaboradorProxy>>() {
    
   @Override
   public void load(ListLoadConfig loadConfig,
     Receiver<? super ListLoadResult<ColaboradorProxy>> receiver) {
    ColaboradorService cs = myRF.colaboradorService();
    cs.list().to(receiver);
    cs.fire();    
   }
  };
   
  loader = new ListLoader<ListLoadConfig, ListLoadResult<ColaboradorProxy>>(proxy);
  ColaboradorProxyProperties props = GWT.create(ColaboradorProxyProperties.class);
   
  store = new ListStore<ColaboradorProxy>(props.id());
  loader.addLoadHandler(new LoadResultListStoreBinding<ListLoadConfig, ColaboradorProxy, ListLoadResult<ColaboradorProxy>>(store));
     
      
     ColumnConfig<ColaboradorProxy, String> nombresColumn = new ColumnConfig<ColaboradorProxy, String>(props.nombres(), 150, "Nombres");
     ColumnConfig<ColaboradorProxy, String> apellidosColumn = new ColumnConfig<ColaboradorProxy, String>(props.apellidos(), 150, "Apellidos");
     ColumnConfig<ColaboradorProxy, Integer> edadColumn = new ColumnConfig<ColaboradorProxy, Integer>(props.edad(), 80, "Edad");
      
     List<ColumnConfig<ColaboradorProxy, ?>> l = new ArrayList<ColumnConfig<ColaboradorProxy, ?>>();
     l.add(nombresColumn);
     l.add(apellidosColumn);
     l.add(edadColumn);
      
     ColumnModel<ColaboradorProxy> cm = new ColumnModel<ColaboradorProxy>(l);
      
     grid = new Grid<ColaboradorProxy>(store, cm) {
         @Override
         protected void onAfterFirstAttach() {
           super.onAfterFirstAttach();
           Scheduler.get().scheduleDeferred(new ScheduledCommand() {
             @Override
             public void execute() {
               loader.load();
             }
           });
         }
     };
      
     grid.setLoader(loader);
     grid.getSelectionModel().setSelectionMode(SelectionMode.SINGLE);     
      
     grid.getSelectionModel().addSelectionChangedHandler(new SelectionChangedHandler<ColaboradorProxy>() {
 
   @Override
   public void onSelectionChanged(
     SelectionChangedEvent<ColaboradorProxy> event) {
    int size = event.getSelection().size();
    if(size == 0){     
      edit.setEnabled(false);
      delete.setEnabled(false);
    }else if(size == 1){      
      edit.setEnabled(true);
      delete.setEnabled(true);
    }else if(size > 1){
      edit.setEnabled(false);
      delete.setEnabled(true);
    }       
     
   }
  });
 
     return uiBinder.createAndBindUi(this);
 }
  
 @UiHandler("add")
 public void onAdd(SelectEvent event){
   
  cs = myRF.colaboradorService();
  colaborador = cs.create(ColaboradorProxy.class);
  cs.save(colaborador);
  driver.edit(colaborador, cs);
  editor.clearFields();
  editor.show("Nuevo Colaborador");
 }
  
 @UiHandler("edit")
 public void onEdit(SelectEvent event){
  cs = myRF.colaboradorService(); 
   
  colaborador = grid.getSelectionModel().getSelectedItem();
  cs.save(colaborador);
  driver.edit(colaborador, cs);
  editor.clearFields();
  editor.show("Editar Colaborador");
 }
  
 @UiHandler("delete")
 public void onDelete(SelectEvent event){
  ConfirmMessageBox box = new ConfirmMessageBox("ExampleRF", "Esta seguro que desea eliminar?");
  box.addHideHandler(new HideHandler() {
    
   @Override
   public void onHide(HideEvent event) {
    Dialog btn = (Dialog) event.getSource();
    if("Yes".equals(btn.getHideButton().getText())){
     cs = myRF.colaboradorService(); 
     colaborador = grid.getSelectionModel().getSelectedItem();
     cs.remove(colaborador).fire(new Receiver<Void>() {
       
      @Override
      public void onSuccess(Void response) {
       Info.display("ExampleRF", "Se elimino correctamente");
       loader.load();
      }
     });
    }
   }
  });
  box.show();  
 } 
  
}