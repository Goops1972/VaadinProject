package com.example.myvaadin;

import java.awt.List;
import java.awt.Window;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Iterator;

import com.google.gwt.dev.generator.ast.WhileLoop;
import com.vaadin.data.Container.ItemSetChangeEvent;
import com.vaadin.data.Container.PropertySetChangeEvent;
import com.vaadin.data.Property.ValueChangeEvent;
import com.vaadin.event.ItemClickEvent;
import com.vaadin.event.ItemClickEvent.ItemClickListener;
import com.vaadin.ui.Button;
import com.vaadin.ui.Notification;
import com.vaadin.ui.Tree;
import com.vaadin.ui.UI;
import com.vaadin.ui.Tree.CollapseListener;
import com.vaadin.ui.TreeTable;
import com.vaadin.ui.Tree.CollapseEvent;

public class CreateBundleDetails extends TreeTable {

	// final TreeTable ttable = new TreeTable("My TreeTable");

	TreeTable ttable = new TreeTable("My TreeTable");

	public CreateBundleDetails() {
		ttable.addContainerProperty("Details", String.class, "");
		ttable.setWidth("15em");
		ttable.addItem(new Object[] { "Packages" }, 1);
		ttable.addItem(new Object[] { "Bundle List" }, 2);
		ttable.addItem(new Object[] { "CQ List" }, 3);
		ttable.addItem(new Object[] { "RuleList" }, 4);
		ttable.addItem(new Object[] { "DataTable List" }, 5);
	}

	public TreeTable getBundleDetails() {

		//fetch data from database 
		//1) select * bundle name 
		//2) select all cq name where cq_b_id = bundle_b_id, 
		//3) select * from rule where rule_cq_id = cq_cq_id && cq_b_id = bundle_b_id
		
		ttable.addItem(new Object[] { "bd1" }, 11);
		ttable.addItem(new Object[] { "bd2" }, 12);
		ttable.addItem(new Object[] { "bd3" }, 13);

		ttable.addItem(new Object[] { "bd19" }, 14);
		ttable.addItem(new Object[] { "bd20" }, 15);
		ttable.addItem(new Object[] { "bd38" }, 16);

		ttable.addItem(new Object[] { "cq1tt" }, 17);
		ttable.addItem(new Object[] { "rl1ss" }, 18);
		ttable.addItem(new Object[] { "last one" }, 19);

		// adding tree
		// ttable.addItemAfter(getTree(), 22);
		// ttable.setParent(22, 4);

		ttable.setParent(11, 1);
		ttable.setParent(12, 1);
		ttable.setParent(13, 1);

		ttable.setParent(111, 11);
		ttable.setParent(112, 11);

		// for second tab
		ttable.setParent(14, 2);
		ttable.setParent(15, 2);
		ttable.setParent(17, 3);
		ttable.setParent(16, 14);
		ttable.setParent(19, 17);
		ttable.setParent(18, 17);
		ttable.setParent(19, 17);

		//

		// Expand the tree
		ttable.addItemClickListener(new ItemClickListener() {
			@Override
			public void itemClick(ItemClickEvent event) {
//				Notification.show(event.getItem().toString()+"me!!!!");
//				String p = ttable.getParent(17).toString();
			}
		});

		ttable.setCollapsed(0, true);
		ttable.setCollapsed(2, true);

		return ttable;

	}

	@SuppressWarnings("deprecation")
	public Tree getTree() {

		final Tree tree = new Tree("Package Details");
		tree.setEnabled(true);
		tree.addItem("Bundle Details");
		tree.addItem("Testing Details");
		tree.addItem("Variables");
		tree.addItem("Messages");
		tree.addItem("Incidents");

//make database call and store all in rule set (bundle details, cq details, rules details
		
		tree.addValueChangeListener(new ValueChangeListener() {

			@Override
			public void valueChange(com.vaadin.data.Property.ValueChangeEvent event) {

				//creating bundles
				java.util.List<String> blist = Arrays.asList("bddomino",
						"bdbbb", "bdBundles");
				java.util.List<String> clist = Arrays.asList("CQdomino",
						"CQbbb", "CQccc", "Rules");
				java.util.List<String> rlist = Arrays.asList(
						"RuleCQdomino", "RuleCQbbb", "RuleCQccc",
						"Rulenumbers");

				
				if (event.getProperty().getValue() !=null) {

					String eventValue = event.getProperty().getValue().toString();

				
					if (eventValue.equalsIgnoreCase("Bundle Details")&& !eventValue.equals(null)) {
						
						eventValue = event.getProperty().getValue().toString();

						for (String bundle : blist) {
							tree.addItem(bundle);
							tree.setParent(bundle, "Bundle Details");
						}

					} else if (eventValue.startsWith("bd") && !eventValue.equals(null)) {
						
						for (String cq : clist) {
							tree.addItem(cq);
							
							tree.setParent(cq, eventValue);

						}
					} else if (eventValue.startsWith("CQ") && !eventValue.equals(null)) {
//						Notification.show(tree.getParent().getCaption().toString());
						
						for (String rule : rlist) {
							tree.addItem(rule);
							tree.setParent(rule, eventValue);
							System.out.println("......from rule list .....parent : "+tree.getParent(rule)+"   >>  "+rule);
						}
					} else if (eventValue.startsWith("Rule")&& !eventValue.equals(null)) {

//						Notification.show(eventValue+"Rule.........");

					} else if (eventValue.equalsIgnoreCase("Testing Details") & !eventValue.equals(null)){

						java.util.List<String> testList = Arrays.asList("tsdomino","tsbbb", "tsBundles");

						String testParent = eventValue.toString();
						for (String test : testList) {
							tree.addItem(test);
							tree.setParent(test, testParent);
						}
					}
					else {
						Notification.show("nothing to ADD");
					}
				} else {
					Notification.show("it was null");
				}

			}

		});

		return tree;

	}
}
