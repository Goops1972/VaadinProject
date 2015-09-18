package com.example.myvaadin;

import java.io.Serializable;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import oracle.jdbc.oracore.TDSPatch;

import com.example.User.LogInInfo;
import com.google.gwt.user.client.ui.FlexTable;
import com.vaadin.data.Property.ValueChangeEvent;
import com.vaadin.data.Property.ValueChangeListener;
import com.vaadin.ui.AbsoluteLayout;
import com.vaadin.ui.Alignment;
import com.vaadin.ui.Button;
import com.vaadin.ui.Button.ClickListener;
import com.vaadin.ui.ComboBox;
import com.vaadin.ui.Component;
import com.vaadin.ui.DateField;
import com.vaadin.ui.FormLayout;
import com.vaadin.ui.HorizontalLayout;
import com.vaadin.ui.Label;
import com.vaadin.ui.Notification;
import com.vaadin.ui.Select;
import com.vaadin.ui.Table;
import com.vaadin.ui.TextArea;
import com.vaadin.ui.TextField;
import com.vaadin.ui.Button.ClickEvent;
import com.vaadin.ui.UI;
import com.vaadin.ui.VerticalLayout;

@SuppressWarnings("deprecation")
public class BundleForm implements Serializable{

	private static final long serialVersionUID = 7526472295622776147L;
	
	List<BundlePojo> list ;
	BundlePojo bdata;
	Map<String, String> map = new HashMap<String, String>();
	public BundleForm() {

		// vlayoutforForm.addComponent(flayout);
	}

	public FormLayout createForm() {
		FormLayout flayout = new FormLayout();
		
		final TextField tbox = new TextField("Bundle Name");
		final TextArea desc = new TextArea("Bundle Description");
		desc.setSizeFull();
		desc.setHeight("60px");
flayout.addComponent(tbox);
flayout.addComponent(desc);

		HorizontalLayout hl1 = new HorizontalLayout();
		DateField designUpdate = new DateField("Design Update Dt.");
		DateField designReview = new DateField("Design Review Dt.");
		DateField ruleCreate = new DateField("Rule Create Dt.");
		DateField ruleTestdate = new DateField("Rule Test Dt.");
		hl1.addComponent(designUpdate);
		hl1.addComponent(designReview);
		hl1.addComponent(ruleCreate);
		hl1.addComponent(ruleTestdate);
		hl1.setSpacing(true);
flayout.addComponent(hl1);
	
		HorizontalLayout hl2 = new HorizontalLayout();
		DateField scenarioData = new DateField("Scenarion Data Setup Dt");
		DateField scenarioTestDt = new DateField("Scenario Test Dt.");
		DateField testReviewDt = new DateField("TestReview Dt.");
		DateField overallReviewDt = new DateField("Overall Review Dt.");
		hl2.addComponent(scenarioData);
		hl2.addComponent(scenarioTestDt);
		hl2.addComponent(testReviewDt);
		hl2.addComponent(overallReviewDt);
		hl2.setSpacing(true);
flayout.addComponent(hl2);

		HorizontalLayout hl3 = new HorizontalLayout();
		TextField bundleTimeframe = new TextField("Bundle Period in days");
		bundleTimeframe.setWidth("80px");
		TextField workForceAvaiable = new TextField("Work Force Available");
		workForceAvaiable.setWidth("80px");
		TextField workDaysAvailable = new TextField("Hours Available");
		workDaysAvailable.setWidth("80px");
		TextField onRelease = new TextField("On-Release ? (Y/N)");
		onRelease.setWidth("80px");
		TextField sizeCal = new TextField("Size Calculation");
		sizeCal.setWidth("80px");
		hl3.addComponent(bundleTimeframe);
		hl3.addComponent(workForceAvaiable);
		hl3.addComponent(workDaysAvailable);
		hl3.addComponent(onRelease);
		hl3.addComponent(sizeCal);
		hl3.setSpacing(true);
flayout.addComponent(hl3);

		HorizontalLayout hl4 = new HorizontalLayout();
		Button clear = new Button("Clear");
		Button add = new Button("Add");
		hl4.addComponent(clear);
		hl4.addComponent(add);
hl4.setExpandRatio(clear, 50);
hl4.setExpandRatio(add, 50);
		hl4.setSpacing(true);
flayout.addComponent(hl4);

		return flayout;
	}

