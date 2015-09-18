package com.example.myvaadin;

import java.io.Serializable;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.support.rowset.SqlRowSet;

import oracle.jdbc.oracore.TDSPatch;

import com.example.User.LogInInfo;
import com.example.database.Insert;
import com.example.database.Query;
import com.example.database.Template;
import com.example.database.Update;
import com.example.pojo.BundleStatusPojo;
import com.example.pojo.CQstatusPojo;
import com.google.gwt.user.client.ui.FlexTable;
import com.vaadin.data.Item;
import com.vaadin.data.Property.ValueChangeEvent;
import com.vaadin.data.Property.ValueChangeListener;
import com.vaadin.data.util.BeanItemContainer;
import com.vaadin.event.ItemClickEvent;
import com.vaadin.event.ItemClickEvent.ItemClickListener;
import com.vaadin.server.VaadinSession;
import com.vaadin.shared.ui.label.ContentMode;
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
import com.vaadin.ui.OptionGroup;
import com.vaadin.ui.Select;
import com.vaadin.ui.TabSheet;
import com.vaadin.ui.TabSheet.Tab;
import com.vaadin.ui.Table;
import com.vaadin.ui.TextArea;
import com.vaadin.ui.TextField;
import com.vaadin.ui.Button.ClickEvent;
import com.vaadin.ui.UI;
import com.vaadin.ui.VerticalLayout;
import com.vaadin.ui.themes.Reindeer;

@SuppressWarnings("deprecation")
public class BundleForm implements Serializable {

	BundleStatusPojo bundleStatus = new BundleStatusPojo();
	
	private static final long serialVersionUID = 7526472295622776147L;
	JdbcTemplate jt = Template.getTemplate();
	Update updateBundle = new Update();
	Insert insert = new Insert();
	Query query = new Query();
	String userName = (String) VaadinSession.getCurrent().getAttribute("userName");

	List<BundlePojo> list;
	BundlePojo bdata;
	Map<String, String> map = new HashMap<String, String>();

	public BundleForm() {

		// vlayoutforForm.addComponent(flayout);
	}

