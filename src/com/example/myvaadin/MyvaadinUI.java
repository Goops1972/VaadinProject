package com.example.myvaadin;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.List;

import javax.servlet.annotation.WebServlet;

import com.vaadin.ui.HorizontalSplitPanel;
import com.google.gwt.user.client.ui.AbsolutePanel;
import com.google.gwt.user.client.ui.FlexTable;
import com.google.gwt.user.client.ui.SplitLayoutPanel;
import com.vaadin.annotations.Theme;
import com.vaadin.annotations.VaadinServletConfiguration;
import com.vaadin.data.Item;
import com.vaadin.data.Property.ValueChangeEvent;
import com.vaadin.data.Property.ValueChangeListener;
import com.vaadin.data.util.BeanItemContainer;
import com.vaadin.event.ItemClickEvent;
import com.vaadin.event.ItemClickEvent.ItemClickListener;
import com.vaadin.server.VaadinRequest;
import com.vaadin.server.VaadinServlet;
import com.vaadin.shared.ui.label.ContentMode;
import com.vaadin.ui.AbsoluteLayout;
import com.vaadin.ui.Alignment;
import com.vaadin.ui.Button;
import com.vaadin.ui.Button.ClickEvent;
import com.vaadin.ui.Button.ClickListener;
import com.vaadin.ui.ComboBox;
import com.vaadin.ui.GridLayout;
import com.vaadin.ui.Layout.AlignmentHandler;
import com.vaadin.ui.MenuBar.Command;
import com.vaadin.ui.MenuBar.MenuItem;
import com.vaadin.ui.Select;
import com.vaadin.ui.Table;
import com.vaadin.ui.Tree.ExpandEvent;
import com.vaadin.ui.Accordion;
import com.vaadin.ui.Form;
import com.vaadin.ui.FormLayout;
import com.vaadin.ui.HorizontalLayout;
import com.vaadin.ui.Label;
import com.vaadin.ui.MenuBar;
import com.vaadin.ui.Notification;
import com.vaadin.ui.Panel;
import com.vaadin.ui.TextField;
import com.vaadin.ui.Tree;
import com.vaadin.ui.Tree.ExpandListener;
import com.vaadin.ui.TreeTable;
import com.vaadin.ui.UI;
import com.vaadin.ui.VerticalLayout;
import com.vaadin.ui.HorizontalSplitPanel;
import com.vaadin.ui.VerticalSplitPanel;
@SuppressWarnings("serial")
@Theme("myvaadin")
public class MyvaadinUI extends UI {

	Panel hpanel = new Panel();
//	Label subtitle = new Label("<html><h2><align = center>RULE MANAGEMENT TOOL</h2></html>", ContentMode.HTML);

	final VerticalLayout left = new VerticalLayout();
	final VerticalLayout center = new VerticalLayout();
	AbsoluteLayout absLayout = new AbsoluteLayout();

	@WebServlet(value = "/*", asyncSupported = true)
	@VaadinServletConfiguration(productionMode = false, ui = MyvaadinUI.class)
	public static class Servlet extends VaadinServlet {
	}

