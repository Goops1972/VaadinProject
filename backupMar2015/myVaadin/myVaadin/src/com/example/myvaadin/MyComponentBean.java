package com.example.myvaadin;

import java.io.Serializable;

import com.vaadin.ui.Button;
import com.vaadin.ui.CheckBox;
import com.vaadin.ui.Label;
import com.vaadin.ui.Notification;
import com.vaadin.ui.TextField;

public class MyComponentBean implements Serializable{

	TextField br = new TextField();
	CheckBox  checkbox  = new CheckBox();
	TextField textfield = new TextField();
    TextField label = new TextField();
    
    
    public MyComponentBean(String text, String labelv, String label2, boolean value) {
//    	Notification.show(text);
    	text = "my Test";
        br.setValue(text);
        checkbox.setValue(value);
        textfield.setValue(labelv);
        label.setValue(label2);
    	
    }
    
    






	public MyComponentBean(String string, String string2, String string3) {
		// TODO Auto-generated constructor stub
		
	}










	public TextField getB() {
		return br;
	}





	public void setB(TextField b) {
		this.br = b;
	}





	public CheckBox getCheckbox() {
        return checkbox;
    }
    public void setCheckbox(CheckBox checkbox) {
        this.checkbox = checkbox;
    }
    
    public TextField getTextfield() {
        return textfield;
    }
    public void setTextfield(TextField textfield) {
        this.textfield = textfield;
    }

}
