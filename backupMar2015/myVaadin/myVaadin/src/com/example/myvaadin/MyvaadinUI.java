package com.example.myvaadin;

import java.io.Serializable;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.List;

import javax.servlet.annotation.WebServlet;

import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.support.rowset.SqlRowSet;

import com.vaadin.ui.HorizontalSplitPanel;
import com.example.User.UserStatus;
import com.example.database.Query;
import com.example.database.Template;
import com.example.database.Update;
import com.example.reports.ReportTable;
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
import com.vaadin.server.VaadinSession;
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
import com.vaadin.ui.NativeSelect;
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
public class MyvaadinUI extends UI implements Serializable {

	Panel hpanel = new Panel();
//	Label subtitle = new Label("<html><h2><align = center>RULE MANAGEMENT TOOL</h2></html>", ContentMode.HTML);

	final VerticalLayout left = new VerticalLayout();
	final VerticalLayout center = new VerticalLayout();
	AbsoluteLayout absLayout = new AbsoluteLayout();
//	JdbcTemplate jt = Template.getTemplate();
	Query query = new Query();
	
	@WebServlet(value = "/*", asyncSupported = true)
	@VaadinServletConfiguration(productionMode = false, ui = MyvaadinUI.class)
	public static class Servlet extends VaadinServlet {
	}

	@Override
	protected void init(VaadinRequest request) {

		Label l = new Label("Welcome to Home Page!!!!");
		l.setSizeFull();

		UserStatus myStatus = new UserStatus();
		AbsoluteLayout statusTable = myStatus.getStatusTable();
		
		Button clearButton = new Button("Clean");
		
		hpanel.setWidth("100%");
		hpanel.setHeight("500px");
		
		absLayout.addComponent(l);// adding custom Home Page
//		absLayout.addComponent(statusTable);
//		absLayout.addComponent(clearButton, "left: 5px; bottom: 10px");// adding custom components Home Page
		
		hpanel.setContent(absLayout);

		clearButton.setSizeUndefined();
		clearButton.addClickListener(new ClickListener() {

			@Override
			public void buttonClick(ClickEvent event) {

				Notification.show("clean button");
				center.removeComponent(hpanel);
				Update updateClean = new Update();
				updateClean.cleanAll();
				
				
			}
		});

		MenuBar barMenu = createMenu();

		left.setWidth("250px");
		CreateBundleDetails bdetails = new CreateBundleDetails();

		final Tree myTree = bdetails.getTree(); // this should return all bundle, cq and rule info from database

		myTree.addValueChangeListener(new ValueChangeListener() {
			@Override
			public void valueChange(ValueChangeEvent event) {

				if(event.getProperty().getValue() != null){		
					String eventvalue = event.getProperty().getValue().toString();

					Notification.show(eventvalue);

					if (eventvalue.equalsIgnoreCase("Bundle Details")) {
						BundleForm bform = new BundleForm();
						AbsoluteLayout bundleDetailsAll = bform.getAllBundleDetails();
						hpanel.setContent(bundleDetailsAll);
						center.addComponent(hpanel);
						 
					 } else if(eventvalue.equalsIgnoreCase("ruleSample")){

//						 RuleForm rf = new RuleForm();
//						 AbsoluteLayout abl = rf.getRuleDetails();
//						 hpanel.setContent(abl);
//						 center.addComponent(hpanel);

					 } else {
						 boolean isBundleExists = query.getBundlesStatus(eventvalue);
					
						 if(isBundleExists){
							 System.out.println("inside bundleExists....");
								BundleForm bundle = new BundleForm();
								AbsoluteLayout bundleStatus = bundle.showBundleStatus(eventvalue);
								hpanel.setContent(bundleStatus);
								center.addComponent(hpanel);
						 } else {
							 System.out.println("no bundle exists.............. from Myvaadin tree listner..."+eventvalue);
						 }
						 
						boolean isCQexists = query.getCQstatus(eventvalue);

						if(isCQexists){
							System.out.println("inside cqExists....");
							CQDetails cq = new CQDetails();
							AbsoluteLayout cqStatus = cq.getRuleStatus(eventvalue);
							hpanel.setContent(cqStatus);
							center.addComponent(hpanel);
						} else {
							System.out.println("no cqNot exists.............. from Myvaadin tree listner..."+eventvalue);
						}

						boolean isRuleExists = query.getRuleStatus(eventvalue);
						if(isRuleExists){
							System.out.println("inside ruleExists....");
							center.removeComponent(hpanel);
							 RuleForm rf = new RuleForm();
							 AbsoluteLayout abl = rf.getRuleDetails();
							 hpanel.setContent(abl);
							 center.addComponent(hpanel);
						} else {
							System.out.println("no rule exists.............. from Myvaadin tree listner..."+eventvalue);
						}


					 }

											
				}}
});
		left.addComponent(myTree);
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

//		VerticalLayout hlHeader = new VerticalLayout();
//		Label header = new Label("<html><h1><b>Tracker Management \n</b></h1></html>", ContentMode.HTML);
//		Button loginName = new Button("Log In");
//		hlHeader.addComponent(header, 0);
//		hlHeader.addComponent(loginName, 1);

		VerticalLayout root = new VerticalLayout();
		root.setMargin(true);
		root.setSizeFull();
		
		HorizontalLayout titleBar = new HorizontalLayout();
		titleBar.setWidth("100%");
		root.addComponent(titleBar);

		Label title = new Label("<html><h1><b><center>Tracker Management</center>\n</b></h1></html>", ContentMode.HTML);
		titleBar.addComponent(title);
//		root.addComponent(new Label("test"));
	
//		root.addComponent(loginName);
//		titleBar.addComponent(user);
//		titleBar.setExpandRatio(user, 1.0f);
		
		
		VerticalSplitPanel vspanel = new VerticalSplitPanel(titleBar, hsplit);
		vspanel.setSplitPosition(20);
		
		setContent(vspanel);
		
	}

