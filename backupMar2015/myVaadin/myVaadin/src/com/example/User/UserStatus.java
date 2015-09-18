package com.example.User;

import java.sql.Date;
import java.text.SimpleDateFormat;
import java.util.Calendar;

import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.support.rowset.SqlRowSet;

import com.example.database.Template;
import com.example.myvaadin.DateConversion;
import com.vaadin.data.Container;
import com.vaadin.data.Item;
import com.vaadin.data.Property.ValueChangeEvent;
import com.vaadin.data.Property.ValueChangeListener;
import com.vaadin.data.fieldgroup.FieldGroup;
import com.vaadin.data.util.IndexedContainer;
import com.vaadin.server.VaadinSession;
import com.vaadin.shared.ui.label.ContentMode;
import com.vaadin.ui.AbsoluteLayout;
import com.vaadin.ui.Button;
import com.vaadin.ui.Button.ClickListener;
import com.vaadin.ui.ComboBox;
import com.vaadin.ui.DateField;
import com.vaadin.ui.FormLayout;
import com.vaadin.ui.HorizontalLayout;
import com.vaadin.ui.Label;
import com.vaadin.ui.LoginForm;
import com.vaadin.ui.LoginForm.LoginListener;
import com.vaadin.ui.NativeSelect;
import com.vaadin.ui.Notification;
import com.vaadin.ui.TabSheet;
import com.vaadin.ui.Table;
import com.vaadin.ui.TextField;
import com.vaadin.ui.Button.ClickEvent;
import com.vaadin.ui.LoginForm.LoginEvent;
import com.vaadin.ui.themes.Reindeer;

public class UserStatus {
	JdbcTemplate jt = Template.getTemplate();
	
//	@SuppressWarnings("deprecation")
	public AbsoluteLayout getStatusTable() {
		final AbsoluteLayout layout = new AbsoluteLayout();
		Button refreshButton = new Button("Refresh");
		final TabSheet tab = new TabSheet();
		final Button cleanDB = new Button("Clean Database");
		final ComboBox user = new ComboBox();
		user.setInputPrompt("--Select User --");
		user.addItems("Gopu", "Maya", "Saurav", "Sujan");
		user.setNullSelectionAllowed(false);
		
		user.addValueChangeListener(new ValueChangeListener() {
			
			@Override
			public void valueChange(ValueChangeEvent event) {
				tab.removeAllComponents();
//				layout.removeComponent(userSelection);
				VaadinSession.getCurrent().setAttribute("userName", event.getProperty().getValue().toString());
				System.out.println(VaadinSession.getCurrent().getAttribute("userName"));
				String user = event.getProperty().getValue().toString();
				Table ongoingTable = getMyStatus(user);
				Table completedTable = getCompletedStatus(user);
	
				tab.addTab(ongoingTable, "On Going");
				tab.addTab(completedTable, "Completed");
				tab.addTab(new Label("Some content"), "Others");
				
			}

			private Table getCompletedStatus(String user) {

				Table table = new Table();
				
				// Define two columns for the built-in container
				table.addContainerProperty("My Task", String.class, null);
				table.addContainerProperty("Role", String.class, null);
				table.addContainerProperty("Closed Dt.", String.class, null);

				Calendar currenttime = Calendar.getInstance();
				DateField dateField = new DateField();
				dateField.setValue(new java.util.Date());
				
				java.sql.Date myDate = DateConversion.getSqlDate(dateField.getValue().getTime());
				
				
			    Date sqldate = new Date((currenttime.getTime()).getTime());

			    SqlRowSet myList = jt.queryForRowSet("select task, role, end_dt from user_status_table where user='"+user+"' and status ='Close'");
			    
			    int n=1;
			    while(myList.next()){
			    	String myTask = myList.getString(1);
			    	String myRole = myList.getString(2);
			    	java.util.Date myStDt = myList.getDate(3);

			    	SimpleDateFormat myDateFormat = new SimpleDateFormat("MM-dd-yyyy");
			    	String closedDt = myDateFormat.format(myStDt);
			    	
			    	table.addItem(new Object[]{myTask, myRole, closedDt}, n);
			    	n++;
			    }
			    
				// Show exactly the currently contained rows (items)
				table.setPageLength(table.size());
				
				return table;

			}
		});
		
		
		
		cleanDB.addClickListener(new ClickListener() {
			@Override
			public void buttonClick(ClickEvent event) {
				try {
					jt.execute("delete from regression");
					jt.execute("delete from rulegroup");
					jt.execute("delete from bundle_details");
					jt.execute("delete from cq");
					jt.execute("delete from statusData");
					jt.execute("delete from bundle");
					jt.execute("delete from user_status_table");
				} catch (Exception e) {
					// TODO: handle exception
				}
				
				Notification.show("cleaned all table");
			}
		});
		
		
		
		
		
		
		
		layout.addComponent(tab);
//		layout.addComponent(refreshButton, "right:5px;");
		layout.addComponent(cleanDB, "right:150px");
		layout.addComponent(user, "right:5px");
//		layout.addComponent(userSelection);
		return layout;
	}

	private Table getMyStatus(String userName) {

		Table table = new Table();
        
		// Define two columns for the built-in container
		table.addContainerProperty("My Task", String.class, null);
		table.addContainerProperty("Role", String.class, null);
		table.addContainerProperty("SignUp Dt.", String.class, null);

		Calendar currenttime = Calendar.getInstance();
		DateField dateField = new DateField();
		dateField.setValue(new java.util.Date());
		
		java.sql.Date myDate = DateConversion.getSqlDate(dateField.getValue().getTime());
		
		
	    Date sqldate = new Date((currenttime.getTime()).getTime());

	    SqlRowSet myList = jt.queryForRowSet("select task, role, st_dt from user_status_table where user='"+userName+"' and status ='Open'");
	    
	    int n=1;
	    while(myList.next()){
	    	String myTask = myList.getString(1);
	    	String myRole = myList.getString(2);
	    	java.util.Date myStDt = myList.getDate(3);

	    	SimpleDateFormat myDateFormat = new SimpleDateFormat("MM-dd-yyyy");
	    	String signupDt = myDateFormat.format(myStDt);
	    	
	    	table.addItem(new Object[]{myTask, myRole, signupDt}, n);
	    	n++;
	    }
	    
		// Show exactly the currently contained rows (items)
		table.setPageLength(table.size());
		
		return table;
	}


}
