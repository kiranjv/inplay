package com.app.player.center;

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
import java.io.File;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.border.Border;
import javax.swing.border.EmptyBorder;
import javax.swing.border.LineBorder;

import com.app.player.InplayComponentFactory;
import com.app.player.common.InplayConstants;
import com.app.player.util.InplayBackgroundImagePanel;
import com.app.player.util.InplayComponentUtils;

public class InplayCenterFillerPanel extends JPanel {
	
	JButton rightArrow;
	JButton leftArrow;
	
	public InplayCenterFillerPanel() {
		setBackground(Color.WHITE);
		setLayout(new BorderLayout());
		
		setMaximumSize(new Dimension(750,45));
		setMinimumSize(new Dimension(750,45));
		setPreferredSize(new Dimension(750,45));

        JLabel label  = new JLabel("       latest releases");
        label.setFont(new Font("CordiaUPC",0,25));
        label.setForeground(new Color(45,45,45));
        label.setBorder(new EmptyBorder(20,0,0,0));
        add(label,BorderLayout.WEST);
        
        JPanel navigatePanel = new JPanel(new FlowLayout());
        navigatePanel.setBackground(Color.WHITE);
        navigatePanel.setBorder(new EmptyBorder(18,2,2,32));
        String path = InplayConstants.LOCAL_IMAGE_DIR+ "/" + "arrowLeft1.png";
		
        leftArrow = InplayComponentUtils.getImageButton(25, 25, path);
        leftArrow.setPreferredSize(new Dimension(25,25));
        leftArrow.setCursor(new Cursor(Cursor.HAND_CURSOR));
        leftArrow.addMouseListener(new LeftNavigationMouseAdaptor());
        leftArrow.setBorder(new LineBorder(Color.WHITE,1));
        
        String right = InplayConstants.LOCAL_IMAGE_DIR+ "/" + "arrowRight1.png";
		rightArrow = InplayComponentUtils.getImageButton(25, 25, right);
        rightArrow.setPreferredSize(new Dimension(25,25));
        rightArrow.setCursor(new Cursor(Cursor.HAND_CURSOR));
        rightArrow.addMouseListener(new RightNavigationMouseAdaptor());
        rightArrow.setBorder(null);

        navigatePanel.add(leftArrow);
        navigatePanel.add(rightArrow);
        add(navigatePanel,BorderLayout.EAST);
 	}
	
	
	
	
	public void displayRightArrow(){
		InplayReleasePanel releasePanel = InplayComponentFactory.getReleasePanel();
		int currentReleaseListSize = releasePanel.getCurrentReleaseListSize();
		int displayCounter = releasePanel.getDisplayCounter();
		if(currentReleaseListSize>displayCounter){
			rightArrow.setVisible(true);
		} else {
			rightArrow.setVisible(false);
		}
	}

	
	public void displayLeftArrow(){
		InplayReleasePanel releasePanel = InplayComponentFactory.getReleasePanel();
		int displayCounter = releasePanel.getDisplayCounter();
		if(displayCounter>7){
			leftArrow.setVisible(true);
		} else {
			leftArrow.setVisible(false);
		}
	}

	
	public static void main(String[] args) {
		JFrame frame = new JFrame();		
		JPanel panel = new JPanel(new FlowLayout());
		final InplayCenterFillerPanel centerFillerPanel = new InplayCenterFillerPanel();
		
		JButton hide = new JButton("Hide");
		hide.addActionListener(new ActionListener() {

			public void actionPerformed(ActionEvent arg0) {
				centerFillerPanel.displayLeftArrow();	
			}
			
		});
		
		JButton show = new JButton("Hide");
		
		show.addActionListener(new ActionListener(){

			public void actionPerformed(ActionEvent arg0) {
				centerFillerPanel.rightArrow.setVisible(true);				
			}
			
		});
		
		
		panel.add(centerFillerPanel);
		
		  frame.add(panel,BorderLayout.CENTER);
		  frame.add(hide,BorderLayout.NORTH);
		  frame.add(show,BorderLayout.SOUTH);
		    frame.setLocation(100, 100);
		    frame.setSize(775, 550);
		    frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		  frame.setVisible(true);
		}

}

class RightNavigationMouseAdaptor extends MouseAdapter {
	
	public void mouseClicked(MouseEvent paramMouseEvent)
	  {
		 InplayComponentFactory.getReleasePanel().navigateRight();
	  }
}

class LeftNavigationMouseAdaptor extends MouseAdapter {
	
	public void mouseClicked(MouseEvent paramMouseEvent)
	  {
		 InplayComponentFactory.getReleasePanel().navigateLeft();
	  }
}
