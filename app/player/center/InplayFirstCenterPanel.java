package com.app.player.center;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Cursor;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.net.MalformedURLException;
import java.net.URL;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;


import com.app.player.InplayComponentFactory;
import com.app.player.common.InplayConstants;
import com.app.player.data.InplayDataCreator;
import com.app.player.data.InplayVideoDetailsDTO;
import com.app.player.listener.InplayPlayMovieMouseListener;
import com.app.player.media.listener.InplayMediaPlayerMouseListener;
import com.app.player.util.InplayBackgroundImagePanel;
import com.app.player.util.InplayImageFinder;
import com.app.player.util.InplayPlayerUtil;
import com.app.player.util.InplayRatingsUtils;

public class InplayFirstCenterPanel extends JPanel{
	
	private InplayVideoDetailsDTO dto = InplayVideoDetailsDTO.getDummyDTO();
	private InplayBackgroundImagePanel imagePanel = null ;
	JLabel movieNameLabel  = null;
	JLabel movieDescriptionLabel  = null;
	JPanel ratingPanel = new JPanel(new FlowLayout());
	InplayPlayMovieMouseListener listener = new InplayPlayMovieMouseListener(dto);
	
	public InplayFirstCenterPanel() {
		addDisplay();
	}
	
	public InplayFirstCenterPanel(InplayVideoDetailsDTO dto) {
		this.dto = dto;
		addDisplay();
	}

	private void addDisplay() {
		
		
		
		setLayout(new BorderLayout());
		setBackground(Color.WHITE);
		setMaximumSize(new Dimension(370,300));
		setMinimumSize(new Dimension(370,300));
		setPreferredSize(new Dimension(370,300));
		
		try {
			imagePanel = new InplayBackgroundImagePanel(new URL(dto.getPoster()));
			InplayPlayerUtil.setToolTipRecursively(imagePanel, dto.getVideoDescription());
		} catch (Exception e) {
			throw new RuntimeException(e);
		}
//		imagePanel.setMaximumSize(new Dimension(310,250));
//		imagePanel.setMinimumSize(new Dimension(310,250));
//		imagePanel.setPreferredSize(new Dimension(310,250));
		imagePanel.setCursor(new Cursor(Cursor.HAND_CURSOR));
		imagePanel.addMouseListener(listener);
        add(imagePanel,BorderLayout.CENTER);
        
        
        JPanel detailsPanel = new JPanel(new GridLayout(0,1));
        detailsPanel.setBackground(Color.WHITE);
        detailsPanel.setPreferredSize(new Dimension(370,29));

        JPanel labelPanel = new JPanel(new BorderLayout());
        labelPanel.setBackground(Color.WHITE);
        movieNameLabel  = new JLabel(dto.getVideoTitle());
        Font nameFont = new Font(InplayConstants.FONT_TOHOMA,1,15);
        movieNameLabel.setFont(nameFont);
        labelPanel.setBorder(new EmptyBorder(0,6,0,0));
        movieNameLabel.setForeground(new Color(100,100,100));
        labelPanel.add(movieNameLabel,BorderLayout.WEST);
        ratingPanel.setBorder(new EmptyBorder(-3,0,0,0));
        ratingPanel.setBackground(Color.WHITE);
        labelPanel.add(ratingPanel,BorderLayout.EAST);
        InplayRatingsUtils.addRatingStars(dto, ratingPanel,12);
        detailsPanel.add(labelPanel);

        movieDescriptionLabel  = new JLabel(dto.getVideoDescription());
        movieDescriptionLabel.setForeground(new Color(100,100,100));
        Font descFont = new Font(InplayConstants.FONT_TOHOMA,Font.PLAIN,10);  
        movieDescriptionLabel.setFont(descFont);
        movieDescriptionLabel.setBorder(new EmptyBorder(0,6,0,0));
        detailsPanel.add(movieDescriptionLabel);
        
        add(detailsPanel,BorderLayout.SOUTH);
	}
	
	public void updateDisplay(final InplayVideoDetailsDTO dto) {
		this.dto = dto;
		listener.setDto(dto);
		movieNameLabel.setText(dto.getVideoTitle());
		movieDescriptionLabel.setText(dto.getVideoDescription());
		InplayRatingsUtils.addRatingStars(dto, ratingPanel,12);
		try {
			imagePanel.updateBackGround(new URL(dto.getPoster()));
		} catch (MalformedURLException e) {
			throw new RuntimeException(e);
		}

	}
	
	public static void main(String[] args) throws MalformedURLException {
		JFrame frame = new JFrame();
		frame.setBackground(Color.WHITE);
		JPanel panel = new JPanel(new FlowLayout());
		final InplayFirstCenterPanel firstCenterPanel = new InplayFirstCenterPanel();
		firstCenterPanel.setToolTipText("Panel tool tip text");
		panel.add(firstCenterPanel);
		panel.add(new InplayFirstCenterPanel());
		JButton button = new JButton("click");
		button.addActionListener(new ActionListener(){

			public void actionPerformed(ActionEvent paramActionEvent) {
				InplayVideoDetailsDTO dto = InplayVideoDetailsDTO.getDummyDTO();
				dto.setVideoDescription("testing");
				dto.setVideoTitle("test2 clxdhfoidl oiuyhfdhbf yifdhf");
				dto.setPoster("http://www.infinitysoft.us/admin/uploads/videos/posters/Lord_Of_The_Ring_1_200_150.jpg");
				dto.setUserRating("2");
				firstCenterPanel.updateDisplay(dto);
				InplayPlayerUtil.setToolTipRecursively(firstCenterPanel, "My custom tooltip text");
			
			}
			
		}) ;
		panel.add(button);
		panel.setBackground(Color.WHITE);
		  frame.add(panel,BorderLayout.CENTER);
		    frame.setLocation(100, 100);
		    frame.setSize(775, 550);
		    frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		  frame.setVisible(true);

	}


}
