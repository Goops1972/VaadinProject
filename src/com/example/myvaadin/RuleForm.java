package com.example.myvaadin;

import java.io.Serializable;
import java.util.Date;

import com.example.User.LogInInfo;
import com.google.gwt.user.client.ui.CheckBox;
import com.google.gwt.user.client.ui.FlexTable;
import com.google.gwt.user.client.ui.HorizontalPanel;
import com.google.gwt.user.client.ui.InsertPanel.ForIsWidget;
import com.google.gwt.user.client.ui.Widget;
import com.vaadin.data.Container;
import com.vaadin.data.Item;
import com.vaadin.data.Property.ValueChangeEvent;
import com.vaadin.data.Property.ValueChangeListener;
import com.vaadin.data.util.IndexedContainer;
import com.vaadin.event.ItemClickEvent;
import com.vaadin.event.ItemClickEvent.ItemClickListener;
import com.vaadin.ui.AbsoluteLayout;
import com.vaadin.ui.Alignment;
import com.vaadin.ui.Button;
import com.vaadin.ui.Button.ClickListener;
import com.vaadin.ui.ComboBox;
import com.vaadin.ui.DateField;
import com.vaadin.ui.FormLayout;
import com.vaadin.ui.GridLayout;
import com.vaadin.ui.HorizontalLayout;
import com.vaadin.ui.Label;
import com.vaadin.ui.LoginForm;
import com.vaadin.ui.Notification;
import com.vaadin.ui.Select;
import com.vaadin.ui.Table;
import com.vaadin.ui.TextArea;
import com.vaadin.ui.TextField;
import com.vaadin.ui.UI;
import com.vaadin.ui.VerticalLayout;
import com.vaadin.ui.Button.ClickEvent;

public class RuleForm implements Serializable{

	public String existingRule;
	public String existingRuleVersion;
	
	public FormLayout createForm() {
		final FormLayout ruleform = new FormLayout();
		HorizontalLayout hl1 = new HorizontalLayout();
		ComboBox bundleList = new ComboBox();
		bundleList.setInputPrompt("Select Bundle");
		bundleList.addItem("bundle 1");
		bundleList.addItem("bundle 2");
		bundleList.addItem("bundle 3");
		bundleList.addItem("bundle 4");
hl1.addComponent(bundleList);		
		
		ComboBox cqList = new ComboBox();
		cqList.setInputPrompt("Select CQ");
		cqList.addItem("cq 1");
		cqList.addItem("cq 2");
		cqList.addItem("cq 3");
		cqList.addItem("cq 4");
hl1.addComponent(cqList);
hl1.setSpacing(true);

	final HorizontalLayout hl2 = new HorizontalLayout();
	final TextField rgName = new TextField();
	rgName.setInputPrompt("Type here to add new Rule Group");
	rgName.setWidth("450px");
	final TextField ver = new TextField();
	ver.setInputPrompt("Enter Version");
	ver.setWidth("120px");
	final Button addButton = new Button("Add More");
	hl2.addComponent(rgName);
	hl2.addComponent(ver);
	hl2.addComponent(addButton);
	hl2.setSpacing(true);
	
	final HorizontalLayout hl4 = new HorizontalLayout();
	hl4.setSpacing(true);
	final Button saveAll = new Button("Save All");
	hl4.addComponent(saveAll);
	ruleform.addComponent(hl1);
	ruleform.addComponent(hl2);
	ruleform.addComponent(hl4);
	
	addButton.addClickListener(new ClickListener() {

			@Override
			public void buttonClick(ClickEvent event) {
				hl4.removeComponent(saveAll);
				HorizontalLayout hl3 = new HorizontalLayout();
				TextField rgName = new TextField();
				rgName.setInputPrompt("Type here to add new Rule Group");
				rgName.setWidth("450px");
				final TextField ver = new TextField();
				ver.setInputPrompt("Enter Version");
				ver.setWidth("120px");
				hl3.setSpacing(true);
				
				hl3.addComponent(rgName);
				hl3.addComponent(ver);
				hl4.addComponent(saveAll);
				ruleform.addComponent(hl3);
				ruleform.addComponent(hl4);
				
				
			}
		});	
		
		return ruleform;
	}

//	private AbsoluteLayout createRuleField() {
//		
//		AbsoluteLayout ablayout = new AbsoluteLayout();
//		final TextField rgName = new TextField();
//		rgName.setInputPrompt("Type here to add new Rule Group");
//		rgName.setWidth("450px");
//		final TextField ver = new TextField();
//		ver.setInputPrompt("Enter Version");
//		ver.setWidth("120px");
//		final Button saveButton = new Button("Save");
//		final Button addButton = new Button("Add More");
//		ablayout.addComponent(rgName);
//		ablayout.addComponent(ver);
//		ablayout.addComponent(saveButton);
//		ablayout.addComponent(addButton);
//		return ablayout;
//	}

