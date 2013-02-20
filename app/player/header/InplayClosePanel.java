package com.app.player.header;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Cursor;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.GridLayout;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.border.EmptyBorder;
import javax.swing.border.LineBorder;

import com.app.player.InplayComponentFactory;
import com.app.player.common.InplayConstants;
import com.app.player.listener.InplayDefaultMouseListener;
import com.app.player.util.InplayBackgroundImagePanel;
import com.app.player.util.InplayPlayerUtil;

public class InplayClosePanel extends JPanel{
	
	public InplayClosePanel() {
		setLayout(new FlowLayout());		
		setBackground(Color.WHITE);
		
		
//		JPanel minimizePanel = new JPanel(new GridLayout(0,1));
		JPanel minimizePanel = new JPanel();
		JPanel minimizeImagePanel = new JPanel();
		minimizeImagePanel.setMinimumSize(new Dimension(15,2));
		minimizeImagePanel.setPreferredSize(new Dimension(15,2));
		minimizeImagePanel.setMaximumSize(new Dimension(15,2));
		minimizeImagePanel.setBackground(new Color(120,120,120));
		minimizeImagePanel.setCursor(new Cursor(Cursor.HAND_CURSOR));
		minimizePanel.add(minimizeImagePanel);
		
		minimizePanel.setBorder(new LineBorder(Color.WHITE,3));
		minimizePanel.setMinimumSize(new Dimension(15,20));
		minimizePanel.setPreferredSize(new Dimension(15,20));
		minimizePanel.setMaximumSize(new Dimension(15,20));
		minimizePanel.setBackground(Color.WHITE);
		minimizePanel.setCursor(new Cursor(Cursor.HAND_CURSOR));
		minimizePanel.addMouseListener(new InplayDefaultMouseListener(){

			public void mouseClicked(MouseEvent arg0) {
				InplayComponentFactory.getMainFrame().setState(JFrame.ICONIFIED);
		}
			
		});
		add(minimizePanel);
		
		
		JLabel label = new JLabel("X");
		label.setFont(new Font(InplayConstants.FONT_TOHOMA,0,15));
		label.setForeground(new Color(120,120,120));
		label.setCursor(new Cursor(Cursor.HAND_CURSOR));
		label.addMouseListener(new InplayDefaultMouseListener(){

			public void mouseClicked(MouseEvent arg0) {
					InplayPlayerUtil.closePlayer();
			}

		});
		add(label);

	}
	
	public static void main(String[] args) {
		JFrame frame = new JFrame();
		JPanel panel = new JPanel(new FlowLayout());
		panel.setBackground(Color.WHITE);
		panel.add(new InplayClosePanel());
		  frame.add(panel,BorderLayout.CENTER);
		    frame.setLocation(100, 100);
		    frame.setSize(775, 550);
		    frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		  frame.setVisible(true);
		}


}
