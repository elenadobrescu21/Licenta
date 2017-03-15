package com.aii.platform.utils;

import java.io.File;
import java.io.IOException;

import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.text.PDFTextStripperByArea;
import org.springframework.stereotype.Service;
import org.apache.pdfbox.text.PDFTextStripper;
import org.apache.pdfbox.text.PDFTextStripperByArea;

public class PDFBoxUtils {
	
	public String getTextContentFromPDF(String filePath) throws IOException {

	    try {
		      PDDocument document = null;
		      document = PDDocument.load(new File(filePath));
		      document.getClass();
		      if(!document.isEncrypted()) {
		    	  PDFTextStripperByArea stripper = new PDFTextStripperByArea();
		    	  stripper.setSortByPosition(true);
		    	  PDFTextStripper Tstripper = new PDFTextStripper();
		    	  int numberOfPages = document.getNumberOfPages();
		    	  Tstripper.setStartPage(1);
		    	  Tstripper.setEndPage(numberOfPages);
		    	  Tstripper.getArticleStart();
		    	  String st = new String(Tstripper.getText(document).getBytes(), "UTF-8");
		    	  document.close();
		    	  return st;
		    	   	  
		      }
		      } catch(Exception e) {
		    	  e.printStackTrace();
		      }
	     return "hello";
	}

}
