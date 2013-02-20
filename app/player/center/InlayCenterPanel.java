package com.app.player.center;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.FlowLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.LineBorder;

import com.app.player.InplayComponentFactory;
import com.app.player.InplayDataWorker;

public class InlayCenterPanel extends JPanel {

	InlayMovieIconPanel inlayMovieIconPanel = new InlayMovieIconPanel(null);

	
	public InlayCenterPanel() {
		setLayout(new BorderLayout());
		setBackground(Color.WHITE);
		add(inlayMovieIconPanel,BorderLayout.CENTER);
		
		JPanel lowerPanel = new JPanel(new BorderLayout());
		
		lowerPanel.add(InplayComponentFactory.getCenterFillerPanel(),BorderLayout.CENTER);
		
		InplayReleasePanel releasePanel = InplayComponentFactory.getReleasePanel();
		lowerPanel.add(releasePanel,BorderLayout.SOUTH);
		
		add(lowerPanel,BorderLayout.SOUTH);
	}
	
	public void updateDisplay(String genere){
		inlayMovieIconPanel.updateDisplay(genere);
		InplayReleasePanel releasePanel = InplayComponentFactory.getReleasePanel();
		releasePanel.resetForGenere(genere);
		releasePanel.updateUI();
	}
	
	public static void main(String[] args) {
		new InplayDataWorker().start();
		JFrame frame = new JFrame();
		JPanel panel = new JPanel(new FlowLayout());
		panel.setBackground(Color.WHITE);
		panel.setBorder(new LineBorder(new Color(106,106,106),1));
		final InlayCenterPanel inlayCenterPanel = new InlayCenterPanel();
		panel.add(inlayCenterPanel);
		
		JButton button = new JButton("update for family");
		button.addActionListener(new ActionListener(){

			public void actionPerformed(ActionEvent arg0) {
				inlayCenterPanel.updateDisplay("Family");				
			}			
		});
		panel.add(button);
		
		JButton button2 = new JButton("update for Action");
		button2.addActionListener(new ActionListener(){

			public void actionPerformed(ActionEvent arg0) {
				inlayCenterPanel.updateDisplay("Action");				
			}			
		});
		panel.add(button2);
		
		JButton button3 = new JButton("hide");
		button2.addActionListener(new ActionListener(){

			public void actionPerformed(ActionEvent arg0) {
								
			}			
		});
		panel.add(button2);
		  frame.add(panel,BorderLayout.CENTER);
		    frame.setLocation(100, 100);
		    frame.setSize(775, 550);
		    frame.setUndecorated(true);
		    frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		  frame.setVisible(true);
		}
		
		

}
