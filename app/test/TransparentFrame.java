package com.app.test;

import java.awt.Color;
import java.awt.FlowLayout;
	import javax.swing.JButton;
	import javax.swing.JFrame;
import javax.swing.JLabel;
	import javax.swing.JPanel;
import javax.swing.JWindow;

import com.app.player.util.InplayComponentUtils;


public class TransparentFrame {

	 
	    public static void main(String[] args)
	    {
	        JFrame frame = new JFrame("The Frame");
	        frame.setSize(300, 300);
	        frame.setLocation(100, 200);
	 
	        com.sun.awt.AWTUtilities.setWindowOpacity(frame, 0.8f);
	 
	        JPanel pan2 = new JPanel();
	        JLabel imageLabel = InplayComponentUtils.getImageLabel(50, 50, "images/appleLogo.png");
	        pan2.add(imageLabel);
	 
	        frame.getContentPane().add(pan2);
	 
	 
	        pan2.setLayout(new FlowLayout());
	        pan2.add(new JButton("Hello"));
	        pan2.setBackground(Color.BLACK);
	        frame.setUndecorated(true);
	        frame.setVisible(true);
	        
	 
	    }
	}
