package com.app.test;

import java.io.BufferedReader;
import java.io.File;
import java.io.InputStream;
import java.net.URL;

import com.app.player.common.InplayConstants;

public class Test {

	public static void main(String[] args) {
		File file = new File(InplayConstants.LOADING_IMAGE_PATH);
		URL resource = Test.class.getResource("loading.jpg");
		InputStream stream = Test.class.getClassLoader().getResourceAsStream("test.xml");
		
		System.out.println(resource);
	}
}
