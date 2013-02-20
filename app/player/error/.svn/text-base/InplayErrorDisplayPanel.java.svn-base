package com.app.player.error;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Font;
import java.net.SocketTimeoutException;
import java.net.UnknownHostException;

import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextArea;
import javax.swing.border.EmptyBorder;
import javax.swing.border.LineBorder;

import com.app.player.InplayComponentFactory;
import com.app.player.common.InplayConstants;
import com.app.player.exception.InplayTimeoutException;
import com.app.player.util.InplayComponentUtils;

public class InplayErrorDisplayPanel extends JPanel {
	
	private Exception exception;
	private int labelWidth = 51;
	
	public InplayErrorDisplayPanel(Exception exception) {
		super();
		this.exception = exception;
		createDisplay();
	}
	
	private void createDisplay(){
		setBackground(Color.WHITE);
		JPanel displayPanel = new JPanel();
		displayPanel.setLayout(new FlowLayout());
		setLayout(new BorderLayout());
		JLabel imageLabel = InplayComponentUtils.getImageLabel(90, 130, "images/warning-icon3.jpg" );
		displayPanel.add(imageLabel);
		displayPanel.add(createDisplayLabel(getDisplayText()));
		displayPanel.setBackground(Color.WHITE);
		displayPanel.setBorder(new EmptyBorder(25,0,0,0));
		add(displayPanel,BorderLayout.CENTER);
	}
	
	private String getDisplayText() {

		if(exception.getCause() instanceof UnknownHostException || exception instanceof UnknownHostException ) {
			return InplayConstants.ERROR_MSG_INTERNET_NOT_CONNECTED;
		}
		
		if(exception.getCause() instanceof SocketTimeoutException || exception instanceof SocketTimeoutException) {
			return InplayConstants.ERROR_MSG_TIME_OUT;
		}
		
		if(exception instanceof InplayTimeoutException) {
			return InplayConstants.ERROR_MSG_TIME_OUT;
		}
		
		return InplayConstants.ERROR_ON_LOAD_GENERIC;
		
	}
	
	private JLabel createDisplayLabel(String text) {
		JLabel label = new JLabel();
		label.setFont(new Font(InplayConstants.FONT_TOHOMA,0,12));
		label.setVerticalAlignment(JLabel.CENTER);
		String displayString = text;
		int length = displayString.length();
		StringBuffer display = new StringBuffer("<HTML>");
		for(int i=0;i<length;) {
			int end = i+labelWidth;
			if(length<end) {
				end = length;
			}
			
			if(end<length) {
			if(displayString.charAt(end)!=' ') {
				 end = findPreviousSpace(displayString, end)+1;
				}
			}
			
			display.append(displayString.substring(i,end));
			i = end;
			display.append("<BR>");
		}
		display.append("</HTML>");
		label.setText(display.toString());
		return label;
	}
	
	private int findPreviousSpace(String searchText, int startIndex) {
		for(;startIndex<searchText.length();) {
			if(searchText.charAt(startIndex--)==' ') {
				return startIndex;
			}
		}
		return startIndex;
	}
	

	public static void main(String[] args) {
		JFrame mainFrame = new JFrame();
		mainFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		mainFrame.setBackground(Color.WHITE);
		mainFrame.setSize(450, 400);
		mainFrame.setLocation(100, 100);
		InplayErrorDisplayPanel panel = new InplayErrorDisplayPanel(new RuntimeException("testing"));
		mainFrame.setContentPane(panel);
		mainFrame.setVisible(true);
	}
}
