package com.app.player.left;

import java.awt.Color;
import java.awt.Font;

import javax.swing.JLabel;
import javax.swing.border.LineBorder;

import com.app.player.common.InplayConstants;

public class InplayGenreLabel extends JLabel {
	
	public InplayGenreLabel(String text, int fontSize) {
		super(text);
		setForeground(new Color(130, 130, 130));
//		setFont(new Font("CordiaUPC",0,fontSize));
		setFont(new Font(InplayConstants.FONT_TOHOMA,0,fontSize));
		

	}

}
