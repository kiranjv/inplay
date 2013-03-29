package com.app.player;

import java.awt.AWTEvent;
import java.awt.BorderLayout;
import java.awt.Canvas;
import java.awt.Color;
import java.awt.Toolkit;
import java.awt.event.AWTEventListener;
import java.awt.event.KeyEvent;
import java.util.List;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.LineBorder;

import uk.co.caprica.vlcj.player.MediaPlayerFactory;
import uk.co.caprica.vlcj.player.embedded.DefaultFullScreenStrategy;
import uk.co.caprica.vlcj.player.embedded.EmbeddedMediaPlayer;
import uk.co.caprica.vlcj.player.embedded.FullScreenStrategy;
import uk.co.caprica.vlcj.runtime.RuntimeUtil;
import uk.co.caprica.vlcj.runtime.windows.WindowsCanvas;

import com.app.player.center.InplayCenterFillerPanel;
import com.app.player.center.InlayCenterPanel;
import com.app.player.center.InlayMovieIconPanel;
import com.app.player.center.InplayReleasePanel;
import com.app.player.header.InplayHeaderPanel;
import com.app.player.left.InplayGenerePanel;
import com.app.player.listener.InplayMainFrameWindowAdaptor;
import com.app.player.media.InplayPlayerControlsPanel;
import com.app.player.media.listener.InplayMediaPlayerEventListener;
import com.app.player.media.listener.InplayMediaPlayerKeyListener;
import com.app.player.media.listener.InplayMediaPlayerMouseListener;
import com.app.player.util.InplayPlayerUtil;

public class InplayComponentBuilder {
	
	public static Canvas buildVideoSurface() {
			Canvas videoSurface = InplayCustomCanvas.getCanvas();
	      	//videoSurface.setBackground(Color.BLACK);
	        videoSurface.addMouseListener(InplayComponentFactory.getMouseListener());
	        videoSurface.addMouseMotionListener(InplayComponentFactory.getMouseListener());
	        videoSurface.addMouseWheelListener(InplayComponentFactory.getMouseListener());
	        videoSurface.addKeyListener(InplayComponentFactory.getMediaPlayerKeyListener());
	       
	        return videoSurface;
	}
	

	public static JPanel buildMainPanel() {
		JPanel mainPanel = new JPanel();
        mainPanel.setBorder(new LineBorder(new Color(182,182,182),2));
        mainPanel.setBackground(Color.WHITE);
        mainPanel.setLayout(new BorderLayout());
        return mainPanel;
	}
	
	
	public static EmbeddedMediaPlayer buildMediaPlayer() {
        FullScreenStrategy fullScreenStrategy = new DefaultFullScreenStrategy(InplayComponentFactory.getMainFrame());

        List<String> vlcArgs = InplayPlayerUtil.getVLCArgs();
        MediaPlayerFactory mediaPlayerFactory = new MediaPlayerFactory(vlcArgs.toArray(new String[vlcArgs.size()]));
        mediaPlayerFactory.setUserAgent("vlcj test player");
        InplayComponentFactory.setMediaPlayerFactory(mediaPlayerFactory);
        

        EmbeddedMediaPlayer mediaPlayer = mediaPlayerFactory.newEmbeddedMediaPlayer(fullScreenStrategy);
        mediaPlayer.setVideoSurface(mediaPlayerFactory.newVideoSurface(InplayComponentFactory.getVideoSurface()));
        mediaPlayer.setPlaySubItems(true);

        mediaPlayer.setEnableKeyInputHandling(false);
        mediaPlayer.setEnableMouseInputHandling(false);
        mediaPlayer.addMediaPlayerEventListener(new InplayMediaPlayerEventListener());
        return mediaPlayer;

	}
	
	private static String formatHttpStream() {
        StringBuilder sb = new StringBuilder(60);
        sb.append(":sout=#duplicate{dst=std{access=http,mux=ts,");
        sb.append("dst=");
        sb.append("www.infinitysoft.us");
        sb.append(':');
        sb.append(80);
        sb.append("}}");
        return sb.toString();
    }
	
	public static InplayPlayerControlsPanel buildControlPanel() {
        InplayPlayerControlsPanel controlsPanel = new InplayPlayerControlsPanel();
        controlsPanel.setBackground(Color.WHITE);
        return controlsPanel;

	}
	
	public static JFrame buildMainFrame() {
		JFrame mainFrame = new JFrame();
        mainFrame.setUndecorated(true);
        mainFrame.setBackground(Color.WHITE);
        mainFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        mainFrame.addWindowListener(new InplayMainFrameWindowAdaptor());
		return mainFrame;
	}


	public static JPanel buildMediaEastPanel() {
        JPanel eastPanel = new JPanel();
        eastPanel.setBackground(Color.WHITE);
        eastPanel.setBorder(new LineBorder(Color.WHITE,15));
        return eastPanel;
		
	}
	
}
