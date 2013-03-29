package com.app.player.util;

import java.awt.AWTEvent;
import java.awt.BorderLayout;
import java.awt.Canvas;
import java.awt.Color;
import java.awt.Component;
import java.awt.Cursor;
import java.awt.Image;
import java.awt.Point;
import java.awt.Rectangle;
import java.awt.Toolkit;
import java.awt.event.AWTEventListener;
import java.awt.event.KeyEvent;
import java.awt.event.MouseEvent;
import java.awt.image.BufferedImage;
import java.io.EOFException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.MalformedURLException;
import java.net.URL;
import java.security.CodeSource;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Set;

import javax.imageio.ImageIO;
import javax.swing.ImageIcon;
import javax.swing.JComponent;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.UIManager;
import javax.swing.UIManager.LookAndFeelInfo;
import javax.swing.border.LineBorder;

import uk.co.caprica.vlcj.player.embedded.EmbeddedMediaPlayer;
import uk.co.caprica.vlcj.runtime.windows.WindowsCanvas;

import com.InplayResourceFinder;
import com.app.player.InplayComponentFactory;
import com.app.player.InplayPlayerContext;
import com.app.player.InplayScreenResolutionHelper;
import com.app.player.common.InplayConstants;
import com.app.player.context.InlayPlayerContext;
import com.app.player.data.InplayDataCreator;
import com.app.player.data.InplayDataProvider;
import com.app.player.data.InplayQueryGenerator;
import com.app.player.data.InplayVideoDetailsDTO;
import com.app.player.header.InplayHeaderPanel;
import com.app.player.http.InplayJavaHttpConnector;
import com.app.player.left.InplayGenerePanel;
import com.app.player.media.InplayPlayerControlsPanel;
import com.app.player.media.InplayPlayerScreenPanel;

public class InplayPlayerUtil {

	public static void playVideo(InplayVideoDetailsDTO dto) throws Exception {
		InplayPlayerContext.setPlayingVideoDTO(dto);
		EmbeddedMediaPlayer mediaPlayer = InplayComponentFactory
				.getMediaPlayer();
		mediaPlayer.stop();
		JPanel mainPanel = InplayComponentFactory.getMainPanel();
		mainPanel.remove(InplayComponentFactory.getCenterPanel());
		mainPanel.add(InplayComponentFactory.getMediaPanel(),
				BorderLayout.CENTER);
		// InplayComponentFactory.getSearchPanel().setVisible(false);
		if (InplayPlayerContext.isUserLoggedIn()) {
			InplayComponentFactory.getRatingsPanel().setVisible(true);
			InplayComponentFactory.getRatingsPanel().setStarRatings();
		}
		mainPanel.updateUI();
		InplayComponentFactory.getMainFrame().setVisible(true);
		InlayPlayerContext.setView(InplayConstants.VIEW_MEDIA);
		InplayComponentFactory.getMediaPlayer().playMedia(dto.getVideoPath());
		InplayComponentFactory.getMediaPlayer().enableOverlay(true);
		InplayComponentFactory.getControlsPanel().getPositionSlider()
				.setValue(0);
		// update to the database must happen at the last
		updateVideoCount(dto);
	}

	public static void updateVideoCount(final InplayVideoDetailsDTO dto) {

		if (dto == null || dto.getId() == null
				|| dto.getId().trim().length() == 0) {
			return;
			// add the code to report this situation to the server.
		}

		class Worker extends Thread {

			public void run() {
				if (InplayPlayerContext.isUserLoggedIn()) {
					String insertQuery = InplayQueryGenerator
							.generateInsertQueryForVideoWatch(dto,
									InplayPlayerContext.getUserID());
					String updateQuery = InplayQueryGenerator
							.generateUpdateQueryForVideoWatchVideoTable(dto);
					String result = null;
					try {
						result = InplayDataCreator.executeQuery(insertQuery);
						result = InplayDataCreator.executeQuery(updateQuery);

					} catch (Exception e) {
						// TODO Auto-generated catch block
						throw new RuntimeException(e);
					}
					String videoWatchCount = dto.getVideoWatchCount();
					int value = new Integer(videoWatchCount).intValue() + 1;
					dto.setVideoWatchCount("" + value);
					System.out.println("updated data with results = " + result);
					// add code to handle the result in case the query fails
				}
			}
		}

		new Worker().start();
	}