	private MenuBar createMenu() {

		MenuBar.Command mycommand = createCommand();
		MenuBar barMenu = new MenuBar();
		// MenuItem menuItem = new MenuItem("myMenu", null, null);
		// A top-level menu item that opens a submenu
		MenuItem home = barMenu.addItem("My DashBoard", mycommand);
		
		final MenuItem create = barMenu.addItem("Create", null, null);
		create.addItem("Create Bundle", null, mycommand);
		create.addItem("Create CQ", null, mycommand);
		create.addItem("Create Rule Group", null, mycommand);
		create.addItem("Create Regression Test", null, mycommand);
		create.addItem("Create Incident", null, mycommand);

		// Another top-level item
		MenuItem modify = barMenu.addItem("Modify", null, null);
		modify.addSeparator();
//		modify.addItem("Bundle", null, mycommand);
		
		MenuItem editfunction = modify.addItem("Bundle", null);
		editfunction.addItem("Target Dt", mycommand);
		editfunction.addItem("Percent", mycommand);
		modify.addItem("CQ", null, mycommand);
		modify.addItem("Rules", null, mycommand);

		MenuItem update = barMenu.addItem("Update", null, null);
		update.addSeparator();
		update.addItem("Update Bundle", mycommand);
		update.addItem("Update CQ", mycommand);
		update.addItem("Update Rule", mycommand);
		update.addItem("Update Rule Review", mycommand);
		update.addItem("Update Regression Test", mycommand);
		update.addItem("Update Individual xxx", mycommand);
		
		MenuItem test = barMenu.addItem("Test", null, null);
		test.addSeparator();
		test.addItem("Unit Test", null, mycommand);
		test.addItem("Scenario Test", null, mycommand);
		test.addItem("Regression Test", null, mycommand);

		
		

		MenuItem signUp =barMenu.addItem("Sign-Up", null, null);
		signUp.addSeparator();
		signUp.addItem("Bundle Lead", mycommand);
		signUp.addItem("Design Updater", mycommand);
		signUp.addItem("Design Reviewer", mycommand);
		signUp.addItem("Rule Creator", mycommand);
		signUp.addItem("Rule Tester", mycommand);
		signUp.addItem("Rule Reviewer", mycommand);
		signUp.addItem("Scenario Tester", mycommand);
		signUp.addItem("Test Reviewer", mycommand);
		signUp.addItem("Over All Reviewer", mycommand);
		signUp.addItem("Regression Tester", mycommand);
		
		MenuItem reports = barMenu.addItem("Reports", null, null);
		reports.addItem("Progress Status", mycommand);
		reports.addItem("Capacity Utilization", mycommand);
		reports.addItem("SignUp Status", mycommand);
		
		
		// Yet another top-level item
		MenuItem help = barMenu.addItem("Help", null, null);
		help.addSeparator();
		help.addItem("Creating Rules", null, mycommand);
		help.addItem("Testing", null, mycommand);
		help.addItem("Rule Review", null, mycommand);
					
		
		
		return barMenu;

	}

