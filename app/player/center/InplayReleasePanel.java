package com.app.player.center;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Cursor;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.io.File;
import java.util.Collection;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;
import java.util.TreeSet;
import java.util.Map.Entry;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.border.LineBorder;

import com.app.player.InplayComponentFactory;
import com.app.player.InplayDataWorker;
import com.app.player.common.InplayConstants;
import com.app.player.data.InplayDataProvider;
import com.app.player.data.InplayVideoDetailsDTO;
import com.app.player.util.InplayPlayerUtil;

public class InplayReleasePanel extends JPanel{
	
	private String genere = null;
	private Map<String, InplayReleaseDetailPanel> displayComponenetsMap = new HashMap<String, InplayReleaseDetailPanel>();
	private int displayCounter = 0;
	private int currentReleaseListSize=0;
	private Set<InplayVideoDetailsDTO> currentReleaseList;
	
	
	public int getCurrentReleaseListSize() {
		return currentReleaseListSize;
	}

		public int getDisplayCounter() {
		return displayCounter;
	}

	
	public String getGenere() {
		return genere;
	}

	public void setGenere(String genere) {
		this.genere = genere;
	}

	public InplayReleasePanel(){
		setBackground(Color.WHITE);
		setLayout(new FlowLayout());
		setGenere(null);
		Set<InplayVideoDetailsDTO> videoSet = InplayDataProvider.getVideoSet();
		addMovieDetails(videoSet);
		setBorder(new EmptyBorder(0,0,19,0));
		
	}

	private void addMovieDetails(Set<InplayVideoDetailsDTO> videoSet) {
		displayCounter=0;
		currentReleaseListSize = videoSet.size();
		currentReleaseList = videoSet;
		for (InplayVideoDetailsDTO dto : videoSet) {
			addMovieReleasePanel(dto);
			if(++displayCounter==7)break;
		}
	}
	
	private void displayNavigation() {
		InplayCenterFillerPanel centerFillerPanel = InplayComponentFactory.getCenterFillerPanel();
		centerFillerPanel.displayLeftArrow();		
		centerFillerPanel.displayRightArrow();
	}
	
	public void navigateRight(){
		Object[] listArray = currentReleaseList.toArray();
		int removeIndex = displayCounter-7;
		InplayVideoDetailsDTO dto = (InplayVideoDetailsDTO)listArray[removeIndex];
		InplayReleaseDetailPanel panel = displayComponenetsMap.get(dto.getId());
		remove(panel);
		displayComponenetsMap.remove(dto.getId());
		dto = (InplayVideoDetailsDTO)listArray[displayCounter++];
		addMovieReleasePanel(dto);
		updateUI();
		displayNavigation();
	}
	
	public void navigateLeft(){
		Collection<InplayReleaseDetailPanel> values = displayComponenetsMap.values();
		for(InplayReleaseDetailPanel detailPanel : values) {
			remove(detailPanel);
		}
		Object[] listArray = currentReleaseList.toArray();
		int endIndex = --displayCounter;
		int startIndex = endIndex-7;
		for(int i=startIndex;i<endIndex;i++) {
			addMovieReleasePanel((InplayVideoDetailsDTO)listArray[i]);
		}
		updateUI();
		displayNavigation();
	}
	


	private void addMovieReleasePanel(InplayVideoDetailsDTO dto) {
		InplayReleaseDetailPanel movieReleaseDetailPanel = new InplayReleaseDetailPanel(dto);
		InplayPlayerUtil.downloadPoster(dto);
		movieReleaseDetailPanel.setMaximumSize(new Dimension(102,185));
		movieReleaseDetailPanel.setMinimumSize(new Dimension(102,185));
		movieReleaseDetailPanel.setPreferredSize(new Dimension(102,185));
		movieReleaseDetailPanel.setBorder(new EmptyBorder(2,10,2,10));
		displayComponenetsMap.put(dto.getId(), movieReleaseDetailPanel);
		
		add(movieReleaseDetailPanel);
		
	}
	
	public void resetForGenere(String genere) {
		setGenere(genere);
		Collection<InplayReleaseDetailPanel> values = displayComponenetsMap.values();
		for(InplayReleaseDetailPanel detailPanel : values) {
			remove(detailPanel);
		}
		if(genere==null || genere.trim().equalsIgnoreCase(InplayConstants.GENERE_VIEW_ALL)) {
			Set<InplayVideoDetailsDTO> videoSet = InplayDataProvider.getVideoSet();
			addMovieDetails(videoSet);
		} else {
			Map<String, TreeSet<InplayVideoDetailsDTO>> map = InplayDataProvider.getVideoMap();
			TreeSet<InplayVideoDetailsDTO> videoSet = map.get(genere);
			addMovieDetails(videoSet);
		}
		displayNavigation();
	}
	
	
	public static void main(String[] args) throws Exception {
		InplayDataProvider.syncDataWithServer();
		JFrame frame = new JFrame();
		JPanel panel = new JPanel(new FlowLayout());
		panel.add(InplayComponentFactory.getReleasePanel());
		  frame.add(panel,BorderLayout.CENTER);
		    frame.setLocation(100, 100);
		    frame.setSize(775, 550);
		    frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		  frame.setVisible(true);
		}


}