	public static void updateUserRating(final int userRating) {

		class Worker extends Thread {

			public void run() {
				if (InplayPlayerContext.isUserLoggedIn()) {
					InplayVideoDetailsDTO playingVideoDTO = InplayPlayerContext
							.getPlayingVideoDTO();
					String selectQuery = InplayQueryGenerator
							.generateSelectQueryForUserRating(playingVideoDTO,
									InplayPlayerContext.getUserID(), userRating);
					String result = null;
					try {
						result = InplayDataCreator.executeQuery(selectQuery);
						result = result.replaceAll("<tr>", "");
						result = result.replaceAll("<br>", "");
						System.out.println("---- result --- " + result);
						if (!result.trim().equals("0")) {
							String insertQuery = InplayQueryGenerator
									.generateUpdateQueryForUserRating(
											playingVideoDTO,
											InplayPlayerContext.getUserID(),
											userRating);
							result = InplayDataCreator
									.executeQuery(insertQuery);
						} else {
							String insertQuery = InplayQueryGenerator
									.generateInsertQueryForUserRating(
											playingVideoDTO,
											InplayPlayerContext.getUserID(),
											userRating);
							result = InplayDataCreator
									.executeQuery(insertQuery);
						}
						result = updateVideoUserRatingsTable(playingVideoDTO);
					} catch (Exception e) {
						throw new RuntimeException(e);
					}
					System.out.println("updated data with results = " + result);
					// add code to handle the result in case the query fails
				}
			}

			private String updateVideoUserRatingsTable(
					InplayVideoDetailsDTO playingVideoDTO) throws Exception {
				String avgQuery = InplayQueryGenerator
						.generateAverageQueryForUserRating(playingVideoDTO);
				String result = InplayDataCreator.executeQuery(avgQuery);
				result = result.replaceAll("<tr>", "");
				result = result.replaceAll("<br>", "");
				result = result.trim();
				int ceil = new Double(Math.ceil(Double.parseDouble(result)))
						.intValue();
				int floor = new Double(Math.floor(Double.parseDouble(result)))
						.intValue();
				int rating = (ceil + floor) / 2;
				String updateQuery = InplayQueryGenerator
						.generateUpdateQueryForUserRatingVideoTable(
								playingVideoDTO, rating);
				result = InplayDataCreator.executeQuery(updateQuery);
				playingVideoDTO.setUserRating("" + rating);
				return result;
			}
		}

		new Worker().start();
	}

	public static String getCurrentFormatedDate() {
		Date date = new Date();
		SimpleDateFormat formater = new SimpleDateFormat(
				InplayConstants.DB_DATE_FORMAT);
		return formater.format(date);
	}

	public static Date formatDate(String date) {
		SimpleDateFormat format = new SimpleDateFormat(
				InplayConstants.DB_DATE_FORMAT);
		try {
			return format.parse(date);
		} catch (ParseException e) {
			throw new RuntimeException(e);
		}
	}

	public static void init() throws IOException {
		JFrame mainFrame = InplayComponentFactory.getMainFrame();
		mainFrame.setSize(950, 650);
		mainFrame.setTitle(InplayConstants.TITLE);
		mainFrame.setIconImage(getTitleIcon());
		mainFrame.setLocation(InplayScreenResolutionHelper.getXLocation(950),
				InplayScreenResolutionHelper.getYLocation(650));
		JPanel mainPanel = InplayComponentFactory.getMainPanel();
		mainFrame.setContentPane(mainPanel);
		mainPanel
				.add(InplayComponentFactory.getHeadPanel(), BorderLayout.NORTH);
		mainPanel.add(InplayComponentFactory.getGenerePanel(),
				BorderLayout.WEST);
		mainPanel.add(InplayComponentFactory.getCenterPanel(),
				BorderLayout.CENTER);
		new InplayPlayerScreenPanel(null);
		mainFrame.setVisible(true);
	}

