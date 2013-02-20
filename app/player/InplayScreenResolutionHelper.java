package com.app.player;

import java.awt.Dimension;
import java.awt.Font;
import java.awt.Toolkit;

public class InplayScreenResolutionHelper {
	
	private static Dimension defaultSize = new Dimension(1600,900);
	
	private static Dimension currentScreenResolution = Toolkit.getDefaultToolkit().getScreenSize();
	
	
	public static double getScreenWidthFactor() {
		return currentScreenResolution.width/defaultSize.width;
	}
	
	public static void main(String[] args) {
		System.out.println(new Double(getScreenWidthFactor()).intValue());
	}
	
	public static int getXLocation(int width) {
		return (currentScreenResolution.width-width)/2;
	}
	
	public static int getYLocation(int height) {
		return (currentScreenResolution.height-height)/2;
	}

}
