package com.app.player.util;

import java.io.InputStream;

import javax.swing.ImageIcon;
import javax.swing.JLabel;

import com.InplayResourceFinder;
import com.app.player.InplayPlayerContext;
import com.app.player.common.InplayConstants;

public class InplayTextAnimation {
	
	private boolean animationFlag = false;
	private JLabel animationLabel;
	Thread animationThread;
	
	public boolean getAnimationFlag() {
		return animationFlag;
	}
	
	public InplayTextAnimation(JLabel animationLabel) {
		this.animationLabel = animationLabel;
	}
	
	public void startAnimation() {
		animationFlag = true;
		final String[] loadingTexts = new String[] {"Loading","Loading.","Loading..","Loading...","Loading....","Loading....."};
		class LoadingUpdater implements  Runnable {
			public void run() {
				int counter = 0;
				while(animationFlag) {
					animationLabel.setText(loadingTexts[counter++%loadingTexts.length]);
				try {
					Thread.currentThread().sleep(InplayConstants.LOADING_ANIMATION_TIME_INTERVAL);
				} catch (InterruptedException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
					}
				}
			}
		}
		
		InplayPlayerContext.getExecutor().execute(new LoadingUpdater());
	}
	
	public void stopAnimation() {
		animationFlag = false;
	}
	
}