	private static void attachEscapeListener() {
		Toolkit.getDefaultToolkit().addAWTEventListener(new AWTEventListener() {

			public void eventDispatched(AWTEvent paramAWTEvent) {
				if (paramAWTEvent instanceof KeyEvent) {
					KeyEvent keyEvent = (KeyEvent) paramAWTEvent;
					boolean fullScreenMode = InplayPlayerContext.isFullScreenMode();
					System.out.println(keyEvent);
					if (fullScreenMode) {
						System.out.println(keyEvent);
						if (keyEvent.getKeyCode() == 27) {
							System.out.println(keyEvent);
						}
					}
				}
			}

		}, AWTEvent.KEY_EVENT_MASK);
	}

	public static void showMainIcon() {
		InplayComponentFactory.getSearchPanel().setVisible(true);
		JPanel mainPanel = InplayComponentFactory.getMainPanel();
		EmbeddedMediaPlayer mediaPlayer = InplayComponentFactory
				.getMediaPlayer();
		mediaPlayer.stop();
		mainPanel.remove(InplayComponentFactory.getMediaPanel());
		mainPanel.add(InplayComponentFactory.getCenterPanel(),
				BorderLayout.CENTER);
		InplayComponentFactory.getHeadPanel().setVisible(true);
		InplayComponentFactory.getRatingsPanel().setVisible(false);
		mainPanel.updateUI();
		InplayComponentFactory.getMainFrame().setVisible(true);
		InlayPlayerContext.setView(InplayConstants.VIEW_ICONS);
		JFrame loadFrame = InplayComponentFactory.getLoadingFrame();
		if (loadFrame.isVisible()) {
			loadFrame.setVisible(false);
			loadFrame.dispose();
		}
	}

