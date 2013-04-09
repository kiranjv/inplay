package com.app.player.media.listener;

import java.awt.Color;
import java.awt.Cursor;
import java.awt.Dimension;
import java.awt.Point;
import java.awt.Toolkit;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseWheelEvent;
import java.awt.image.BufferedImage;

import javax.swing.JPanel;
import javax.swing.border.Border;
import javax.swing.border.LineBorder;

import com.app.player.InplayComponentFactory;
import com.app.player.InplayPlayerContext;
import com.app.player.common.InplayConstants;
import com.app.player.header.InplayHeaderPanel;
import com.app.player.left.InplayGenerePanel;
import com.app.player.media.InplayPlayerControlsPanel;
import com.app.player.util.InplayPlayerUtil;

public class InplayMediaPlayerMouseListener extends MouseAdapter {
	
	int doubleClickCounter = 0;
	
    public void mouseMoved(MouseEvent e) {
    	
//    	if(InplayPlayerContext.isFullScreenMode()) {
//    		InplayPlayerUtil.toggleFullScreen();
//        	Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
//        	double screenHeight = screenSize.getHeight();
//    		Point locationOnScreen = e.getLocationOnScreen();
//        	double height = locationOnScreen.getY();
//        	if(screenHeight-height<300) {
//        		InplayComponentFactory.getControlsPanel().setVisible(true);
//        		e.getComponent().setCursor(null);
//        	} else {
//        		InplayComponentFactory.getControlsPanel().setVisible(false);
//        		InplayPlayerUtil.hideCursor();
//        	}
//        	
//        	if(height<300) {
//        		InplayComponentFactory.getHeadPanel().setVisible(true);
//        		e.getComponent().setCursor(null);
//        	} else {
//        		InplayComponentFactory.getHeadPanel().setVisible(false);
//        		InplayPlayerUtil.hideCursor();
//        	}
//    	}
    }



  
    public void mouseClicked(MouseEvent e) {
    	System.out.println("Number of mouse clicks: "+e.getClickCount());
    	if(e.getClickCount()==2){
    		System.out.println("Clicked 2 times");
        }
    }
    public void mousePressed(MouseEvent e) {
    	
    }

    
    public void mouseReleased(MouseEvent e) {
    	
    	if(doubleClickCounter==2) {
    	InplayPlayerUtil.toggleFullScreen();
    		//InplayComponentFactory.getMediaPlayer().toggleFullScreen();
    	doubleClickCounter = 0 ;
    	} else {
    		doubleClickCounter++;
    	}
    }



    
    public void mouseWheelMoved(MouseWheelEvent e) {
//        Logger.debug("mouseWheelMoved(e={})", e);
    }

    
    public void mouseEntered(MouseEvent e) {
//        Logger.debug("mouseEntered(e={})", e);
    	
    }

    
    public void mouseExited(MouseEvent e) {
//        Logger.debug("mouseExited(e={})", e);
    }


}
