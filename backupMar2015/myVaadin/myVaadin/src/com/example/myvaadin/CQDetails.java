package com.example.myvaadin;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import java.util.Iterator;
import java.util.List;

import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.support.rowset.SqlRowSet;

import com.example.User.LogInInfo;
import com.example.database.Insert;
import com.example.database.Query;
import com.example.database.Template;
import com.example.database.Update;
import com.example.pojo.RoleSegregration;
import com.example.pojo.RuleGroupStatus;
import com.example.service.RoleSegregationLogic;
import com.vaadin.data.Item;
import com.vaadin.data.Property.ValueChangeEvent;
import com.vaadin.data.Property.ValueChangeListener;
import com.vaadin.data.util.BeanItemContainer;
import com.vaadin.event.ItemClickEvent;
import com.vaadin.event.ItemClickEvent.ItemClickListener;
import com.vaadin.server.Page;
import com.vaadin.server.VaadinSession;
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
import com.vaadin.ui.Table;
import com.vaadin.ui.TextArea;
import com.vaadin.ui.TextField;
import com.vaadin.ui.VerticalLayout;
import com.vaadin.ui.Button.ClickEvent;
import com.vaadin.ui.Button.ClickListener;

public class CQDetails implements Serializable {

	JdbcTemplate jt = Template.getTemplate();
	Query query = new Query();
	Update updateInto = new Update();
	String userName = (String) VaadinSession.getCurrent().getAttribute("userName");
	RuleGroupStatus rgStatus = new RuleGroupStatus();	
	String bundle;
	String cq;
	String cqDetails;

	public CQDetails() {

	}

	public CQDetails(String bname) {
		this.bundle = bname;
	}

	public FormLayout createForm() {
		FormLayout cqform = new FormLayout();

		Query queryBundle = new Query();
		List<String> blist = queryBundle.getBundles();
		
		
		final ComboBox bundleList = new ComboBox("Select Bundle");

		for (String bundle : blist) {
			bundleList.addItem(bundle);
			cqform.addComponent(bundleList);
		}
		
		final TextField cqfieldName = new TextField("CQ Name");
		cqfieldName.setWidth("200");
		final TextField cqdesc = new TextField("CQ Description");
		cqdesc.setWidth("300px");
		final TextArea requirements = new TextArea("CQ Requirements");
		requirements.setWidth("550px");
		requirements.setHeight("80px");
cqform.addComponent(cqfieldName);
cqform.addComponent(cqdesc);
cqform.addComponent(requirements);


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

//adding into database
add.addClickListener(new ClickListener() {
	
	@Override
	public void buttonClick(ClickEvent event) {

		try {
			String cqname = cqfieldName.getValue().toString();
			String cqDescData = cqdesc.getValue().toString();
			String bundleName = bundleList.getValue().toString();
			
			SqlRowSet bundleTargetDt = jt.queryForRowSet("select design_update_dt, design_rvw_dt,  rule_create_dt, rule_test_dt, scenario_test_dt,"
					+ " test_rvw_dt, overall_rvw_dt, rule_rvw_dt from bundle where b_name ='"+bundleName+"'");
			
			while(bundleTargetDt.next()){
				java.sql.Date designUpDt = bundleTargetDt.getDate(1);
				java.sql.Date designReviewDt = bundleTargetDt.getDate(2);
				java.sql.Date ruleCreateDt = bundleTargetDt.getDate(3);
				java.sql.Date ruleTestDt = bundleTargetDt.getDate(4);
				java.sql.Date scenarioTestDt = bundleTargetDt.getDate(5);
				java.sql.Date testReviewDt = bundleTargetDt.getDate(6);
				java.sql.Date overAllReviewDt = bundleTargetDt.getDate(7);
				java.sql.Date ruleReviewDt = bundleTargetDt.getDate(8);
				
				
				System.out.println("............insert into cq (cqname, cqdesc, bundleName, designUpTarget, designRvwTarget, ruleCreateTarget, ruleTestTarget, ScenarioTarget, testRvwTarget, oaTarget, stage) "
						+ "values ('"+cqname+"','"+cqDescData+"','"+bundleName+"','"+designUpDt+"','"+designReviewDt+"','"+ruleCreateDt+"','"+ruleTestDt+"','"+scenarioTestDt+"','"+testReviewDt+"','"+overAllReviewDt+"',"
						+ "', '"+ruleReviewDt+"', 'Created')");

				jt.execute("insert into cq (cqname, cqdesc, bundleName, designUpTarget, designRvwTarget, ruleCreateTarget, ruleTestTarget, ScenarioTarget, testRvwTarget, oaTarget, ruleReviewTarget, stage) "
						+ "values ('"+cqname+"','"+cqDescData+"','"+bundleName+"','"+designUpDt+"','"+designReviewDt+"','"+ruleCreateDt+"','"+ruleTestDt+"','"+scenarioTestDt+"','"+testReviewDt+"','"+overAllReviewDt+"', '"+ruleReviewDt+"', 'Created')");

				jt.execute("update cq set designUpdater='-', designReviewer='-', scenarioTester='-', testReviewer='-', overAllReviewer='-' where bundleName='"+bundleName+"' and cqname='"+cqname+"'");
				
				jt.execute("update statusdata set cqname ='cqname' where bname ='"+bundleName+"'");
				
			}
			System.out.println(cqname+" "+cqDescData+" "+bundleName);
//			jt.execute("insert into cq (cqname, cqdesc, bundleName) values ('"+cqname+"','"+cqDescData+"','"+bundleName+"')");
			
			try {
			
				jt.update("update statusdata set cqname ='"+cqname+"' where bname ='"+bundleName+"'");
				//update statusdata set cqname = 'mytrial' where bname ='StatusBundle1'
				Notification.show("insert successfull!!!!!");
			} catch (Exception e) {
				// TODO: handle exception
				Notification.show("failed...to update cqData in bundle.");
				
				e.printStackTrace();
			}
			
			
			Notification.show("insert successfull!!!!!");
			cqfieldName.setValue("");
			cqdesc.setValue("");
			bundleList.setValue("");
			
		} catch (Exception  e) {
			e.printStackTrace();
			Notification.show("entry failed.....");
		}
		
		
	}
});

		return cqform;
	
		
		
	}