	public FormLayout editForm() {

		
		final FormLayout flayout = new FormLayout();
		HorizontalLayout hl = new HorizontalLayout();
		ComboBox bundleList = new ComboBox();
		bundleList.addItem("Bundle1");
		bundleList.addItem("Bundle2");
		bundleList.addItem("Bundle3");
		bundleList.addItem("Bundle4");
hl.addComponent(bundleList);		
		ComboBox cqList = new ComboBox();
		cqList.addItem("cq1");
		cqList.addItem("cq2");
		cqList.addItem("cq3");
		cqList.addItem("cq4");
hl.addComponent(cqList);

		ComboBox editableList = new ComboBox();
		editableList.addItem("Rule Group 1");
		editableList.addItem("Rule Group 2");
		editableList.addItem("Rule Group 3");
		editableList.addItem("Rule Group 4");
		editableList.addItem("Rule Group 5");
hl.addComponent(editableList);
		ComboBox version = new ComboBox();
		version.addItem("v1");
		version.addItem("v2");
		version.addItem("v3");
		version.addItem("v4");
		version.addItem("v5");
hl.addComponent(version);
hl.setSpacing(true);

final TextField existingRuleField = new TextField();
final TextField existingRuleVerField = new TextField();
final HorizontalLayout hl1 = new HorizontalLayout();
final Button save = new Button("Save");



editableList.addValueChangeListener(new ValueChangeListener() {
	
	@Override
	public void valueChange(ValueChangeEvent event) {

		hl1.removeComponent(existingRuleField);
		hl1.removeComponent(existingRuleVerField);
		hl1.removeComponent(save);
		existingRule = event.getProperty().getValue().toString();
		Notification.show(existingRule);
		existingRuleField.setValue(event.getProperty().getValue().toString());
		hl1.addComponent(existingRuleField);
	}
});

version.addValueChangeListener(new ValueChangeListener() {
	
	@Override
	public void valueChange(ValueChangeEvent event) {

		existingRuleVersion = event.getProperty().getValue().toString();
		existingRuleVerField.setValue(existingRuleVersion);
		
		
hl1.addComponent(existingRuleField);
		hl1.addComponent(existingRuleField);
		hl1.addComponent(existingRuleVerField);
		hl1.setSpacing(true);
		hl1.addComponent(save);
	}

});

save.addClickListener(new ClickListener() {
	
	@Override
	public void buttonClick(ClickEvent event) {

		Notification.show("Saving into Database"+existingRule+"  "+existingRuleVersion);
	}
});		


flayout.addComponent(hl);
flayout.addComponent(hl1);

		return flayout;
	
		
		
	
		
		
	}

