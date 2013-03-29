package com.java.fullscreen.exclisive;

import java.awt.DisplayMode;
import java.awt.GraphicsDevice;
import java.awt.GraphicsEnvironment;

import javax.swing.JFrame;
import javax.swing.JLabel;

public class FullScreenDeviceList {

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		JFrame frame = new JFrame();
		frame.add(new JLabel("Full mode exclusive"));
		frame.pack();
		frame.setVisible(true);
		
		GraphicsDevice defaultScreenDevice = GraphicsEnvironment.getLocalGraphicsEnvironment().getDefaultScreenDevice();
		System.out.println("Default screen device: "+defaultScreenDevice);
		System.out.println("isFullScreeen Support: "+defaultScreenDevice.isFullScreenSupported());
		System.out.println("Display mode");
//		DisplayMode displayMode = defaultScreenDevice.getDisplayMode();
//		System.out.println("Dispaly mode refresh = "+displayMode.getRefreshRate()+" depth: "+displayMode.getBitDepth());
//		
		

//		GraphicsDevice[] screenDevices = GraphicsEnvironment.getLocalGraphicsEnvironment().getScreenDevices();
//	   for (int i = 0; i < screenDevices.length; i++) {
//		GraphicsDevice graphicsDevice = screenDevices[i];
//		System.out.println("Device id: "+i+" = "+graphicsDevice);
//	}
		
		//new DisplayMode(width, height, bitDepth, refreshRate)
		 DisplayMode[] displayModes = defaultScreenDevice.getDisplayModes();
		 for (int i = 0; i < displayModes.length; i++) {
		 DisplayMode dm = displayModes[i];
		 System.out.println("Display modes id: "+i+": refresh = "+dm.getRefreshRate()+" bitdepth:"+dm.getBitDepth()+" height:"+dm.getHeight()+" width:"+dm.getWidth());
		 }
	}

}
