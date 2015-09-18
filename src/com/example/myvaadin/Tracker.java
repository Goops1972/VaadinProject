package com.example.myvaadin;

import java.util.Date;

import com.vaadin.data.Item;
import com.vaadin.ui.Notification;
import com.vaadin.ui.Table;

public class Tracker {

	final Table table = new Table("The Brightest Stars");
	Object newItemId = table.addItem();
	final Item row1 = table.getItem(newItemId);

	public Tracker(){

		table.setWidth("100%");
		table.setHeight("100%");
		table.setColumnWidth("CQName", 150);
		table.setColumnWidth("Status", 120);
		table.setColumnWidth("Start Dt.", 110);
		table.setColumnWidth("End Dt.", 110);
		table.setColumnWidth("Done by", 150);
		
		table.addContainerProperty("CQName", String.class, null);
		table.addContainerProperty("Status", String.class, null);
		table.addContainerProperty("Start Dt.",  Date.class, null);
		table.addContainerProperty("End Dt.",  Date.class, null);
		table.addContainerProperty("Done by",  String.class, null);
		
	
	}
	int no=1;
	public Table createTable(String status, int no) {
//		System.out.println("......"+i);
		if(status.equalsIgnoreCase("Design")){
//			row1.getItemProperty("CQName").setValue("Sirius");
//			row1.getItemProperty("Status").setValue(status);
//			row1.getItemProperty("Start Dt.").setValue(new Date());
//			row1.getItemProperty("End Dt.").setValue(new Date());
//			row1.getItemProperty("Done by").setValue("Gopu1");
			// Add a few other rows using shorthand addItem()
//			table.addItem(new Object[]{"Canopus", status, new Date(), new Date(), "gopu2.", no});
			table.addItem(new Object[]{"Canopus", status, new Date(), new Date(), "gopu2...test", 2});
System.out.println("xxxxx-----"+no);
				
		} else if (status.equalsIgnoreCase("Design Review")){
//			row1.getItemProperty("CQName").setValue("Sirius Design Review");
//			row1.getItemProperty("Status").setValue(status);
//			row1.getItemProperty("Start Dt.").setValue(new Date());
//			row1.getItemProperty("End Dt.").setValue(new Date());
//			row1.getItemProperty("Done by").setValue("Gopu2");
//			
//			// Add a few other rows using shorthand addItem()
//			table.addItem(new Object[]{"Canopus rev", status, new Date(), new Date(), "gopu-3"}, no);
			table.addItem(new Object[]{"Canopus rev from revwi", status, new Date(), new Date(), "gopu3-4"}, 4);

		} else if (status.equalsIgnoreCase("Rule Creation")){
		
//			row1.getItemProperty("CQName").setValue("Rule Creation");
//			row1.getItemProperty("Status").setValue(status);
//			row1.getItemProperty("Start Dt.").setValue(new Date());
//			row1.getItemProperty("End Dt.").setValue(new Date());
//			row1.getItemProperty("Done by").setValue("Gopu3");
			
//			table.addItem(new Object[]{"Rule Create rev", status, new Date(), new Date(), "gopu2-5"}, no);
			table.addItem(new Object[]{"Rule Cres rev ", status, new Date(), new Date(), "gopu3-6"}, 6);
			
		} else if (status.equalsIgnoreCase("Rule Test")){
		
//			row1.getItemProperty("CQName").setValue("rule Test");
//			row1.getItemProperty("Status").setValue(status);
//			row1.getItemProperty("Start Dt.").setValue(new Date());
//			row1.getItemProperty("End Dt.").setValue(new Date());
//			row1.getItemProperty("Done by").setValue("Gopu4");
			
//			table.addItem(new Object[]{"R Test rev", status, new Date(), new Date(), "gopu7"}, no);
			table.addItem(new Object[]{"Rle rev from revwi", status, new Date(), new Date(), "gopu3-8"}, 8);
			
		}
		
		// Show 5 rows
		table.setSelectable(true);

		return table;
	}

}
