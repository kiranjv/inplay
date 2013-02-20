package com.app.player.util;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLEncoder;
import java.util.Date;

import javax.swing.ImageIcon;
import javax.swing.JLabel;


import com.app.player.common.InplayConstants;
import com.app.player.http.InplayHttpConnectorFactory;

public class InplayImageFinder {
	
	
	
	public static String getLocalPath(URL imageURL) throws IOException {
		File file = new File(InplayConstants.ROOT_DIR);
		File imageDir = new File(file.getAbsolutePath()+"/"+InplayConstants.LOCAL_IMAGE_CACHE_DIR_NAME);
		if(!imageDir.exists()) {
			imageDir.mkdir();
		}
		
		
		String resourceFilePath = imageURL.getFile();
		String[] paths = resourceFilePath.split(InplayConstants.RESOURCE_FILE_PATH_SEPERATOR);
		String imageFileName = paths[paths.length-1];
		File imageFile = new File(imageDir,imageFileName);
		
		
		if(imageFile.exists()) return imageFile.getAbsolutePath();
		
		FileOutputStream outStream = null;
		try {
		byte[] bytesRead = InplayHttpConnectorFactory.getConnector(InplayConstants.HTTP_CONNECTOR_JAVA).receieveGetResponse(imageURL);
		outStream = new FileOutputStream(imageFile);
		outStream.write(bytesRead);
		}catch (Exception e) {
			throw new RuntimeException(e);
		}finally {
			if(outStream!=null) {
			outStream.flush();
			outStream.close();
			}
		}
		return imageFile.getAbsolutePath();
		
	}
	
	
	
	public static void main(String[] args) throws MalformedURLException, IOException {
		String path = getLocalPath(new URL("http://www.infinitysoft.us/admin/uploads/videos/thumbs/Se7en.jpg"));
		System.out.println(path);
		
	}
	

}