	@Override
	protected void init(VaadinRequest request) {

		Label l = new Label("Welcome to Home Page!!!!");
		l.setSizeFull();

		Button mainButton = new Button("Clean");
		// hpanel = new Panel();
		// hpanel.setSizeFull();
		hpanel.setWidth("100%");
		hpanel.setHeight("500px");
//		hpanel.setSizeFull();
		
		absLayout.addComponent(l);// adding custom Home Page
		absLayout.addComponent(mainButton, "left: 5px; bottom: 10px");// adding custom components Home Page
//		uploadLayout.addComponent(uploadInput, "left: 5px; top: 10px;");
		hpanel.setContent(absLayout);

		mainButton.setSizeUndefined();
		mainButton.addClickListener(new ClickListener() {

			@Override
			public void buttonClick(ClickEvent event) {

				center.removeComponent(hpanel);
			}
		});

		MenuBar barMenu = createMenu();

		left.setWidth("250px");
		// Accordion accordian = createAccordion();
		// Accordion accordian = new Accordion();
		CreateBundleDetails bdetails = new CreateBundleDetails();
//		TreeTable accordian = bdetails.getBundleDetails();

		final Tree myTree = bdetails.getTree(); // this should return all bundle, cq and rule info from database
		myTree.addExpandListener(new ExpandListener() {

			@Override
			public void nodeExpand(ExpandEvent event) {
//fetch data from Database to create Tree

			}
		});

		myTree.addValueChangeListener(new ValueChangeListener() {
	@Override
	public void valueChange(ValueChangeEvent event) {
		if(event.getProperty().getValue() != null){		
		String eventvalue = event.getProperty().getValue().toString();
				
					if (eventvalue.equalsIgnoreCase("Bundle Details")) {
						//myTree.createBundle();
						//bundleForm.getDetails()//display all the info fetched from database like req lead, rule lead, dep date, capacity
						//add to the panel (center)
//						Button b = new Button("from my tree");
//						hpanel.setContent(b);
//						center.addComponent(hpanel);
					} else if(eventvalue.equalsIgnoreCase("bddomino")){
						//populate all bundles feteched from database
						BundleForm bform = new BundleForm();
						AbsoluteLayout bformLayout = bform.getDetails();
						
						Tracker tracker = new Tracker();
						List<String> cqSt = Arrays.asList("Design", "Design Review", "Rule Creation", "Rule Test");
						
					for (int i = 0; i < cqSt.size(); i++) {
						
						Table table = tracker.createTable(cqSt.get(i), i);
						System.out.println("......."+cqSt.get(i));
						hpanel.setContent(table);
					}	
						center.addComponent(hpanel);
						
						
					} else if(eventvalue.equalsIgnoreCase("cq")){
						//populate all cq associated with bundle
						
						
					} else if(eventvalue.equalsIgnoreCase("Rulenumbers")){
						//populate all rules associated with parent cq 
						RuleForm rf = new RuleForm();
						AbsoluteLayout abl = rf.getDetails();
						hpanel.setContent(abl);
						center.addComponent(hpanel);
						
					} else if(eventvalue.equalsIgnoreCase("CQdomino")){
						
						CQDetails cq = new CQDetails();
						
						AbsoluteLayout cqd = cq.getInfo();
						
						hpanel.setContent(cqd);
						center.addComponent(hpanel);
					}
			Notification.show(event.getProperty().getValue().toString());

				}
					
	}
});
		left.addComponent(myTree);
		// left.addComponent(accordian);
		center.addComponent(barMenu);
		center.addComponent(hpanel);

		HorizontalLayout horizontalLayout = new HorizontalLayout(left, center);
		horizontalLayout.setExpandRatio(center, 1.0f);
		horizontalLayout.setSizeFull();

		
		
//		Panel hpanel = new Panel("Horizontal Split");
		VerticalLayout hpanel = new VerticalLayout();

		
		HorizontalSplitPanel hsplit = new HorizontalSplitPanel();
		hsplit.setFirstComponent(left);
		hsplit.setSecondComponent(center);
		hsplit.setSizeFull();
		hsplit.setSplitPosition(15);

		Label header = new Label("<html><h1><b>Risk Evaluation Management \n</b></h1></html>", ContentMode.HTML);
//		header.setHeight("30px");
		VerticalSplitPanel vspanel = new VerticalSplitPanel(header, hsplit);
		vspanel.setSplitPosition(20);
		
		setContent(vspanel);
		
	}

	private MenuBar createMenu() {

		MenuBar.Command mycommand = createCommand();
		MenuBar barMenu = new MenuBar();
		// MenuItem menuItem = new MenuItem("myMenu", null, null);
		// A top-level menu item that opens a submenu
		final MenuItem create = barMenu.addItem("Create", null, null);
		create.addItem("Bundle", null, mycommand);
		create.addItem("CQ", null, mycommand);
		create.addItem("Rule", null, mycommand);

		// Another top-level item
		MenuItem modify = barMenu.addItem("Modify", null, null);
		modify.addSeparator();
		modify.addItem("Edit Bundle", null, mycommand);
		modify.addItem("Edit ClearQuest", null, mycommand);
		modify.addItem("Edit Rules", null, mycommand);

		MenuItem update = barMenu.addItem("Update", null, null);
		update.addSeparator();
		update.addItem("Update Bundle", mycommand);
		update.addItem("Update CQ", mycommand);
		update.addItem("Update Rules", mycommand);
		
		MenuItem test = barMenu.addItem("Test", null, null);
		test.addSeparator();
		test.addItem("Unit Test", null, mycommand);
		test.addItem("Scenario Test", null, mycommand);
		test.addItem("Regression Test", null, mycommand);

		
		

		MenuItem signUp =barMenu.addItem("Sign-Up", null, null);
		signUp.addSeparator();
		signUp.addItem("Bundle Lead", mycommand);
		signUp.addItem("Rule Lead", mycommand);
		signUp.addItem("Design Reviewer", mycommand);
		signUp.addItem("Rule Creator", mycommand);
		signUp.addItem("Rule Tester", mycommand);
		signUp.addItem("Scenario Tester", mycommand);
		signUp.addItem("Test Reviewer", mycommand);
		signUp.addItem("Over All Reviewer", mycommand);
		
		MenuItem reports = barMenu.addItem("Reports", null, null);
		reports.addItem("Progress Status", mycommand);
		reports.addItem("Capacity Utilization", mycommand);
		
		
		// Yet another top-level item
		MenuItem help = barMenu.addItem("Help", null, null);
		help.addSeparator();
		help.addItem("Creating Rules", null, mycommand);
		help.addItem("Testing", null, mycommand);
		help.addItem("Rule Review", null, mycommand);
						
		return barMenu;

	}

