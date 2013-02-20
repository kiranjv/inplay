package com.app.player.search;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.Image;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.io.File;
import java.net.MalformedURLException;
import java.net.URL;

import javax.swing.AbstractButton;
import javax.swing.BorderFactory;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JComponent;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JPanel;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.ListCellRenderer;
import javax.swing.border.EmptyBorder;
import javax.swing.border.LineBorder;
import javax.swing.plaf.basic.BasicComboBoxUI;

import com.app.player.InplayComponentFactory;
import com.app.player.InplayDataWorker;
import com.app.player.data.InplayDataProvider;
import com.app.player.util.InplayBackgroundImagePanel;
import com.app.player.util.InplayComponentUtils;

public class InplaySearchPanel extends JPanel {

	public InplaySearchPanel() {
		setBackground(Color.WHITE);
		setLayout(new FlowLayout());		
		
		InplaySearchComboBox searchComboBox = new InplaySearchComboBox();
		add(searchComboBox);
		

		String imagePath = "images" + "/"  +"search.jpg";
		JLabel label = InplayComponentUtils.getImageLabel(15, 15, imagePath);		
		add(label);
	}

	public static void main(String[] args) throws Exception {
		InplayDataProvider.syncDataWithServer();
		JFrame frame = new JFrame();
		JPanel panel = new JPanel(new FlowLayout());
		panel.add(InplayComponentFactory.getSearchPanel());
		frame.add(panel, BorderLayout.CENTER);
		frame.setLocation(100, 100);
		frame.setSize(775, 550);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.setVisible(true);
	}

}


class TextKeyListener implements KeyListener {

	private JTextField field;

	private JComboBox box;

	public TextKeyListener(JTextField field) {
		this.field = field;
	}

	public TextKeyListener(JComboBox box) {
		
		this.box = box;
	}

	public void keyPressed(KeyEvent paramKeyEvent) {
	}

	public void keyReleased(KeyEvent paramKeyEvent) {
	}

	public void keyTyped(KeyEvent paramKeyEvent) {
		System.out.println(paramKeyEvent.getKeyChar());
		box.showPopup();

	}

}

