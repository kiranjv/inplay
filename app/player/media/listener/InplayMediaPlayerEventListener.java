package com.app.player.media.listener;

import java.awt.BorderLayout;
import java.awt.Canvas;
import java.awt.Color;
import java.awt.Point;
import java.net.MalformedURLException;
import java.net.URL;

import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.border.LineBorder;

import com.app.player.InplayComponentFactory;
import com.app.player.InplayLoadingFrame;
import com.app.player.InplayPlayer;
import com.app.player.InplayPlayerContext;
import com.app.player.common.InplayConstants;
import com.app.player.data.InplayVideoDetailsDTO;
import com.app.player.exception.InplayTimeoutException;
import com.app.player.login.InplayLoginThread;
import com.app.player.util.InplayBackgroundImagePanel;
import com.app.player.util.InplayImageAnimation;
import com.app.player.util.InplayPlayerUtil;
import com.app.player.util.InplayTextAnimation;

import uk.co.caprica.vlcj.binding.internal.libvlc_media_t;
import uk.co.caprica.vlcj.player.MediaPlayer;
import uk.co.caprica.vlcj.player.MediaPlayerEventAdapter;

public class InplayMediaPlayerEventListener extends MediaPlayerEventAdapter {
    
    public void mediaChanged(MediaPlayer mediaPlayer, libvlc_media_t media, String mrl) {
    	System.out.println("changed");
    }

    
    public void finished(MediaPlayer mediaPlayer) {
    	mediaPlayer.stop();
    	boolean fullScreenMode = InplayPlayerContext.isFullScreenMode();    	
    	if(fullScreenMode) {
    		InplayPlayerUtil.toggleFullScreen();
    	}
    }

    
    public void paused(MediaPlayer mediaPlayer) {
    	System.out.println("paused");
    }

    
    public void playing(MediaPlayer mediaPlayer) {
    	System.out.println("playing video");
    	new InplayLoginThread(InplayConstants.LOGIN_THREAD_SLEEP_INTERVAL).start();
    }

    
    public void stopped(MediaPlayer mediaPlayer) {
    	System.out.println("stopped");

    }

    
    public void videoOutput(MediaPlayer mediaPlayer, int newCount) {
    	System.out.println("videoOutput");
    }

    
    public void error(MediaPlayer mediaPlayer) {
    	System.out.println("error");
    }

    
    public void mediaSubItemAdded(MediaPlayer mediaPlayer, libvlc_media_t subItem) {
    	System.out.println("mediaSubItemAdded");
    }

    
    public void mediaDurationChanged(MediaPlayer mediaPlayer, long newDuration) {
    	System.out.println("mediaDurationChanged");
    }

    
    public void mediaParsedChanged(MediaPlayer mediaPlayer, int newStatus) {
    	System.out.println("mediaParsedChanged");
    }

    
    public void mediaFreed(MediaPlayer mediaPlayer) {
    	System.out.println("mediaFreed");
    }
    private JLabel loadingLabel = InplayComponentFactory.createLoadingLabel();
    
    
	private InplayTextAnimation labelAnimation = new InplayTextAnimation(loadingLabel);
//	private InplayImageAnimation labelAnimation = new InplayImageAnimation(loadingLabel);
    
    public void mediaStateChanged(MediaPlayer mediaPlayer, int newState) {
    	loadUsingFrame(newState);
    }

    private void loadUsingPanel(int newState) {
   		Canvas videoSurface = InplayComponentFactory.getVideoSurface();
		Point screenLocation = videoSurface.getLocationOnScreen();
		InplayVideoDetailsDTO dto = InplayPlayerContext.getPlayingVideoDTO();
		JPanel panel = null;
		try {
			panel = new InplayBackgroundImagePanel(new URL(dto.getPoster()));
		} catch (MalformedURLException e) {
			// TODO Auto-generated catch block
			System.out.println("path of imaeg = " + dto.getPoster());
			e.printStackTrace();
			throw new RuntimeException(e);
		}
		panel.setLayout(new BorderLayout());
		loadingLabel.setHorizontalAlignment(JLabel.LEFT);
		panel.add(loadingLabel);
		panel.setBorder(new LineBorder(Color.GRAY,1));
		loadingLabel.setBorder(new EmptyBorder(0,150,0,0));
		videoSurface.setSize(450,200);
//		videoSurface.
//		videoSurface.setVisible(true);
		labelAnimation.startAnimation();


    }
    
    private boolean loadingState = false;
    private JPanel panel;
	private void loadUsingFrame(int newState) {
		final JFrame loadingFrame = InplayComponentFactory.getLoadingFrame();
		loadingFrame.setAlwaysOnTop(true);
		com.sun.awt.AWTUtilities.setWindowOpacity(loadingFrame, 0.6f);
   		Canvas videoSurface = InplayComponentFactory.getVideoSurface();
		Point screenLocation = videoSurface.getLocationOnScreen();
		panel = getDisplayPanel();
    	if(newState ==1) {
    		loadingLabel.setBorder(new EmptyBorder(0,videoSurface.getWidth()/2-75,0,0));
 			loadingFrame.setLocation(screenLocation.x,screenLocation.y);
    		loadingFrame.setSize(videoSurface.getWidth(), videoSurface.getHeight());
			loadingFrame.setContentPane(panel);
    		loadingFrame.setUndecorated(true);
    		loadingFrame.setVisible(true);
    		labelAnimation.startAnimation();
    		loadingState = true;
    		return;
    	} 
    		else {    			
    		if(loadingState) {	
    		if(panel!=null) {	
    		loadingFrame.remove(panel);
    		}
    		if(loadingFrame.isVisible()) {
    		loadingFrame.setVisible(false);
    		loadingFrame.dispose();
    		}
    		labelAnimation.stopAnimation();
    		loadingState = false;
    		return;
    		}
    	}
    	
    	if(newState==7) {
    		errorDisplay(loadingFrame, videoSurface, screenLocation);
    	}
    	
 	}


	private void errorDisplay(final JFrame loadingFrame, Canvas videoSurface, Point screenLocation) {
		System.out.println("new state =7");
		int xPosition = screenLocation.x + videoSurface.getSize().width/2 - 200;
		int yPosition = screenLocation.y + videoSurface.getSize().height/2 -100;
		loadingFrame.setLocation(xPosition,yPosition);
		System.out.println("handle error");
		InplayPlayer.handleError(new InplayTimeoutException());
		System.out.println(System.currentTimeMillis());
		class Worker implements Runnable {

			public void run() {
				
				try {
					Thread.sleep(2500);
				} catch (InterruptedException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				
				loadingFrame.setVisible(false);
				loadingFrame.dispose();
			}
			
		}
		
		InplayPlayerContext.getExecutor().execute(new Worker());
	}

    
    public void mediaMetaChanged(MediaPlayer mediaPlayer, int metaType) {
    	System.out.println("mediaMetaChanged");
    }
    
    
    
    
    private JPanel getDisplayPanel() {
    	InplayVideoDetailsDTO dto = InplayPlayerContext.getPlayingVideoDTO();
//		JPanel panel = new InplayBackgroundImagePanel("images/search.jpg");
		JPanel panel = new JPanel();
		panel.setBackground(Color.WHITE);
		panel.setLayout(new BorderLayout());
		loadingLabel.setHorizontalAlignment(JLabel.LEFT);
		panel.add(loadingLabel);
		return panel;

    }
}

