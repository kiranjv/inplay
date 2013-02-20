package com.app.player;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.FlowLayout;

import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.border.LineBorder;

import com.app.player.center.InlayCenterPanel;
import com.app.player.header.InplayHeaderPanel;
import com.app.player.left.InplayGenerePanel;

public class InplayCompositePanel extends JPanel {
	
	public InplayCompositePanel() {
		createDisplay();
	}
	

	public void createDisplay() {
		setBackground(Color.WHITE);
		setLayout(new BorderLayout());
		InplayGenerePanel generePanel = InplayComponentFactory.getGenerePanel();
		InlayCenterPanel inlayCenterPanel = InplayComponentFactory.getCenterPanel();
		InplayHeaderPanel headerPanel = InplayComponentFactory.getHeadPanel();
//		headerPanel.setBorder(new EmptyBorder(50,50,50,50));
		add(headerPanel,BorderLayout.NORTH);
		add(generePanel,BorderLayout.WEST);
		add(inlayCenterPanel,BorderLayout.CENTER);
		
	}

	public static void main(String[] args) {
		JFrame frame = new JFrame();
		  InplayCompositePanel compositePanel = new InplayCompositePanel();
		  compositePanel.createDisplay();
		  compositePanel.setBorder(new LineBorder(new Color(182,182,182),1));
		frame.add(compositePanel);
		    frame.setLocation(100, 100);
		    frame.setSize(950, 620);
		    frame.setUndecorated(true);
		    frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		  frame.setVisible(true);
		}

}
