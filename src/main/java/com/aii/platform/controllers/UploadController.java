package com.aii.platform.controllers;

import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.aii.platform.models.Response;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

import javax.ws.rs.Consumes;

@Controller
public class UploadController {
	
	  //Save the uploaded file to this folder
    private static String UPLOADED_FOLDER = "uploads";
	
	@PostMapping("/uploadFile2") // //new annotation since 4.3\
	@Consumes("multipart/form-data")
    public ResponseEntity<?> singleFileUpload(@RequestParam("file") MultipartFile file) {

        if (file.isEmpty()) {
//            redirectAttributes.addFlashAttribute("message", "Please select a file to upload");
//            return "redirect:uploadStatus";
        	System.out.println("FIle is empty");
        	return new ResponseEntity<Response>(new Response("File is empty"), new HttpHeaders(), HttpStatus.NO_CONTENT);
        }

        try {

            // Get the file and save it somewhere
            byte[] bytes = file.getBytes();
            Path path = Paths.get(UPLOADED_FOLDER + file.getOriginalFilename());
            Files.write(path, bytes);
            System.out.println("You successfully uploaded '" + file.getOriginalFilename() + "'");

        } catch (IOException e) {
            e.printStackTrace();
        }

        return new ResponseEntity<Response>(new Response("File uploaded successfully"), new HttpHeaders(), HttpStatus.OK);
    }

}
