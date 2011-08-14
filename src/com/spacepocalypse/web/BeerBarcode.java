package com.spacepocalypse.web;

import java.io.File;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.Iterator;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.fileupload.FileItem;
import org.apache.commons.fileupload.FileItemFactory;
import org.apache.commons.fileupload.FileUploadException;
import org.apache.commons.fileupload.disk.DiskFileItemFactory;
import org.apache.commons.fileupload.servlet.ServletFileUpload;

import com.spacepocalypse.data.BarcodeReader;

public class BeerBarcode extends HttpServlet {
	private static final long serialVersionUID = -1211806233703954101L;
	private static final String FILE_STORE_LOCATION = "/home/dylan/written_files/";
	private boolean writeToFile = true;

	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse resp)
			throws ServletException, IOException {
		resp.setContentType("text/html");
		PrintWriter out = resp.getWriter();
		out.println("<html><body>");
		
		if (ServletFileUpload.isMultipartContent(req)) {
			// Create a factory for disk-based file items
			FileItemFactory factory = new DiskFileItemFactory();

			// Create a new file upload handler
			ServletFileUpload upload = new ServletFileUpload(factory);
			
			List<FileItem> items = null;
			// Parse the request
			try {
				items = upload.parseRequest(req);
			} catch (FileUploadException e) {
				e.printStackTrace();
			}
			
			if (items == null) {
				out.println("Error: parsed items are null");
				out.println("</body></html>");
				return;
			}
			
			
			
			// Process the uploaded items
			Iterator<FileItem> iter = items.iterator();
			while (iter.hasNext()) {
			    FileItem item = iter.next();

			    if (item.isFormField()) {
			        processFormField(item);
			    } else {
			        String filePath = processUploadedFile(item);
			        String barCodeData = BarcodeReader.decodeDataFromImage(filePath);
			        if (filePath == null) {
			        	out.println("Error decoding image.");
			        } else {
			        	out.println("Barcode data decoded as: [" + barCodeData + "]");
			        }
			    }
			}
		} 
		out.println("</body></html>");
		
	}
	
	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp)
			throws ServletException, IOException {
		resp.setContentType("text/html");
		PrintWriter out = resp.getWriter();
		out.println("<html><body>");
		out.println(
				"<form enctype='multipart/form-data' method='POST' action='beerbarcode'>" +
				"<input type='file' name='mptest'>" +
				"<input type='submit' value='upload'>" +
				"</form>");
	}

	private String processUploadedFile(FileItem item) {
		String fieldName = item.getFieldName();
	    String fileName = item.getName();
	    String contentType = item.getContentType();
	    boolean isInMemory = item.isInMemory();
	    long sizeInBytes = item.getSize();
	    File uploadedFile = null;
	    // Process a file upload
	    if (isWriteToFile()) {
	        uploadedFile = new File(FILE_STORE_LOCATION + System.nanoTime() + fileName);
	        try {
				item.write(uploadedFile);
			} catch (Exception e) {
				e.printStackTrace();
			}
	    } else {
//	        InputStream uploadedStream = item.getInputStream();
//	        ...
//	        uploadedStream.close();
	    }
	    
	    if (uploadedFile != null) {
	    	return uploadedFile.getAbsolutePath();
	    } else {
	    	return "";
	    }
	}

	private void processFormField(FileItem item) {
//		String name = item.getFieldName();
//	    String value = item.getString();
	}

	public void setWriteToFile(boolean writeToFile) {
		this.writeToFile = writeToFile;
	}

	public boolean isWriteToFile() {
		return writeToFile;
	}
}
