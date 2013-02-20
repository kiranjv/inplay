package com.app.player.header;

import java.awt.FlowLayout;

import javax.swing.JLabel;
import javax.swing.JPanel;

public class InplayVideoNameDisplay extends JPanel {
	
	private JLabel movieName;
	private JLabel status;
	public JLabel getMovieName() {
		return movieName;
	}
	public void setMovieName(JLabel movieName) {
		this.movieName = movieName;
	}
	public JLabel getStatus() {
		return status;
	}
	public void setStatus(JLabel status) {
		this.status = status;
	}
	
	public InplayVideoNameDisplay() {
		setLayout(new FlowLayout());
		add(movieName);
		add(status);
	}
	

}
