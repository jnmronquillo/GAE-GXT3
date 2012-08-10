package com.examplerf.client;
 
import com.examplerf.client.events.SaveEvent;
import com.examplerf.client.events.SaveEventHandler;
import com.examplerf.shared.proxy.ColaboradorProxy;
import com.google.gwt.core.client.GWT;
import com.google.gwt.editor.client.Editor;
import com.google.gwt.event.shared.GwtEvent;
import com.google.gwt.event.shared.HandlerManager;
import com.google.gwt.event.shared.HandlerRegistration;
import com.google.gwt.event.shared.HasHandlers;
import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.uibinder.client.UiField;
import com.google.gwt.uibinder.client.UiHandler;
import com.google.gwt.user.client.ui.Widget;
import com.sencha.gxt.widget.core.client.FramedPanel;
import com.sencha.gxt.widget.core.client.Window;
import com.sencha.gxt.widget.core.client.event.SelectEvent;
import com.sencha.gxt.widget.core.client.form.NumberField;
import com.sencha.gxt.widget.core.client.form.NumberPropertyEditor;
import com.sencha.gxt.widget.core.client.form.TextField;
 
public class ColaboradorEditor implements Editor<ColaboradorProxy>, HasHandlers {
 
 private HandlerManager handlerManager;
  
 private static ColaboradorUiBinder uiBinder = GWT.create(ColaboradorUiBinder.class);
 
 interface ColaboradorUiBinder extends UiBinder<Widget, ColaboradorEditor> {
 }
 @UiField
 FramedPanel form;
  
 @UiField
 TextField nombres;
  
 @UiField
 TextField apellidos;
  
 @UiField(provided = true)
 NumberField<Integer> edad;
   
 Window panel;
  
 public ColaboradorEditor() {
  handlerManager = new HandlerManager(this);
  
  edad = new NumberField<Integer>(new NumberPropertyEditor.IntegerPropertyEditor());
   
  panel = (Window) uiBinder.createAndBindUi(this);
 }
   
 public void show(String title){
  panel.setHeadingText(title);
  panel.show();
 }  
  
 @UiHandler("save")
 public void onSave(SelectEvent event){  
  fireEvent(new SaveEvent());
 }
  
 @UiHandler("cancel")
 public void onCancel(SelectEvent event){
  hide();
 }
 
 @Override
 public void fireEvent(GwtEvent<?> event) {
  handlerManager.fireEvent(event);  
 }
  
 public HandlerRegistration addSaveEventHandler(
            SaveEventHandler handler) {
        return handlerManager.addHandler(SaveEvent.TYPE, handler);
    }
 
 public void clearFields() {
  nombres.clearInvalid();
  apellidos.clearInvalid();  
 }
  
 public void hide() {
  panel.hide();
 }
 
}