	@SuppressWarnings("deprecation")
	public AbsoluteLayout getDetails() {

		AbsoluteLayout abl = new AbsoluteLayout();
		RulePojo rpojo = new RulePojo();
//		HorizontalPanel hpanel = new HorizontalPanel();
		VerticalLayout vpanel = new VerticalLayout();
//		Label ruleName = rpojo.setRuleGroupName("rulegroup");
		rpojo.setRuleGroupName("ruleGroup");
		rpojo.setCqNumber("cqname");
		rpojo.setBundleNumber("bundleName");
		

//		Label l1 = new Label(rpojo.getRuleGroupName());
//		l1.setCaption("Rule Name : ");
//		Label l2 = new Label(rpojo.getCqNumber());
//		Label l3 = new Label(rpojo.getBundleNumber());
		TextField l1 = new TextField("CQ Name: ", rpojo.getRuleGroupName());
		TextField l2 = new TextField("Bundle Name : ", rpojo.getBundleNumber());
		l1.setReadOnly(true);
		l2.setReadOnly(true);
		final Table table = new Table("Rule Group Name");
		table.addContainerProperty("Rule No", String.class, null);
		table.addContainerProperty("Rule description", TextArea.class, null);
		table.addContainerProperty("edit", com.vaadin.ui.CheckBox.class, null);
		table.addContainerProperty("save", Button.class, null);

		table.setSizeFull();
		// Insert this data
		String people[][] = {{"1113",  "IF A = '1'"+ "DEC_INFO_ELIGIBILE=true"},
		                     {"2323",  "IF DEC_INFO=true"+ "TX_GENERAL_FORM= 'available'"+ "THEN "+ "Decision = true"},
		                     {"2333V",  "IF DEC_INFO=true"
			                     		+ "TX_GENERAL_FORM= 'available'"
			                     		+ "THEN "
			                     		+ "Decision = true"},
		                     {"O2323",   "IF DEC_INFO=true"
			                     		+ "TX_GENERAL_FORM= 'available'"
			                     		+ "THEN "
			                     		+ "Decision = true"},
		                     {"1323ier",  "IF DEC_INFO=true"
			                     		+ "TX_GENERAL_FORM= 'available'"
			                     		+ "THEN "
			                     		+ "Decision = true"},
		                     {"223432423",  "if a= 1"
		                     		+ "then setPriority = 1"},
		                     };
		        
		// Insert the data and the additional component column
		for (int i=0; i<people.length; i++) {
		    final Object itemId = new Integer(i);

		    final TextArea area = new TextArea(null, people[i][1]);
		    area.setReadOnly(true);
		    area.setSizeFull();
//		    area.setRows(2);
		    
		    final com.vaadin.ui.CheckBox checkbox = new com.vaadin.ui.CheckBox();
		    checkbox.setData(itemId); // Store item ID
		    checkbox.addValueChangeListener(new ValueChangeListener() {
		        @Override
		        public void valueChange(ValueChangeEvent event) {
//		        	Notification.show(event.getProperty().getValue().toString());
		        	boolean checkvalue =event.getProperty().getValue().toString() != null;
		        	TextArea textArea = new TextArea();
if(checkvalue){
    Object itemId = checkbox.getData();
    
    // As the property (column) type is a component type,
    // we just get the property and its value to get the component.
    textArea = ((TextArea)table.getContainerProperty(itemId, "Rule description").getValue());
    textArea.setSizeFull();
    textArea.setReadOnly(false);
//    Notification.show("readonly false");
    
} else {
    textArea.setReadOnly(true);
//    Notification.show("readonly true");
	
}
		        	// Get the stored item ID
//		            textArea.setReadOnly(false);
		            // Modify the referenced component
//		            boolean value = ((Boolean) checkbox.getValue()).booleanValue();
//		            textArea.setEnabled(!value);
		        }
		    });
		    checkbox.setImmediate(false);
		    
		    Button b = new Button("save it");
		    
		    b.addClickListener(new ClickListener() {
				
				@Override
				public void buttonClick(ClickEvent event) {
//					Notification.show(itemId.toString());
//					Notification.show(area.getValue().toString());
					 MySub sub = new MySub();
					 // Add it to the root component
				        UI.getCurrent().addWindow(sub);
				}
			});
		    // Add an item with two components
		    table.addItem(new Object[] {people[i][0], area, checkbox, b}, itemId);
		}
		        
		        
		table.setPageLength(5);
//		hpanel.addComponent(table);
	vpanel.setSpacing(true);
//	vpanel.addComponent(l1);
//	vpanel.addComponent(l2);
	HorizontalLayout hp = new HorizontalLayout();
	hp.setSpacing(true);
	hp.setSizeFull();
	hp.addComponent(l1);
	hp.addComponent(l2);
	vpanel.addComponent(hp);
	vpanel.addComponent(table);
	abl.addComponent(vpanel);
		
		return abl;
	}

