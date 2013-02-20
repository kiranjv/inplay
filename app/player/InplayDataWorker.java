package com.app.player;

import java.net.SocketTimeoutException;
import java.net.UnknownHostException;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;

import com.app.player.common.InplayConstants;
import com.app.player.data.InplayDataProvider;

public class InplayDataWorker extends Thread {

	public InplayDataWorker() {
		super(InplayConstants.THREAD_INIT_DATA_WORKER_NAME);
	}

	
	public static void syncData() {
		ScheduledExecutorService executor = Executors.newSingleThreadScheduledExecutor();
//		executor.

	}
	
	public void run() {

		while (true) {
			try {
				InplayDataProvider.syncDataWithServer();
			} catch (Exception e) {
				// put the code to push all exception to the server.
				if(e instanceof UnknownHostException) {
					System.err.println("InplayDataWorker Unknow host exception raised");
				}
				else if(e instanceof SocketTimeoutException ) {
					System.err.println("InplayDataWorker Sock timeout exception raised");
				}
				
				e.printStackTrace();
			}
			try {
				Thread.sleep(InplayConstants.DATA_SYNC_THREAD_TIME_INTERVAL);
			} catch (InterruptedException e1) {
				// put the code to push all exception to the server.
				e1.printStackTrace();
			}
		}
	}

	public static void main(String[] args) {
		new InplayDataWorker().start();
	}

}