	private Command createCommand() {

		MenuBar.Command mycommand = new MenuBar.Command() {
			public void menuSelected(MenuItem selectedItem) {

				
				Button clearButton = new Button("Clear");
				VerticalLayout vlayoutforForm = new VerticalLayout();
				// FormLayout cqForm = null;
				String selected = selectedItem.getText().toString();

				if (selected.equalsIgnoreCase("Create Bundle")) {
					BundleForm bf = new BundleForm();
					FormLayout flayout = bf.createForm();
					Button addButton = new Button("Add");
					flayout.addComponent(clearButton);
					
					vlayoutforForm.addComponent(flayout);
					hpanel.setContent(vlayoutforForm);
					center.addComponent(hpanel);
				} else if (selected.equalsIgnoreCase("Create CQ")) {
					CQDetails cqd = new CQDetails();
					FormLayout cqForm = cqd.createForm();
					hpanel.setContent(cqForm);
					center.addComponent(hpanel);
				} else if (selected.equalsIgnoreCase("Create Rule Group")) {
					RuleForm rform = new RuleForm();
					FormLayout ruleForm = rform.createForm();
					hpanel.setContent(ruleForm);
					center.addComponent(hpanel);
				} else if (selected.equalsIgnoreCase("Create Regression Test")) {
					RegressionForm regressionCreate = new RegressionForm();
					FormLayout regressinForm = regressionCreate.createForm();
					hpanel.setContent(regressinForm);
					center.addComponent(hpanel);
				} else if (selected.equalsIgnoreCase("Bundle")) {
					
					BundleForm bf = new BundleForm();
					FormLayout flayout = bf.editBundle();
//					flayout.addComponent(clearButton);
					vlayoutforForm.addComponent(flayout);
					hpanel.setContent(vlayoutforForm);
					center.addComponent(hpanel);
				} else if (selected.equalsIgnoreCase("CQ")) {
					Notification.show("Edit ClearQuest");
				
					CQDetails cqd = new CQDetails();	
					FormLayout gl = cqd.editCQForm();
					gl.setSizeFull();
//					gl.addComponent(clearButton, 2, 1);
//					gl.setComponentAlignment(clearButton,
//							Alignment.MIDDLE_RIGHT);
					hpanel.setContent(gl);
					center.addComponent(hpanel);

					
				} else if (selected.equalsIgnoreCase("Rules")) {
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
					
				}else if (selected.equalsIgnoreCase("Update Rule")){

					RuleForm rule = new RuleForm();
					FormLayout updatedRule = rule.updateRuleGroup();
					hpanel.setContent(updatedRule);
					center.addComponent(hpanel);
				}else if (selected.equalsIgnoreCase("Update Rule Review")){

					RuleForm ruleForm = new RuleForm();
					FormLayout ruleUpdateData = ruleForm.getRuleReviewUpdate();
					hpanel.setContent(ruleUpdateData);
					center.addComponent(hpanel);
	
				} else if (selected.equalsIgnoreCase("Update Regression Test")){
					
					RegressionForm regressionForm = new RegressionForm();
					FormLayout updateRegression = regressionForm.updateRegression();
					hpanel.setContent(updateRegression);
					center.addComponent(hpanel);
					
				}else if (selected.equalsIgnoreCase("Update Individual xxx")){

					RuleForm rule = new RuleForm();
					FormLayout updatedRule = rule.update();
					hpanel.setContent(updatedRule);
					center.addComponent(hpanel);
					
				}else if (selected.equalsIgnoreCase("Bundle Lead")){
					BundleForm bform = new BundleForm();
					FormLayout signupDetails = bform.getSignup(selected);
					hpanel.setContent(signupDetails);
					center.addComponent(hpanel);
					
					
				}else if (selected.equalsIgnoreCase("Design Updater")){
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
				
				}else if (selected.equalsIgnoreCase("Rule Reviewer")){
//					Notification.show(selected);
					RuleForm ruleForm = new RuleForm();
					FormLayout ruleReviewSignup = ruleForm.getSignupRuleReview(selected);
					hpanel.setContent(ruleReviewSignup);
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
				}else if (selected.equalsIgnoreCase("Regression Tester")){
					Notification.show(selected);
					RegressionForm regressionForm = new RegressionForm();
					FormLayout signupRegression = regressionForm.getSignup(selected);
					hpanel.setContent(signupRegression);
					center.addComponent(hpanel);
			} else if(selected.equalsIgnoreCase("SignUp Status")){

				ReportTable signupReport = new ReportTable();
				FormLayout tableStatus = signupReport.getSignupStatus();
				hpanel.setContent(tableStatus);
				center.addComponent(hpanel);
				
			} else if (selected.equalsIgnoreCase("Target Dt")){
				
				BundleForm bundle = new BundleForm();
				FormLayout editbundle = bundle.editBundleTargetDt();
				hpanel.setContent(editbundle);
				center.addComponent(hpanel);
				
			} else if (selected.equalsIgnoreCase("Percent")){
				Notification.show("percent selected");
				
				BundleForm bundle = new BundleForm();
				FormLayout editbundlePercent = bundle.editBundlePercent();
				hpanel.setContent(editbundlePercent);
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

				if(selectedItem.getText().toString().equalsIgnoreCase("My DashBoard")){
					
					UserStatus myStatus = new UserStatus();
					AbsoluteLayout statusTable = myStatus.getStatusTable();
					hpanel.setContent(statusTable);
				}
			}

			
		};
		
		
		
		return mycommand;
	}
}