	public String getbName() {
		return bundle;
	}

	public void setbName(String bName) {
		this.bundle = bName;
	}

	public String getCqName() {
		return cq;
	}

	public void setCqName(String cqName) {
		this.cq = cqName;
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
		final ComboBox user = new ComboBox("Select User Name");
		user.setValue(userName);
		user.addItem(userName);
		
		final ComboBox bundles = new ComboBox("Select Bundle");
		
		List<String> bundleList = query.getBundles();
		for (String bundle : bundleList) {
			bundles.addItem(bundle);
		}

		final ComboBox cqs = new ComboBox("Select CQs");
		bundles.addValueChangeListener(new ValueChangeListener() {
	
	@Override
	public void valueChange(ValueChangeEvent event) {

		String selectedBundle = event.getProperty().getValue().toString();
		List<String> cqQuery = query.getCQlist(selectedBundle);
		for (String cq : cqQuery) {
			cqs.addItem(cq);			
		}
		
	}
});		
		
		final ComboBox role = new ComboBox("Choose Role");
		role.addItem("Design Updater");
		role.addItem("Design Reviewer");
//		role.addItem("Rule Creator");
		role.addItem("DataLoad Scenario");
		role.addItem("Scenario Tester");
		role.addItem("Test Reviewer");
		role.addItem("Over All Reviewer");
		
		final TextField hours = new TextField("Actual Hours ");
		hours.setWidth("40px");
		final DateField updatedDt = new DateField("Updating Date");
		updatedDt.setValue(new Date());
		Date inputDt = updatedDt.getValue();
//		final java.sql.Date updatemyDt = DateConversion.getSqlDate(inputDt.getTime());

		
		Button updateButton = new Button("Update");
		updateButton.addClickListener(new ClickListener() {
			
			@Override
			public void buttonClick(ClickEvent event) {
				updateInto.updateCQ(bundles.getValue().toString(), cqs.getValue().toString(), role.getValue().toString(), 
						user.getValue().toString(), updatedDt, hours.getValue());
			}
		});		

		Button clear = new Button("Clear");
		HorizontalLayout hl = new HorizontalLayout();
		hl.addComponent(clear);
		hl.addComponent(updateButton);
		hl.setSpacing(true);
		
		updatedCQ.addComponent(user);
		updatedCQ.addComponent(bundles);
		updatedCQ.addComponent(cqs);
		updatedCQ.addComponent(role);
		updatedCQ.addComponent(hours);
		updatedCQ.addComponent(updatedDt);
		updatedCQ.addComponent(hl);
		return updatedCQ;
		
	}

