package com.example.myvaadin;

import java.io.Serializable;
import java.util.Arrays;
import java.util.List;

import javax.naming.directory.SearchResult;

import sun.awt.VerticalBagLayout;

import com.vaadin.data.Property.ValueChangeEvent;
import com.vaadin.data.Property.ValueChangeListener;
import com.vaadin.event.ItemClickEvent;
import com.vaadin.event.ItemClickEvent.ItemClickListener;
import com.vaadin.event.MouseEvents.ClickEvent;
import com.vaadin.ui.AbsoluteLayout;
import com.vaadin.ui.Button;
import com.vaadin.ui.Button.ClickListener;
import com.vaadin.ui.CheckBox;
import com.vaadin.ui.Component.Listener;
import com.vaadin.ui.CustomComponent;
import com.vaadin.ui.HorizontalLayout;
import com.vaadin.ui.Label;
import com.vaadin.ui.Layout;
import com.vaadin.ui.Notification;
import com.vaadin.ui.PopupView;
import com.vaadin.ui.TabSheet;
import com.vaadin.ui.TextField;
import com.vaadin.ui.TreeTable;
import com.vaadin.ui.Component.Event;
import com.vaadin.ui.PopupView.PopupVisibilityEvent;
import com.vaadin.ui.PopupView.PopupVisibilityListener;
import com.vaadin.ui.Select;
import com.vaadin.ui.Table;
import com.vaadin.ui.TextArea;
import com.vaadin.ui.Upload;
import com.vaadin.ui.VerticalLayout;

public class ScenarioClass implements Serializable {

	private static final long serialVersionUID = 1L;

	private Upload upload;
	AbsoluteLayout tablelayout = new AbsoluteLayout();
	VerticalLayout vlayout = new VerticalLayout();
	Table table = new Table();
	UploadExample uploadInput;
	AbsoluteLayout uploadLayout = new AbsoluteLayout();
//	TheButtons loadButton;
	@SuppressWarnings("deprecation")
	public AbsoluteLayout createLayoutUpload() {
//		uploadLayout.setWidth("100%");
//		uploadLayout.setHeight("100%");

		uploadLayout.setSizeFull();
		uploadInput = new UploadExample();
//		TheButtons search = new TheButtons();
//		Button searchb = search.getSearchButton();

		uploadLayout.addComponent(uploadInput, "left: 5px; top: 10px;");
//		uploadLayout.addComponent(searchb, "left: 5px; bottom:10px");
		return uploadLayout;
	}

	
//	TheButtons theb = new TheButtons();
	@SuppressWarnings("deprecation")
	public AbsoluteLayout createTable() {

		Select bundleSelect = new Select("Select Bundle");
		bundleSelect.addItem("Bundle 1");
		bundleSelect.addItem("Bundle 2");
		bundleSelect.addItem("Bundle 3");
		tablelayout.addComponent(bundleSelect, "left: 5px; top: 20px;");

		table.setPageLength(9);
		table.setWidth("100%");
		bundleSelect.addValueChangeListener(new ValueChangeListener() {

			@Override
			public void valueChange(ValueChangeEvent event) {

				// table.addStyleName("components-inside");
				table.addContainerProperty("Rule#", Label.class, null);
				table.addContainerProperty("RuleGroup", Label.class, null);
				table.addContainerProperty("CQ", Label.class, null);
				table.addContainerProperty("Details", PopupView.class, null);

				List<ComponentBean> beans = Arrays.asList(new ComponentBean(
						"111", "RuleGroup1", "12", "CQ1", true),
						new ComponentBean("2111", "RuleGroup1", "22", "CQ1",
								true), new ComponentBean("3111", "RuleGroup3",
								"32", "CQ1", true), new ComponentBean("2111",
								"RuleGroup1", "22", "CQ1", true),
						new ComponentBean("3111", "RuleGroup3", "32", "CQ1",
								true), new ComponentBean("2111", "RuleGroup1",
								"22", "CQ1", true), new ComponentBean("3111",
								"RuleGroup3", "32", "CQ1", true),
						new ComponentBean("2111", "RuleGroup1", "22", "CQ1",
								true), new ComponentBean("3111", "RuleGroup3",
								"32", "CQ1", true), new ComponentBean("2111",
								"RuleGroup1", "22", "CQ1", true),
						new ComponentBean("3111", "RuleGroup3", "32", "CQ1",
								true), new ComponentBean("4111",
								"RuleGroup423", "152", "CQ1", true));

				for (int i = 0; i < beans.size(); i++) {

					final Integer itemId = new Integer(i);

					TextArea area = new TextArea();

					// Put some content in it
					area.setValue("A row\n" + "Another row\n"
							+ "Yet another row");

					area.setValue(beans.get(i).getRuleNo() + "\n"
							+ beans.get(i).getRuleGroup() + "\n"
							+ "Yet another row");

					final Label ruleNo = new Label(beans.get(i).getRuleNo());
					final CheckBox checkbox = new CheckBox();
					checkbox.setValue(beans.get(i).value);
					final Label commentsField = new Label();
					commentsField.setValue(beans.get(i).getRuleGroup());
					final Label ruleGroup = new Label(beans.get(i).getVer());
					// final Button detailbutton = new Button("show details");
					final PopupView detailbutton = new PopupView(
							"show rule details", area);

					detailbutton
							.addPopupVisibilityListener(new PopupVisibilityListener() {

								@Override
								public void popupVisibilityChange(
										PopupVisibilityEvent event) {

									detailbutton.setVisible(true);
									tablelayout.addComponent(detailbutton,
											"left: 550px; top:60px");
								}
							});
					detailbutton.setData(itemId);

					table.addItem(new Object[] { ruleNo, commentsField,
							ruleGroup, detailbutton }, itemId);

//					TheButtons theb = new TheButtons("loadButton");
					final Button loadButton = new Button("move on");
					tablelayout.addComponent(loadButton, "left: 5px; bottom: 10px;");
					loadButton.addClickListener(new ClickListener() {
						
						@Override
						public void buttonClick(com.vaadin.ui.Button.ClickEvent event) {
							
//							tablelayout.removeComponent(loadButton);
							tablelayout.removeAllComponents();
							getSearchResult();
						}

						private void getSearchResult() {

//							tablelayout.removeComponent(table);
							AbsoluteLayout uploadFile = createLayoutUpload();
//							sclayout1.addComponent(b);
							tablelayout.addComponent(uploadFile, "left: 5px; top: 0px;");
							tablelayout.setWidth("100%");
							tablelayout.setHeight("80%");
//							tablelayout.addComponent(b, "left: 5px; bottom: 10px;");
							
						}
					});
					
//					tablelayout.addComponent(loadButton, "left: 5px; bottom: 10px;");
					
//					tablelayout.addComponent(table, "left: 5px; top: 50px;");
				}
			}

		});

//		loadButton = theb.createLoadButton();
//		tablelayout.addComponent(loadButton, "left: 5px; bottom: 10px;");
		tablelayout.addComponent(table, "left: 5px; top: 50px;");

		return tablelayout;

	}

	Layout buttonRoot = new HorizontalLayout();


}
