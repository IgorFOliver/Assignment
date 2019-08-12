package com.waes.assignment.service;

import java.io.File;
import java.io.IOException;

import org.apache.commons.io.FileUtils;
import org.apache.tomcat.util.codec.binary.Base64;


public class FileUtil {
	
	public static final String BASE_PATH_IMAGES = "images";
	
	

	public static void saveBase64ToFileSystem(String filename, String base64) throws IOException{
		byte[] decoded = Base64.decodeBase64(base64.getBytes());
		try {
			FileUtils.writeByteArrayToFile(new File(filename), decoded);
		} catch (IOException e) {
			throw e;
		}
	}



	public static File recoveryFile(String path) {
		return new File(path);
	}
	
}