	public FormLayout createForm() {
		FormLayout flayout = new FormLayout();

		final TextField bundleName = new TextField("Bundle Name");
		final TextArea bundleDesc = new TextArea("Bundle Description");
		bundleDesc.setSizeFull();
		bundleDesc.setHeight("60px");
		flayout.addComponent(bundleName);
		flayout.addComponent(bundleDesc);

		HorizontalLayout hl1 = new HorizontalLayout();
		final DateField designUpdate = new DateField("Design Update Dt.");
		final DateField designReview = new DateField("Design Review Dt.");
		final DateField ruleCreate = new DateField("Rule Create Dt.");
		final DateField ruleTestdate = new DateField("Rule Test Dt.");

		designUpdate.setValue(new Date());
		designReview.setValue(new Date());
		ruleCreate.setValue(new Date());
		ruleTestdate.setValue(new Date());
		final TextField designUpdatePer = new TextField("Design Update %");
		final TextField designReviewPer = new TextField("Design Review %");
		final TextField ruleCreatePer = new TextField("Rule Create %");
		final TextField ruleTestPer = new TextField("Rule Test %");
		final TextField ruleReviewPer = new TextField("Rule Review %");
		final TextField scenarioTestPer = new TextField("Scenario Test %");
		final TextField testReviewPer = new TextField("TestReview %");
		final TextField scenarioDataPer = new TextField("Scenario Data %");
		final TextField overAllReviewPer = new TextField("Overall Review %");
		final TextField regressionPer = new TextField("Regression %" );
		
		
		HorizontalLayout hl5 = new HorizontalLayout();
		hl5.addComponents(designUpdatePer, designReviewPer, ruleCreatePer, ruleTestPer);
	hl5.setSpacing(true);
	
	HorizontalLayout hl2 = new HorizontalLayout();
	
		hl1.addComponent(designUpdate);
		hl1.addComponent(designReview);
		hl1.addComponent(ruleCreate);
		hl1.addComponent(ruleTestdate);
		hl1.setSpacing(true);
		flayout.addComponent(hl1);
		flayout.addComponent(hl2);
		
		
		final DateField ruleReviewDate = new DateField("Rule Review Dt.");
		final DateField scenarioData = new DateField("Scenarion Data Setup Dt");
		final DateField scenarioTestDate = new DateField("Scenario Test Dt.");
		final DateField testReviewDate = new DateField("TestReview Dt.");
		final DateField overallReviewDate = new DateField("Overall Review Dt.");
		
		HorizontalLayout hl7 = new HorizontalLayout();
		final DateField regressionDate = new DateField("Regression Dt");
		hl7.addComponent(overallReviewDate);
		hl7.addComponent(regressionDate);
		
		ruleReviewDate.setValue(new Date());
		scenarioData.setValue(new Date());
		scenarioTestDate.setValue(new Date());
		testReviewDate.setValue(new Date());
		overallReviewDate.setValue(new Date());
		regressionDate.setValue(new Date());
		
		hl2.addComponent(ruleReviewDate);
		hl2.addComponent(scenarioData);
		hl2.addComponent(scenarioTestDate);
		hl2.addComponent(testReviewDate);
		hl2.setSpacing(true);

		flayout.addComponent(hl7);
		flayout.addComponent(hl5);
		
		HorizontalLayout hl6 = new HorizontalLayout();
		hl6.addComponents(ruleReviewPer, scenarioDataPer, scenarioTestPer, testReviewPer);
		
		HorizontalLayout hl8 = new HorizontalLayout();
		hl8.setSpacing(true);
		hl8.addComponents(overAllReviewPer, regressionPer);
	
		hl6.setSpacing(true);
		flayout.addComponent(hl6);
		flayout.addComponent(hl8);
	
		
		HorizontalLayout hl3 = new HorizontalLayout();
		final TextField bundleTimeframe = new TextField("Bundle Period in days");
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
		
		
		
		//Temp for working status only.
				designUpdatePer.setValue("10");
				designReviewPer.setValue("20");
				ruleCreatePer.setValue("25");
				ruleTestPer.setValue("35");
				ruleReviewPer.setValue("45");
				scenarioDataPer.setValue("55");
				scenarioTestPer.setValue("60");
				testReviewPer.setValue("70");
				overAllReviewPer.setValue("85");
				regressionPer.setValue("100");
				bundleTimeframe.setValue("14");

		add.addClickListener(new ClickListener() {

			@Override
			public void buttonClick(ClickEvent event) {

				// date conversion
				try {
					String bundleTime = bundleTimeframe.getValue().toString();
					int bundleDays = Integer.parseInt(bundleTime);

					JdbcTemplate jt = com.example.database.Template.getTemplate();
					Date myDate = designUpdate.getValue();
					long ruleReviewDtLong = ruleReviewDate.getValue().getTime();
					java.sql.Date sqlDate = DateConversion.getSqlDate(myDate.getTime());
					java.sql.Date designUpdateDt = DateConversion.getSqlDate(designUpdate.getValue().getTime());
					java.sql.Date designReviewDt = DateConversion.getSqlDate(designReview.getValue().getTime());
					java.sql.Date overallReviewDt = DateConversion.getSqlDate(overallReviewDate.getValue().getTime());
					java.sql.Date ruleCreateDt = DateConversion.getSqlDate(ruleCreate.getValue().getTime());
					java.sql.Date ruleTestDt = DateConversion.getSqlDate(ruleTestdate.getValue().getTime());
					java.sql.Date ruleReviewDate = DateConversion.getSqlDate(ruleReviewDtLong);
					java.sql.Date scenarioDataLoadDt = DateConversion.getSqlDate(scenarioData.getValue().getTime());
					java.sql.Date scenarioTestDt = DateConversion.getSqlDate(scenarioTestDate.getValue().getTime());
					java.sql.Date testReviewDt = DateConversion.getSqlDate(testReviewDate.getValue().getTime());
					java.sql.Date regressionDt = DateConversion.getSqlDate(regressionDate.getValue().getTime());

					
					System.out.println("before inserting bundle.........");
					//Inserting into database
					insert.addBundle(bundleName.getValue().toString(),bundleDesc.getValue().toString(),bundleDays, designReviewDt, designUpdateDt, overallReviewDt,ruleCreateDt, ruleTestDt, ruleReviewDate, scenarioDataLoadDt, scenarioTestDt, testReviewDt, regressionDt);

					jt.execute("insert into statusdata (bname, bstatus, currentstatus) values ('"+ bundleName.getValue().toString() + "', 'BundleCreated', 'BundleCreated')");

					insert.bundlePercent(bundleName.getValue().toString(), designUpdatePer.getValue().toString(), designReviewPer.getValue().toString(), ruleCreatePer.getValue().toString(),
							ruleTestPer.getValue().toString(), ruleReviewPer.getValue().toString(), scenarioDataPer.getValue().toString(), scenarioTestPer.getValue().toString(), testReviewPer.getValue().toString(), overAllReviewPer.getValue().toString(), regressionPer.getValue().toString());
					
//					insert.bundleTargetDt(designUpdate.getValue(), designReview.getValue(), ruleCreate.getValue(), ruleTestdate.getValue(), scenarioData.getValue(), scenarioTestDate.getValue(), testReviewDate.getValue(), 
//overallReviewDate.getValue(), regressionDate.getValue() );
					
					Notification.show("insert successfully");
				} catch (DataIntegrityViolationException e) {

					e.printStackTrace();
				} catch (Exception e) {
					Notification.show("failed to insert data",
							Notification.TYPE_ERROR_MESSAGE);

					e.printStackTrace();

				}

			}
		});
		return flayout;
	}