	public static void setLookAndFeel() {
		String lookAndFeelClassName = null;
		LookAndFeelInfo[] lookAndFeelInfos = UIManager
				.getInstalledLookAndFeels();
		for (LookAndFeelInfo lookAndFeel : lookAndFeelInfos) {
			if ("Nimbus".equals(lookAndFeel.getName())) {
				lookAndFeelClassName = lookAndFeel.getClassName();
			}
		}
		if (lookAndFeelClassName == null) {
			lookAndFeelClassName = UIManager.getSystemLookAndFeelClassName();
		}
		try {
			UIManager.setLookAndFeel(lookAndFeelClassName);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public static List<String> getVLCArgs() {
		List<String> vlcArgs = new ArrayList<String>();

		// vlcArgs.add("--ffmpeg-hw"); // <--- if your system supports it, this
		// might be useful
		// vlcArgs.add("--no-plugins-cache");
		vlcArgs.add("--no-video-title-show");
		vlcArgs.add("--no-snapshot-preview");
		vlcArgs.add("--clock-jitter=0");
		// vlcArgs.add("--quiet");
		// vlcArgs.add("--quiet-synchro");
		// vlcArgs.add("--intf");
		// vlcArgs.add("dummy");
		return vlcArgs;
	}

	public static Rectangle last_bound = null;

	public static void toggleFullScreen() {
		JFrame mainFrame = InplayComponentFactory.getMainFrame();
		if(InplayPlayerContext.isFullScreenMode()) {
			// change to normal mode
			InplayComponentFactory.getMediaPlayer().toggleFullScreen();
			InplayPlayerContext.setFullScreenMode(false);
			
		}
		else {
			//change to full screen mode
			InplayComponentFactory.getMediaPlayer().toggleFullScreen();
			InplayPlayerContext.setFullScreenMode(false);
		}
	}
	
	
	public static void old_toggleFullScreen() {

		// InplayComponentFactory.getMediaPlayer().toggleFullScreen();
		
		if (InplayPlayerContext.isFullScreenMode()) {
			// showCursor();
			//mainFrame.setBounds(last_bound);
			InplayComponentFactory.getHeadPanel().setVisible(true);

			InplayComponentFactory.getGenerePanel().setVisible(true);

			InplayComponentFactory.getControlsPanel().setVisible(true);
			InplayComponentFactory.getMainPanel().setBorder(
					new LineBorder(new Color(182, 182, 182), 2));

			InplayComponentFactory.getMediaPlayerEastPanel().setVisible(true);
			 validateComponents();

			//InplayComponentFactory.getMediaPlayer().setFullScreen(false);
			 InplayComponentFactory.getMediaPlayer().toggleFullScreen();
			InplayPlayerContext.setFullScreenMode(false);

			// InplayComponentFactory.getGenerePanel().revalidate();
			//
			// InplayComponentFactory.getHeadPanel().revalidate();
			// InplayComponentFactory.getMediaPlayerEastPanel().revalidate();
			// InplayComponentFactory.getMediaPlayerEastPanel().revalidate();
			// mainPanel.revalidate();
			// controlsPanel.revalidate();

		} else {
			// hideCursor();
			//last_bound = mainFrame.getBounds();
			InplayComponentFactory.getHeadPanel().setVisible(false);

			InplayComponentFactory.getGenerePanel().setVisible(false);

			InplayComponentFactory.getControlsPanel().setVisible(false);
			InplayComponentFactory.getMainPanel().setBorder(null);

			InplayComponentFactory.getMediaPlayerEastPanel().setVisible(false);

			validateComponents();
			//InplayComponentFactory.getMediaPlayer().setFullScreen(true);
			InplayComponentFactory.getMediaPlayer().toggleFullScreen();
			
			InplayPlayerContext.setFullScreenMode(true);
			// InplayComponentFactory.getMediaPlayerEastPanel().repaint();
			// InplayComponentFactory.getGenerePanel().repaint();
			// InplayComponentFactory.getHeadPanel().repaint();
			// InplayComponentFactory.getMediaPlayerEastPanel().repaint();
			// mainPanel.repaint();
			// controlsPanel.repaint();
		}

	}

	private static void validateComponents() {
		InplayComponentFactory.getGenerePanel().validate();
		InplayComponentFactory.getGenerePanel().repaint();
		InplayComponentFactory.getHeadPanel().validate();
		InplayComponentFactory.getHeadPanel().repaint();
		InplayComponentFactory.getMediaPlayerEastPanel().validate();
		InplayComponentFactory.getMediaPlayerEastPanel().repaint();
		InplayComponentFactory.getMediaPlayerEastPanel().validate();
		InplayComponentFactory.getMediaPlayerEastPanel().repaint();
		InplayComponentFactory.getControlsPanel().validate();
		InplayComponentFactory.getControlsPanel().repaint();
		InplayComponentFactory.getMainPanel().validate();
		InplayComponentFactory.getMainPanel().repaint();

	}

	public static void showCursor() {
		InplayComponentFactory.getVideoSurface().setCursor(null);
	}

	public static void hideCursor() {
		BufferedImage cursorImg = new BufferedImage(16, 16,
				BufferedImage.TYPE_INT_ARGB);

		Cursor blankCursor = Toolkit.getDefaultToolkit().createCustomCursor(
				cursorImg, new Point(0, 0), "blank cursor");

		InplayComponentFactory.getVideoSurface().setCursor(blankCursor);
	}

	public static void cleanUp() {
		Canvas videoSurface = InplayComponentFactory.getVideoSurface();
		if (videoSurface instanceof WindowsCanvas) {
			((WindowsCanvas) videoSurface).release();
		}

		EmbeddedMediaPlayer mediaPlayer = InplayComponentFactory
				.getMediaPlayer();
		if (mediaPlayer != null) {
			mediaPlayer.release();
			InplayComponentFactory.setMediaPlayer(null);
		}

		if (InplayComponentFactory.getMediaPlayerFactory() != null) {
			InplayComponentFactory.getMediaPlayerFactory().release();
			InplayComponentFactory.setMediaPlayerFactory(null);
		}
	}

	public static void closePlayer() {
		cleanUp();
		System.exit(0);
	}

	private static Image loadingImage = null;

	public static Image getLoadingImage() {
		try {
			if (loadingImage == null) {
				InputStream resourceAsStream = InplayResourceFinder
						.getResourceAsStream(InplayConstants.LOADING_IMAGE_PATH);
				loadingImage = ImageIO.read(resourceAsStream);
			}
			return loadingImage;
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			throw new RuntimeException(e);
		}
	}

	public static void downloadPoster(final InplayVideoDetailsDTO dto) {
		InplayPlayerContext.getExecutor().execute(new Runnable() {

			public void run() {
				try {
					InplayImageFinder.getLocalPath(new URL(dto.getPoster()));
				} catch (Exception e) {
					System.out.println("downloading poster = "
							+ dto.getPoster());
					e.printStackTrace();
					throw new RuntimeException(e);
				}
			}

		});
	}

	public static byte[] readFully(InputStream paramInputStream, int paramInt,
			boolean paramBoolean) throws IOException {
		byte[] arrayOfByte = new byte[0];
		if (paramInt == -1)
			paramInt = 2147483647;
		int i = 0;
		while (i < paramInt) {
			int j;
			if (i >= arrayOfByte.length) {
				j = Math.min(paramInt - i, arrayOfByte.length + 1024);
				if (arrayOfByte.length < i + j)
					arrayOfByte = Arrays.copyOf(arrayOfByte, i + j);
			} else {
				j = arrayOfByte.length - i;
			}
			int k = paramInputStream.read(arrayOfByte, i, j);
			if (k < 0) {
				if ((paramBoolean) && (paramInt != 2147483647)) {
					throw new EOFException("Detect premature EOF");
				}
				if (arrayOfByte.length == i)
					break;
				arrayOfByte = Arrays.copyOf(arrayOfByte, i);
				break;
			}

			i += k;
		}
		return arrayOfByte;
	}

	public static void showCurrentDTOData() {
		Set<InplayVideoDetailsDTO> videoSet = InplayDataProvider.getVideoSet();
		System.out.println("-------------DTO video thumb details-------------");
		for (Iterator iterator = videoSet.iterator(); iterator.hasNext();) {
			InplayVideoDetailsDTO inplayVideoDetailsDTO = (InplayVideoDetailsDTO) iterator
					.next();
			System.out.println("DTO id:" + inplayVideoDetailsDTO.getId()
					+ " Video thumb" + inplayVideoDetailsDTO.getVideoThumb());

		}
		System.out
				.println("--------------------------------------------------");

	}

	public static void setToolTipRecursively(JComponent c, String text) {
		if (text.equalsIgnoreCase(""))
			text = "Video description unavailable";
		c.validate();
		c.updateUI();
		String formated_text = "<html><body bgcolor = 'white' border = 0>"
				+ "<font color='" + "grey" + "' size=4> " + text;
		c.setToolTipText(formated_text);

		for (Component cc : c.getComponents())
			if (cc instanceof JComponent)
				setToolTipRecursively((JComponent) cc, text);
	}

	public static Image getTitleIcon() throws IOException {
		InputStream resourceAsStream = InplayResourceFinder
				.getResourceAsStream(InplayConstants.TITLE_ICON);
		Image iconImage = ImageIO.read(resourceAsStream);
		return iconImage;
	}
}