	public FormLayout getSignup(final String role) {
		final ComboBox cqName = new ComboBox();
		final FormLayout cqSignup = new FormLayout();

		final ComboBox user = new ComboBox("User Name");
		user.setInputPrompt(userName);
		user.addItem(userName);
		
//		boolean isConform = query.checkConfirmity(userName,role);
				
		final HorizontalLayout hl = new HorizontalLayout();
		
		final ComboBox bundleName = new ComboBox();
		bundleName.setInputPrompt("Select Bundle");
		
		
		List<String> bundleList = query.getBundles();
		for (String bundle : bundleList) {
			bundleName.addItem(bundle);
			
		}
		hl.addComponent(bundleName);
		
		cqSignup.addComponent(hl);
		
		bundleName.addValueChangeListener(new ValueChangeListener() {
			
			@Override
			public void valueChange(ValueChangeEvent event) {
				
				hl.removeComponent(cqName);
				String selectedBundle = event.getProperty().toString();
				cqName.setInputPrompt("- Select CQ -");
				String bundle = bundleName.getValue().toString();
				
				if(!bundle.isEmpty()){
					List<String> cqList = query.getCQlist(bundle);

					cqName.removeAllItems();
					for (String cq : cqList) {
						
						cqName.addItem(cq);
					}
					hl.addComponent(cqName);
	
				}
				
							}
		});

		Button signUpbutton = new Button("Sign Up");
		final TextField roleSigned = new TextField("As");
		
		roleSigned.setValue(role);
	roleSigned.setReadOnly(true);
	hl.addComponent(roleSigned);	
	hl.setSpacing(true);

		
	final HorizontalLayout hl2 = new HorizontalLayout();
	final DateField signupDate = new DateField("Select Date");
	signupDate.setValue(new Date());
	hl2.addComponent(user);
	hl2.addComponent(roleSigned);
	hl2.addComponent(signupDate);
	hl2.addComponent(signUpbutton);
	hl2.setSpacing(true);
	
	final VerticalLayout vl = new VerticalLayout();
	final RoleSegregationLogic logicClass = new RoleSegregationLogic();
	
	cqName.addValueChangeListener(new ValueChangeListener() {
		
		@Override
		public void valueChange(ValueChangeEvent event) {

			String cqSelected = event.getProperty().getValue().toString();
			
			RoleSegregration roleSignedup = logicClass.checkConfirmity(userName, role, cqSelected);

			System.out.println("Is Eligible : "+roleSignedup.isEligible());
			
			if(roleSignedup.isEligible()){
				Notification.show("is Eligible..........");
				vl.addComponent(hl);
				vl.addComponent(hl2);
				vl.setSpacing(true);

				System.out.println("is Eligible........");
			} else {
			
				Notification.show("Nope !!!!! ");

				Table signedUpRoles = logicClass.getTable(userName, role, roleSignedup);
				
				VerticalLayout vlforStatus = new VerticalLayout();
				vlforStatus.setSpacing(true);
				
				Label status = new Label("You can not sign up as ' "+role+" ', because you had signed Up as following Roles :");
				vlforStatus.addComponent(status);
				vlforStatus.addComponent(signedUpRoles);
				cqSignup.addComponent(vlforStatus);
				
			}

			cqSignup.addComponent(vl);

		}
	});

	

	
	signUpbutton.addClickListener(new ClickListener() {
		
		@Override
		public void buttonClick(ClickEvent event) {
			Insert insertDao = new Insert();
			try {
				String bname = bundleName.getValue().toString();
				String cqname = cqName.getValue().toString();
				
//				String cqname = "cqName.getValue().toString()";
				String signedRole = roleSigned.getValue().toString();
//				String userName = user.getValue().toString();
				Date dateInput = signupDate.getValue();
				java.sql.Date signUpdt = DateConversion.getSqlDate(dateInput.getTime());

				System.out.println(bname+"  "+cqname+"  "+ signedRole+"  "+ userName+"  "+signUpdt+ "from CQDetails methods....");
				updateInto.signupCQ(bname, cqname, signedRole, userName, signUpdt);
				updateInto.updateBundleStatus(bname, signedRole, userName);

			} catch (NullPointerException e) {
				
				Notification.show("Sorry, Null values", Notification.TYPE_ERROR_MESSAGE);
			} catch (Exception excp){
				excp.printStackTrace();
			}
		}
	});
	
		return cqSignup;
	

	
	}