	public FormLayout editBundle() {
		final FormLayout flayout = new FormLayout();
		HorizontalLayout hl = new HorizontalLayout();
		ComboBox bundleList = new ComboBox();
		bundleList.addItem("Bundle1");
		bundleList.addItem("Bundle2");
		bundleList.addItem("Bundle3");
		bundleList.addItem("Bundle4");
hl.addComponent(bundleList);		
		ComboBox editableList = new ComboBox();
		editableList.addItem("Bundle Name");
		editableList.addItem("Bundle Description");
		editableList.addItem("Design Update Dt.");
		editableList.addItem("Design Review Dt.");
		editableList.addItem("Rule Create Dt.");
		editableList.addItem("Rule Test Dt.");
		editableList.addItem("Scenarion Data Setup Dt");
		editableList.addItem("Scenario Test Dt.");
		editableList.addItem("TestReview Dt.");
		editableList.addItem("Overall Review Dt.");
		editableList.addItem("Bundle Period in days");
		editableList.addItem("Work Force Available");
		editableList.addItem("Hours Available");
		editableList.addItem("On-Release");
		editableList.addItem("Size Calculation");
hl.addComponent(editableList);
hl.setSpacing(true);

flayout.addComponent(hl);

editableList.addValueChangeListener(new ValueChangeListener() {
	
	@Override
	public void valueChange(ValueChangeEvent event) {

		HorizontalLayout hl1 = new HorizontalLayout();
		TextField existing = getExistingData();
		Button save = new Button("Save");
		
save.addClickListener(new ClickListener() {
	
	@Override
	public void buttonClick(ClickEvent event) {

		Notification.show("Saving into Database");
	}
});		
		hl1.addComponent(existing);
		hl1.addComponent(save);
		hl1.setSpacing(true);
		
		
		flayout.addComponent(hl1);
	}

	private TextField getExistingData() {

		TextField value = new TextField();
		String newValue = "fetched from Database";
		value.setValue(newValue);
		return value;
	}
});
		return flayout;
	}

	public AbsoluteLayout getDetails() {

		FormLayout flayout = new FormLayout();
		Label l = new Label("Bundle Infomation");
		final TextField tbox = new TextField("Bundle Name");
		final TextField desc = new TextField("Bundle Description");
		Button but = new Button("Enter");

		desc.setWidth("580px");
		flayout.addComponent(l);
		flayout.addComponent(tbox);
		flayout.addComponent(desc);
		flayout.addComponent(but);

		AbsoluteLayout ablayout = new AbsoluteLayout();
		ablayout.addComponent(flayout);
		return ablayout;
	}
	
	public FormLayout upDate(){
		

		ComboBox bundles = new ComboBox("Select Bundle");
		bundles.addItem("Io");
		bundles.addItem("Europa");
		bundles.addItem("Ganymedes");
		bundles.addItem("Callisto");
		
		FormLayout updatedBundle = new FormLayout();
		
		DateField scenarioDataLoad = new DateField("Scenario Data Load Dt");
		Button update = new Button("Update");
		updatedBundle.addComponent(bundles);
		updatedBundle.addComponent(scenarioDataLoad);
		updatedBundle.addComponent(update);
		
		return updatedBundle;
		
	}

	public FormLayout getSignup(String role) {

		FormLayout bundleSignup = new FormLayout();
		
		
		LogInInfo logger = new LogInInfo();
		String userName = logger.setUserName("Gopu");
		
		TextField user = new TextField();
		user.setValue(userName);
		user.setReadOnly(true);

		HorizontalLayout hl = new HorizontalLayout();
		
		ComboBox bundles = new ComboBox("Select Bundle");
		bundles.addItem("bundle1");
		bundles.addItem("bundle2");
		bundles.addItem("bundle3");
		
	
	TextField roleSigned = new TextField("Role As");
	roleSigned.setValue(role);
	roleSigned.setReadOnly(true);
	
	DateField signupDate = new DateField();
	
	Button add = new Button("Sign-up");
	hl.addComponent(bundles, 0);	
	hl.addComponent(roleSigned, 1);	
	hl.setSpacing(true);
	
	HorizontalLayout hl2 = new HorizontalLayout();
	hl2.addComponent(user);
	hl2.addComponent(signupDate);
	hl2.addComponent(add);
	hl2.setSpacing(true);

	VerticalLayout vl = new VerticalLayout();
	vl.addComponent(hl);
	vl.addComponent(hl2);
	vl.setSpacing(true);
	
	bundleSignup.addComponent(vl);
		return bundleSignup;
	
	}
}
