package com.app.player;

import java.awt.BorderLayout;
import java.awt.Color;
import java.io.IOException;

import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.border.LineBorder;

import com.app.player.common.InplayConstants;
import com.app.player.util.InplayPlayerUtil;

public class InplayLoadingFrame extends JFrame {
	
	private static InplayLoadingFrame frame = new InplayLoadingFrame(); 

	static {
		try {
			initializeFrame();
		} catch (IOException e) {
			System.err.println("Exception to initialize loading frame");
			e.printStackTrace();
		}
	}
	
	private InplayLoadingFrame() {
	}

	private static void initializeFrame() throws IOException {
		frame.setBackground(Color.WHITE);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.setUndecorated(true);
		frame.setLocation(InplayScreenResolutionHelper.getXLocation(500), InplayScreenResolutionHelper.getYLocation(250));
		frame.setSize(250, 200);
		frame.setTitle(InplayConstants.TITLE);
		frame.setIconImage(InplayPlayerUtil.getTitleIcon());
		frame.setVisible(false);
	}
	
	public static InplayLoadingFrame getLoadingFrame() {
		return frame;
	}
	
	
}
