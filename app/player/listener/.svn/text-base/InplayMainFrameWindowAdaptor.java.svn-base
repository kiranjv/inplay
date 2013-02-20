package com.app.player.listener;

import java.awt.Canvas;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

import uk.co.caprica.vlcj.logger.Logger;
import uk.co.caprica.vlcj.player.embedded.EmbeddedMediaPlayer;
import uk.co.caprica.vlcj.runtime.windows.WindowsCanvas;

import com.app.player.InplayComponentFactory;
import com.app.player.util.InplayPlayerUtil;

public class InplayMainFrameWindowAdaptor extends WindowAdapter{
	

    public void windowClosing(WindowEvent evt) {
    	InplayPlayerUtil.cleanUp();
    }

}
