package com.example.myvaadin;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

import com.vaadin.server.FileResource;
import com.vaadin.server.NoOutputStreamException;
import com.vaadin.server.Page;
import com.vaadin.server.StreamResource;
import com.vaadin.server.StreamResource.StreamSource;
import com.vaadin.server.UploadException;
import com.vaadin.ui.CustomComponent;
import com.vaadin.ui.Image;
import com.vaadin.ui.Label;
import com.vaadin.ui.Layout;
import com.vaadin.ui.Notification;
import com.vaadin.ui.Panel;
import com.vaadin.ui.ProgressBar;
import com.vaadin.ui.Upload;
import com.vaadin.ui.Upload.FailedEvent;
import com.vaadin.ui.Upload.FailedListener;
import com.vaadin.ui.Upload.ProgressListener;
import com.vaadin.ui.Upload.Receiver;
import com.vaadin.ui.Upload.StartedEvent;
import com.vaadin.ui.Upload.StartedListener;
import com.vaadin.ui.Upload.SucceededEvent;
import com.vaadin.ui.Upload.SucceededListener;
import com.vaadin.ui.VerticalLayout;


public class UploadBox extends CustomComponent implements Receiver, ProgressListener, FailedListener, SucceededListener {

	 private static final long serialVersionUID = -46336015006190050L;

     // Put upload in this memory buffer that grows automatically
     ByteArrayOutputStream os =
         new ByteArrayOutputStream(10240);

     // Name of the uploaded file
     String filename;
     File file;     
     ProgressBar progress = new ProgressBar(0.0f);
     
     // Show uploaded file in this placeholder
     Image image = new Image("Uploaded Image");
     
     public UploadBox() {
         // Create the upload component and handle all its events
         Upload upload = new Upload("click below button", null);
         upload.setReceiver(this);
         upload.addProgressListener(this);
         upload.addFailedListener(this);
         upload.addSucceededListener(this);
         
         // Put the upload and image display in a panel
         Panel panel = new Panel();
         panel.setWidth("400px");
//         panel.setSizeFull();
         VerticalLayout panelContent = new VerticalLayout();
         panelContent.setSpacing(true);
         panel.setContent(panelContent);
         panelContent.addComponent(upload);
         panelContent.addComponent(progress);
         panelContent.addComponent(image);
         
         progress.setVisible(true);
         image.setVisible(true);
         
         setCompositionRoot(panel);
     } 
	@Override
	public void uploadSucceeded(SucceededEvent event) {
		// TODO Auto-generated method stub
		image.setVisible(true);
//        image.setCaption("Uploaded Image " + filename +" has length " + os.toByteArray().length);
        image.setCaption(filename+" upload and saved successfully  at location "+file.getAbsolutePath());
        // Display the image as a stream resource from
        // the memory buffer
        StreamSource source = new StreamSource() {
            private static final long serialVersionUID = -4905654404647215809L;

            public InputStream getStream() {
                return new ByteArrayInputStream(os.toByteArray());
            }
        };
        
        if (image.getSource() == null)
            // Create a new stream resource
            image.setSource(new StreamResource(source, filename));
        else { // Reuse the old resource
            StreamResource resource =
                    (StreamResource) image.getSource();
            resource.setStreamSource(source);
            resource.setFilename(filename);
        }

        image.markAsDirty();

	}

	@Override
	public void uploadFailed(FailedEvent event){
//		Notification.show(event.getSource().toString());
//		if(event.getReason().toString().equalsIgnoreCase("Could not open file")){
//		
			try{
				
			} catch(NullPointerException e){
				Notification.show("Please Select a file, ---------"+Notification.Type.ERROR_MESSAGE);	
				
			}
			
		}
		

	@Override
	public void updateProgress(long readBytes, long contentLength) {
		progress.setVisible(true);
        if (contentLength == -1)
            progress.setIndeterminate(true);
        else {
            progress.setIndeterminate(false);
            progress.setValue(((float)readBytes) /
                              ((float)contentLength));
        }
	}

	@Override
	public OutputStream receiveUpload(String filename, String mimeType) {
		this.filename = filename;
        
        FileOutputStream fos = null; // Stream to write to
        try {
            // Open the file for writing.
        	
            file = new File("/Users/gshrestha/Desktop/" + filename);
            
            fos = new FileOutputStream(file);
            Notification.show("Written on "+file.getAbsolutePath());
            
        } catch (FileNotFoundException e) {
        	Notification.show("-----Please Select a file, because the error is   ");
        	return null;
        }
        	return fos; // Return the output stream to write to
    
		
		
//		os.reset(); // Needed to allow re-uploading
//        return os;
        
	} 
	

}
