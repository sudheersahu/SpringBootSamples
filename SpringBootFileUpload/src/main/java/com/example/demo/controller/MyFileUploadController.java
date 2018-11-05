package com.example.demo.controller;

import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.springframework.http.HttpRequest;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.multipart.MultipartFile;

import com.example.demo.model.MyUploadForm;

@Controller
public class MyFileUploadController {
	
	@RequestMapping("/")
	public String homePage() {
		return "index";
	}
	
	@RequestMapping(value = "/uploadOneFile", method = RequestMethod.GET)
	public String uplaodOneFileHandler(Model model) {
		MyUploadForm  myuplaodForm =new MyUploadForm();
		
		model.addAttribute("myuplaodForm", myuplaodForm);
		return "uploadOneFile";
		
	}
	
	@RequestMapping(value = "/uploadOneFile", method = RequestMethod.POST)
	public String uplaodOneFileHandlerPOST(HttpServletRequest request, Model model, @ModelAttribute("myuplaodForm") MyUploadForm  myuplaodForm ) {
		
		return this.doUplaod(request, model, myuplaodForm);
		
	}
	
	@RequestMapping(value = "/uploadMultiFile", method = RequestMethod.GET)
	public String uplaodMultiFileHandler(Model model) {
		MyUploadForm  myuplaodForm =new MyUploadForm();
		
		model.addAttribute("myuplaodForm", myuplaodForm);
		return "uploadMultiFile";
		
	}
	
	 // POST: Do Upload
	   @RequestMapping(value = "/uploadMultiFile", method = RequestMethod.POST)
	   public String uploadMultiFileHandlerPOST(HttpServletRequest request, //
	         Model model, //
	         @ModelAttribute("myUploadForm") MyUploadForm myUploadForm) {
	 
	      return this.doUplaod(request, model, myUploadForm);
	 
	   }
	
	private String doUplaod(HttpServletRequest request, Model model,MyUploadForm  myuplaodForm ) {
		
		String description = myuplaodForm.getDescription();
		String uploadRootPath = request.getServletContext().getRealPath("upload");
		
		File updateRootDir = new File(uploadRootPath);
		  // Create directory if it not exists.
	      if (!updateRootDir.exists()) {
	    	  updateRootDir.mkdirs();
	      }
		
	      MultipartFile[] fileDatas = myuplaodForm.getFileDatas();
	      
	      List<File> uploadedFiles = new ArrayList<File>();
	      List<String> failedFiles = new ArrayList<String>();
	      
	      for (MultipartFile fileData : fileDatas) {
	    	  
	    	  String name = fileData.getOriginalFilename();
	    	  if(name!=null && name.length()>0) {
	    		  
	    		  try {
	                  // Create the file at server
	                  File serverFile = new File(updateRootDir.getAbsolutePath() + File.separator + name);
	    
	                  BufferedOutputStream stream = new BufferedOutputStream(new FileOutputStream(serverFile));
	                  stream.write(fileData.getBytes());
	                  stream.close();
	                  //
	                  uploadedFiles.add(serverFile);
	                  System.out.println("Write file: " + serverFile);
	               } catch (Exception e) {
	                  System.out.println("Error Write file: " + name);
	                  failedFiles.add(name);
	               }
	    	  }
	      }
	      model.addAttribute("description", description);
	      model.addAttribute("uploadedFiles", uploadedFiles);
	      model.addAttribute("failedFiles", failedFiles);
	      return "uploadResult";
		
	}

}
