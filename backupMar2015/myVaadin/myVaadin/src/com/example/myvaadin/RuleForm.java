package com.example.myvaadin;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.support.rowset.SqlRowSet;

import com.example.User.LogInInfo;
import com.example.database.Insert;
import com.example.database.Query;
import com.example.database.Template;
import com.example.database.Update;
import com.example.pojo.RoleSegregration;
import com.example.service.RoleSegregationLogic;
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
import com.vaadin.server.VaadinSession;
import com.vaadin.ui.AbsoluteLayout;
import com.vaadin.ui.Alignment;
import com.vaadin.ui.Button;
import com.vaadin.ui.Button.ClickListener;
import com.vaadin.ui.ComboBox;
import com.vaadin.ui.Component;
import com.vaadin.ui.DateField;
import com.vaadin.ui.FormLayout;
import com.vaadin.ui.GridLayout;
import com.vaadin.ui.HorizontalLayout;
import com.vaadin.ui.Label;
import com.vaadin.ui.LoginForm;
import com.vaadin.ui.Notification;
import com.vaadin.ui.OptionGroup;
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
	int numberOfRules = 0;
	int rulesCreateCompletionPercent = 0;
	int rulesTestCompletionPercent = 0;
	OptionGroup group = getOptionAction();
	String userName = (String) VaadinSession.getCurrent().getAttribute(
			"userName");

	JdbcTemplate jt = Template.getTemplate();
	Query query = new Query();
	ComboBox cqList = new ComboBox();
	ComboBox ruleNameList = new ComboBox();
	Update updateInto = new Update();
	TextField ver = new TextField();
	TextField newRuleSignup = new TextField();

	final FormLayout ruleReviewed = new FormLayout();
	final ComboBox bundles = new ComboBox("Select Bundle");
	final Button cancel = new Button("Cancel");
	final ComboBox cqs = new ComboBox("Select CQs");
	ComboBox ruleCombo = new ComboBox("Select Rule Group");
	RulePojo ruleBean = new RulePojo();
	ComboBox versionCombo = new ComboBox("Select Version");
	
	final TextField rgName = new TextField();

	final RoleSegregationLogic logicClass = new RoleSegregationLogic();
	
	HorizontalLayout hlforRule = new HorizontalLayout();

	String bundle;
	String cqName;

	public FormLayout createForm() {
		final FormLayout ruleform = new FormLayout();
		final HorizontalLayout hl1 = new HorizontalLayout();
		hl1.setSpacing(true);

		final HorizontalLayout hl2 = new HorizontalLayout();

		final ComboBox bundleList = new ComboBox();
		bundleList.setInputPrompt("Select Bundle");

		cqList.setInputPrompt("select cq");

		ruleNameList.setInputPrompt("select rule group");

		List<String> bundlefromDB = query.getBundles();

		for (String bundle : bundlefromDB) {
			bundleList.addItem(bundle);
			hl1.addComponent(bundleList);
		}
		// final Button saveButton = new Button("Save");
		final HorizontalLayout hl4 = new HorizontalLayout();
		hl4.setSpacing(true);

		bundleList.addValueChangeListener(new ValueChangeListener() {

			@Override
			public void valueChange(ValueChangeEvent event) {
				final Button newRuleButton = new Button("Add New Rule");
				hl1.removeComponent(cqList);
				hl2.removeComponent(newRuleButton);
				cqList = new ComboBox();

				query = new Query();
				List<String> cqQuery = query.getCQlist(bundleList.getValue()
						.toString());

				for (String cq : cqQuery) {

					cqList.addItem(cq);
				}
				hl1.addComponent(cqList);

				cqList.addValueChangeListener(new ValueChangeListener() {

					@Override
					public void valueChange(ValueChangeEvent event) {
						hl2.removeAllComponents();

						ruleNameList = new ComboBox();
						String cqSelected = event.getProperty().getValue()
								.toString();

						String selectedbundle = bundleList.getValue()
								.toString();
						String selectedcq = cqList.getValue().toString();

						query = new Query();
						List<RulePojo> ruleQuery = query.getRulelist(
								selectedbundle, cqSelected);

						for (RulePojo rule : ruleQuery) {

							Notification.show("getting rule pojo");
							ruleNameList.addItem(rule.getRuleGroupName()
									.toString());
						}
						hl2.addComponent(ruleNameList);
						hl2.addComponent(newRuleButton);
						hl2.setSpacing(true);

						newRuleButton.addClickListener(new ClickListener() {

							@Override
							public void buttonClick(ClickEvent event) {

								// TextField newRuleGroup =
								// createNewRuleGroup();
								hl2.removeAllComponents();
								Button save = new Button("Save");
								final TextField newRuleGroup = new TextField();
								newRuleGroup.setWidth("240px");
								newRuleGroup.setInputPrompt("Enter new Rule");
								ver = new TextField();
								ver.setWidth("80px");
								ver.setInputPrompt("Version #");

								hl2.addComponent(newRuleGroup);
								hl2.addComponent(ver);
								hl2.addComponent(save);

								save.addClickListener(new ClickListener() {

									@Override
									public void buttonClick(ClickEvent event) {

										hl2.removeComponent(newRuleButton);
										String bundle = bundleList.getValue()
												.toString();
										String cqName = cqList.getValue()
												.toString();
										String newRule = newRuleGroup
												.getValue().toString();

										String version = ver.getValue()
												.toString();

										Insert insert = new Insert();
										Notification.show(bundle + "  "
												+ cqName + "  " + newRule
												+ "  " + version);
										insert.addNewRule(bundle, cqName, newRule, version);
										try {
											jt.update("update statusdata set rulename ='"
													+ newRule
													+ "' where bname ='"
													+ bundle
													+ "' and cqname ='"
													+ cqName + "'");
											Notification.show("success");
										} catch (Exception e) {
											// TODO: handle exception
											Notification.show("failed.....");
										}

									}
								});
							}

						});

						hl2.addComponent(ruleNameList);
						hl2.addComponent(newRuleButton);
						hl2.setSpacing(true);
					}
				});

				ruleNameList.addValueChangeListener(new ValueChangeListener() {
					TextField version;//
					Button addNew = new Button("add New Version");

					@Override
					public void valueChange(ValueChangeEvent event) {

						final String itemSelected = event.getProperty()
								.getValue().toString();
						version = new TextField();
						if (!itemSelected.isEmpty()) {
							version = new TextField();
							version.setInputPrompt("Version xxxxx");
							version.setWidth("120px");
							bundle = bundleList.getValue().toString();
							cqName = cqList.getValue().toString();
							hl2.addComponent(version);
							hl2.addComponent(addNew);
						}
						addNew.addClickListener(new ClickListener() {

							@Override
							public void buttonClick(ClickEvent event) {
								// Notification.show(bundle+"  "+cqName+"  "+itemSelected+"  "+version.getValue().toString());
								Insert insert = new Insert();
								insert.addNewRule(bundle, cqName, itemSelected,
										version.getValue().toString());
								updateInto.updateRuleStatus(bundle, cqName,
										itemSelected, version.getValue()
												.toString());
							}
						});

					}

				});

			}

			private HorizontalLayout getRuleEntryForm(String bundleName,
					String cqName) {
				Notification.show("inside method");
				final ComboBox ruleNameList = new ComboBox();
				Notification.show(bundleName + " " + cqName);
				SqlRowSet ruleList = jt
						.queryForRowSet("select rulename from rulegroup where bundleName ='"
								+ bundle + "'" + "and cqname ='" + cqName + "'");
				while (ruleList.next()) {
					String ruleName = ruleList.getString(1).toString();
					ruleNameList.addItem(ruleName);
					ruleNameList.addItem("+ New Rule Group");
				}

				// HorizontalLayout hlforRule = new HorizontalLayout();
				rgName.setInputPrompt("Type here to add new Rule Group");
				rgName.setWidth("450px");
				// ver = new TextField();
				// ver.setInputPrompt("Enter Version");
				ver.setWidth("120px");
				// hlforRule.addComponent(rgName);
				hlforRule.addComponent(ruleNameList);
				hlforRule.addComponent(ver);
				return hlforRule;
			}

		});

		ruleform.addComponent(hl1);
		ruleform.addComponent(hl2);
		ruleform.addComponent(hl4);

		return ruleform;
	}

	public TextField createNewRuleGroup() {
		// TODO Auto-generated method stub
		return null;
	}

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
				existingRuleField.setValue(event.getProperty().getValue()
						.toString());
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

				Notification.show("Saving into Database" + existingRule + "  "
						+ existingRuleVersion);
			}
		});

		flayout.addComponent(hl);
		flayout.addComponent(hl1);

		return flayout;

	}

	@SuppressWarnings("deprecation")
	public AbsoluteLayout getRuleDetails() {

		AbsoluteLayout abl = new AbsoluteLayout();
		RulePojo rpojo = new RulePojo();
		// HorizontalPanel hpanel = new HorizontalPanel();
		VerticalLayout vpanel = new VerticalLayout();
		// Label ruleName = rpojo.setRuleGroupName("rulegroup");
		rpojo.setRuleGroupName("ruleGroup");
		rpojo.setCqName("cqname");
		rpojo.setBundleName("bundleName");

		// Label l1 = new Label(rpojo.getRuleGroupName());
		// l1.setCaption("Rule Name : ");
		// Label l2 = new Label(rpojo.getCqNumber());
		// Label l3 = new Label(rpojo.getBundleNumber());
		TextField l1 = new TextField("CQ Name: ", rpojo.getRuleGroupName());
		TextField l2 = new TextField("Bundle Name : ", rpojo.getBundleName());
		l1.setReadOnly(true);
		l2.setReadOnly(true);
		final Table table = new Table("Rule Group Name");
		table.addContainerProperty("Rule No", String.class, null);
		table.addContainerProperty("Rule description", TextArea.class, null);
		table.addContainerProperty("edit", com.vaadin.ui.CheckBox.class, null);
		table.addContainerProperty("save", Button.class, null);

		table.setSizeFull();
		// Insert this data
		String people[][] = {
				{ "1113", "IF A = '1'" + "DEC_INFO_ELIGIBILE=true" },
				{
						"2323",
						"IF DEC_INFO=true" + "TX_GENERAL_FORM= 'available'"
								+ "THEN " + "Decision = true" },
				{
						"2333V",
						"IF DEC_INFO=true" + "TX_GENERAL_FORM= 'available'"
								+ "THEN " + "Decision = true" },
				{
						"O2323",
						"IF DEC_INFO=true" + "TX_GENERAL_FORM= 'available'"
								+ "THEN " + "Decision = true" },
				{
						"1323ier",
						"IF DEC_INFO=true" + "TX_GENERAL_FORM= 'available'"
								+ "THEN " + "Decision = true" },
				{ "223432423", "if a= 1" + "then setPriority = 1" }, };

		// Insert the data and the additional component column
		for (int i = 0; i < people.length; i++) {
			final Object itemId = new Integer(i);

			final TextArea area = new TextArea(null, people[i][1]);
			area.setReadOnly(true);
			area.setSizeFull();
			// area.setRows(2);

			final com.vaadin.ui.CheckBox checkbox = new com.vaadin.ui.CheckBox();
			checkbox.setData(itemId); // Store item ID
			checkbox.addValueChangeListener(new ValueChangeListener() {
				@Override
				public void valueChange(ValueChangeEvent event) {
					// Notification.show(event.getProperty().getValue().toString());
					boolean checkvalue = event.getProperty().getValue()
							.toString() != null;
					TextArea textArea = new TextArea();
					if (checkvalue) {
						Object itemId = checkbox.getData();

						// As the property (column) type is a component type,
						// we just get the property and its value to get the
						// component.
						textArea = ((TextArea) table.getContainerProperty(
								itemId, "Rule description").getValue());
						textArea.setSizeFull();
						textArea.setReadOnly(false);
						// Notification.show("readonly false");

					} else {
						textArea.setReadOnly(true);
						// Notification.show("readonly true");

					}
					// Get the stored item ID
					// textArea.setReadOnly(false);
					// Modify the referenced component
					// boolean value = ((Boolean)
					// checkbox.getValue()).booleanValue();
					// textArea.setEnabled(!value);
				}
			});
			checkbox.setImmediate(false);

			Button b = new Button("save it");

			b.addClickListener(new ClickListener() {

				@Override
				public void buttonClick(ClickEvent event) {
					// Notification.show(itemId.toString());
					// Notification.show(area.getValue().toString());
					MySub sub = new MySub();
					// Add it to the root component
					UI.getCurrent().addWindow(sub);
				}
			});
			// Add an item with two components
			table.addItem(new Object[] { people[i][0], area, checkbox, b },
					itemId);
		}

		table.setPageLength(5);
		// hpanel.addComponent(table);
		vpanel.setSpacing(true);
		// vpanel.addComponent(l1);
		// vpanel.addComponent(l2);
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

		// hl.addComponent(role, 2);
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

		// select Data

		// Insert this data
		String people[][] = { { "TX_General_Galileo", "Design Update" },
				{ "FD_MAE_Monnier", "Design Review", "", "" },
				{ "PRICING_PROG_Väisälä", "Rule Creation", "", "" },
				{ "RDP_MOD_Oterma", "Rule Testing", "", "" },
				{ "VET_SValtaoja", "Scenario ", "", "" }, };

		// Insert the data and the additional component column
		for (int i = 0; i < people.length; i++) {
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
			area.setRows(2);// displaying in 2 rows

			final com.vaadin.ui.CheckBox checkbox = new com.vaadin.ui.CheckBox();
			checkbox.setData(itemId); // Store item ID
			checkbox.addValueChangeListener(new ValueChangeListener() {

				@Override
				public void valueChange(ValueChangeEvent event) {

					// Get the stored item ID
					final Object itemId = checkbox.getData();

					// As the property (column) type is a component type,
					// we just get the property and its value to get the
					// component.
					TextArea textArea = ((TextArea) table.getContainerProperty(
							itemId, "Stage").getValue());

					// Modify the referenced component
					boolean value = ((Boolean) checkbox.getValue())
							.booleanValue();
					textArea.setEnabled(!value);

					select.setEnabled(true);
					dateField.setEnabled(true);
					saveButton.setEnabled(true);

					saveButton.addClickListener(new ClickListener() {

						@Override
						public void buttonClick(ClickEvent event) {
							TextArea selectData = (TextArea) table
									.getContainerProperty(itemId, "Stage")
									.getValue();
							ComboBox comboData = (ComboBox) table
									.getContainerProperty(itemId, "New Stage")
									.getValue();
							Notification.show("Modifying from "
									+ selectData.getValue() + " to >  "
									+ comboData.getValue());
						}
					});
				}
			});
			checkbox.setImmediate(true);

			// Add an item with two components
			table.addItem(new Object[] { checkbox, people[i][0], area, select,
					dateField, saveButton }, itemId);
		}

		table.setPageLength(table.size());
		return table;
	}

	public FormLayout getSignup(String role) {

		if (role.equalsIgnoreCase("Rule Creator")) {
			role = "Rule Creator";
			ruleBean.setSignUpRole("Rule Creator");
		} else {
			role = "Rule Tester";
			ruleBean.setSignUpRole("Rule Tester");
		}

		final FormLayout ruleSignup = new FormLayout();

		final ComboBox user = new ComboBox("User Name");
		// String userName = (String)
		// VaadinSession.getCurrent().getAttribute("userName");
		user.addItem(userName);
		user.setInputPrompt(userName);

		final HorizontalLayout hl = new HorizontalLayout();


final ComboBox bundlesList = new ComboBox();
		bundlesList.setInputPrompt("- Select Bundle -");
		
		Query querybundle = new Query();
		List<String> bundleList = querybundle.getBundles();
		for (String bundle : bundleList) {
			bundlesList.addItem(bundle);
		}

		hl.addComponent(bundlesList);
		hl.setSpacing(true);
		
		ruleSignup.addComponent(hl);

		final ComboBox cqlist = new ComboBox();
		cqlist.setInputPrompt("- Select CQ -");

		bundlesList.addValueChangeListener(new ValueChangeListener() {
			@Override
			public void valueChange(ValueChangeEvent event) {

				cqlist.removeAllItems();
				String bundleSelect = event.getProperty().getValue().toString();
				ruleBean.setBundleName(bundleSelect);
	
				System.out.println(ruleBean.getBundleName()+"==== is the Bundle Name   ");			
				
				if (!bundleSelect.isEmpty()) {
					Query queryCQ = new Query();
					List<String> cqList = queryCQ.getCQlist(bundlesList
							.getValue().toString());

					for (String cq : cqList) {

						cqlist.addItem(cq);

					}
				}

				hl.addComponent(cqlist);
			}
		});

		final ComboBox ruleGroup = new ComboBox();
		cqlist.addValueChangeListener(new ValueChangeListener() {

			@Override
			public void valueChange(ValueChangeEvent event) {

				String selectedcq = event.getProperty().getValue().toString();
				ruleBean.setCqName(selectedcq);
				
				System.out.println("bundle name = : "+ruleBean.getBundleName().toString());
						RoleSegregration roleSignedup = logicClass.checkConfirmityRuleReview(userName, ruleBean.getSignUpRole(), ruleBean.getBundleName(), ruleBean.getCqName());

						Notification.show("inside Logic Result....");
						
						System.out.println("Is Eligible : "+roleSignedup.isEligible());
						
						if(roleSignedup.isEligible()){
						
							Button signupRuleButton = new Button("Sign Up rule");

							final TextField roleSigned = new TextField("Signing Up As :");
							roleSigned.setValue(ruleBean.getSignUpRole());
							roleSigned.setReadOnly(true);
							hl.addComponent(roleSigned);
							hl.setSpacing(true);

							HorizontalLayout hl2 = new HorizontalLayout();
							final DateField signupDate = new DateField("Sign Up Dt.");
							signupDate.setValue(new Date());
							hl2.addComponent(user);
							hl2.addComponent(roleSigned);
							hl2.addComponent(signupDate);
							hl2.addComponent(signupRuleButton);
							hl2.setSpacing(true);

							VerticalLayout vl = new VerticalLayout();
							vl.addComponent(hl);
							vl.addComponent(hl2);
							vl.setSpacing(true);

							ruleSignup.addComponent(vl);

							signupRuleButton.addClickListener(new ClickListener() {
								//
											@Override
											public void buttonClick(ClickEvent event) {
								
												String rname = "initial value";
												Insert insertDao = new Insert();
								
												String bname = bundlesList.getValue().toString();
												String cname = cqlist.getValue().toString();
												// String rname = newRuleSignup.getValue().toString();
												// String rname = ruleGroup.getValue().toString();
												String signedRole = ruleBean.getSignUpRole();
												// String userName = userName;
												Date dateInput = signupDate.getValue();
												java.sql.Date signUpdt = DateConversion.getSqlDate(dateInput
														.getTime());
								
												if (!newRuleSignup.getValue().toString().isEmpty()) {
													rname = newRuleSignup.getValue().toString();
												} else {
													rname = ruleGroup.getValue().toString();
												}
												updateInto.signupRuleGroup(bname, cname, signedRole, userName,
														signUpdt, rname, ver);
											}
										});

							if (!selectedcq.isEmpty()) {
								bundlesList.setEnabled(false);
								String selectedbundle = bundlesList.getValue().toString();

								Query querycq = new Query();
								List<String> ruleList = querycq.getRuleOnly(selectedbundle,
										selectedcq);
								for (String rule : ruleList) {
									ruleGroup.addItem(rule);
								}

								final Button addRule = new Button("Add New Rule");
								hl.addComponent(ruleGroup);
								hl.addComponent(addRule);

								
								ruleGroup.addValueChangeListener(new ValueChangeListener() {

									@Override
									public void valueChange(ValueChangeEvent event) {

										String ruleGroupSelected = event.getProperty()
												.getValue().toString();
										hl.removeComponent(addRule);
										if (!ruleGroupSelected.isEmpty()) {
											newRuleSignup.setValue(ruleGroupSelected);
										}

									}
								});
								addRule.addClickListener(new ClickListener() {

									@Override
									public void buttonClick(ClickEvent event) {
										hl.removeComponent(ruleGroup);
										hl.removeComponent(addRule);

										newRuleSignup = new TextField();
										newRuleSignup.setWidth("240px");
										newRuleSignup.setInputPrompt("Enter new Rule");
										ver = new TextField();
										ver.setWidth("80px");
										ver.setInputPrompt("Version #");
										hl.addComponent(newRuleSignup);
										hl.addComponent(ver);

									}
								});
							}
						
							
//							signupRuleButton.addClickListener(new ClickListener() {
//								
//								@Override
//								public void buttonClick(ClickEvent event) {
//
//									updateInto.signupRuleGroup(ruleBean.getBundleNumber(), ruleBean.getCqNumber(), ruleBean.getSignUpRole(),
//											user, signupDate, newRuleGroup, roleSigned);
//								}
//							});	
						
						} else {
						
							Notification.show("Nope !!!!! ");

							Table signedUpRoles = logicClass.getTable(userName, ruleBean.getSignUpRole(), roleSignedup);
							
							VerticalLayout vlforStatus = new VerticalLayout();
							vlforStatus.setSpacing(true);
							
							Label status = new Label("You can not sign up as ' "+ruleBean.getSignUpRole()+" ', because you had signed Up as following Roles :");
							vlforStatus.addComponent(status);
							vlforStatus.addComponent(signedUpRoles);
							ruleSignup.addComponent(vlforStatus);
						
//							Button signupButton = new Button("Sign Up");
//
//							final TextField roleSigned = new TextField("Signing Up As :");
//							roleSigned.setValue(ruleBean.getSignUpRole());
//							roleSigned.setReadOnly(true);
//							hl.addComponent(roleSigned);
//							hl.setSpacing(true);
//
//							HorizontalLayout hl2 = new HorizontalLayout();
//							final DateField signupDate = new DateField("Sign Up Dt.");
//							signupDate.setValue(new Date());
//							hl2.addComponent(user);
//							hl2.addComponent(roleSigned);
//							hl2.addComponent(signupDate);
//							hl2.addComponent(signupButton);
//							hl2.setSpacing(true);
//
//							VerticalLayout vl = new VerticalLayout();
//							vl.addComponent(hl);
//							vl.addComponent(hl2);
//							vl.setSpacing(true);
//
//							ruleSignup.addComponent(vl);
						}


						
//				if (!selectedcq.isEmpty()) {
//					bundlesList.setEnabled(false);
//					String selectedbundle = bundlesList.getValue().toString();
//
//					Query querycq = new Query();
//					List<String> ruleList = querycq.getRuleOnly(selectedbundle,
//							selectedcq);
//					for (String rule : ruleList) {
//						ruleGroup.addItem(rule);
//					}
//
//					final Button addRule = new Button("Add New Rule");
//					hl.addComponent(ruleGroup);
//					hl.addComponent(addRule);
//
//					ruleGroup.addValueChangeListener(new ValueChangeListener() {
//
//						@Override
//						public void valueChange(ValueChangeEvent event) {
//
//							String ruleGroupSelected = event.getProperty()
//									.getValue().toString();
//							hl.removeComponent(addRule);
//							if (!ruleGroupSelected.isEmpty()) {
//								newRuleSignup.setValue(ruleGroupSelected);
//							}
//
//						}
//					});
//					addRule.addClickListener(new ClickListener() {
//
//						@Override
//						public void buttonClick(ClickEvent event) {
//							hl.removeComponent(ruleGroup);
//							hl.removeComponent(addRule);
//
//							newRuleSignup = new TextField();
//							newRuleSignup.setWidth("240px");
//							newRuleSignup.setInputPrompt("Enter new Rule");
//							ver = new TextField();
//							ver.setWidth("80px");
//							ver.setInputPrompt("Version #");
//							hl.addComponent(newRuleSignup);
//							hl.addComponent(ver);
//
//						}
//					});
//				}

			}
		});

//		Button signupButton = new Button("Sign Up");
//
//		final TextField roleSigned = new TextField("Signing Up As :");
//		roleSigned.setValue(role);
//		roleSigned.setReadOnly(true);
//		hl.addComponent(roleSigned);
//		hl.setSpacing(true);
//
//		HorizontalLayout hl2 = new HorizontalLayout();
//		final DateField signupDate = new DateField("Sign Up Dt.");
//		signupDate.setValue(new Date());
//		hl2.addComponent(user);
//		hl2.addComponent(roleSigned);
//		hl2.addComponent(signupDate);
//		hl2.addComponent(signupButton);
//		hl2.setSpacing(true);
//
//		VerticalLayout vl = new VerticalLayout();
//		vl.addComponent(hl);
//		vl.addComponent(hl2);
//		vl.setSpacing(true);
//
//		ruleSignup.addComponent(vl);

//		signupButton.addClickListener(new ClickListener() {
//
//			@Override
//			public void buttonClick(ClickEvent event) {
//
//				String rname = "initial value";
//				Insert insertDao = new Insert();
//
//				String bname = bundlesList.getValue().toString();
//				String cname = cqlist.getValue().toString();
//				// String rname = newRuleSignup.getValue().toString();
//				// String rname = ruleGroup.getValue().toString();
//				String signedRole = ruleBean.getSignUpRole();
//				// String userName = userName;
//				Date dateInput = signupDate.getValue();
//				java.sql.Date signUpdt = DateConversion.getSqlDate(dateInput
//						.getTime());
//
//				if (!newRuleSignup.getValue().toString().isEmpty()) {
//					rname = newRuleSignup.getValue().toString();
//				} else {
//					rname = ruleGroup.getValue().toString();
//				}
//				updateInto.signupRuleGroup(bname, cname, signedRole, userName,
//						signUpdt, rname, ver);
//			}
//		});
		return ruleSignup;

	}

	public FormLayout updateRuleGroup() {
		final FormLayout updatedRuleGroup = new FormLayout();

		final DateField updatedDt = new DateField();

		updatedDt.setValue(new Date());
		Date inputDt = updatedDt.getValue();
		final HorizontalLayout hl = new HorizontalLayout();
		final ComboBox role = new ComboBox("Role as :");
		role.addItem("Rule Creator");
		role.addItem("Rule Tester");

		final ComboBox user = new ComboBox("Select User Name");
		user.setInputPrompt(userName);
		user.addItem(userName);

		final ComboBox bundles = new ComboBox("Select Bundle");
		final TextField hours = new TextField("Actual Hours");
		hours.setValue("8");

		List<String> bundleList = query.getBundles();
		for (String bundle : bundleList) {
			bundles.addItem(bundle);
		}

		final ComboBox cqs = new ComboBox("Select CQs");

		bundles.addValueChangeListener(new ValueChangeListener() {
			@Override
			public void valueChange(ValueChangeEvent event) {

				String selectedBundle = event.getProperty().getValue()
						.toString();
				// ComboBox cqs = new ComboBox("Select CQs");
				List<String> cqQuery = query.getCQlist(selectedBundle);
				for (String cq : cqQuery) {
					cqs.addItem(cq);

				}

			}
		});

		final ComboBox ruleGroup = new ComboBox("Select Rule ");
		cqs.addValueChangeListener(new ValueChangeListener() {

			@Override
			public void valueChange(ValueChangeEvent event) {

				String selectedcq = event.getProperty().getValue().toString();

				if (!selectedcq.isEmpty()) {
					bundles.setEnabled(false);
					String selectedbundle = bundles.getValue().toString();

					Query querycq = new Query();
					List<RulePojo> ruleList = querycq.getRulelist(
							selectedbundle, selectedcq);

					int ruleCreatecompl = 0;
					int ruleTestcompl = 0;
					for (RulePojo rule : ruleList) {

						ruleGroup.addItem(rule.getRuleGroupName().toString());
						ruleCreatecompl = rule.getRuleCreatePercent();
						ruleTestcompl = rule.getRuleTestPercent();
						rulesCreateCompletionPercent = rulesCreateCompletionPercent
								+ ruleCreatecompl;
						rulesTestCompletionPercent = rulesTestCompletionPercent
								+ ruleTestcompl;

						numberOfRules++;
					}

					System.out.println(rulesCreateCompletionPercent
							+ " .....+++++.." + rulesTestCompletionPercent);

					updatedRuleGroup.addComponent(ruleGroup);
					updatedRuleGroup.addComponent(role);
					updatedRuleGroup.addComponent(updatedDt);
					updatedRuleGroup.addComponent(hours);
					updatedRuleGroup.addComponent(hl);

				}

			}
		});

		Button updateButton = new Button("Update");
		updateButton.addClickListener(new ClickListener() {

			@Override
			public void buttonClick(ClickEvent event) {

				updateInto.updateRuleGroup(bundles.getValue().toString(), cqs
						.getValue().toString(), role.getValue().toString(),
						userName, updatedDt, hours.getValue().toString(),
						numberOfRules, rulesCreateCompletionPercent,
						rulesTestCompletionPercent, ruleGroup.getValue()
								.toString());
			}
		});

		Button clear = new Button("Clear");

		hl.addComponent(clear);
		hl.addComponent(updateButton);
		hl.setSpacing(true);

		updatedRuleGroup.addComponent(user);
		updatedRuleGroup.addComponent(bundles);
		updatedRuleGroup.addComponent(cqs);
		// updatedRuleGroup.addComponent(version);
		// updatedRuleGroup.addComponent(updatedDt);
		// updatedRuleGroup.addComponent(hl);
		//
		return updatedRuleGroup;
	}

	public FormLayout getSignupRuleReview(final String role) {

		final FormLayout ruleReviewSignup = new FormLayout();
		final ComboBox cqName = new ComboBox();
		final ComboBox user = new ComboBox("User Name");
		// String userName = (String)
		// VaadinSession.getCurrent().getAttribute("userName");
		user.addItem(userName);
		user.setInputPrompt(userName);

		final HorizontalLayout hl = new HorizontalLayout();
		hl.setSpacing(true);

		final ComboBox bundlesList = new ComboBox();
		bundlesList.setInputPrompt("- Select Bundle -");

		Query querybundle = new Query();
		List<String> bundleList = querybundle.getBundles();
		for (String bundle : bundleList) {
			bundlesList.addItem(bundle);
		}

		hl.addComponent(bundlesList);

		bundlesList.addValueChangeListener(new ValueChangeListener() {
			
			@Override
			public void valueChange(ValueChangeEvent event) {

				final String bundleSelected = event.getProperty().getValue().toString();
				RoleSegregration roleSignedup = logicClass.checkConfirmityRuleReview(userName, role, bundleSelected);

				System.out.println("Is Eligible : "+roleSignedup.isEligible());
				
				if(roleSignedup.isEligible()){
					Notification.show("is Eligible..........");
					final DateField signUpDt = new DateField();
					signUpDt.setValue(new Date());
					Button signUp = new Button("Sign Up As Rule Reviewer");
					
					hl.addComponent(signUpDt);
					hl.addComponent(signUp);

					System.out.println("is Eligible........");
					
					signUp.addClickListener(new ClickListener() {
						
						@Override
						public void buttonClick(ClickEvent event) {
							updateInto.updateRuleReviewSignUp(bundleSelected, role, userName, signUpDt);
						}
					});
					
				} else {
				
					Notification.show("Nope !!!!! ");

					Table signedUpRoles = logicClass.getTable(userName, role, roleSignedup);
					
					VerticalLayout vlforStatus = new VerticalLayout();
					vlforStatus.setSpacing(true);
					
					Label status = new Label("You can not sign up as ' "+role+" ', because you had signed Up as following Roles :");
					vlforStatus.addComponent(status);
					vlforStatus.addComponent(signedUpRoles);
					ruleReviewSignup.addComponent(vlforStatus);
					
				}


			}
		});

		ruleReviewSignup.addComponent(hl);
		return ruleReviewSignup;
	}

	public FormLayout getRuleReviewUpdate() {

//		public FormLayout getRuleReviewUpdate(String bundle) {
		
		cancel.addClickListener(new ClickListener() {
			
			@Override
			public void buttonClick(ClickEvent event) {

				ruleReviewed.removeComponent(bundles);
				ruleReviewed.removeComponent(group);
				
				group = getOptionAction();
				ruleReviewed.removeComponent(cancel);
				ruleReviewed.addComponent(group);
			}
		});
		ruleReviewed.addComponent(group);
		
		return ruleReviewed;
	}

	private OptionGroup getOptionAction() {

		group = new OptionGroup("Select Action");
		group.addItems("Update All Rule Group");
		group.addItems("Except few Update All");
		group.addItems("Create Incident");
		group.addItems("Update one Rule Group");
		
		group.addValueChangeListener(new ValueChangeListener() {
			
			@Override
			public void valueChange(ValueChangeEvent event) {

				String selectedAction = event.getProperty().getValue().toString();

				if(selectedAction.equalsIgnoreCase("Update All Rule Group")){
					group.removeItem("Create Incident");
					group.removeItem("Update one Rule Group");
					group.removeItem("Except few Update All");

					getBundles();
		//Update All except Rule Group
					bundles.addValueChangeListener(new ValueChangeListener() {
						
						@Override
						public void valueChange(ValueChangeEvent event) {
							String selectedBundle = event.getProperty().getValue().toString();
							ruleBean.setBundleName(selectedBundle);
							getCQs(selectedBundle);
						}
						
					});

					cqs.addValueChangeListener(new ValueChangeListener() {
						
						@Override
						public void valueChange(ValueChangeEvent event) {

//							int numberOfRules = 1;
							String selectedCQ = event.getProperty().getValue().toString();
							ruleBean.setCqName(selectedCQ);
							final Table table = getRulesInTable(selectedCQ, ruleBean.getBundleName());
							final Button updateButton = new Button("OK Update");
							ruleReviewed.addComponent(table);
							ruleReviewed.addComponent(updateButton);
							
							HorizontalLayout hl = new HorizontalLayout();
							ruleReviewed.removeComponent(bundles);
							ruleReviewed.removeComponent(cqs);
							ruleReviewed.removeComponent(table);
							ruleReviewed.removeComponent(updateButton);
							hl.setSpacing(true);
							hl.addComponents(bundles, cqs, table);
							
							HorizontalLayout hl2 = new HorizontalLayout();
							hl2.setSpacing(true);
							TextField updatedby = new TextField("Updated by");
							updatedby.setValue(userName);
							
							final TextField hours = new TextField("Hours Worked");
							hours.setValue("4");
							
							final DateField updateDt = new DateField("Update Date");
							updateDt.setValue(new Date());
							
							hl2.addComponents(updatedby, hours, updateDt);
							ruleReviewed.addComponent(hl);
							ruleReviewed.addComponent(hl2);
							ruleReviewed.addComponent(updateButton);
							updateButton.addClickListener(new ClickListener() {
								
								@Override
								public void buttonClick(ClickEvent event) {

									updateInto.updateRuleReview(ruleBean.getBundleName(), ruleBean.getCqName(), userName, hours.getValue(), updateDt.getValue(), ruleBean.getNoOfRules());
								}
							});
						}

					});
					
//					updateInto.updateRuleReview();
					
				} else if (selectedAction.equalsIgnoreCase("Except few Update All")){
					group.removeItem("Update All Rule Group");
					group.removeItem("Update one Rule Group");
					group.removeItem("Create Incident");
//					
					getBundles();
					
					bundles.addValueChangeListener(new ValueChangeListener() {
						
						@Override
						public void valueChange(ValueChangeEvent event) {

							String selectedBundle = event.getProperty().getValue().toString();
							ruleBean.setBundleName(selectedBundle);
							getCQs(selectedBundle);
						
						}
					});
					
					cqs.addValueChangeListener(new ValueChangeListener() {
						
						@Override
						public void valueChange(ValueChangeEvent event) {
				
							OptionGroup exceptField = new OptionGroup("Except   :");
//							exceptField.setValue("exclude following rule group....");
							ruleReviewed.addComponent(exceptField);
	
							String selectedCq = event.getProperty().getValue().toString();
					
							getRules(selectedCq, ruleBean.getBundleName());
							
							ruleBean.setCqName(event.getProperty().getValue().toString());
							
							ruleReviewed.addComponent(ruleCombo);
							ruleReviewed.addComponent(versionCombo);
						}
					
					
					});
					
					ruleCombo.addValueChangeListener(new ValueChangeListener() {
						
						@Override
						public void valueChange(ValueChangeEvent event) {
							String ruleSelected = event.getProperty().getValue().toString();
							ruleBean.setRuleGroupName(ruleSelected);
							
							

						}
					});
					
					versionCombo.addValueChangeListener(new ValueChangeListener() {
						
						@Override
						public void valueChange(ValueChangeEvent event) {
							HorizontalLayout hl = new HorizontalLayout();
							HorizontalLayout hl2 = new HorizontalLayout();
							Button upDateFew = new Button("Update This");
							ruleReviewed.removeComponent(hl);
							ruleReviewed.removeComponent(hl2);
							ruleReviewed.removeComponent(upDateFew);
							
							String ver = event.getProperty().getValue().toString();
							ruleBean.setVersion(Integer.parseInt(ver));
							final List<RulePojo> ruleList = query.getRulelist(ruleBean.getBundleName(), ruleBean.getCqName(), ruleBean.getRuleGroupName(), ver);
//							Table table = getRulesInTable(ruleBean.getCqNumber(), ruleBean.getBundleNumber(), ruleBean.getRuleGroupName(), ruleBean.getVersion());
							Table table1 = getRulesInTableList2(ruleList);
							
							final DateField incidentDt = new DateField("Update Dt.");
							incidentDt.setValue(new Date());
							
							
							hl.setSpacing(true);
							hl.addComponents(ruleCombo, table1);
							
							hl2.setSpacing(true);
							final TextField hoursWorked = new TextField("Hours Worked");
							hoursWorked.setValue("3");
							TextField user = new TextField("Updating by");
							user.setValue(userName);
							hl2.addComponents(user,incidentDt, hoursWorked);
							
							ruleReviewed.addComponent(hl);
							ruleReviewed.addComponent(hl2);
							ruleReviewed.addComponent(upDateFew);
							
							upDateFew.addClickListener(new ClickListener() {
								
								@Override
								public void buttonClick(ClickEvent event) {

									int n=0;
									for (RulePojo rule : ruleList) {
										updateInto.updateRuleGroupWithException(rule.getBundleName(), rule.getCqName(), rule.getRuleGroupName(), rule.getVersion(), hoursWorked, incidentDt, ruleBean.getNoOfRules(), userName);
										n++;
										System.out.println("....."+n);
									}
									
//									updateInto.updateRuleGroupWithException(List ruleBean, cqNumber, ruleGroupName, version, hoursWorked, incidentDt, noOfRules);
								}
							});
						}

						private Table getRulesInTableList2(List<RulePojo> ruleList) {

							if(ruleList.isEmpty()){
								Notification.show("its emply");
							} else {
								
								Notification.show("noooooooooo");
							}
							Table table = new Table("Rules to be Updated oooooooo");
							table.setHeight("250px");
							table.addContainerProperty("Rule Group", String.class, null);
							table.addContainerProperty("Version", Integer.class, null);
							table.addContainerProperty("UpDate Dt.", Date.class, null);
							
//							List<RulePojo> ruleList = query.getRulelist(bundleName, selectedCQ, ruleNameExcluded, ver);
							
							int n=1;
							for (RulePojo ruleName : ruleList) {
								
//								ruleBean.setBundleNumber(ruleName.getBundleNumber());
//								ruleBean.setCqNumber(ruleName.getCqNumber());
//								ruleBean.setRuleGroupName(ruleName.getRuleGroupName());
//								ruleBean.setVersion(ruleName.getVersion());
								System.out.println("....."+ruleName.getBundleName()+"  ===== "+ruleName.getCqName());
								table.addItem(new Object[]{ruleName.getRuleGroupName(), ruleName.getVersion(), new Date()}, n);
								n++;
							}
							ruleBean.setNoOfRules(n);
							return table;
						}
					});
					
				} else if (selectedAction.equalsIgnoreCase("Create Incident")){
//					group.removeAllItems();
					group.removeItem("Update All Rule Group");
					group.removeItem("Update one Rule Group");
					group.removeItem("Except few Update All");
					
					getBundles();
					bundles.addValueChangeListener(new ValueChangeListener() {
						
						@Override
						public void valueChange(ValueChangeEvent event) {

							String selectedBundle = event.getProperty().getValue().toString();
							ruleBean.setBundleName(selectedBundle);
							getCQs(selectedBundle);
						}
					});

					cqs.addValueChangeListener(new ValueChangeListener() {
						
						@Override
						public void valueChange(ValueChangeEvent event) {
				
							String selectedCq = event.getProperty().getValue().toString();
//							getRules(selectedCq, ruleBean.getBundleNumber());
					
//							Notification.show("inside cqlist listner");
							ruleBean.setCqName(event.getProperty().getValue().toString());
							getRules(ruleBean.getCqName(), ruleBean.getBundleName() );
							

						}
						
						
					});
					
					ruleCombo.addValueChangeListener(new ValueChangeListener() {
						
						@Override
						public void valueChange(ValueChangeEvent event) {

							ruleBean.setRuleGroupName(event.getProperty().getValue().toString());

//							final ComboBox ver = new ComboBox("Version :");
//							Object version = ruleBean.getVersion();
//							ver.setConvertedValue(version.toString());
			
							final DateField incidentDt = new DateField("Incident Dt.");
							final TextField incidentNo = new TextField("Incident Number");
							
							Button createIncident = new Button("Create Incident");
							incidentDt.setValue(new Date());
							
							ruleReviewed.addComponent(ruleCombo);
							ruleReviewed.addComponent(versionCombo);
							ruleReviewed.addComponent(incidentNo);
							ruleReviewed.addComponent(incidentDt);
							ruleReviewed.addComponent(createIncident);
							
							createIncident.addClickListener(new ClickListener() {
								
								@Override
								public void buttonClick(ClickEvent event) {

									java.sql.Date mySql = DateConversion.getSqlDate(incidentDt.getValue().getTime());
									System.out.println(ruleBean.getBundleName()+"   "+ruleBean.getCqName()+"  "+ruleBean.getRuleGroupName());


									updateInto.updateRuleReviewIncident(incidentNo.getValue().toString(), mySql, ruleBean.getBundleName(), ruleBean.getCqName(), ruleBean.getRuleGroupName(), versionCombo.getValue().toString());

								}
							});	
						}
					});
					ruleReviewed.addComponent(bundles);
					ruleReviewed.addComponent(cancel);
					
				} else if (selectedAction.equalsIgnoreCase("Update one Rule Group")) {
					group.removeItem("Create Incident");
					group.removeItem("Update All Rule Group");
					group.removeItem("Except few Update All");
					
					getBundles();
//					final RulePojo rulePojo = new RulePojo();
					bundles.addValueChangeListener(new ValueChangeListener() {
						@Override
						public void valueChange(ValueChangeEvent event) {
							String selectedBundle = event.getProperty().getValue().toString();
							ruleBean.setBundleName(selectedBundle);
							getCQs(selectedBundle);
						}
					});
					
					cqs.addValueChangeListener(new ValueChangeListener() {
						
						@Override
						public void valueChange(ValueChangeEvent event) {

							String selectedCq = event.getProperty().getValue().toString();
							ruleBean.setCqName(selectedCq);
							getRules(selectedCq, ruleBean.getBundleName());
						}
					});
					
					ruleReviewed.addComponent(bundles);
					ruleReviewed.addComponent(cancel);
					
					ruleCombo.addValueChangeListener(new ValueChangeListener() {
					
					@Override
					public void valueChange(ValueChangeEvent event) {
						String ruleSelected = event.getProperty().getValue().toString();
						
						ruleBean.setRuleGroupName(ruleSelected);
						Notification.show(ruleBean.getRuleGroupName().toString());
						
						
						final DateField updateDt = new DateField("Update Dt.");
						Button updateButton = new Button("Update");
						updateDt.setValue(new Date());
						final TextField hours = new TextField("Hours worked ");
						hours.setValue("2");
						ruleReviewed.addComponent(versionCombo);
						ruleReviewed.addComponent(hours);
						ruleReviewed.addComponent(updateDt);
						ruleReviewed.addComponent(updateButton);
						
						updateButton.addClickListener(new ClickListener() {
							
							@Override
							public void buttonClick(ClickEvent event) {

								System.out.println(ruleBean.getBundleName()+" "+ruleBean.getCqName()+" "+ruleBean.getRuleNumber());
								updateInto.updateRuleReviewIncidentClose(ruleBean.getBundleName(), ruleBean.getCqName(), ruleBean.getRuleGroupName(), versionCombo.getValue().toString(), updateDt.getValue(), hours);
							}
						});
					}
				});

				}
					
			}

			public void getBundles() {
				
				List<String> bundleList = query.getBundles();
				for (String bundle : bundleList) {
					bundles.addItem(bundle);
			}
				ruleReviewed.addComponent(bundles);
				ruleReviewed.addComponent(cancel);				
			}
			
			public void getCQs(String selectedBundle){
				ruleReviewed.removeComponent(cancel);
				List<String> cqQuery = query.getCQlist(selectedBundle);
				for (String cq : cqQuery) {
					cqs.addItem(cq);

				}
				ruleReviewed.addComponent(cqs);

			}
			
			public void getRules(String cqName, String bundleName){
				ruleReviewed.removeComponent(cancel);
				ruleReviewed.removeComponent(ruleCombo);
				
				List<RulePojo> ruleList = query.getRulelist(bundleName, cqName);
				
				
				for (RulePojo ruleName : ruleList) {
					ruleBean.setRuleGroupName(ruleName.getRuleGroupName());
					ruleBean.setVersion(ruleName.getVersion());
					ruleCombo.addItem(ruleName.getRuleGroupName());
					versionCombo.addItem(ruleName.getVersion());
					System.out.println(ruleBean.getRuleGroupName()+"  "+ruleBean.getVersion());
				}

				ruleReviewed.addComponent(ruleCombo);
				ruleReviewed.addComponent(versionCombo);

				
			}
		});
		return group;
	}

	public Table getRulesInTable(String selectedCQ, String bundleName) {
//	public Table getRulesInTable(List rulePojo) {
		int n=1;
		Table table = new Table();
		table.setHeight("250px");
		table.addContainerProperty("Rule Group", String.class, null);
		table.addContainerProperty("Version", Integer.class, null);
		table.addContainerProperty("UpDate Dt.", Date.class, null);
		
		List<RulePojo> ruleList = query.getRulelist(bundleName, selectedCQ);
		
		
		for (RulePojo ruleName : ruleList) {
			ruleBean.setRuleGroupName(ruleName.getRuleGroupName());
			ruleBean.setVersion(ruleName.getVersion());
			ruleBean.setNoOfRules(n);
			
			table.addItem(new Object[]{ruleBean.getRuleGroupName(), ruleBean.getVersion(), new Date()}, n);
			n++;
		}
		
		return table;
	}

	public Table getRulesInTable(String selectedCQ, String bundleName, String ruleNameExcluded, int ver) {

		Table table = new Table("Rules to be Updated");
		table.setHeight("250px");
		table.addContainerProperty("Rule Group", String.class, null);
		table.addContainerProperty("Version", Integer.class, null);
		table.addContainerProperty("UpDate Dt.", Date.class, null);
		
		List<RulePojo> ruleList = query.getRulelist(bundleName, selectedCQ, ruleNameExcluded, ver);
		
		int n=1;
		for (RulePojo ruleName : ruleList) {
			ruleBean.setBundleName(ruleName.getBundleName());
			ruleBean.setCqName(ruleName.getCqName());
			ruleBean.setRuleGroupName(ruleName.getRuleGroupName());
			ruleBean.setVersion(ruleName.getVersion());
			
			table.addItem(new Object[]{ruleBean.getRuleGroupName(), ruleBean.getVersion(), new Date()}, n);
			n++;
		}
		ruleBean.setNoOfRules(n);
		return table;
	}
}
