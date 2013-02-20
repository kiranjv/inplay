package com.app.player.media;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Cursor;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;

import com.app.player.InplayPlayerContext;
import com.app.player.center.InplayReleaseDetailPanel;
import com.app.player.common.InplayConstants;
import com.app.player.data.InplayVideoDetailsDTO;
import com.app.player.util.InplayComponentUtils;
import com.app.player.util.InplayPlayerUtil;

public class InplayRatingsPanel extends JPanel {
	
	public static StarLabel[] starArray = new StarLabel[5];
	public static ImageIcon goldStarIcon;
	public static ImageIcon greyStarIcon;
	public static ImageIcon halfStarIcon;
	
	static {
		goldStarIcon = InplayComponentUtils.getImageIcon(20, 20, InplayConstants.LOCAL_IMAGE_GOLDEN_STAR);
		greyStarIcon = InplayComponentUtils.getImageIcon(20, 20, InplayConstants.LOCAL_IMAGE_GREY_STAR);
		halfStarIcon = InplayComponentUtils.getImageIcon(20, 20, InplayConstants.LOCAL_IMAGE_HALF_STAR);

	for(int i=0;i<starArray.length;i++) {
		starArray[i] = new StarLabel(greyStarIcon);
		starArray[i].setIndex(i);
		starArray[i].setState(StarLabel.STATE_GREY);
		starArray[i].setCursor(new Cursor(Cursor.HAND_CURSOR));
		}
	}
	
	
	
	public static InplayRatingsPanel instance = new InplayRatingsPanel();
	
	public static int getLabelIndex(JLabel label) {
		for (int i=0;i<starArray.length;i++) {
			if(label==starArray[i]) {
				return i;
			}
		}
		
		throw new RuntimeException("index not found");
	}
	
	public static void setStarRatings(){
		InplayVideoDetailsDTO dto = InplayPlayerContext.getPlayingVideoDTO();
		for(int i=0;i<5;i++) {
			starArray[i].setIcon(greyStarIcon);
		}
		String userRating = dto.getUserRating();
		if(userRating.indexOf(".5")!=-1) {
			int intValue = new Double(userRating).intValue();
			int i=0;
			for(;i<intValue;i++) {
				starArray[i].setIcon(goldStarIcon);
			}
			starArray[i].setIcon(halfStarIcon);
		} else {
			int intValue = new Double(userRating).intValue();
			for(int i=0;i<intValue;i++) {
				starArray[i].setIcon(goldStarIcon);
			}
		}
	}
	
	private InplayRatingsPanel() {
		setBackground(Color.WHITE);
		setLayout(new FlowLayout());
		createDisplay();
		attachActions();
	}
	
	private void createDisplay() {
        JLabel ratingLabel  = new JLabel("Rate this movie.");
        ratingLabel.setFont(new Font(InplayConstants.FONT_TOHOMA,0,12));
        ratingLabel.setBorder(new EmptyBorder(8,0,0,0));
        add(ratingLabel);

		for(JLabel label: starArray) {
			add(label);
		}
	}
	
	private void attachActions() {
		for(StarLabel label: starArray) {
			ChangeIconAdaptor adaptor = new ChangeIconAdaptor(label);
			label.addMouseListener(adaptor);
		}
	}
	

	
	public static void main(String[] args) {
		JFrame frame = new JFrame();
		JButton button = new JButton("change");
		  frame.setContentPane(instance);
		    frame.setLocation(100, 100);
		    frame.setSize(775, 550);
		    frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		  frame.setVisible(true);
		}

}


class ChangeIconAdaptor extends  MouseAdapter {
	
	private StarLabel starLabel;
	
	public ChangeIconAdaptor(StarLabel label) {
		this.starLabel = label;
	}
	
	public void mouseClicked(MouseEvent paramMouseEvent)
	  {
		int index = starLabel.getIndex();
		String state = starLabel.getState();
		if(StarLabel.STATE_GOLD.equals(state)) {
			for(int i=0;i<5;i++) {
				InplayRatingsPanel.starArray[i].setIcon(InplayRatingsPanel.greyStarIcon);
				InplayRatingsPanel.starArray[i].setState(StarLabel.STATE_GREY);
			}
		} else {
			starLabel.setIcon(InplayRatingsPanel.goldStarIcon);
			starLabel.setState(StarLabel.STATE_GOLD);
			InplayPlayerUtil.updateUserRating(index+1);
			for(int i=0;i<index;i++) {
				InplayRatingsPanel.starArray[i].setIcon(InplayRatingsPanel.goldStarIcon);
				InplayRatingsPanel.starArray[i].setState(StarLabel.STATE_GOLD);				
			}
		}
	 }	
}


class StarLabel extends JLabel {
	
	private int index;
	private String state;
	public static String STATE_GOLD = "STATE_GOLD";
	public static String STATE_GREY = "STATE_GREY";

	public int getIndex() {
		return index;
	}

	public void setIndex(int index) {
		this.index = index;
	}
	
	public StarLabel(ImageIcon icon){
		super(icon);
	}

	public String getState() {
		return state;
	}

	public void setState(String state) {
		this.state = state;
	}
}