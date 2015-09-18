package com.example.myvaadin;

import java.io.Serializable;

import com.vaadin.data.Item;
import com.vaadin.data.Property;
import com.vaadin.data.Property.ValueChangeEvent;
import com.vaadin.data.util.IndexedContainer;
import com.vaadin.server.Sizeable;
import com.vaadin.ui.AbsoluteLayout;
import com.vaadin.ui.Button;
import com.vaadin.ui.Button.ClickEvent;
import com.vaadin.ui.Button.ClickListener;
import com.vaadin.ui.ComboBox;
import com.vaadin.ui.GridLayout;
import com.vaadin.ui.Label;
import com.vaadin.ui.Panel;
import com.vaadin.ui.Select;
import com.vaadin.ui.Table;
import com.vaadin.ui.TextField;
import com.vaadin.ui.Upload;
import com.vaadin.ui.VerticalLayout;

public class TestClass implements Serializable{

	public AbsoluteLayout createLayout() {

		final AbsoluteLayout layout = new AbsoluteLayout();
		layout.setWidth("400px");
		layout.setHeight("250px");

		// // A component with coordinates for its top-left corner
		// TextField text = new TextField("Somewhere someplace");
		// layout.addComponent(text, "left: 50px; top: 50px;");

		// At the top-left corner
//		TextField ruleNo = new TextField("Enter Rule#");
//		ruleNo.setWidth("90px");
//		layout.addComponent(ruleNo, "left: 0px; top: 20px;");

		Select ruleNameSelect = new Select("Select Rule");
		ruleNameSelect.addItem("Rule1");
		ruleNameSelect.addItem("Rule2");
		ruleNameSelect.addItem("Rule3");
		layout.addComponent(ruleNameSelect, "left: 5px; top: 20px;");

		TextField ver = new TextField("Version");
		ver.setWidth("40px");
		layout.addComponent(ver, "left: 200px; top: 20px;");

		Select cqNameSelect = new Select("Select CQ");
		cqNameSelect.addItem("cqRule1");
		cqNameSelect.addItem("cqRule2");
		cqNameSelect.addItem("cq Rule3");
		layout.addComponent(cqNameSelect, "left: 370px; top: 20px;");

		Select bundleNameSelect = new Select("Select Bundle");
		bundleNameSelect.addItem("BundlecqRule1");
		bundleNameSelect.addItem("BundlecqRule2");
		bundleNameSelect.addItem("Bundlecq Rule3");
		layout.addComponent(bundleNameSelect, "left: 570px; top: 20px;");
		
//		Upload uploadInput = new Upload();
		UploadExample uploadInput = new UploadExample();
		uploadInput.setCaption("Upload Input File");
		layout.addComponent(uploadInput, "left: 5px; top: 120px;");

//		final Upload uploadoutput = new Upload();
		final UploadBox uploadoutput = new UploadBox();
//		uploadoutput.setCaption("Upload Output File");
		layout.addComponent(uploadoutput, "left: 480px; top: 120px;");

		
//		final Upload uploadRuleLogic = new Upload();
		final UploadBox uploadRuleLogic = new UploadBox();
//		uploadRuleLogic.setCaption("Upload Rule  File");
		layout.addComponent(uploadRuleLogic, "left: 480px; top: 220px;");

		
		
		
		
		// At the bottom-right corner
		final Button runTest = new Button("Run Test");
		
		layout.addComponent(runTest, "left: 0px; top: 320px;");

		runTest.addClickListener(new ClickListener() {

			@Override
			public void buttonClick(ClickEvent event) {

//				layout.removeAllComponents();
				layout.removeComponent(uploadoutput);

				final Table table = new Table("Test Results");
//		        table.setWidth("600px");
		        table.setSizeFull();
		        table.setSelectable(true);
				// Define two columns for the built-in container
				table.addContainerProperty("Rule#", String.class, null);
				table.addContainerProperty("RuleGroup",  String.class, null);
				table.addContainerProperty("Expected",  String.class, null);
				table.addContainerProperty("Result",  String.class, null);
				table.addContainerProperty("Error",  String.class, null);
				
				table.setColumnCollapsingAllowed(true);

				// Collapse this column programmatically
				try {
				    table.setColumnCollapsed("Error", true);
//				    table.setColumnCollapsingAllowed(true);
				} catch (IllegalStateException e) {
				    // Can't occur - collapsing was allowed above
				    System.err.println("Something horrible occurred");
				}
				// Add a row the hard way
				Object newItemId = table.addItem();
				Item row1 = table.getItem(newItemId);
				row1.getItemProperty("Rule#").setValue("132345");
				row1.getItemProperty("RuleGroup").setValue("Home Loan1.46");
				row1.getItemProperty("Expected").setValue("Will Fire");
				row1.getItemProperty("Result").setValue("True");
				row1.getItemProperty("Error").setValue("-no error");
				// Add a few other rows using shorthand addItem()
				table.addItem(new Object[]{"Canopus",        -0.72}, 2);
				table.addItem(new Object[]{"Arcturus",       -0.04}, 3);
				table.addItem(new Object[]{"Alpha Centauri", -0.01}, 4);
				        
				// Show 5 rows
				table.setPageLength(5);				
				Panel pane = new Panel();
				pane.setSizeFull();
//				vlayout.addComponent(tableHeader);
//				vlayout.addComponent(table);
				pane.setContent(table);
				layout.addComponent(table, "left:0; bottom: 50; ");
//				layout.addComponent(runTest, "right: 700px; top: 120px;");
			}
		});
		return layout;
	}

}
