package com.app.player.left;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Cursor;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.GridLayout;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.util.Map;
import java.util.Set;
import java.util.TreeSet;

import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.border.LineBorder;

import com.app.player.InplayComponentFactory;
import com.app.player.common.InplayConstants;
import com.app.player.context.InplayBasePanel;
import com.app.player.context.InlayPlayerContext;
import com.app.player.data.InplayDataProvider;
import com.app.player.data.InplayVideoDetailsDTO;
import com.app.player.util.InplayPlayerUtil;

public class InplayGenerePanel extends JPanel{
	
	public InplayGenerePanel() {
		setBackground(Color.WHITE);
		setLayout(new BorderLayout());
		setPreferredSize(new Dimension(150,0));
		createUILayout();
	}
	
	private void createUILayout() {
		
		JPanel headerPanel = new JPanel(new GridLayout(0,1));
		headerPanel.setBackground(Color.WHITE);
		headerPanel.setBorder(new LineBorder(Color.WHITE));
		JLabel moviesLabel = new InplayGenreLabel("Genres",20);
		moviesLabel.setForeground(new Color(100, 100, 100));
		
		headerPanel.add(moviesLabel);
		headerPanel.setBorder(new EmptyBorder(0,30,6,0));
		
		
//		JLabel genresLabel = new InlayGenreLabel("genres",40);
//		headerPanel.add(genresLabel);

		add(headerPanel,BorderLayout.NORTH);

		layoutGenres();
		setBorder(new EmptyBorder(0,0,20,0));
		
	}
	
	private void layoutGenres() {
		String genreList = InlayPlayerContext.getGenreList();
		String[] genres = genreList.split(",");

		GridLayout gridLayout = new GridLayout(0,1);
//		gridLayout.setVgap(10);
		
		if(genres.length<20) {
		gridLayout.setVgap(10);
		} 
		
		if(genres.length>20) {
			gridLayout.setVgap(4);
		} 
		
		if(genres.length>30) {
			gridLayout.setVgap(1);
		}
		
		JPanel genrePanel = new JPanel(gridLayout);
		
		genrePanel.setBackground(Color.WHITE);
//		genrePanel.setBorder(new EmptyBorder(0,30,0,0));
		
		for(int i=0;i<genres.length;i++) {
			InplayGenreLabel genreLabel = new InplayGenreLabel(genres[i],12);
			genreLabel.setForeground(new Color(76,76,76));
//			genreLabel.setBorder(new EmptyBorder(5,2,2,2));
			genreLabel.setCursor(new Cursor(Cursor.HAND_CURSOR));
			genreLabel.addMouseListener(new GenereLabelMouseAdaptor(genres[i]));
			genrePanel.add(genreLabel);
		}

		JPanel genreTopPanel = new JPanel(new FlowLayout());
		genreTopPanel.setBackground(Color.WHITE);
		genreTopPanel.add(genrePanel,BorderLayout.CENTER);
		genreTopPanel.setBorder(new EmptyBorder(0,-30,0,0));
		add(genreTopPanel,BorderLayout.CENTER);
	}
	
	
	public static void main(String[] args) throws Exception {
		JFrame frame = new JFrame();
		JPanel centerPanel = new JPanel(new BorderLayout());
		InplayDataProvider.syncInitialDataWithServer();
		centerPanel.add(new InplayGenerePanel());
		  frame.add(centerPanel);
		    frame.setLocation(100, 100);
		    frame.setSize(900, 550);
		    frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		    frame.setUndecorated(false);
		  frame.setVisible(true);

	}
}

class GenereLabelMouseAdaptor extends MouseAdapter {
	
	 private String genere;
	 
	 public GenereLabelMouseAdaptor(String genere) {
		 this.genere = genere;
	 }
	
	 public void mouseClicked(MouseEvent paramMouseEvent)
	  {
		 
		 if(genere==null || !genere.trim().equalsIgnoreCase(InplayConstants.GENERE_VIEW_ALL)) {
				Map<String, TreeSet<InplayVideoDetailsDTO>> map = InplayDataProvider.getVideoMap();
				TreeSet<InplayVideoDetailsDTO> videoSet = map.get(genere);
				if(videoSet==null||videoSet.size()==0) return;
			}
		 
		 
		 if(InlayPlayerContext.getView().equals(InplayConstants.VIEW_MEDIA)) {
			 InplayPlayerUtil.showMainIcon();
		 }
		 InplayComponentFactory.getCenterPanel().updateDisplay(genere);
	  }
	
}