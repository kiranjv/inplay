package com.app.player;

import java.awt.Canvas;
import java.awt.Color;
import java.awt.Font;

import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;

import uk.co.caprica.vlcj.player.MediaPlayerFactory;
import uk.co.caprica.vlcj.player.embedded.EmbeddedMediaPlayer;

import com.app.player.center.InplayCenterFillerPanel;
import com.app.player.center.InlayCenterPanel;
import com.app.player.center.InlayMovieIconPanel;
import com.app.player.center.InplayReleasePanel;
import com.app.player.common.InplayConstants;
import com.app.player.header.InplayHeaderPanel;
import com.app.player.left.InplayGenerePanel;
import com.app.player.login.InplayLoginFrame;
import com.app.player.media.InplayPlayerControlsPanel;
import com.app.player.media.InplayPlayerScreenPanel;
import com.app.player.media.InplayRatingsPanel;
import com.app.player.media.listener.InplayMediaPlayerKeyListener;
import com.app.player.media.listener.InplayMediaPlayerMouseListener;
import com.app.player.search.InplaySearchPanel;

public class InplayComponentFactory {  
	
	private static InplayHeaderPanel headPanel;
	private static InplayCenterFillerPanel centerFillerPanel;
	private static InlayCenterPanel centerPanel;
	private static InplayGenerePanel generePanel;
	private static EmbeddedMediaPlayer mediaPlayer;
	private static InplayPlayerControlsPanel controlsPanel;
	private static Canvas videoSurface;
	private static JPanel mainPanel;
	private static JFrame mainFrame;
	private static JPanel mediaPanel;
	private static InplayMediaPlayerMouseListener mouseListener;
	private static InplayMediaPlayerKeyListener mediaPlayerKeyListener;
	private static MediaPlayerFactory mediaPlayerFactory;
	private static InplayLoginFrame loginFrame;
	private static InplayPlayerScreenPanel playerScreenPanel;
	private static InplayCompositePanel compositePanel;
	private static JPanel mediaPlayerEastPanel;
	private static JPanel firstCenterPanel;
	private static InplayReleasePanel releasePanel;
	private static InplaySearchPanel searchPanel;
	private static InplayLoadingFrame loadingFrame;
	
	
	public static InplayLoadingFrame getLoadingFrame() {
		if(loadingFrame==null) loadingFrame = InplayLoadingFrame.getLoadingFrame();
		return loadingFrame;
	}
	
	public static InplayRatingsPanel getRatingsPanel() {
		return InplayRatingsPanel.instance;
	}

	public static void setLoadingFrame(InplayLoadingFrame loadingFrame) {
		InplayComponentFactory.loadingFrame = loadingFrame;
	}

	public static InplayCenterFillerPanel getCenterFillerPanel() {
		if(centerFillerPanel==null) centerFillerPanel = new InplayCenterFillerPanel();
		return centerFillerPanel;
	}

	public static void setCenterFillerPanel(InplayCenterFillerPanel centerFillerPanel) {
		InplayComponentFactory.centerFillerPanel = centerFillerPanel;
	}

	public static InlayCenterPanel getCenterPanel() {
		if(centerPanel==null) centerPanel = new InlayCenterPanel();
		return centerPanel;
	}

	public static void setCenterPanel(InlayCenterPanel centerPanel) {
		InplayComponentFactory.centerPanel = centerPanel;
	}


	public static InplayGenerePanel getGenerePanel() {
		if(generePanel ==null) generePanel = new InplayGenerePanel();

		return generePanel;
	}

	public static void setGenerePanel(InplayGenerePanel generePanel) {
		InplayComponentFactory.generePanel = generePanel;
	}

	public static InplayHeaderPanel getHeadPanel() {
		if(headPanel == null) 		headPanel = new InplayHeaderPanel();
		return headPanel;
	}

	public static void setHeadPanel(InplayHeaderPanel headPanel) {
		InplayComponentFactory.headPanel = headPanel;
	}


	public static EmbeddedMediaPlayer getMediaPlayer() {
		if(mediaPlayer==null) mediaPlayer = InplayComponentBuilder.buildMediaPlayer();
		return mediaPlayer;
	}

	public static void setMediaPlayer(EmbeddedMediaPlayer mediaPlayer) {
		InplayComponentFactory.mediaPlayer = mediaPlayer;
	}

	public static InplayPlayerControlsPanel getControlsPanel() {
		if(controlsPanel==null) InplayComponentBuilder.buildControlPanel();
		return controlsPanel;
	}

	public static void setControlsPanel(InplayPlayerControlsPanel controlsPanel) {
		InplayComponentFactory.controlsPanel = controlsPanel;
	}

