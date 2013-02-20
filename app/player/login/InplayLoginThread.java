package com.app.player.login;

import uk.co.caprica.vlcj.player.embedded.EmbeddedMediaPlayer;

import com.app.player.InplayComponentFactory;
import com.app.player.InplayPlayerContext;
import com.app.player.common.InplayConstants;
import com.app.player.util.InplayPlayerUtil;

public class InplayLoginThread extends Thread {
	
	private static int sleepInterval;
	
	public InplayLoginThread(int sleepInterval) {
		this.sleepInterval = sleepInterval;
	}
	
	public void run() {
		if(InplayPlayerContext.isUserLoggedIn()) return;
		EmbeddedMediaPlayer mediaPlayer = InplayComponentFactory.getMediaPlayer();
		if(mediaPlayer.isPlaying()) {
		try {
			Thread.currentThread().sleep(sleepInterval);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
		if(InplayPlayerContext.isUserLoggedIn()) return;
		if(mediaPlayer.isPlaying()) {
			mediaPlayer.pause();
			if(mediaPlayer.isFullScreen()) {
			InplayPlayerUtil.toggleFullScreen();
			}
			InplayLoginFrame loginFrame = InplayComponentFactory.getLoginFrame();
			loginFrame.getLoginLabel().setText(InplayConstants.LOGIN_MOVIE_MESSAGE_DISPLAY);
			loginFrame.getLoginFrame().setVisible(true);
			}
		}
	}

}
