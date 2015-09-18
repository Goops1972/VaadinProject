package com.example.myvaadin;

import com.vaadin.event.MouseEvents.ClickEvent;
import com.vaadin.ui.Alignment;
import com.vaadin.ui.Button;
import com.vaadin.ui.Button.ClickListener;
import com.vaadin.ui.HorizontalLayout;
import com.vaadin.ui.Label;
import com.vaadin.ui.Notification;
import com.vaadin.ui.VerticalLayout;
import com.vaadin.ui.Window;

public class MySub extends Window {
    public MySub() {
        super("Do you want to Save it ?"); // Set window caption
        center();

        // Some basic content for the window
        VerticalLayout content = new VerticalLayout();
//        content.addComponent(new Label("Click Save, if yes"));
        content.setWidth("300px");
        setContent(content);
        
        // Disable the close button
        setClosable(false);

        // Trivial logic for closing the sub-window
        Button cancel = new Button("Cancel");
        Button save = new Button("Save");
        save.addClickListener(new ClickListener() {

        	@Override
			public void buttonClick(com.vaadin.ui.Button.ClickEvent event) {
				// TODO Auto-generated method stub
//                close(); // Close the sub-window
	Notification.show("Saving into the database");			
			}
        });
        
        cancel.addClickListener(new ClickListener() {
			
			@Override
			public void buttonClick(com.vaadin.ui.Button.ClickEvent event) {
				close();
			}
		});
        
        HorizontalLayout hlforButton = new HorizontalLayout();
//        hlforButton.setSpacing(true);
        hlforButton.setSizeFull();
        hlforButton.addComponent(save);
        hlforButton.addComponent(cancel);
        hlforButton.setComponentAlignment(save, Alignment.BOTTOM_LEFT);
        hlforButton.setComponentAlignment(cancel, Alignment.BOTTOM_RIGHT);
        
        content.addComponent(hlforButton);
    }
}