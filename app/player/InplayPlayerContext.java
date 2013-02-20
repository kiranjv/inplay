package com.app.player;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import com.app.player.common.InplayConstants;
import com.app.player.data.InplayUserDTO;
import com.app.player.data.InplayVideoDetailsDTO;

public class InplayPlayerContext {
	
	
	private static boolean userLoggedIn = false;
	private static boolean fullScreenMode;
	private static String userID;
	private static InplayVideoDetailsDTO playingVideoDTO;
	static ExecutorService executor = Executors.newFixedThreadPool(InplayConstants.THREAD_POOL_SIZE);
	
	public static InplayVideoDetailsDTO getPlayingVideoDTO() {
		return playingVideoDTO;
	}

	public static void setPlayingVideoDTO(InplayVideoDetailsDTO playingVideoDTO) {
		InplayPlayerContext.playingVideoDTO = playingVideoDTO;
	}

	public static String getUserID() {
		return userID;
	}

	public static void setUserID(String userID) {
		InplayPlayerContext.userID = userID;
	}

	public static boolean isFullScreenMode() {
		return fullScreenMode;
	}

	public static void setFullScreenMode(boolean fullScreenMode) {
		InplayPlayerContext.fullScreenMode = fullScreenMode;
	}

	public static boolean isUserLoggedIn() {
		return userLoggedIn;
	}

	public static void setUserLoggedIn(boolean userLoggedIn) {
		InplayPlayerContext.userLoggedIn = userLoggedIn;
	}
	
	
	public static void main(String[] args) throws InterruptedException {
		Interrupter interrupter = new Interrupter();
		interrupter.start();
		Thread.currentThread().sleep(1000);
		interrupter.interrupt();
	}

	public static ExecutorService getExecutor() {
		return executor;
	}

	public static void setExecutor(ExecutorService executor) {
		InplayPlayerContext.executor = executor;
	}
}


class Interrupter extends Thread {
	
	public void run() {
		try {
			Thread currentThread = Thread.currentThread();
			System.out.println("This thread is = " +currentThread.getName());
			currentThread.sleep(100000);
		} catch (InterruptedException e) {
			System.out.println("I am interrupted");
			e.printStackTrace();
		}
	}
}