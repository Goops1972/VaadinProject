package com.example.myvaadin;

import java.io.Serializable;

import com.example.User.LogInInfo;
import com.vaadin.data.Property.ValueChangeEvent;
import com.vaadin.data.Property.ValueChangeListener;
import com.vaadin.data.util.BeanItemContainer;
import com.vaadin.ui.AbsoluteLayout;
import com.vaadin.ui.Alignment;
import com.vaadin.ui.Button;
import com.vaadin.ui.ComboBox;
import com.vaadin.ui.DateField;
import com.vaadin.ui.Form;
import com.vaadin.ui.FormLayout;
import com.vaadin.ui.GridLayout;
import com.vaadin.ui.HorizontalLayout;
import com.vaadin.ui.Label;
import com.vaadin.ui.Notification;
import com.vaadin.ui.Select;
import com.vaadin.ui.TextArea;
import com.vaadin.ui.TextField;
import com.vaadin.ui.VerticalLayout;
import com.vaadin.ui.Button.ClickEvent;
import com.vaadin.ui.Button.ClickListener;

public class CQDetails implements Serializable {

	String bName;
	String cqName;
	String cqDetails;

	public CQDetails() {

	}

	public CQDetails(String bname) {
		this.bName = bname;
	}

	public FormLayout createForm() {
		FormLayout cqform = new FormLayout();
		ComboBox bundleList = new ComboBox("Select Bundle");
		bundleList.addItem("bundle 1");
		bundleList.addItem("bundle 2");
		bundleList.addItem("bundle 3");
		bundleList.addItem("bundle 4");
cqform.addComponent(bundleList);		
		
		final TextField tbox = new TextField("CQ Name");
		tbox.setWidth("200");
		final TextField desc = new TextField("CQ Description");
		desc.setWidth("300px");
		final TextArea requirements = new TextArea("CQ Requirements");
		requirements.setWidth("550px");
		requirements.setHeight("80px");
cqform.addComponent(tbox);
cqform.addComponent(desc);
cqform.addComponent(requirements);

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
cqform.addComponent(hl1);
	
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
cqform.addComponent(hl2);

		HorizontalLayout hl3 = new HorizontalLayout();
		ComboBox cqsize = new ComboBox("CQ Size");
		cqsize.addItem("Small");
		cqsize.addItem("Medium");
		cqsize.addItem("Large");
		cqsize.addItem("XLarge");
		TextField migration = new TextField("Migration Required ?");
		migration.setInputPrompt("Y/N");
		migration.setWidth("80px");
		
		hl3.addComponent(cqsize);
		hl3.addComponent(migration);
		hl3.setSpacing(true);
cqform.addComponent(hl3);

		HorizontalLayout hl4 = new HorizontalLayout();
		Button clear = new Button("Clear");
		Button add = new Button("Add");
		hl4.addComponent(clear);
		hl4.addComponent(add);
		hl4.setSpacing(true);
cqform.addComponent(hl4);

		return cqform;
	
		
		
	}

	public String getbName() {
		return bName;
	}

	public void setbName(String bName) {
		this.bName = bName;
	}

	public String getCqName() {
		return cqName;
	}

	public void setCqName(String cqName) {
		this.cqName = cqName;
	}

	public String getCqDetails() {
		return cqDetails;
	}

	public void setCqDetails(String cqDetails) {
		this.cqDetails = cqDetails;
	}

	public GridLayout gLayout() {
		final GridLayout grid = new GridLayout(3, 3);
		// grid.setWidth(400, Sizeable.UNITS_PIXELS);
		// grid.setHeight(200, Sizeable.UNITS_PIXELS);

		// Button topleft = new Button("Top Left");
		TextField cqName = new TextField("Enter CQ Name");
		grid.addComponent(cqName, 0, 0); 
		grid.setComponentAlignment(cqName, Alignment.TOP_LEFT);

		// Button topcenter = new Button("Top Center");
		TextArea description = new TextArea("Enter Details");
		description.setWidth("400px");
		grid.addComponent(description, 0, 1); //1st col, 2nd row
//		grid.setComponentAlignment(description, Alignment.TOP_CENTER);
		grid.setComponentAlignment(description, Alignment.MIDDLE_LEFT);
		
		Button topright = new Button("Top Right");
		Select select = new Select("Select Bundle");
		select.addItem("aaaa");
		select.addItem("aaaabbbb");
		select.addItem("aaaacccc");

		grid.addComponent(select, 2, 0); //3cols 1st row
		grid.setComponentAlignment(select, Alignment.TOP_RIGHT);

		TextField size = new TextField("CQ Size");
		grid.addComponent(size, 1, 0);
		grid.setComponentAlignment(size, Alignment.TOP_CENTER);
		
		Button middleright = new Button("Add");
		grid.addComponent(middleright, 0, 2); //1st col, 2nd row
		grid.setComponentAlignment(middleright, Alignment.MIDDLE_LEFT);
		return grid;
	}