	public FormLayout update() {

		FormLayout ruleData = new FormLayout();
		
//		ComboBox user = new ComboBox();
//		user.addItem("Mr. A");
//		user.addItem("Mr. B");
//		user.addItem("Ms C");
//		user.addItem("Ms D");
//	ruleData.addComponent(user);

		HorizontalLayout hl = new HorizontalLayout();
		
		ComboBox bundles = new ComboBox("Select Bundle");
		bundles.addItem("bundle1");
		bundles.addItem("bundle2");
		bundles.addItem("bundle3");
	hl.addComponent(bundles, 0);	
		ComboBox cqs = new ComboBox("Select CQs");
		cqs.addItem("cq1");
		cqs.addItem("cq2");
		cqs.addItem("cq3");
	hl.addComponent(cqs, 1);
	
	
		
//	hl.addComponent(role, 2);
	HorizontalLayout hl2 = new HorizontalLayout();
	LogInInfo logger = new LogInInfo();
	String updater = logger.setUserName("Mr. G");
	TextField role = new TextField();
	role.setValue(logger.setCurrentRole("Creator"));
	
	TextField user = new TextField();
	user.setValue(updater);
	
	hl2.addComponent(role);
	hl2.addComponent(user);
	hl2.setSpacing(true);
	
	hl.setSpacing(true);
	
	Table table = getTableDetails();

	ruleData.addComponent(hl);
	ruleData.addComponent(hl2);
	ruleData.addComponent(table);
	
		return ruleData;
	}

	@SuppressWarnings({ "deprecation", "deprecation" })
	private Table getTableDetails() {
		
		
		final Table table = new Table();
		table.addContainerProperty("Update", com.vaadin.ui.CheckBox.class, null);
		table.addContainerProperty("Rule Group", String.class, null);
		table.addContainerProperty("Stage", TextArea.class, null);
		table.addContainerProperty("New Stage", ComboBox.class, null);
		table.addContainerProperty("New Update Dt", DateField.class, null);
		table.addContainerProperty("Action", Button.class, null);
		
		
		//select Data
		
		// Insert this data
		String people[][] = {{"TX_General_Galileo",  "Design Update"},
		                     {"FD_MAE_Monnier",  "Design Review", "",""},
		                     {"PRICING_PROG_Väisälä",  "Rule Creation", "",""},
		                     {"RDP_MOD_Oterma",   "Rule Testing", "",""},
		                     {"VET_SValtaoja", "Scenario ", "",""},
		                     };
		        
		// Insert the data and the additional component column
		for (int i=0; i<people.length; i++) {
		    Object itemId = new Integer(i);

		    
		    final ComboBox select = new ComboBox();
			select.addItem("Review");
			select.addItem("Coding");
			select.setEnabled(false);
			
			final DateField dateField = new DateField();
			dateField.setEnabled(false);
			
			final Button saveButton = new Button("Save");
			saveButton.setEnabled(false);
			
		    TextArea area = new TextArea(null, people[i][1]);
		    area.setRows(2);//displaying in 2 rows
		    
		    final com.vaadin.ui.CheckBox checkbox = new com.vaadin.ui.CheckBox();
		    checkbox.setData(itemId); // Store item ID
		    checkbox.addValueChangeListener(new ValueChangeListener() {
				
				@Override
				public void valueChange(ValueChangeEvent event) {


		            // Get the stored item ID
		            final Object itemId = checkbox.getData();
		            
		            // As the property (column) type is a component type,
		            // we just get the property and its value to get the component.
		            TextArea textArea = ((TextArea)table.getContainerProperty(itemId, "Stage").getValue());

		            // Modify the referenced component
		            boolean value = ((Boolean) checkbox.getValue()).booleanValue();
		            textArea.setEnabled(!value);
		            
		            select.setEnabled(true);
		            dateField.setEnabled(true);
		            saveButton.setEnabled(true);
		            
		            saveButton.addClickListener(new ClickListener() {
						
						@Override
						public void buttonClick(ClickEvent event) {
							TextArea selectData = (TextArea) table.getContainerProperty(itemId, "Stage").getValue();
							ComboBox comboData = (ComboBox) table.getContainerProperty(itemId, "New Stage").getValue();
							Notification.show("Modifying from "+selectData.getValue()+" to >  "+comboData.getValue());
						}
					});
				}
			});
		    checkbox.setImmediate(true);
		    
		    // Add an item with two components
		    table.addItem(new Object[] {checkbox, people[i][0], area,  select, dateField, saveButton}, itemId);
		}
		        
		        
		table.setPageLength(table.size());
		return table;
	}

