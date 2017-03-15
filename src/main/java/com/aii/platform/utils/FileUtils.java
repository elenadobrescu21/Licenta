package com.aii.platform.utils;

import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.nio.file.DirectoryNotEmptyException;
import java.nio.file.Files;
import java.nio.file.NoSuchFileException;
import java.nio.file.Path;
import java.nio.file.Paths;

import org.apache.commons.io.FilenameUtils;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

@Service
public class FileUtils {
	
	//aici avem o eroare pentru ca fisierul nu exista pe server, metoda trebuie apelata dupa ce 
	//se salveaza pe server
	public File multipartToFile(MultipartFile multipart) throws IllegalStateException, IOException {
		File convFile = new File(multipart.getOriginalFilename());
		multipart.transferTo(convFile);
		return convFile;
			
	}
	
	public int countPDFsInDirectory(String directoryName) {
		File[] files = new File(directoryName).listFiles();
		int numberOfFiles = 0;
		//System.out.println("Numarul de fisiere:" + files.length);
		for(File file: files) {
			if(!file.isDirectory() && !file.isHidden() && file.exists() && file.canRead() 
					&& FilenameUtils.getExtension(file.getName()).equals("pdf")) {
				numberOfFiles++;
			}
		}
		return numberOfFiles;
	}
	
	public void deleteFileFromDirectory(String path) {
		
		Path filepath = Paths.get(path);
		
		try {
		    Files.delete(filepath);
		} catch (NoSuchFileException x) {
		    System.err.format("%s: no such" + " file or directory%n", filepath);
		} catch (DirectoryNotEmptyException x) {
		    System.err.format("%s not empty%n", filepath);
		} catch (IOException x) {
		    // File permission problems are caught here.
		    System.err.println(x);
		}
		
	}
	
	public void saveFileLocally(String filepath, MultipartFile file) throws IOException {
		 BufferedOutputStream stream =
		          new BufferedOutputStream(new FileOutputStream(new File(filepath)));
		      stream.write(file.getBytes());
		      stream.close();
	}

}
