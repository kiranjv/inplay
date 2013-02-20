package com;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.URL;
import java.security.CodeSource;

import com.app.player.common.InplayConstants;
import com.app.player.util.InplayPlayerUtil;


public class InplayResourceFinder {
	
	public static void main(String[] args) throws Exception {
		System.out.println(File.separator);
	}

	
	
	public static URL getResource(String resourcePath) {
		return InplayResourceFinder.class.getResource(resourcePath);
	}
	
	public static InputStream getResourceAsStream(String resourcePath) {
		System.out.println("loading resource = " + resourcePath);
		InputStream stream = InplayResourceFinder.class.getResourceAsStream(resourcePath);
		return stream;
	}
	
	public static void saveStream(OutputStream outStream, InputStream inStream) throws IOException {
		byte[] readFully = InplayPlayerUtil.readFully(inStream, -1, false);
		inStream.close();
		outStream.write(readFully);
		outStream.flush();
		outStream.close();
	}

}
