package com.app.player.context;

import java.awt.Color;
import java.awt.GridLayout;

import javax.swing.JPanel;

public class InplayBasePanel extends JPanel {
	
	public InplayBasePanel() {
		super();
		setBackground(Color.WHITE);
	}

	public InplayBasePanel(GridLayout layout) {
		super(layout);
		setBackground(Color.WHITE);
	}

}
