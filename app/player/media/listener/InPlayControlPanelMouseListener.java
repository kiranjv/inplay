package com.app.player.media.listener;

import java.awt.Dimension;
import java.awt.Toolkit;
import java.awt.event.FocusEvent;
import java.awt.event.FocusListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.util.Timer;
import java.util.TimerTask;

import javax.management.timer.TimerMBean;

import uk.co.caprica.vlcj.player.embedded.EmbeddedMediaPlayer;

import com.app.player.InplayComponentFactory;
import com.app.player.InplayPlayerContext;

public class InPlayControlPanelMouseListener extends MouseAdapter {

	private static final String TAG = "InPlayControlPanelFocusListener";
	private int seconds;
	private Timer timer;

	public InPlayControlPanelMouseListener(int seconds) {
		this.seconds = seconds;

	}

	private class RemindTask extends TimerTask {

		@Override
		public void run() {
			System.out.println(TAG + " Timer fired");
			// hide the control panel when mediaplayer in fullscreen
			EmbeddedMediaPlayer mediaPlayer = InplayComponentFactory
					.getMediaPlayer();
			// if (mediaPlayer.isFullScreen()) {
			System.out.println(TAG + " Hidding control panel");
			InplayComponentFactory.getControlsPanel().setVisible(false);
			// }

		}

	}

	@Override
	public void mouseMoved(MouseEvent e) {
		
	}

	@Override
	public void mouseEntered(MouseEvent e) {
	
		
		int x = e.getPoint().x;
		int y = e.getPoint().y;
		
//		Dimension fullScreenSize = Toolkit.getDefaultToolkit().getScreenSize();
//		int fullscreen_height = fullScreenSize.height;
		
		int fullscreen_height = InplayComponentFactory.getMainFrame().getBounds().height;
		
		
		int panel_height = InplayComponentFactory.getControlsPanel()
				.getHeight();
		panel_height = fullscreen_height - panel_height;
//		System.out.println("y= " + y+" fullscreen_height: "+fullscreen_height+" panel_height:"+panel_height);
		EmbeddedMediaPlayer mediaPlayer = InplayComponentFactory
				.getMediaPlayer();
		
		if (mediaPlayer.isPlaying() && InplayPlayerContext.isFullScreenMode()) {
			System.out.println("player in full screen");
			if (y >= (panel_height) && y <= fullscreen_height)  {
				System.out.println("Showing control panel");
				InplayComponentFactory.getControlsPanel().setVisible(true);
				
			}
			else {
				System.out.println("Hideing control panel");
				InplayComponentFactory.getControlsPanel().setVisible(false);
			}
		} else if(!InplayComponentFactory.getControlsPanel().isVisible()) {
			InplayComponentFactory.getControlsPanel().setVisible(true);
		}
		
//		System.out.println(TAG + " Focus gained");

		// if (timer != null) {
		// System.out.println(TAG + " Focus gained and clearing timer");
		// timer.cancel();
		// timer = null;
		// }
		//
		

	}

	@Override
	public void mouseExited(MouseEvent e) {
		// System.out.println(TAG + " Focus lost");
		// System.out.println("point: x = " + e.getPoint().x + " y= "
		// + e.getPoint().y);
		// InplayComponentFactory.getControlsPanel().setVisible(false);
		// if (timer == null) {
		// System.out.println(TAG + " Focus lost and creating timer");
		// timer = new Timer();
		// timer.schedule(new RemindTask(), seconds * 1000);
		// }

	}

}
