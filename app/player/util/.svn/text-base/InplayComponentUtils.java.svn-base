package com.app.player.util;

import java.awt.Image;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.MalformedURLException;
import java.net.URL;

import javax.imageio.ImageIO;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JLabel;

import com.InplayResourceFinder;

public class InplayComponentUtils {
	
	public static JLabel getImageLabel(int width, int height, String imagePath) {
	
	 Image img =null;
		try {
			if(imagePath.startsWith("http")) {				
				URL url = new URL(imagePath);
				String localPath = InplayImageFinder.getLocalPath(url);
				FileInputStream stream = new FileInputStream(new File(localPath));
				img = getImageIcon(width, height, stream).getImage();
			} else {
			img = getImageIcon(width, height, imagePath).getImage();
				}
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		   Image newimg = img.getScaledInstance( width, height,  java.awt.Image.SCALE_SMOOTH) ;  
		   ImageIcon icon = new ImageIcon( newimg );
			JLabel label = new JLabel();
			label.setIcon(icon);
			return label;
	}
	
	
	public static ImageIcon getImageIcon(int width, int height, String imagePath) {
		
		 Image img =null;
			try {
				img = ImageIO.read(InplayResourceFinder.getResourceAsStream(imagePath));
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			   Image newimg = img.getScaledInstance( width, height,  java.awt.Image.SCALE_SMOOTH) ;  
			   return new ImageIcon( newimg );
		}
	
	public static ImageIcon getImageIcon(int width, int height, InputStream stream) {
		
		 Image img =null;
			try {
				img = ImageIO.read(stream);
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			   Image newimg = img.getScaledInstance( width, height,  java.awt.Image.SCALE_SMOOTH) ;  
			   return new ImageIcon( newimg );
		}
	
	public static JButton getImageButton(int width, int height, String imagePath) {
		
		 Image img =null;
			try {
				img = ImageIO.read(InplayResourceFinder.getResourceAsStream(imagePath));
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			   Image newimg = img.getScaledInstance( width, height,  java.awt.Image.SCALE_SMOOTH) ;  
			   ImageIcon icon = new ImageIcon( newimg );
			   JButton button = new JButton();
			   button.setIcon(icon);
				return button;
		}

}