	public FormLayout editBundle() {
		final FormLayout flayout = new FormLayout();
		HorizontalLayout hl = new HorizontalLayout();

		ComboBox editableList = new ComboBox();
		editableList.addItem("Bundle Name");
		editableList.addItem("Bundle Description");
		
		HorizontalLayout hl1 = new HorizontalLayout();
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
		
		
//		hl.addComponent(editableList);
//		hl.addComponent(designUpdate);
//		hl1.addComponents(designUpdate, designReview, ruleCreate, ruleTest, scenarioTest, testReview, overAllReview, regression);
//		hl1.addComponent(designUpdate);//, designReview, ruleCreate, ruleTest, scenarioTest, testReview, overAllReview, regression);
		hl.setSpacing(true);
		hl1.setSpacing(true);
		
		flayout.addComponent(hl);
		flayout.addComponent(hl1);
		
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

	public FormLayout upDate() {
		Query query = new Query();

		final ComboBox bundles = new ComboBox("Select Bundle");
//		bundles.setInputPrompt("-- Select Bundle --");
		List<String> bundleList = query.getBundles();
		for (String bundle : bundleList) {
			bundles.addItem(bundle);
		}

		final FormLayout updatedBundle = new FormLayout();

		final DateField scenarioDataLoad = new DateField("Scenario Data Load Dt");
		final DateField deploymentDt = new DateField("Deployment Date");
		final OptionGroup options = new OptionGroup("select one");
//		options.addItem("Update Rule Review");
		options.addItem("Update Deployment Dt");
		options.addItem("Update Completion %");
		
//		updatedBundle.addComponent(bundles);

		final Button updateRuleReview = new Button("Update Rule Review");
		final Button updateDeployment = new Button("Update Deploy Dt");
		final Button update = new Button("Update");
		options.addValueChangeListener(new ValueChangeListener() {

			@Override
			public void valueChange(ValueChangeEvent event) {

				String selectedItem = event.getProperty().getValue().toString();
				Notification.show(selectedItem);
////
//				if (!selectedItem.isEmpty() && selectedItem.equalsIgnoreCase("Update Rule Review")){
//					updatedBundle.removeComponent(options);
////					updatedBundle.addComponent(bundles);
//					RuleForm ruleForm = new RuleForm();
//					FormLayout ruleUpdateData = ruleForm.getRuleReviewUpdate();
//					updatedBundle.addComponent(ruleUpdateData);
//					
//				} else
					if (!selectedItem.isEmpty() && selectedItem.equalsIgnoreCase("Update Completion %")){
					updatedBundle.removeComponent(options);
					updatedBundle.addComponent(bundles);
					TextField designUpdate = new TextField("Design Update %");
					TextField designReview = new TextField("Design Review %");
					TextField ruleCreate = new TextField("Rule Create %");
					TextField ruleTest = new TextField("Rule Test %");
					TextField scenarioTest = new TextField("Scenario Test %");
					TextField testReview = new TextField("TestReview %");
					TextField overAllReview = new TextField("Overall Review %");
					TextField regression = new TextField("Regression %" );
			
					updatedBundle.addComponent(designUpdate);
					updatedBundle.addComponent(designReview);
					updatedBundle.addComponent(ruleCreate);
					updatedBundle.addComponent(ruleTest);
					updatedBundle.addComponent(scenarioTest);
					updatedBundle.addComponent(testReview);
					updatedBundle.addComponent(overAllReview);
					updatedBundle.addComponent(regression);
					
					Button updatePerComplete = new Button("Update %");
					updatedBundle.addComponent(updatePerComplete);
					
				}else if (!selectedItem.isEmpty() && selectedItem.equalsIgnoreCase("Update Deployment Dt")){
					updatedBundle.removeComponent(options);
					updatedBundle.addComponent(bundles);
					deploymentDt.setValue(new Date());
					updatedBundle.addComponent(deploymentDt);
					updatedBundle.addComponent(updateDeployment);
				}
					
			}
		});

		updatedBundle.addComponent(options);

		update.addClickListener(new ClickListener() {

			@Override
			public void buttonClick(ClickEvent event) {
				
				String bundle = bundles.getValue().toString();
				Date inputDt = scenarioDataLoad.getValue();
				java.sql.Date dataLoadDt = DateConversion.getSqlDate(inputDt.getTime());
				updateBundle.updateDataLoad(bundle, dataLoadDt);
			}
		});
		
		updateDeployment.addClickListener(new ClickListener() {
			
			@Override
			public void buttonClick(ClickEvent event) {
				String bundle = bundles.getValue().toString();
				java.sql.Date deployDt = DateConversion.getSqlDate(deploymentDt.getValue().getTime());
				updateBundle.updateDeploymentDate(bundle, deployDt);
				
			}
		});
		return updatedBundle;

	}

	public FormLayout getSignup(String role) {

		FormLayout bundleSignup = new FormLayout();

		final ComboBox user = new ComboBox("User Name");
		user.setInputPrompt(userName);
		user.addItem(userName);

		HorizontalLayout hl = new HorizontalLayout();

		final ComboBox bun_name = new ComboBox("Select Bundle");

		Query querybundle = new Query();
		List<String> bundleList = querybundle.getBundles();
		for (String bundle : bundleList) {
			bun_name.addItem(bundle);
		}

		final TextField roleSigned = new TextField("Role As");
		roleSigned.setValue(role);
		roleSigned.setReadOnly(true);

		final DateField signupDate = new DateField("Sign Up Dt.:");
		signupDate.setValue(new Date());

		Button add = new Button("Sign-up");
		hl.addComponent(bun_name, 0);
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

		add.addClickListener(new ClickListener() {

			@Override
			public void buttonClick(ClickEvent event) {

				Insert insertDao = new Insert();
				String bname = bun_name.getValue().toString();
				String signedRole = roleSigned.getValue().toString();
//				String userName = user.getValue().toString();
				String userName = (String) VaadinSession.getCurrent().getAttribute("userName");
				Date dateInput = signupDate.getValue();
				java.sql.Date signUpdt = DateConversion.getSqlDate(dateInput
						.getTime());

//				updateBundle.signupCQ(bname, signedRole, userName, signUpdt);
				
				updateBundle.signupBundle(bname, signedRole, userName, signUpdt);

			}
		});
		return bundleSignup;

	}

	String stage = null;
	String expectedPer = null;
	String completedPer = null;
	String statusToQuery = null;
	
	public AbsoluteLayout showBundleStatus(String bundleName) {
		Date todayDate = new Date();
		final AbsoluteLayout bundleLayout = new AbsoluteLayout();
		final Table bundletable = new Table("Bundle Details");
		bundletable.setSelectable(true);

		int totalCqcompleteStatus = query.getCqCompletePercent(bundleName);
System.out.println("totalCQcompleteStatus:  ,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,  "+totalCqcompleteStatus);
		bundletable.setSizeFull();
		
		bundletable.addContainerProperty("CQ Name", String.class, null);
		bundletable.addContainerProperty("Completed %", Integer.class, null);
		bundletable.addContainerProperty("Expected %", Integer.class, null);
		bundletable.addContainerProperty("Target Dt.", String.class, null);
		bundletable.addContainerProperty("Today Dt", String.class, null);
		bundletable.addContainerProperty("from Bundle", String.class, null);

		Object newItemId;
		Item row1;
		int n=1;
		try {
			
			SqlRowSet expectedStatusToDate = jt.queryForRowSet("select designUptarget, designreviewtarget, rulecreateTarget, ruletesttarget, "
					+ "rulereviewtarget, scenariotesttarget, testreviewtarget, overallreviewtarget, regressiontarget,"
					+ "designReviewPer, designUpPer, ruleCreatePer, ruleTestPer, ruleReviewPer, scenarioTestPer, testReviewPer, "
					+ "overallReviewPer, b_name  from bundle_details where b_name='"+bundleName+"'");
		

		CQstatusPojo cqpojo = new CQstatusPojo();
			int t=1;
	
			while(expectedStatusToDate.next()){
					cqpojo.setDesignUptarget(expectedStatusToDate.getDate(1));
					cqpojo.setDesignreviewtarget(expectedStatusToDate.getDate(2));
					cqpojo.setRulecreateTarget(expectedStatusToDate.getDate(3));
					cqpojo.setRuletesttarget(expectedStatusToDate.getDate(4));
					cqpojo.setRulereviewtarget(expectedStatusToDate.getDate(5));
					cqpojo.setScenariotesttarget(expectedStatusToDate.getDate(6));
					cqpojo.setTestreviewtarget(expectedStatusToDate.getDate(7));
					cqpojo.setOverallreviewtarget(expectedStatusToDate.getDate(8));
					cqpojo.setRegressiontarget(expectedStatusToDate.getDate(9));
					
					cqpojo.setDesignUpPer(expectedStatusToDate.getInt(10));
					cqpojo.setDesignReviewPer(expectedStatusToDate.getInt(11));
					cqpojo.setRuleCreatePer(expectedStatusToDate.getInt(12));
					cqpojo.setRuleTestPer(expectedStatusToDate.getInt(13));
					cqpojo.setRuleReviewPer(expectedStatusToDate.getInt(14));
					cqpojo.setScenarioTestPer(expectedStatusToDate.getInt(15));
					cqpojo.setTestReviewPer(expectedStatusToDate.getInt(16));
					cqpojo.setOverallReviewPer(expectedStatusToDate.getInt(17));
					
					cqpojo.setBundleName(expectedStatusToDate.getString(18));
					
					//calculating ruleCompletion
//					int totalCqcompleteStatus = (cqpojo.getRuleCreatePer()+cqpojo.getRuleTestPer()+cqpojo.getRuleReviewPer())/3;
					
					//calculating total cqcompletion
//					cqpojo.setCurrentPercent(totalCqcompleteStatus);

					cqpojo.setCqCompletedPercent(totalCqcompleteStatus);
					
					CQstatusPojo cqpojo1 = getExpectedDate(todayDate, cqpojo, bundletable);
					
					t++;
//				}
			}
			
			
		} catch (Exception e) {

			Notification.show("Failed retriving CQstatus for Bundle ",
					Notification.TYPE_ERROR_MESSAGE);
			e.printStackTrace();
		}

		bundleLayout.addComponent(bundletable);

		bundletable.addItemClickListener(new ItemClickListener() {
			
			@Override
			public void itemClick(ItemClickEvent event) {
				
				Item itemDetail = bundletable.getItem(event.getItemId());
				Object bundleName = itemDetail.getItemProperty("from Bundle").getValue();
						
				bundleLayout.removeAllComponents();
				String value = event.getItem().toString();
				
				TabSheet tabSheet = new TabSheet();
				Table myTable = getTable(bundleName);
				tabSheet.addComponent(myTable);
				bundleLayout.addComponent(tabSheet);
				
			}


			private Table getTable(Object bundleName) {

				Table table = new Table("CQ Complete Status");
				CQstatusPojo cqcompleteDetail = query.getCqCompletePercentDetail(bundleName);
				System.out.println("inside bundleForm getting ruleComplete............"+cqcompleteDetail.getTotalRuleComplete());
				
		        table.setCaption("CQ Completion Status");
				table.addContainerProperty("CQ Name", String.class, null);
				table.addContainerProperty("Design Update", Integer.class, null);
				table.addContainerProperty("Design Review", Integer.class, null);	
				table.addContainerProperty("Rule Completion", Integer.class, null);	
				table.addContainerProperty("Rule Review", Integer.class, null);
				table.addContainerProperty("Data Load", Integer.class, null);	
				table.addContainerProperty("Scenario Test", Integer.class, null);	
				table.addContainerProperty("Test Review", Integer.class, null);	
				table.addContainerProperty("Over All Review", Integer.class, null);
				table.addContainerProperty("Total", Integer.class, null);
				
				
				// Add a few other rows using shorthand addItem()
				table.addItem(new Object[]{cqcompleteDetail.getCqName(), cqcompleteDetail.getDesignUpPer(), cqcompleteDetail.getDesignReviewPer(), 
						cqcompleteDetail.getTotalRuleComplete(), 
						cqcompleteDetail.getRuleReviewPer(),cqcompleteDetail.getScenarioDataLoad(), cqcompleteDetail.getScenarioTestPer(), cqcompleteDetail.getTestReviewPer(), cqcompleteDetail.getOverallReviewPer(), cqcompleteDetail.getCurrentPercent()}, 1);

				System.out.println(cqcompleteDetail.getCqName()+" ...........  "+ cqcompleteDetail.getDesignUpPer()+"   "+ cqcompleteDetail.getDesignReviewPer()+"   "+ cqcompleteDetail.getTotalRuleComplete());
				
				
				return table;
			}
						
		});
		return bundleLayout;
	}

	private CQstatusPojo getExpectedDate(Date todayDate, CQstatusPojo cqpojo, Table table) {
		
		BundleStatusPojo bundlecompleteStatus = query.getCompleteStatus(cqpojo.getBundleName());
		
		SqlRowSet cqList = jt.queryForRowSet("select cqname, stage, TotalComplete from cq where bundleName ='"+cqpojo.getBundleName()+"'");
		
//		cqpojo.setCurrentPercent(bundlecompleteStatus.getCqComplete()+bundlecompleteStatus.getRegressionComplete());
		
		System.out.println("cqpojo.getCurrentPercent>>>>>>>>>>>>>>>>> "+bundlecompleteStatus.getCqComplete()+"....... regresion complete "+bundlecompleteStatus.getRegressionComplete());
		
		int n =1;
		while (cqList.next()) {
			cqpojo.setCqName(cqList.getString(1).toString());
			cqpojo.setExpectedStage(cqList.getString(2).toString());
//			cqpojo.setCurrentPercent(cqList.getInt(3));

			java.sql.Date myDate = DateConversion.getSqlDate(todayDate.getTime());
			
			if(myDate.before(cqpojo.getDesignUptarget())){
				cqpojo.setTargetDt(cqpojo.getDesignUptarget());
				cqpojo.setTargetPercent(cqpojo.getDesignUpPer());
				
			} else if (myDate.before(cqpojo.getDesignreviewtarget())){
				cqpojo.setTargetDt(cqpojo.getDesignreviewtarget());
				cqpojo.setTargetPercent(cqpojo.getDesignReviewPer());

			} else if(myDate.before(cqpojo.getRulecreateTarget())){
				System.out.println(cqpojo.getRulecreateTarget()+"     before myDate   "+myDate);
				cqpojo.setTargetDt(cqpojo.getRulecreateTarget());
				cqpojo.setTargetPercent(cqpojo.getRuleCreatePer());
			} else if (myDate.before(cqpojo.getRuletesttarget())){
				cqpojo.setTargetDt(cqpojo.getRuletesttarget());
				cqpojo.setTargetPercent(cqpojo.getRuleTestPer());
				cqpojo.setExpectedStage("Rule Testing");
				
			} else if (myDate.before(cqpojo.getRulereviewtarget())){
				cqpojo.setTargetDt(cqpojo.getRulereviewtarget());
				cqpojo.setTargetPercent(cqpojo.getRuleReviewPer());
				System.out.println(cqpojo.getRulereviewtarget()+"     before ruleReview   "+myDate);

			} else if (myDate.before(cqpojo.getScenariotesttarget())){
				cqpojo.setTargetDt(cqpojo.getTestreviewtarget());
				cqpojo.setTargetPercent(cqpojo.getScenarioTestPer());
				System.out.println("....myDate : "+myDate+"  ScenarioTest target  "+cqpojo.getScenariotesttarget());

			} else if (myDate.before(cqpojo.getTestreviewtarget())){
				cqpojo.setTargetDt(cqpojo.getTestreviewtarget());
				cqpojo.setTargetPercent(cqpojo.getTestReviewPer());
				System.out.println("test review target "+cqpojo.getTestreviewtarget());

			} else if (myDate.before(cqpojo.getOverallreviewtarget())){
				cqpojo.setTargetDt(cqpojo.getOverallreviewtarget());
				cqpojo.setTargetPercent(cqpojo.getOverallReviewPer());
//				statusList.add(bundleStatus);
				System.out.println("overall review target " + cqpojo.getOverallreviewtarget());
			} else {
				cqpojo.setTargetDt(cqpojo.getOverallreviewtarget());
				cqpojo.setTargetPercent(cqpojo.getOverallReviewPer());
			}

			String targetDt = DateConversion.getDateFormat(cqpojo.getTargetDt());
			String todayDt = DateConversion.getDateFormat(todayDate);
			table.addItem(new Object[]{cqpojo.getCqName(), cqpojo.getCqCompletedPercent(), cqpojo.getTargetPercent(), targetDt,   todayDt, cqpojo.getBundleName()}, n );
			n++;
		}
	
		return cqpojo;
	}

	public AbsoluteLayout getAllBundleDetails() {
		
		final AbsoluteLayout allDetails = new AbsoluteLayout();

		TabSheet tabSheet = new TabSheet();
		tabSheet.setCaption("TabSheet");
		
		Table table = new Table("All bundle Details");
		table.setSelectable(true);
		Table deployedTable = new Table("Deployed Bundles");
		table.setWidth("80%");
		tabSheet.addStyleName(Reindeer.LAYOUT_BLUE);
		tabSheet.addTab(table, "Current Bundles");
		tabSheet.addTab(deployedTable, "Deployed");

		Date myDate = new Date();
		final List<BundleStatusPojo> bundleStatuslist = query.getStatusTodate(myDate);
		String myDateFormat = DateConversion.getDateFormat(myDate);
		
		table.addContainerProperty("Bundle Name", String.class, null);
		table.addContainerProperty("Expected %",  Integer.class, null);
//		table.addContainerProperty("Target Dt", java.sql.Date.class, null);
		table.addContainerProperty("Target Dt", String.class, null);
		table.addContainerProperty("Complete %",  Integer.class, null);
		table.addContainerProperty("Today Dt", String.class, null);

		int n=1;

		String targetDt;
		
		for (int i = 0; i < bundleStatuslist.size(); i++) {

			//total complete calculation
			int totalCompleteSum = bundleStatuslist.get(i).getCqComplete() + bundleStatuslist.get(i).getRegressionComplete();
			int totalComplete = totalCompleteSum/2;
			
			//table for main UI
			table.addItem(new Object[] {bundleStatuslist.get(i).getBundleName(), bundleStatuslist.get(i).getTargetPercent(),
					targetDt = DateConversion.getDateFormat(bundleStatuslist.get(i).getTargetDt()),
							totalComplete, myDateFormat}, i);
		}

		deployedTable.addContainerProperty("Bundle Name", String.class, null);
		deployedTable.addContainerProperty("Stage", String.class, null);
		deployedTable.addContainerProperty("Deployed Dt.", String.class, null);
		deployedTable.setWidth("70%");
		
		
		SqlRowSet deployedList = jt.queryForRowSet("select b_name, status, deploydt from bundle where status ='Deployed'");
	
		int m =1;
		while(deployedList.next()){
			String bundleName = deployedList.getString(1);
			String status = deployedList.getString(2);
			Date deployedDt = deployedList.getDate(3);
			SimpleDateFormat format = new SimpleDateFormat("MM/dd/yyyy");
			String DateToStr = format.format(deployedDt);

			System.out.println(DateToStr);
			deployedTable.addItem(new Object[]{bundleName, status, DateToStr}, m);
			m++;
		}
		

		table.addItemClickListener(new ItemClickListener() {
			
			@Override
			public void itemClick(ItemClickEvent event) {
				String value = event.getItem().toString();
				
				
				
				
				for (BundleStatusPojo bundleStatusPojo : bundleStatuslist) {

				if (value.contains(bundleStatusPojo.getBundleName())) {

					Table myTable = getTable(bundleStatusPojo.getBundleName());
					allDetails.removeAllComponents();
					TabSheet myNewTab = new TabSheet();
					myNewTab.addComponent(myTable);
					allDetails.addComponent(myNewTab);
					
				}
				}
				
			}

			private Table getTable(String bundleName) {

				Table table = new Table("Details of Bundle");
				table.addContainerProperty("Bundle Name", String.class, null);
				table.addContainerProperty("CQ Complete%",  Integer.class, null);
				table.addContainerProperty("Regression Complete%",  Integer.class, null);
				table.addContainerProperty("Total Complete%",  Integer.class, null);

				try {
					SqlRowSet cqforBundle = jt.queryForRowSet("select b_name, complete_cq_percent, complete_regression_percent, total_bundleCompletion from bundle where b_name='"+bundleName+"'");
				
					bundleStatus = query.getCompleteStatus(bundleName);
					System.out.println("......xxxx......"+bundleStatus.getCqComplete());
			        
					bundleStatus = query.getRegressionCompletion(bundleName);
					System.out.println("........xxxx====   "+bundleStatus.getRegressionComplete());
//					
					int n =0;
			        while(cqforBundle.next()){
			        	bundleStatus.setBundleName(cqforBundle.getString(1));
//			        	bundleStatus.setCqComplete(cqforBundle.getInt(2));
//			        	bundleStatus.setRegressionComplete(cqforBundle.getInt(3));
//			        	bundleStatus.setTotalComplete(cqforBundle.getInt(4));
			        	int completePercent = (bundleStatus.getCqComplete()+bundleStatus.getRegressionComplete())/2;
			        	
			        	table.addItem(new Object[]{bundleStatus.getBundleName(), bundleStatus.getCqComplete(), bundleStatus.getRegressionComplete(), completePercent }, n);
			        	n++;
			        }
			        
			        
				} catch (Exception e) {
					e.printStackTrace();
				}
		        
				return table;
			}
		});
		
		        tabSheet.addComponent(table);
		        tabSheet.addComponent(deployedTable);
				allDetails.addComponent(tabSheet);

		return allDetails;
	}

	private void getQueryField(String statusToQuery) {
		
	
		if(statusToQuery.equalsIgnoreCase("designUpdater")){
			expectedPer = "designUpper";
			completedPer = "compdesignupdate";
			stage = "Design Update";
		} else if(statusToQuery.equalsIgnoreCase("designReviewer")){
			expectedPer = "designreviewper";
			completedPer = "compdesignreview";
			stage = "Design Review";
		} else if(statusToQuery.equalsIgnoreCase("ruleCreator")){
			expectedPer = "ruleCreatePer";
			completedPer = "compRuleCreate";
			stage = "Rule Creation";
		} else if (statusToQuery.equalsIgnoreCase("ruleTester")){
			expectedPer = "ruleTestPer";
			completedPer = "compRuleTest";
			stage = "Rule Testing";
		} else if (statusToQuery.equalsIgnoreCase("scenarioTester")){
			expectedPer = "scenarioTestPer";
			completedPer = "compScenarioTest";
			stage = "Scenario Test";
		} else if(statusToQuery.equalsIgnoreCase("testReviewer")){
			expectedPer = "testreviewper";
			completedPer = "comptestreview";
			stage = "Test Review";
		}  else if(statusToQuery.equalsIgnoreCase("overAllReviewer")){
			expectedPer = "overallreviewPer";
			completedPer = "compoverallreview";
			stage = "Over All Review";
		}  else if(statusToQuery.equalsIgnoreCase("Created")){
			expectedPer = "designUpper";
			completedPer = "compdesignupdate";
			stage = "Created";
		}else {
			expectedPer = "regressionPer";
			completedPer = "compRegressionTest";
			stage = "Regression Test";
		};
		

		
		
	}

	public FormLayout editBundleTargetDt() {
		final FormLayout editbundleTargetDt = new FormLayout();
		final ComboBox bundles = new ComboBox();
		
		List bundlelist = query.getBundles();
		
		for (Object bundleName : bundlelist) {
		bundles.addItem(bundleName);	
		}
		editbundleTargetDt.addComponents(bundles);
		final HorizontalLayout hl1 = new HorizontalLayout();
		final HorizontalLayout hl2 = new HorizontalLayout();
		final HorizontalLayout hl3 = new HorizontalLayout();
		
		bundles.addValueChangeListener(new ValueChangeListener() {
			
			@Override
			public void valueChange(ValueChangeEvent event) {
				editbundleTargetDt.removeComponent(bundles);
				
				String bundleSelected = event.getProperty().getValue().toString();
				BundlePojo bundleInfo = query.getBundlesTargetDt(bundleSelected);
				
				hl1.setSpacing(true);
				Label title = new Label("<html><h2><b><center>Target Date Revise Mode for </center>\n</b></h2></html>", ContentMode.HTML);
				final TextField bundleName = new TextField("Bundle Name: ");
				final TextField bundleLead = new TextField("Bundle Lead :");
				bundleName.setValue(bundleInfo.getBname());
				bundleLead.setValue(bundleInfo.getBundleLead());
				hl1.addComponents(title, bundleName, bundleLead);
//				hl1.addComponents(title);
				final DateField designUpdate = new DateField("Design Update Dt.");
				designUpdate.setValue(bundleInfo.getDesignUptarget());
				final DateField designReview = new DateField("Design Review Dt.");
				designReview.setValue(bundleInfo.getDesignreviewtarget());
				final DateField ruleCreate = new DateField("Rule Create Dt.");
				ruleCreate.setValue(bundleInfo.getRulecreateTarget());
				final DateField ruleTestdate = new DateField("Rule Test Dt.");
				ruleTestdate.setValue(bundleInfo.getRuletesttarget());
				final DateField ruleReviewDate = new DateField("Rule Review Dt.");
				ruleReviewDate.setValue(bundleInfo.getRulereviewtarget());
				hl2.setSpacing(true);
				hl2.addComponents(designUpdate, designReview, ruleCreate, ruleTestdate, ruleReviewDate);
				
				final DateField scenarioData = new DateField("Scenarion Data Setup Dt");
				scenarioData.setValue(bundleInfo.getScenarioDataTarget());
				final DateField scenarioTestDate = new DateField("Scenario Test Dt.");
				scenarioTestDate.setValue(bundleInfo.getScenarioDataTarget());
				final DateField testReviewDate = new DateField("TestReview Dt.");
				testReviewDate.setValue(bundleInfo.getTestreviewtarget());
				final DateField overallReviewDate = new DateField("Overall Review Dt.");
				overallReviewDate.setValue(bundleInfo.getOverallreviewtarget());
				final DateField regressionDate = new DateField("Regression Dt");
				regressionDate.setValue(bundleInfo.getRegressiontarget());
				hl3.setSpacing(true);
				hl3.addComponents(scenarioData, scenarioTestDate, testReviewDate, overallReviewDate, regressionDate);

				HorizontalLayout hl4 = new HorizontalLayout();
				Button clear = new Button("Clear");
				Button save = new Button("Save");
				hl4.setSpacing(true);
				hl4.addComponents(clear, save);
				
				HorizontalLayout hl5 = new HorizontalLayout();
				final TextArea reason = new TextArea("Reason of Modification");
				reason.setWidth("850px");
				hl5.addComponent(reason);
				
				editbundleTargetDt.addComponent(hl1);
				editbundleTargetDt.addComponent(hl2);
				editbundleTargetDt.addComponent(hl3);
				editbundleTargetDt.addComponent(hl5);
				editbundleTargetDt.addComponent(hl4);
				
				save.addClickListener(new ClickListener() {
					
					@Override
					public void buttonClick(ClickEvent event) {

						Notification.show(ruleReviewDate.getValue().toString());
						
						updateBundle.updateBundleTargetDt(designUpdate, designReview, ruleCreate, ruleTestdate, ruleReviewDate, scenarioData, scenarioTestDate, testReviewDate, overallReviewDate, regressionDate, bundleName, bundleLead);

						insert.changeLogTargetDt(bundleName, bundleLead, reason);
					}
				});
			}
		});
		
		
		return editbundleTargetDt;
	}

	public FormLayout editBundlePercent() {
		
		final FormLayout editbundleTargePercent = new FormLayout();
		final ComboBox bundles = new ComboBox();
		List bundlelist = query.getBundles();
		
		for (Object bundleName : bundlelist) {
			bundles.addItem(bundleName);	
		}

		editbundleTargePercent.addComponent(bundles);		
		final HorizontalLayout hl1 = new HorizontalLayout();
		final HorizontalLayout hl2 = new HorizontalLayout();
		final HorizontalLayout hl3 = new HorizontalLayout();
		final HorizontalLayout hl4 = new HorizontalLayout();
		final HorizontalLayout hl5 = new HorizontalLayout();
		
		bundles.addValueChangeListener(new ValueChangeListener() {
			
			@Override
			public void valueChange(ValueChangeEvent event) {
				editbundleTargePercent.removeComponent(bundles);
				String bundleSelected = event.getProperty().getValue().toString();
				BundlePojo bundleInfo = query.getBundlesTargetPercent(bundleSelected);
	
				Label title = new Label("<html><h2><b><center>Target PERCENT MODIFIED Mode</center>\n</b></h2></html>", ContentMode.HTML);
				final TextField bundleName = new TextField("Bundle Name: ");
				bundleName.setValue(bundleInfo.getBname());
				final TextField bundleLead = new TextField("Bundle Lead :");
				bundleLead.setValue(bundleInfo.getBundleLead());
				hl1.setSpacing(true);
				hl1.addComponents(title, bundleName, bundleLead);
				
				hl2.setSpacing(true);
				final TextField designUpdatePer = new TextField("Design Update %");
				designUpdatePer.setValue(Integer.toString(bundleInfo.getDesignUpPer()));
				final TextField designReviewPer = new TextField("Design Review %");
				designReviewPer.setValue(Integer.toString(bundleInfo.getDesignReviewPer()));
				final TextField ruleCreatePer = new TextField("Rule Create %");
				ruleCreatePer.setValue(Integer.toString(bundleInfo.getRuleCreatePer()));
				final TextField ruleTestPer = new TextField("Rule Test %");
				ruleTestPer.setValue(Integer.toString(bundleInfo.getRuleTestPer()));
				final TextField ruleReviewPer = new TextField("Rule Review %");
				ruleReviewPer.setValue(Integer.toString(bundleInfo.getRuleReviewPer()));
				hl2.addComponents(designUpdatePer, designReviewPer, ruleCreatePer, ruleTestPer, ruleReviewPer);
				
				
				final TextField scenarioDataPer = new TextField("Scenario Data %");
				scenarioDataPer.setValue(Integer.toString(bundleInfo.getScenarioDataPer()));
				final TextField scenarioTestPer = new TextField("Scenario Test %");
				scenarioTestPer.setValue(Integer.toString(bundleInfo.getScenarioTestPer()));
				final TextField testReviewPer = new TextField("TestReview %");
				testReviewPer.setValue(Integer.toString(bundleInfo.getTestReviewPer()));
				final TextField overAllReviewPer = new TextField("Overall Review %");
				overAllReviewPer.setValue(Integer.toString(bundleInfo.getOverallReviewPer()));
				final TextField regressionPer = new TextField("Regression %" );
				regressionPer.setValue(Integer.toString(bundleInfo.getRegressionPer()));
				hl3.setSpacing(true);
				hl3.addComponents(scenarioDataPer, scenarioTestPer, testReviewPer, overAllReviewPer, regressionPer);
				
				Button clear = new Button("Clear");
				Button save = new Button("Save");
				
				hl4.setSpacing(true);
				hl4.addComponents(clear, save);
			
				final TextArea reason = new TextArea("Reason of Modification");
				reason.setWidth("850px");
				hl5.addComponent(reason);
				
				editbundleTargePercent.addComponents(hl1, hl2, hl3, hl5, hl4);
				
				save.addClickListener(new ClickListener() {
					
					@Override
					public void buttonClick(ClickEvent event) {

						updateBundle.updateBundlePercent(designUpdatePer, designReviewPer, ruleCreatePer, ruleTestPer, ruleReviewPer, scenarioDataPer, scenarioTestPer, testReviewPer, overAllReviewPer, regressionPer, bundleName, bundleLead);
						insert.changeLogTargetDt(bundleName, bundleLead, reason);	
					}
				});
			}
		});
		
		return editbundleTargePercent;
	}
}
