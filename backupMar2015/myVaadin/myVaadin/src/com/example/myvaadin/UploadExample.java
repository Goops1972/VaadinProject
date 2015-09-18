package com.example.myvaadin;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;

import org.apache.commons.io.output.NullOutputStream;

import com.vaadin.server.FileResource;
import com.vaadin.server.Page;
import com.vaadin.ui.AbsoluteLayout;
import com.vaadin.ui.Button;
import com.vaadin.ui.Button.ClickListener;
import com.vaadin.ui.CustomComponent;
import com.vaadin.ui.Image;
import com.vaadin.ui.Label;
import com.vaadin.ui.Notification;
import com.vaadin.ui.Panel;
import com.vaadin.ui.TabSheet;
import com.vaadin.ui.Table;
import com.vaadin.ui.Upload;
import com.vaadin.ui.Button.ClickEvent;
import com.vaadin.ui.Upload.Receiver;
import com.vaadin.ui.Upload.SucceededEvent;
import com.vaadin.ui.Upload.SucceededListener;
import com.vaadin.ui.VerticalLayout;

public class UploadExample extends CustomComponent {
	VerticalLayout verticalLayout = new VerticalLayout();
	Upload upload;
	Panel panel;

	// AbsoluteLayout tablelayout = new AbsoluteLayout();
	public UploadExample() {

		panel = new Panel();
		panel.setWidth("100%");
		ImageReceiver receiver = new ImageReceiver();
		upload = new Upload("Upload the File here", receiver);

		verticalLayout.setVisible(true);
		verticalLayout.setSpacing(true);
		panel.setContent(verticalLayout);
		verticalLayout.setWidth("100%");
		verticalLayout.addComponent(upload);

		setCompositionRoot(panel);

	}

	class ImageReceiver implements Receiver, SucceededListener {
		private static final long serialVersionUID = -1276759102490466761L;

		public File file;

		public OutputStream receiveUpload(String filename, String mimeType) {
			// Create upload stream

			FileOutputStream fos = null; // Stream to write to
			try {
				// Open the file for writing.

				file = new File("/Users/gshrestha/Desktop/" + filename);
				fos = new FileOutputStream(file);

				// need method to search in Trace file

				Label l = new Label("my new searies");
				TabSheet tabSheet = new TabSheet();
				VerticalLayout tab1 = new VerticalLayout();
				VerticalLayout tab2 = new VerticalLayout();
				VerticalLayout tab3 = new VerticalLayout();
				tab3.setCaption("in-Eligible Loans");
				tab1.setCaption("Loan Summary");
				tab2.setCaption("Eligible Loans");
				tab1.addComponent(new Label("gopu1"));
				tab2.addComponent(new Button("gopu2"));
				tab3.addComponent(l);
				tabSheet.addTab(tab1);
				tabSheet.addTab(tab2);
				tabSheet.addTab(tab3);

				verticalLayout.addComponent(tabSheet);

				verticalLayout.removeComponent(upload);

				Notification.show("Done writing!!!! " + file.getAbsolutePath());

			} catch (final java.io.FileNotFoundException e) {
				Notification.show("Please select file to upload!!!!");
				OutputStream ops = new OutputStream() {
					
					@Override
					public void write(int b) throws IOException {
						// TODO Auto-generated method stub
						
					}
				};
				return ops;
			}
			return fos; // Return the output stream to write to

		}

		public void uploadSucceeded(SucceededEvent event) {
			// Show the uploaded file in the image viewer
			System.out.println("upload Success................");

			final Image image = new Image("Uploaded Image");
			image.setVisible(true);
			image.setSource(new FileResource(file));

		}
	};

}
