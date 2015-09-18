package com.example.reports;

import java.util.List;

import com.example.database.Query;
import com.example.pojo.RoleSegregration;
import com.vaadin.data.Property.ValueChangeEvent;
import com.vaadin.data.Property.ValueChangeListener;
import com.vaadin.ui.ComboBox;
import com.vaadin.ui.FormLayout;
import com.vaadin.ui.Table;

public class ReportTable {

	Query query = new Query();

	public FormLayout getSignupStatus() {
		
		final FormLayout signupStatus = new FormLayout();
		final Table table = new Table();
		
		final ComboBox bundleName = new ComboBox();
		bundleName.setInputPrompt("Select Bundle");
		signupStatus.addComponent(bundleName);	

		List<String> bundleList = query.getBundles();
		for (String bundle : bundleList) {
			bundleName.addItem(bundle);
		}
		
        bundleName.addValueChangeListener(new ValueChangeListener() {
			
			@Override
			public void valueChange(ValueChangeEvent event) {
			
				String bundleSelected = event.getProperty().getValue().toString();
				
				Table table = query.getCQsignUpstatus(bundleSelected);
				signupStatus.addComponent(table);
						
			}
		});
		
		
		
		return signupStatus;
	}

}