	public static Canvas getVideoSurface() {
		if(videoSurface==null) videoSurface = InplayComponentBuilder.buildVideoSurface();
		return videoSurface;
	}

	public static void setVideoSurface(Canvas videoSurface) {
		InplayComponentFactory.videoSurface = videoSurface;
	}

	public static JFrame getMainFrame() {
		if(mainFrame==null) mainFrame = InplayComponentBuilder.buildMainFrame();
		return mainFrame;
	}

	public static void setMainFrame(JFrame mainFrame) {
		InplayComponentFactory.mainFrame = mainFrame;
	}

	public static JPanel getMainPanel() {
		if(mainPanel==null) mainPanel = InplayComponentBuilder.buildMainPanel();
		return mainPanel;
	}

	public static void setMainPanel(JPanel mainPanel) {
		InplayComponentFactory.mainPanel = mainPanel;
	}

	public static JPanel getMediaPanel() {
		return mediaPanel;
	}

	public static void setMediaPanel(JPanel mediaPanel) {
		InplayComponentFactory.mediaPanel = mediaPanel;
	}

	public static InplayMediaPlayerMouseListener getMouseListener() {
		if(mouseListener ==null) mouseListener = new InplayMediaPlayerMouseListener();
		return mouseListener;
	}

	public static void setMouseListener(InplayMediaPlayerMouseListener mouseListener) {
		InplayComponentFactory.mouseListener = mouseListener;
	}

	public static InplayMediaPlayerKeyListener getMediaPlayerKeyListener() {
		if(mediaPlayerKeyListener==null) mediaPlayerKeyListener = new InplayMediaPlayerKeyListener(); 
		return mediaPlayerKeyListener;
	}

	public static void setMediaPlayerKeyListener(
			InplayMediaPlayerKeyListener mediaPlayerKeyListener) {
		InplayComponentFactory.mediaPlayerKeyListener = mediaPlayerKeyListener;
	}

	public static MediaPlayerFactory getMediaPlayerFactory() {
		return mediaPlayerFactory;
	}

	public static void setMediaPlayerFactory(MediaPlayerFactory mediaPlayerFactory) {
		InplayComponentFactory.mediaPlayerFactory = mediaPlayerFactory;
	}

	public static InplayLoginFrame getLoginFrame() {
		if(loginFrame == null) loginFrame = new InplayLoginFrame();
		return loginFrame;
	}

	public static void setLoginFrame(InplayLoginFrame loginFrame) {
		InplayComponentFactory.loginFrame = loginFrame;
	}

	public static InplayPlayerScreenPanel getPlayerScreenPanel() {
		if(playerScreenPanel ==null) playerScreenPanel = new InplayPlayerScreenPanel(null);
		return playerScreenPanel;
	}

	public static void setPlayerScreenPanel(InplayPlayerScreenPanel playerScreenPanel) {
		InplayComponentFactory.playerScreenPanel = playerScreenPanel;
	}

	public static InplayCompositePanel getCompositePanel() {
		if(compositePanel == null) compositePanel = new InplayCompositePanel();
		return compositePanel;
	}

	public static void setCompositePanel(InplayCompositePanel compositePanel) {
		InplayComponentFactory.compositePanel = compositePanel;
	}

	public static JPanel getMediaPlayerEastPanel() {
		if(mediaPlayerEastPanel==null)mediaPlayerEastPanel = InplayComponentBuilder.buildMediaEastPanel();
		return mediaPlayerEastPanel;
	}

	public static void setMediaPlayerEastPanel(JPanel mediaPlayerEastPanel) {
		InplayComponentFactory.mediaPlayerEastPanel = mediaPlayerEastPanel;
	}

	public static JPanel getFirstCenterPanel() {
		return firstCenterPanel;
	}

	public static void setFirstCenterPanel(JPanel firstCenterPanel) {
		InplayComponentFactory.firstCenterPanel = firstCenterPanel;
	}

	public static InplayReleasePanel getReleasePanel() {
		if(releasePanel==null) releasePanel = new InplayReleasePanel();
		return releasePanel;
	}

	public static void setReleasePanel(InplayReleasePanel releasePanel) {
		InplayComponentFactory.releasePanel = releasePanel;
	}

	public static InplaySearchPanel getSearchPanel() {
		if(searchPanel==null) searchPanel = new InplaySearchPanel();
		return searchPanel;
	}

	public static void setSearchPanel(InplaySearchPanel searchPanel) {
		InplayComponentFactory.searchPanel = searchPanel;
	}
	
	public static JLabel createLoadingLabel() {
		JLabel loadingLabel = new JLabel();
		loadingLabel.setFont(new Font(InplayConstants.FONT_TOHOMA,0,35));
		loadingLabel.setForeground(new Color(125,125,125));
		return loadingLabel;
	}
	

}