	public AbsoluteLayout getRuleStatus(String cqname) {

		final AbsoluteLayout cqStatus = new AbsoluteLayout();
		final Table ruleTable = new Table("Rule Details");
		ruleTable.setSelectable(true);
		
//		ruleTable.setSizeFull();
		// Define two columns for the built-in container
		ruleTable.addContainerProperty("Rule Name", String.class, null);
		ruleTable.addContainerProperty("Ver", String.class, null);
		ruleTable.addContainerProperty("% Completed", Integer.class, null);
		ruleTable.addContainerProperty("% Expected", Integer.class, null);
		ruleTable.addContainerProperty("Target Dt", String.class, null);
		ruleTable.addContainerProperty("Today Dt.", String.class, null);
		ruleTable.addContainerProperty("from CQ", String.class, null);
		ruleTable.addContainerProperty("and Bundle", String.class, null);
		

		Date myDate = new Date();
		Object newItemId;
		Item row1;
		try {
			
//			
			//rule complete
			SqlRowSet ruleList = jt.queryForRowSet("select bundleName, cqname, ruleName, ver, rule_creator_complete, rule_test_complete, rule_review_complete  from rulegroup where cqname ='"+cqname+"'");
			System.out.println("inside cqdetail getrulestatus()......"+cqname);
			
			int n=1;
			while(ruleList.next()){
				rgStatus.setBundleName(ruleList.getString(1));
				rgStatus.setCqName(ruleList.getString(2));
				rgStatus.setRuleGroup(ruleList.getString(3).toString());
				rgStatus.setVersion(ruleList.getString(4).toString());
				rgStatus.setCompletedPercentRuleCreate(ruleList.getInt(5));
				rgStatus.setCompletedPercentRuleTest(ruleList.getInt(6));
				rgStatus.setCompletedPercentRuleReview(ruleList.getInt(7));

				int ruleComplete = rgStatus.getCompletedPercentRuleCreate()+rgStatus.getCompletedPercentRuleTest()+rgStatus.getCompletedPercentRuleReview();
				rgStatus.setCompletedPercent(ruleComplete/3);
				rgStatus = getTargetedRuleInfo(rgStatus.getBundleName(), rgStatus.getCqName(), new Date());
				
				String targetDt = DateConversion.getDateFormat(rgStatus.getTargetDate());
				String todayDt = DateConversion.getDateFormat(myDate);

				ruleTable.addItem(new Object[]{rgStatus.getRuleGroup(), rgStatus.getVersion(), 
						rgStatus.getCompletedPercent(), rgStatus.getExpectedPercent(), targetDt, 
						todayDt, rgStatus.getCqName(), rgStatus.getBundleName()}, n);
		
				
				n++;
				
			}
			
			
			ruleTable.setFooterVisible(true);
			ruleTable.setColumnFooter("Ver", "Average");
			ruleTable.setColumnFooter("% Completed", String.valueOf(11));
			ruleTable.setPageLength(n);
			
		} catch (Exception e) {

			Notification.show("Failed retriving ");
			e.printStackTrace();
		}
		 
		
		ruleTable.addItemClickListener(new ItemClickListener() {
		
			@Override
			public void itemClick(ItemClickEvent event) {

				Item bundleDetail = ruleTable.getItem(event.getItemId());
				Object bundleName = bundleDetail.getItemProperty("and Bundle").getValue();
				
				Item cqDetail = ruleTable.getItem(event.getItemId());
				Object cqName = cqDetail.getItemProperty("from CQ").getValue();

				Item ruleDetail = ruleTable.getItem(event.getItemId());
				Object ruleName = cqDetail.getItemProperty("Rule Name").getValue();

				Item versionDetail = ruleTable.getItem(event.getItemId());
				Object version = cqDetail.getItemProperty("Ver").getValue();

				cqStatus.removeAllComponents();
			
				String value = event.getItem().toString();
				
				Table myTable = getTable(bundleName, cqName, ruleName, version);
				cqStatus.addComponent(myTable);
			}

			private Table getTable(Object bundleName, Object cqName, Object ruleName, Object version) {

				Table table = new Table("Details of Bundle");
				table.addContainerProperty("Rule Name", String.class, null);
				table.addContainerProperty("Rule Create %", Integer.class, null);
				table.addContainerProperty("Rule Created By", String.class, null);
				table.addContainerProperty("Rule Test %", Integer.class, null);	
				table.addContainerProperty("Rule Tested By", String.class, null);
				table.addContainerProperty("Rule Review %", Integer.class, null);
				table.addContainerProperty("Rule Reviewed By", String.class, null);
				table.addContainerProperty("Total %", Integer.class, null);
				table.addContainerProperty("from CQ", String.class, null);

				List<RulePojo> ruleDataList = query.getRuleDetaillist(bundleName, cqName, ruleName, version);
				
				int n=1;
				for (RulePojo rule : ruleDataList) {
				int totalComplete = rule.getRuleCreatePercent()+rule.getRuleTestPercent()+rule.getRuleReviewPercent();
				int totalCompletePercent = totalComplete/3;
				
					table.addItem(new Object[]{rule.getRuleGroupName(), 
							rule.getRuleCreatePercent(), rule.getRuleCreator(), 
							rule.getRuleTestPercent(), rule.getRuleTester(),
							rule.getRuleReviewPercent(), rule.getRuleReviewer(),
							totalCompletePercent, rule.getCqName()}, n);
					n++;
				}
								
//				}
//				table.addItem(new Object[]{"Canopus", 72, "aa", 32,"bb", 43, "cc", 44, "daa"}, 1);
//				table.addItem(new Object[]{"Arcturus", 4, "ff", 5, "dd", 6, "ee", 7, "ddd"}, 2);
//				table.addItem(new Object[]{"Alpha Centauri", 1, "zz", 2, "kk", 3, "ddx", 4, "sss"}, 3);
//				        
				return table;
			
			}
		});

		cqStatus.addComponent(ruleTable);

		return cqStatus;
	}

