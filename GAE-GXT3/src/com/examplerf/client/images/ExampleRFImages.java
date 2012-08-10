package com.examplerf.client.images;
 
import com.google.gwt.core.client.GWT;
import com.google.gwt.resources.client.ClientBundle;
import com.google.gwt.resources.client.ImageResource;
 
public interface ExampleRFImages extends ClientBundle {
 
 public ExampleRFImages IMAGES = GWT.create(ExampleRFImages.class);
  
 @Source("add.gif")
 ImageResource add();
  
 @Source("delete.gif")
 ImageResource delete();
  
 @Source("update.png")
 ImageResource update();
  
 @Source("save.png")
 ImageResource save();
  
 @Source("cancel.png")
 ImageResource cancel();
}