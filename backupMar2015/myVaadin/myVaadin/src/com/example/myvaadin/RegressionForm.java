package com.example.myvaadin;

import java.io.Serializable;
import java.sql.Date;
import java.util.ArrayList;
import java.util.List;

import com.example.database.Insert;
import com.example.database.Query;
import com.example.database.Update;
import com.vaadin.data.Property.ValueChangeEvent;
import com.vaadin.data.Property.ValueChangeListener;
import com.vaadin.server.VaadinSession;
import com.vaadin.ui.Button;
import com.vaadin.ui.Button.ClickListener;
import com.vaadin.ui.ComboBox;
import com.vaadin.ui.DateField;
import com.vaadin.ui.FormLayout;
import com.vaadin.ui.Notification;
import com.vaadin.ui.Table;
import com.vaadin.ui.TextField;
import com.vaadin.ui.Button.ClickEvent;

public class RegressionForm implements Serializable {

	Query query = new Query();
	Update update = new Update();
	Insert insert = new Insert();
	String userName = (String) VaadinSession.getCurrent().getAttribute("userName");
	
	public FormLayout updateRegression() {
		Notification.show("inside update regression");
		
		final FormLayout regressionUpdate = new FormLayout();

		final ComboBox bundleList = new ComboBox("Select Bundle");
		List <String> bundlefromDB = query.getBundles();
		
		for (String bundle : bundlefromDB) {
			bundleList.addItem(bundle);
			regressionUpdate.addComponent(bundleList);
		}

		
		
		bundleList.addValueChangeListener(new ValueChangeListener() {
			final ComboBox listofRegType = new ComboBox("Select RegressionType");
			final DateField updateDate = new DateField("Select Update Date");
			
			final TextField hoursWorked = new TextField("Hours Worked");
			final Button updateButton = new Button("Update");
			
			@Override
			public void valueChange(ValueChangeEvent event) {
				regressionUpdate.removeComponent(listofRegType);
				final String bundleselected = event.getProperty().getValue().toString();

				List<String> regTypes = query.getRegressionType(bundleselected);
				
				listofRegType.removeAllItems();

				for (final String regType : regTypes) {
					listofRegType.addItem(regType);

				}

				updateButton.addClickListener(new ClickListener() {
					@Override
					public void buttonClick(ClickEvent event) {
						java.sql.Date myDate = DateConversion.getSqlDate(updateDate.getValue().getTime());
						System.out.println(listofRegType.getValue().toString());
						System.out.println(bundleselected);
						System.out.println(myDate);
						System.out.println(hoursWorked.getValue().toString());
						
						try {
							update.updateRegressionData(listofRegType.getValue().toString(), bundleselected, myDate, hoursWorked.getValue().toString(), userName);	
						} catch (Exception e) {
							Notification.show("Entry failed");
							e.printStackTrace();
						}
						
						
					}
				});				
				regressionUpdate.addComponent(listofRegType);
				regressionUpdate.addComponent(updateDate);
				regressionUpdate.addComponent(hoursWorked);
				regressionUpdate.addComponent(updateButton);
				
			}
		});
		
		
		
		return regressionUpdate;
	}

	public FormLayout getSignup(String selected) {
		final String userName = (String) VaadinSession.getCurrent().getAttribute("userName");
		final FormLayout regressionSignup = new FormLayout();
		final ComboBox bundleList = new ComboBox("Select Bundle");
		final ComboBox listofRegType = new ComboBox("Select RegressionType");
		final DateField signUpDate = new DateField("Select SignUp Date");
		final Button signUpbutton = new Button("Update");
		final TextField user = new TextField("Signing Up As: ");
		user.setValue(userName);
		user.setEnabled(true);
		
		List <String> bundlefromDB = query.getBundles();
		
		for (String bundle : bundlefromDB) {
			bundleList.addItem(bundle);
			regressionSignup.addComponent(bundleList);
		}

		
		bundleList.addValueChangeListener(new ValueChangeListener() {
		@Override
		public void valueChange(ValueChangeEvent event) {
			
			final String bundleselected = event.getProperty().getValue().toString();
			List<String> regTypes = query.getRegressionType(bundleselected);
			
			for (final String regType : regTypes) {
				listofRegType.addItem(regType);
			}
			
			regressionSignup.addComponent(listofRegType);
			regressionSignup.addComponent(signUpDate);
			regressionSignup.addComponent(user);
			regressionSignup.addComponent(signUpbutton);
		
			signUpbutton.addClickListener(new ClickListener() {
				
				@Override
				public void buttonClick(ClickEvent event) {
					java.sql.Date myDate = DateConversion.getSqlDate(signUpDate.getValue().getTime());
					update.signupRegressionTest(bundleselected, listofRegType.getValue().toString(), myDate, userName);
				}
			});
		
		}
	});	


		return regressionSignup;
	}

	public FormLayout createForm() {
		final FormLayout regressionCreate = new FormLayout();
		final ComboBox bundleList = new ComboBox("Select Bundle");
		final TextField regressionType = new TextField("Select Regression Type");
		final Button regressionButton = new Button("Add");
		
//		final String bundleNameSelected = null;
		List <String> bundlefromDB = query.getBundles();
		
		for (String bundle : bundlefromDB) {
			bundleList.addItem(bundle);
			regressionCreate.addComponent(bundleList);
		}
		
		bundleList.addValueChangeListener(new ValueChangeListener() {
			String	bundleNameSelected;	
			@Override
			public void valueChange(ValueChangeEvent event) {
				bundleNameSelected = event.getProperty().getValue().toString();
				regressionType.setWidth("350px");
				
				final List<String> regressionList = new ArrayList<String>();
				
				
				//		table.addContainerProperty("Target Finish Date", DateField.class, null);
				final Table table = new Table();
				table.setWidth("440px");
				table.setHeight("200px");
				table.addContainerProperty("List of Regression Type", String.class, null);

				regressionButton.addClickListener(new ClickListener() {
					int n=0;	
					@Override
					public void buttonClick(ClickEvent event) {
						
						regressionCreate.addComponent(table);
//						table.addItem(new Object[]{regressionType.getValue().toString(), targetDate}, n );
						table.addItem(new Object[]{regressionType.getValue().toString()}, n );
						regressionList.add(regressionType.getValue().toString());
						n++;
				
						
						insert.insertRegressionTest(regressionType.getValue().toString(), bundleNameSelected);
						regressionType.setValue("");
						update.updateRegressionCompletion(bundleNameSelected);
					}
					
				});
				
				regressionCreate.addComponent(regressionType);
				regressionCreate.addComponent(regressionButton);
	
			}
			
		});
	
		
		
		return regressionCreate;
		}

}
