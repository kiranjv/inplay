package com.app.player.header;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Cursor;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.GridLayout;
import java.awt.Point;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionAdapter;
import java.io.File;

import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.border.LineBorder;

import uk.co.caprica.vlcj.player.embedded.EmbeddedMediaPlayer;

import com.app.player.InplayComponentFactory;
import com.app.player.InplayDataWorker;
import com.app.player.InplayPlayerContext;
import com.app.player.center.InplayCenterFillerPanel;
import com.app.player.common.InplayConstants;
import com.app.player.media.InplayRatingsPanel;
import com.app.player.search.InplaySearchPanel;
import com.app.player.util.InplayComponentUtils;
import com.app.player.util.InplayPlayerUtil;

public class InplayHeaderPanel extends JPanel {

	private JLabel loginLabel;

	public JLabel getLoginLabel() {
		return loginLabel;
	}

	public void setLoginLabel(JLabel loginLabel) {
		this.loginLabel = loginLabel;
	}

	public InplayHeaderPanel() {
		setBackground(Color.WHITE);
		setLayout(new BorderLayout());

		setMaximumSize(new Dimension(750, 80));
		setMinimumSize(new Dimension(750, 80));
		setPreferredSize(new Dimension(750, 80));

		loginLabel = new JLabel("Login");
		loginLabel.setFont(new Font("CordiaUPC", Font.PLAIN, 25));
		loginLabel.setForeground(new Color(53, 53, 53));
		loginLabel.setCursor(new Cursor(Cursor.HAND_CURSOR));
		loginLabel.setBorder(new EmptyBorder(0, 23, 0, 0));
		loginLabel.addMouseListener(new MouseAdapter() {

			public void mouseClicked(MouseEvent arg0) {
				if (InplayPlayerContext.isUserLoggedIn()) {
					loginLabel.setText("Login");
					InplayPlayerContext.setUserLoggedIn(false);
					/* stop media play if it in progress */
					EmbeddedMediaPlayer mediaPlayer = InplayComponentFactory
							.getMediaPlayer();

					if (mediaPlayer.isPlaying()) {
						mediaPlayer.pause();
						if (mediaPlayer.isFullScreen()) {
							InplayPlayerUtil.toggleFullScreen();
						}
					}

				} else {
					InplayComponentFactory.getLoginFrame().getLoginFrame()
							.setVisible(true);
				}
			}
		});

		add(loginLabel, BorderLayout.WEST);

		JPanel logoPanel = new JPanel();
		logoPanel.setBackground(Color.WHITE);
		String path = InplayConstants.LOCAL_IMAGE_DIR + "/"
				+ "inplayFinalLogo.png";
		JLabel logoLabel = InplayComponentUtils.getImageLabel(155, 50, path);
		loginLabel.setHorizontalAlignment(JLabel.CENTER);
		logoPanel.add(logoLabel);
		logoPanel.setBorder(new EmptyBorder(5, 150, 0, 0));
		add(logoPanel, BorderLayout.CENTER);

		InplayClosePanel closePanel = new InplayClosePanel();
		// closePanel.setBackground(Color.BLACK);
		closePanel.setBackground(Color.WHITE);
		closePanel.setBorder(new EmptyBorder(0, 300, 0, 0));
		JPanel eastPanel = new JPanel(new GridLayout(0, 1));
		eastPanel.setPreferredSize(new Dimension(350, 0));
		eastPanel.setBackground(Color.WHITE);
		eastPanel.add(closePanel);
		InplaySearchPanel searchPanel = InplayComponentFactory.getSearchPanel();
		eastPanel.add(searchPanel);
		InplayRatingsPanel ratingsPanel = InplayComponentFactory
				.getRatingsPanel();
		ratingsPanel.setVisible(false);
		eastPanel.add(ratingsPanel);
		add(eastPanel, BorderLayout.EAST);
		addScrollingSupport();

	}

	private final Point point = new Point();

	private void addScrollingSupport() {

		addMouseListener(new MouseAdapter() {
			public void mousePressed(MouseEvent e) {
				point.x = e.getX();
				point.y = e.getY();
			}
		});

		addMouseMotionListener(new MouseMotionAdapter() {
			public void mouseDragged(MouseEvent e) {
				Point p = InplayComponentFactory.getMainFrame().getLocation();
				InplayComponentFactory.getMainFrame().setLocation(
						p.x + e.getX() - point.x, p.y + e.getY() - point.y);
			}
		});

	}

	public static void main(String[] args) {
		new InplayDataWorker().start();
		JFrame frame = new JFrame();
		JPanel panel = new JPanel(new FlowLayout());
		JPanel centerPanel = new JPanel();
		centerPanel.setBackground(Color.GRAY);
		panel.setBackground(Color.WHITE);
		panel.add(new InplayHeaderPanel());
		frame.add(centerPanel, BorderLayout.CENTER);
		frame.add(panel, BorderLayout.NORTH);
		frame.setBackground(Color.WHITE);
		frame.setLocation(100, 100);
		frame.setSize(775, 550);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.setVisible(true);
	}

}
