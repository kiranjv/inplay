package com.app.player.util;

import java.io.InputStream;

import javax.swing.ImageIcon;
import javax.swing.JLabel;
import javax.swing.JPanel;

import com.InplayResourceFinder;
import com.app.player.InplayPlayerContext;
import com.app.player.common.InplayConstants;

public class InplayImageAnimation {
	
	private boolean animationFlag = false;
	private JLabel animationLabel;
	Thread animationThread;
	private static ImageIcon[] icons = new ImageIcon[6];
	
	static {
		for(int i=1;i<=icons.length;i++) {
			InputStream stream = InplayResourceFinder.getResourceAsStream(InplayConstants.LOADING_IMAGE_DIR +"/" + "load"+i +".jpg");
			icons[i-1] = InplayComponentUtils.getImageIcon(59, 59, stream);
			
		}
	}
	
	public boolean getAnimationFlag() {
		return animationFlag;
	}
	
	public InplayImageAnimation(JLabel animationLabel) {
		this.animationLabel = animationLabel;
	}
	
	public void startAnimation() {
		animationFlag = true;
		class LoadingUpdater implements  Runnable {
			public void run() {
				int counter = 0;
				while(animationFlag) {
//					animationLabel.setText(loadingTexts[counter++%loadingTexts.length]);
					animationLabel.setIcon(icons[counter++%icons.length]);
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