	private Command createCommand() {

		// final Label selection = new Label("-");

		MenuBar.Command mycommand = new MenuBar.Command() {
			public void menuSelected(MenuItem selectedItem) {

				Button clearButton = new Button("Clear");
				VerticalLayout vlayoutforForm = new VerticalLayout();
				// FormLayout cqForm = null;
				String selected = selectedItem.getText().toString();

				if (selected.equalsIgnoreCase("Bundle")) {

//Note in future create seperate service class					
					BundleForm bf = new BundleForm();
					FormLayout flayout = bf.createForm();
					Button addButton = new Button("Add");
//					flayout.addComponent(addButton);
//					flayout.setSpacing(true);
					flayout.addComponent(clearButton);
					
					vlayoutforForm.addComponent(flayout);
					hpanel.setContent(vlayoutforForm);
					center.addComponent(hpanel);
					addButton.addClickListener(new ClickListener() {
						
						@Override
						public void buttonClick(ClickEvent event) {

							Connection con=null;
							try {
								
								Class.forName("com.mysql.jdbc.Driver");
								con = DriverManager.getConnection("jdbc:mysql://localhost:3306/RuleManagement","root",null);
								
								con.setAutoCommit(false);
								Statement st = con.createStatement();
								boolean rs= st.execute("INSERT INTO BUNDLE (B_NAME,B_DESC) " +
										"VALUES ('Neerav','Neerav DESCRIPTON')");
								
								st.execute("INSERT INTO BUNDLE (B_NAME,B_DESC) " +
										"VALUES ('Neerav1','Neerav FROM DESC')");
										
								if(!rs){
									Notification.show("Record is inserted successfully.");
								}else{
									Notification.show("Record is insertion is failed.");
								}
								con.commit();
								st.close();
								con.close();
								
							} catch (Exception e) {
								
								Notification.show("No Success !!!!!See Trace.");
								e.printStackTrace();
							}
							
						}
					});
				} else if (selected.equalsIgnoreCase("CQ")) {
					CQDetails cqd = new CQDetails();
					FormLayout cqForm = cqd.createForm();
					hpanel.setContent(cqForm);
					center.addComponent(hpanel);

					//currently deactivated for gridlayout
				/*	GridLayout gl = cqd.gLayout();
					gl.setSizeFull();
//					gl.addComponent(clearButton, 2, 1);
//					gl.setComponentAlignment(clearButton,Alignment.MIDDLE_RIGHT);
					hpanel.setContent(gl);
					center.addComponent(hpanel);
				*/
				} else if (selected.equalsIgnoreCase("Rule")) {
					RuleForm rform = new RuleForm();
					FormLayout ruleForm = rform.createForm();
//					ruleForm.addComponent(clearButton, 2, 2);
//					ruleForm.setComponentAlignment(clearButton,Alignment.MIDDLE_RIGHT);
					hpanel.setContent(ruleForm);
					center.addComponent(hpanel);
				} else if (selected.equalsIgnoreCase("Edit Bundle")) {
					Notification.show("Edit Bundle");
					
					BundleForm bf = new BundleForm();
					FormLayout flayout = bf.editBundle();
//					flayout.addComponent(clearButton);
					vlayoutforForm.addComponent(flayout);
					hpanel.setContent(vlayoutforForm);
					center.addComponent(hpanel);
				} else if (selected.equalsIgnoreCase("Edit ClearQuest")) {
					Notification.show("Edit ClearQuest");
				
					CQDetails cqd = new CQDetails();	
					FormLayout gl = cqd.editCQForm();
					gl.setSizeFull();
//					gl.addComponent(clearButton, 2, 1);
//					gl.setComponentAlignment(clearButton,
//							Alignment.MIDDLE_RIGHT);
					hpanel.setContent(gl);
					center.addComponent(hpanel);

					
				} else if (selected.equalsIgnoreCase("Edit Rules")) {
					Notification.show("Edit Rules");

					RuleForm rform = new RuleForm();
					FormLayout ruleForm = rform.editForm();
//					ruleForm.addComponent(clearButton, 2, 2);
//					ruleForm.setComponentAlignment(clearButton,Alignment.MIDDLE_RIGHT);
					hpanel.setContent(ruleForm);
					center.addComponent(hpanel);
					
				} else if (selected.equalsIgnoreCase("Unit Test")) {

					TestClass tc = new TestClass();
					AbsoluteLayout ablayout = tc.createLayout();
					ablayout.setSizeFull();
					ablayout.addComponent(clearButton, "right: 0px; bottom: 0px;");
//					ablayout.addComponent(clearButton);
					hpanel.setContent(ablayout);
					center.addComponent(hpanel);
				}else if (selected.equalsIgnoreCase("Scenario Test")) {
					ScenarioClass sc = new ScenarioClass();
//					AbsoluteLayout sclayout1 = sc.createLayoutUpload();
					AbsoluteLayout sclayout = sc.createTable();
//					sclayout1.setSizeFull();
//					sclayout.addComponent(clearButton, "right: 0px; bottom: 0px;");
//					hpanel.setContent(sclayout1);
//					hpanel.setSizeFull();
					hpanel.setContent(sclayout);
					center.addComponent(hpanel);
					
				}else if (selected.equalsIgnoreCase("Update Bundle")) {

					BundleForm bundleUpdate = new BundleForm();
					FormLayout bupdate = bundleUpdate.upDate();
					hpanel.setContent(bupdate);
					center.addComponent(hpanel);
					
				} else if (selected.equalsIgnoreCase("Update CQ")){

					CQDetails cq = new CQDetails();
					FormLayout cqUpdate = cq.update();
					hpanel.setContent(cqUpdate);
					center.addComponent(hpanel);
					
				}else if (selected.equalsIgnoreCase("Update Rules")){

					RuleForm rule = new RuleForm();
					FormLayout updatedRule = rule.update();
					hpanel.setContent(updatedRule);
					center.addComponent(hpanel);
					
				}else if (selected.equalsIgnoreCase("Bundle Lead")){
					BundleForm bform = new BundleForm();
					FormLayout signupDetails = bform.getSignup(selected);
					hpanel.setContent(signupDetails);
					center.addComponent(hpanel);
					
					
				}else if (selected.equalsIgnoreCase("Rule Lead")){
					CQDetails cqform = new CQDetails();
					FormLayout signupCQ = cqform.getSignup(selected);
					hpanel.setContent(signupCQ);
					center.addComponent(hpanel);
					
					
				}else if (selected.equalsIgnoreCase("Design Reviewer")){
//					Notification.show(selected);
					CQDetails cqform = new CQDetails();
					FormLayout signupCQ = cqform.getSignup(selected);
					hpanel.setContent(signupCQ);
					center.addComponent(hpanel);
					
				}else if (selected.equalsIgnoreCase("Rule Creator")){
					RuleForm ruleForm = new RuleForm();
					FormLayout ruleSignup = ruleForm.getSignup(selected);
					hpanel.setContent(ruleSignup);
					center.addComponent(hpanel);
					
					
				}else if (selected.equalsIgnoreCase("Rule Tester")){
//					Notification.show(selected);
					RuleForm ruleForm = new RuleForm();
					FormLayout ruleSignup = ruleForm.getSignup(selected);
					hpanel.setContent(ruleSignup);
					center.addComponent(hpanel);
					
				}else if (selected.equalsIgnoreCase("Test Reviewer")){
//					Notification.show(selected);
					CQDetails cqform = new CQDetails();
					FormLayout signupCQ = cqform.getSignup(selected);
					hpanel.setContent(signupCQ);
					center.addComponent(hpanel);
					
				}else if (selected.equalsIgnoreCase("Scenario Tester")){
					Notification.show(selected);
					CQDetails cqform = new CQDetails();
					FormLayout signupCQ = cqform.getSignup(selected);
					hpanel.setContent(signupCQ);
					center.addComponent(hpanel);
				}else if (selected.equalsIgnoreCase("Over All Reviewer")){
					Notification.show(selected);
					CQDetails cqform = new CQDetails();
					FormLayout signupCQ = cqform.getSignup(selected);
					hpanel.setContent(signupCQ);
					center.addComponent(hpanel);
				}
				else {

//					Notification.show(selected);

				}

				clearButton.addClickListener(new ClickListener() {

					@Override
					public void buttonClick(ClickEvent event) {
						center.removeComponent(hpanel);
					}
				});

			}

		};
		return mycommand;
	}
}