	public FormLayout getSignup(String role) {


		if(role.equalsIgnoreCase("Rule Creator")){
			role = "Rule Creator";
		} else {
			role = "Rule Tester";
		}
		FormLayout ruleSignup = new FormLayout();
		
		LogInInfo logger = new LogInInfo();
		String userName = logger.setUserName("Gopu");
		
		TextField user = new TextField();
		user.setValue(userName);
		user.setReadOnly(true);
		
		HorizontalLayout hl = new HorizontalLayout();
		
		ComboBox bundles = new ComboBox();
		bundles.setInputPrompt("Select Bundle");
		bundles.addItem("bundle1");
		bundles.addItem("bundle2");
		bundles.addItem("bundle3");
	hl.addComponent(bundles, 0);	
		ComboBox cqs = new ComboBox();
		cqs.setInputPrompt("- Select CQ -");
		cqs.addItem("cq1");
		cqs.addItem("cq2");
		cqs.addItem("cq3");
	hl.addComponent(cqs, 1);
	
		ComboBox ruleGroup = new ComboBox();
		ruleGroup.setInputPrompt("-- Select Rule Group --");
		ruleGroup.addItem("Rule Gropu1");
		ruleGroup.addItem("Rule Gropu2");
		ruleGroup.addItem("Rule Gropu3");
		ruleGroup.addItem("Rule Gropu4");
	hl.addComponent(ruleGroup, 2);
	
		Button b = new Button("Sign Up");
//	hl.addComponent(b, 2);	
	
	TextField roleSigned = new TextField();
	roleSigned.setValue("As : "+role);
	roleSigned.setReadOnly(true);
//		ComboBox roleSigned = new ComboBox("Role As");
//		roleSigned.setInputPrompt(role);
	hl.addComponent(roleSigned, 2);	
	hl.setSpacing(true);
	
	HorizontalLayout hl2 = new HorizontalLayout();
	DateField signupDate = new DateField();
	hl2.addComponent(user);
	hl2.addComponent(roleSigned);
	hl2.addComponent(signupDate);
	hl2.addComponent(b);
	hl2.setSpacing(true);
	
	VerticalLayout vl = new VerticalLayout();
	vl.addComponent(hl);
	vl.addComponent(hl2);
	vl.setSpacing(true);
	
	ruleSignup.addComponent(vl);
	
		return ruleSignup;
	
	
	
	}


}
