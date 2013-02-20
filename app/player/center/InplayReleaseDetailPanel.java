package com.app.player.center;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Cursor;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.net.MalformedURLException;
import java.net.URL;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;

import sun.awt.HorizBagLayout;

import com.app.player.common.InplayConstants;
import com.app.player.data.InplayVideoDetailsDTO;
import com.app.player.listener.InplayPlayMovieMouseListener;
import com.app.player.util.InplayBackgroundImagePanel;
import com.app.player.util.InplayComponentUtils;
import com.app.player.util.InplayPlayerUtil;
import com.app.player.util.InplayRatingsUtils;

public class InplayReleaseDetailPanel extends JPanel {
	
	
	
	public InplayReleaseDetailPanel(InplayVideoDetailsDTO dto) {
		setLayout(new BorderLayout());
		setBackground(Color.WHITE);
		InplayBackgroundImagePanel imagePanel;
		try {
			imagePanel = new InplayBackgroundImagePanel(new URL(dto.getVideoThumb()));
		} catch (MalformedURLException e) {
			System.out.println("URL not found = " + dto.getVideoThumb() + " for id = " + dto.getId());
			System.out.println("loading a local image.");
			imagePanel = new InplayBackgroundImagePanel(InplayConstants.LOADING_IMAGE_PATH);
		}
		imagePanel.setCursor(new Cursor(Cursor.HAND_CURSOR));
		imagePanel.addMouseListener(new InplayPlayMovieMouseListener(dto));
         
		InplayPlayerUtil.setToolTipRecursively(imagePanel, dto.getVideoDescription());
        
        JPanel detailsPanel = new JPanel(new GridLayout(0,1));
        detailsPanel.setBackground(Color.WHITE);
        JLabel label2  = new JLabel(dto.getVideoTitle());
        label2.setFont(new Font(InplayConstants.FONT_TOHOMA,0,12));
        JLabel label3  = new JLabel(dto.getVideoDescription());
        label2.setForeground(new Color(53,53,53));
        label2.setBorder(new EmptyBorder(0,2,-4,0));
        label3.setForeground(new Color(100,100,100));
        label3.setFont(new Font(InplayConstants.FONT_TOHOMA,0,8));
        label3.setBorder(new EmptyBorder(0,2,-2,0));
//        detailsPanel.setPreferredSize(new Dimension(0,30));
//        detailsPanel.setMaximumSize(new Dimension(0,30));
        detailsPanel.add(label2);
        detailsPanel.add(label3);
        JPanel panel = createRatingsPanel(dto);
        panel.setBorder(new EmptyBorder(-6,-10,0,0));
        detailsPanel.add(panel);
        
        JPanel displayPanel = new JPanel(new BorderLayout());
        displayPanel.add(imagePanel,BorderLayout.CENTER);
 //       displayPanel.add(detailsPanel,BorderLayout.SOUTH);
        displayPanel.setBorder(new EmptyBorder(0,0,-2,0));
        
        add(imagePanel,BorderLayout.CENTER);
        
        add(createViewsPanel(dto),BorderLayout.NORTH);
        add(detailsPanel,BorderLayout.SOUTH);
	}
	
	private JPanel createRatingsPanel(InplayVideoDetailsDTO dto) {
		JPanel panel = new JPanel(new FlowLayout());
		panel.setBackground(Color.WHITE);
		InplayRatingsUtils.addRatingStars(dto, panel,10);
		return panel;
	}
	
	private JPanel createViewsPanel(InplayVideoDetailsDTO dto){
		JPanel panel = new JPanel(new FlowLayout());
		panel.setBorder(new EmptyBorder(-4,-4,0,0));
		String userRating = dto.getUserRating();
		if(userRating.indexOf(".0")!=-1) {
			userRating = "" + new Double(userRating).intValue();
		} 
		JLabel label = new JLabel(userRating);
		label.setBorder(new EmptyBorder(0,0,0,0));
		label.setFont(new Font(InplayConstants.FONT_TOHOMA,0,10));
		panel.setBackground(Color.WHITE);
        JLabel viewLabel  = new JLabel("Views("+dto.getVideoWatchCount()+")");
        viewLabel.setFont(new Font(InplayConstants.FONT_TOHOMA,0,10));
        panel.add(viewLabel);
		return panel;
	}


	public static void main(String[] args) {
	JFrame frame = new JFrame();
	JPanel panel = new JPanel(new FlowLayout());
//	panel.setBackground(Color.WHITE);
	InplayReleaseDetailPanel inplayReleaseDetailPanel = new InplayReleaseDetailPanel(InplayVideoDetailsDTO.getDummyDTO());
	panel.add(inplayReleaseDetailPanel);
	inplayReleaseDetailPanel.setMaximumSize(new Dimension(102,175));
	inplayReleaseDetailPanel.setMinimumSize(new Dimension(102,175));
	inplayReleaseDetailPanel.setPreferredSize(new Dimension(102,175));
	inplayReleaseDetailPanel.setBorder(new EmptyBorder(2,10,2,10));
	inplayReleaseDetailPanel.setToolTipText("testing");
	frame.add(panel,BorderLayout.CENTER);
	frame.setLocation(100, 100);
	frame.setSize(775, 550);
	frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
	frame.setVisible(true);
	}

}