	public VerticalLayout createV() {

		VerticalLayout vertical = new VerticalLayout();
		vertical.setSizeUndefined();

		Select selectOption = new Select("Select Bundle");
		selectOption.addItem("aaa");
		selectOption.addItem("bbb");
		selectOption.addItem("cccc");

		// vertical.addComponent(selectOption);
		HorizontalLayout hl = new HorizontalLayout();
		HorizontalLayout hl1 = new HorizontalLayout();
		Button butt = new Button("Add");
		hl.setSizeFull();
		hl.setSpacing(true);
		hl1.setSizeFull();
		hl1.setSpacing(true);

		TextField cqname = new TextField("Enter CQ Name");
		TextArea tarea = new TextArea("Enter Details");
		tarea.setWidth("600px");
		hl.addComponentAsFirst(cqname);
		hl.addComponent(selectOption);
		hl1.addComponentAsFirst(tarea);
		hl1.addComponent(butt, 1);
		vertical.addComponent(hl);
		vertical.addComponent(hl1);

		// hl.addComponent(butt);
		// vertical.addComponent(butt);

		return vertical;

	}

	public FormLayout editCQForm() {
		
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
		editableList.addItem("CQ Name");
		editableList.addItem("CQ Description");
		editableList.addItem("CQ Requirements");
		editableList.addItem("Design Update Dt.");
		editableList.addItem("Design Review Dt.");
		editableList.addItem("Rule Create Dt.");
		editableList.addItem("Rule Test Dt.");
		editableList.addItem("Scenarion Data Setup Dt");
		editableList.addItem("Scenario Test Dt.");
		editableList.addItem("TestReview Dt.");
		editableList.addItem("Overall Review Dt.");
		editableList.addItem("CQ Size");
		editableList.addItem("Migration Requirement");
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

	public AbsoluteLayout getInfo() {
		AbsoluteLayout abl = new AbsoluteLayout();

		GridLayout grid = new GridLayout(4, 4);
		grid.setSizeFull();
		//cq number, main reason, business case, changes col0-row1-2-3
		//req lead, rule lead col3, row0
		//file attached, cq size =large small col3,row3
		
		TextField cqNumber = new TextField("CQ Number");
		TextField bundleName = new TextField("Bundle Name");
		TextArea cqMain = new TextArea("Main purpose");
		TextArea cqBusinesscase = new TextArea("Business Case");
		cqBusinesscase.setWidth("60%");
		TextArea cqreq = new TextArea("Requirements");
		cqreq.setWidth("60%");
		TextField rlead = new TextField("Requirement Lead");
		TextField llead = new TextField("Rule Lead");
		TextArea fileAttached = new TextArea("Attached File info");
		TextField cqSize = new TextField("Size");
		//comp, col, row
		grid.addComponent(cqNumber, 0, 0);
		grid.addComponent(bundleName, 2, 0);
		grid.addComponent(cqMain, 0, 1);
		grid.addComponent(cqBusinesscase, 0, 2);
		grid.addComponent(cqreq, 0, 3);
		grid.addComponent(rlead, 3, 0);
		grid.addComponent(llead, 3, 1);
		grid.addComponent(fileAttached, 3, 2);
		grid.addComponent(cqSize, 3, 3);
		abl.addComponent(grid);
		
		return abl;
	}


	public FormLayout update() {

		FormLayout updatedCQ = new FormLayout();
		ComboBox user = new ComboBox("Select User Name");
		user.addItem("Mr. A");
		user.addItem("Mr. B");
		user.addItem("Ms C");
		user.addItem("Ms D");
		
		ComboBox bundles = new ComboBox("Select Bundle");
		bundles.addItem("bundle1");
		bundles.addItem("bundle2");
		bundles.addItem("bundle3");
		
		ComboBox cqs = new ComboBox("Select CQs");
		cqs.addItem("cq1");
		cqs.addItem("cq2");
		cqs.addItem("cq3");
		
		ComboBox role = new ComboBox("Choose Role");
		role.addItem("Design Updated");
		role.addItem("Design Reviewer");
		role.addItem("Rule Creator");
		role.addItem("Rule Tester");
		role.addItem("Scenario Tester");
		role.addItem("Test Reviewer");
		role.addItem("Over All Reviewer");
		
		DateField updatedDt = new DateField();
		Button update = new Button("Update");
		Button clear = new Button("Clear");
		HorizontalLayout hl = new HorizontalLayout();
		hl.addComponent(clear);
		hl.addComponent(update);
		hl.setSpacing(true);
		
		updatedCQ.addComponent(user);
		updatedCQ.addComponent(bundles);
		updatedCQ.addComponent(cqs);
		updatedCQ.addComponent(role);
		updatedCQ.addComponent(updatedDt);
		updatedCQ.addComponent(hl);
		
		return updatedCQ;
	}

	public FormLayout getSignup(String role) {

		if(role.equalsIgnoreCase("Rule Lead")){
			role = "Rule Lead";
		} else if (role.equalsIgnoreCase("Design Reviewer")){
			role = "Design Reviewer";
		} else if (role.equalsIgnoreCase("Scenario Tester")){
			role = "Scenario Tester";
		} else if (role.equalsIgnoreCase("Test Reviewer")){
			role = "Test Reviewer";
		} else {
			role = "Over All Reviewer";
	}	
		FormLayout cqSignup = new FormLayout();
		
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
	
	cqSignup.addComponent(vl);
	
		return cqSignup;
	
	
	}

}