	private RuleGroupStatus getTargetedRuleInfo(String bundleName, String cqName, Date todaydate) {
		
		SqlRowSet expectedStatusToDate = jt.queryForRowSet("select rulecreateTarget, ruletesttarget, rulereviewtarget, ruleCreatePer,"
				+ " ruleTestPer, ruleReviewPer, b_name  from bundle_details where b_name='"+bundleName+"'");

		while(expectedStatusToDate.next()){
		
			rgStatus.setTargetRuleCreateDt(expectedStatusToDate.getDate(1));
			rgStatus.setTargetRuleTestDt(expectedStatusToDate.getDate(2));
			rgStatus.setTargetRuleReviewDt(expectedStatusToDate.getDate(3));
			rgStatus.setTargetPercentRuleCreate(expectedStatusToDate.getInt(4));
			rgStatus.setTargetPercentRuleTest(expectedStatusToDate.getInt(5));
			rgStatus.setTargetPercentRuleReview(expectedStatusToDate.getInt(6));
			
			rgStatus = getApplicableDate(todaydate);
		}
		
		return rgStatus;
	}

	private RuleGroupStatus getApplicableDate(Date todaydate) {
		java.sql.Date myDate = DateConversion.getSqlDate(todaydate.getTime());
		
		if(myDate.before(rgStatus.getTargetRuleCreateDt())){
			rgStatus.setExpectedPercent(rgStatus.getTargetPercentRuleCreate());
			rgStatus.setTargetDate(rgStatus.getTargetRuleCreateDt());
			System.out.println("inside target rule date..................");
		} else if (myDate.before(rgStatus.getTargetRuleTestDt())){
			rgStatus.setExpectedPercent(rgStatus.getTargetPercentRuleTest());
			rgStatus.setTargetDate(rgStatus.getTargetRuleTestDt());
			System.out.println("inside ruleTest logic verification............");
		} else if (myDate.before(rgStatus.getTargetRuleReviewDt())){
			rgStatus.setExpectedPercent(rgStatus.getTargetPercentRuleReview());
			rgStatus.setTargetDate(rgStatus.getTargetRuleReviewDt());
			System.out.println("inside rule Review Test logic.................");
		} else {
			rgStatus.setExpectedPercent(rgStatus.getTargetPercentRuleReview());
			rgStatus.setTargetDate(rgStatus.getTargetRuleReviewDt());
		}
		return rgStatus;
	}


}
