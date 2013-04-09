package com.app.player.media;

/*
* This file is part of VLCJ.
*
* VLCJ is free software: you can redistribute it and/or modify
* it under the terms of the GNU General Public License as published by
* the Free Software Foundation, either version 3 of the License, or
* (at your option) any later version.
*
* VLCJ is distributed in the hope that it will be useful,
* but WITHOUT ANY WARRANTY; without even the implied warranty of
* MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the
* GNU General Public License for more details.
*
* You should have received a copy of the GNU General Public License
* along with VLCJ. If not, see <http://www.gnu.org/licenses/>.
*
* Copyright 2009, 2010, 2011, 2012 Caprica Software Limited.
*/


import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.io.File;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JSlider;
import javax.swing.SwingUtilities;
import javax.swing.border.EmptyBorder;
import javax.swing.border.LineBorder;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;
import javax.swing.filechooser.FileFilter;

import com.app.player.InplayComponentFactory;
import com.app.player.InplayCompositePanel;

import com.app.player.media.listener.InPlayControlPanelMouseListener;
import com.app.player.util.InplayBackgroundImagePanel;
import com.app.player.util.InplayComponentUtils;
import com.sun.jna.Native;
import com.sun.jna.NativeLibrary;

import uk.co.caprica.vlcj.binding.LibVlc;
import uk.co.caprica.vlcj.binding.LibVlcConst;
import uk.co.caprica.vlcj.component.EmbeddedMediaPlayerComponent;
import uk.co.caprica.vlcj.filter.swing.SwingFileFilterFactory;
import uk.co.caprica.vlcj.player.MediaPlayer;
import uk.co.caprica.vlcj.player.MediaPlayerEventAdapter;
import uk.co.caprica.vlcj.player.embedded.EmbeddedMediaPlayer;
import uk.co.caprica.vlcj.runtime.RuntimeUtil;

public class InplayPlayerControlsPanel extends JPanel {

    private static final long serialVersionUID = 1L;

    private static final int SKIP_TIME_MS = 10 * 1000;
  
    private final ScheduledExecutorService executorService = Executors.newSingleThreadScheduledExecutor();

  
    private JLabel timeLabel;
    private JSlider positionSlider;
    private JLabel chapterLabel;
  
    private JLabel rewindPanel;
    private JButton stopPanel;
    private JButton pauseButton;
    private JButton playPanel;
    private JButton fastForwardPanel;
  
    private JButton toggleMuteButton;
    private JSlider volumeSlider;
    
    
  
    private JButton ejectButton;
  
    private JButton fullScreenButton;
  
  
    private JFileChooser fileChooser;
  
    private boolean mousePressedPlaying = false;

	private boolean setPositionValue;
    
    {
        positionSlider = new JSlider();
    }

    public JSlider getPositionSlider() {
		return positionSlider;
	}

	public InplayPlayerControlsPanel() {

        createUI();
     
       
        
       // this.addMouseListener(new InPlayControlPanelMouseListener(1));
        
        executorService.scheduleAtFixedRate(new UpdateRunnable(), 0L, 1L, TimeUnit.SECONDS);
    }

    private void createUI() {
        createControls();
        layoutControls();
        registerListeners();
        InplayComponentFactory.setControlsPanel(this);
    }

    private void createControls() {
        timeLabel = new JLabel("hh:mm:ss");


        positionSlider.setMinimum(0);
        positionSlider.setMaximum(1000);
        positionSlider.setValue(0);
        positionSlider.setToolTipText("Position");
        positionSlider.setBackground(Color.WHITE);

        chapterLabel = new JLabel("00/00");

        String playerImagePath = "images" + "/" + "player" ;
        String imagePath = playerImagePath + "/" + "back.png";
        rewindPanel = InplayComponentUtils.getImageLabel(31, 20, imagePath);

//		rewindPanel =  new BackgroundImagePanel(string);
//      rewindPanel.setPreferredSize(new Dimension(31,20));

        String path = playerImagePath + "/" + "stop2.png";
        System.out.println(path);
		stopPanel = InplayComponentUtils.getImageButton(18,23,path);
        stopPanel.setBorder(null);

        pauseButton = InplayComponentUtils.getImageButton(20, 25, playerImagePath + "/" + "pause.png");
//        pausePanel = new BackgroundImagePanel("D:\\personal\\work\\nikhil\\jvlc\\images\\player\\pause.png");
        pauseButton.setBackground(Color.WHITE);
        pauseButton.setPreferredSize(new Dimension(18,23));
        pauseButton.setBorder(null);
//        

        playPanel = InplayComponentUtils.getImageButton(26, 41, playerImagePath + "/" + "play2.png");
//        playPanel = new BackgroundImagePanel("D:\\personal\\work\\nikhil\\jvlc\\images\\player\\play2.png");
		playPanel.setPreferredSize(new Dimension(23,36));
        playPanel.setToolTipText("Play");
        playPanel.setBackground(Color.WHITE);
        playPanel.setBorder(null);

        fastForwardPanel = InplayComponentUtils.getImageButton(31,20,playerImagePath + "/" + "forward2.png");
//        fastForwardPanel.setIcon(new ImageIcon(getClass().getClassLoader().getResource("icons/control_fastforward_blue.png")));
        fastForwardPanel.setToolTipText("Skip forward");
        fastForwardPanel.setPreferredSize(new Dimension(31,20));
        fastForwardPanel.setBorder(null);

        toggleMuteButton = new JButton("Mu");
//        toggleMuteButton.setIcon(new ImageIcon(getClass().getClassLoader().getResource("icons/sound_mute.png")));
        toggleMuteButton.setToolTipText("Toggle Mute");
        toggleMuteButton.setBackground(Color.GRAY);

        volumeSlider = new JSlider();
        volumeSlider.setOrientation(JSlider.HORIZONTAL);
        volumeSlider.setMinimum(LibVlcConst.MIN_VOLUME);
        volumeSlider.setMaximum(LibVlcConst.MAX_VOLUME);
        volumeSlider.setPreferredSize(new Dimension(130, 40));
        volumeSlider.setToolTipText("Change volume");
        volumeSlider.setBackground(Color.WHITE);
        

        ejectButton = new JButton("EJ");
//        ejectButton.setIcon(new ImageIcon(getClass().getClassLoader().getResource("icons/control_eject_blue.png")));
        ejectButton.setToolTipText("Load/eject media");
        ejectButton.setBackground(Color.GRAY);


        fileChooser = new JFileChooser();
        fileChooser.setApproveButtonText("Play");
        fileChooser.addChoosableFileFilter(SwingFileFilterFactory.newVideoFileFilter());
        fileChooser.addChoosableFileFilter(SwingFileFilterFactory.newAudioFileFilter());
        fileChooser.addChoosableFileFilter(SwingFileFilterFactory.newPlayListFileFilter());
        FileFilter defaultFilter = SwingFileFilterFactory.newMediaFileFilter();
        fileChooser.addChoosableFileFilter(defaultFilter);
        fileChooser.setFileFilter(defaultFilter);
        fileChooser.setBackground(Color.GRAY);

        fullScreenButton = new JButton("FS");
//        fullScreenButton.setIcon(new ImageIcon(getClass().getClassLoader().getResource("icons/image.png")));
        fullScreenButton.setToolTipText("Toggle full-screen");
        fullScreenButton.setBackground(Color.GRAY);

    }

    private void layoutControls() {
        setBorder(new EmptyBorder(4, 4, 4, 4));

        setLayout(new BorderLayout());

        JPanel positionPanel = new JPanel();
        positionPanel.setBackground(Color.WHITE);
        positionPanel.setLayout(new GridLayout(1, 1));
        // positionPanel.add(positionProgressBar);
        positionPanel.add(positionSlider);

        JPanel topPanel = new JPanel();
        topPanel.setBackground(Color.WHITE);
        topPanel.setLayout(new BorderLayout(8, 0));

        topPanel.add(timeLabel, BorderLayout.WEST);
        topPanel.add(positionPanel, BorderLayout.CENTER);
        topPanel.add(chapterLabel, BorderLayout.EAST);

        add(topPanel, BorderLayout.NORTH);

        JPanel bottomPanel = new JPanel(new BorderLayout());
        bottomPanel.setBackground(Color.WHITE);
        
        JPanel controlPanel = new JPanel();
        
        controlPanel.setBackground(Color.WHITE);
        controlPanel.setLayout(new FlowLayout());

//        controlPanel.add(rewindPanel);
        controlPanel.add(stopPanel);
        controlPanel.add(playPanel);
        controlPanel.add(pauseButton);
//        controlPanel.add(fastForwardPanel);

        controlPanel.add(volumeSlider);
//        controlPanel.add(toggleMuteButton);
//
//
//        controlPanel.add(ejectButton);
//
//        controlPanel.add(fullScreenButton);
        bottomPanel.add(controlPanel,BorderLayout.CENTER);
        
        add(bottomPanel, BorderLayout.SOUTH);
    }

    /**
* Broken out position setting, handles updating mediaPlayer
*/
    private void setSliderBasedPosition() {
        if(!InplayComponentFactory.getMediaPlayer().isSeekable()) {
            return;
        }
        float positionValue = (float)positionSlider.getValue() / 1000.0f;
        // Avoid end of file freeze-up
        if(positionValue > 0.99f) {
            positionValue = 0.99f;
        }
        InplayComponentFactory.getMediaPlayer().setPosition(positionValue);
        
    }

    private void updateUIState() {
        if(!InplayComponentFactory.getMediaPlayer().isPlaying()) {
            // Resume play or play a few frames then pause to show current position in video
        	InplayComponentFactory.getMediaPlayer().play();
            if(!mousePressedPlaying) {
                try {
                    // Half a second probably gets an iframe
                    Thread.sleep(500);
                }
                catch(InterruptedException e) {
                    // Don't care if unblocked early
                }
                InplayComponentFactory.getMediaPlayer().pause();
            }
        }
        long time = InplayComponentFactory.getMediaPlayer().getTime();
        int position = (int)(InplayComponentFactory.getMediaPlayer().getPosition() * 1000.0f);
        int chapter = InplayComponentFactory.getMediaPlayer().getChapter();
        int chapterCount = InplayComponentFactory.getMediaPlayer().getChapterCount();
        updateTime(time);
        updatePosition(position);
        updateChapter(chapter, chapterCount);
    }

    private void skip(int skipTime) {
        // Only skip time if can handle time setting
        if(InplayComponentFactory.getMediaPlayer().getLength() > 0) {
        	InplayComponentFactory.getMediaPlayer().skip(skipTime);
            updateUIState();
        }
    }

    private void registerListeners() {
    	InplayComponentFactory.getMediaPlayer().addMediaPlayerEventListener(new MediaPlayerEventAdapter() {
            
            public void playing(MediaPlayer mediaPlayer) {
                updateVolume(mediaPlayer.getVolume());
            }
        });

    	positionSlider.addChangeListener(new ChangeListener() {
    	      @Override
    	      public void stateChanged(ChangeEvent e) {
    	        if(!positionSlider.getValueIsAdjusting() && !setPositionValue) {
    	          float positionValue = (float)positionSlider.getValue() / 100.0f;
    	          InplayComponentFactory.getMediaPlayer().setPosition(positionValue);
    	        }
    	      }
    	    });
    	/* code commented by kiran */
       /* positionSlider.addMouseListener(new MouseAdapter() {
            
            public void mousePressed(MouseEvent e) {
                if(InplayComponentFactory.getMediaPlayer().isPlaying()) {
                    mousePressedPlaying = true;
                    //InplayComponentFactory.getMediaPlayer().pause();
                }
                else {
                    mousePressedPlaying = false;
                }
                setSliderBasedPosition();
            }

            
            public void mouseReleased(MouseEvent e) {
                setSliderBasedPosition();
                updateUIState();
            }
        });*/


        rewindPanel.addMouseListener(new MouseListener() {
            

			public void mouseClicked(MouseEvent arg0) {
                skip(-SKIP_TIME_MS);
				
			}

			public void mouseEntered(MouseEvent arg0) {
				// TODO Auto-generated method stub
				
			}

			public void mouseExited(MouseEvent arg0) {
				// TODO Auto-generated method stub
				
			}

			public void mousePressed(MouseEvent arg0) {
				// TODO Auto-generated method stub
				
			}

			public void mouseReleased(MouseEvent arg0) {
				// TODO Auto-generated method stub
				
			}
        });

        stopPanel.addMouseListener(new MouseAdapter() {
            

			public void mouseClicked(MouseEvent arg0) {
            	InplayComponentFactory.getMediaPlayer().stop();
				
			}

        });

        pauseButton.addMouseListener(new MouseAdapter() {
            

			public void mouseClicked(MouseEvent arg0) {
            	InplayComponentFactory.getMediaPlayer().pause();
				
			}

        });

        playPanel.addMouseListener(new MouseAdapter() {
            

			public void mouseClicked(MouseEvent arg0) {
				InplayComponentFactory.getMediaPlayer().play();
				
			}

        });

        fastForwardPanel.addMouseListener(new MouseAdapter() {
            

			public void mouseClicked(MouseEvent arg0) {
				skip(SKIP_TIME_MS);
				
			}

        });


        toggleMuteButton.addActionListener(new ActionListener() {
            
            public void actionPerformed(ActionEvent e) {
            	InplayComponentFactory.getMediaPlayer().mute();
            }
        });

        volumeSlider.addChangeListener(new ChangeListener() {
            
            public void stateChanged(ChangeEvent e) {
                JSlider source = (JSlider)e.getSource();
                // if(!source.getValueIsAdjusting()) {
                InplayComponentFactory.getMediaPlayer().setVolume(source.getValue());
                // }
            }
        });


        ejectButton.addActionListener(new ActionListener() {
            
            public void actionPerformed(ActionEvent e) {
            	InplayComponentFactory.getMediaPlayer().enableOverlay(false);
                if(JFileChooser.APPROVE_OPTION == fileChooser.showOpenDialog(InplayPlayerControlsPanel.this)) {
                	InplayComponentFactory.getMediaPlayer().playMedia(fileChooser.getSelectedFile().getAbsolutePath());
                }
                InplayComponentFactory.getMediaPlayer().enableOverlay(true);
            }
        });


        fullScreenButton.addActionListener(new ActionListener() {
            
            public void actionPerformed(ActionEvent e) {
            	InplayComponentFactory.getMediaPlayer().toggleFullScreen();
            	setVisible(false);
//            	ComponentFactory.getMoviesSelectPanel().setVisible(false);
            }
        });

    }

    private final class UpdateRunnable implements Runnable {


        private UpdateRunnable() {
        }

        
        public void run() {
            final long time = InplayComponentFactory.getMediaPlayer().getTime();
            final int position = (int)(InplayComponentFactory.getMediaPlayer().getPosition() * 1000.0f);
            final int chapter = InplayComponentFactory.getMediaPlayer().getChapter();
            final int chapterCount = InplayComponentFactory.getMediaPlayer().getChapterCount();

            // Updates to user interface components must be executed on the Event
            // Dispatch Thread
            SwingUtilities.invokeLater(new Runnable() {
                
                public void run() {
                    if(InplayComponentFactory.getMediaPlayer().isPlaying()) {
                        updateTime(time);
                        updatePosition(position);
                        updateChapter(chapter, chapterCount);
                    }
                }
            });
        }
    }

    private void updateTime(long millis) {
        String s = String.format("%02d:%02d:%02d", TimeUnit.MILLISECONDS.toHours(millis), TimeUnit.MILLISECONDS.toMinutes(millis) - TimeUnit.HOURS.toMinutes(TimeUnit.MILLISECONDS.toHours(millis)), TimeUnit.MILLISECONDS.toSeconds(millis) - TimeUnit.MINUTES.toSeconds(TimeUnit.MILLISECONDS.toMinutes(millis)));
        timeLabel.setText(s);
    }

    private void updatePosition(int value) {
        // positionProgressBar.setValue(value);
    	setPositionValue = true;
        positionSlider.setValue(value);
        setPositionValue = false;
    }

    private void updateChapter(int chapter, int chapterCount) {
        String s = chapterCount != -1 ? (chapter + 1) + "/" + chapterCount : "-";
        chapterLabel.setText(s);
        chapterLabel.invalidate();
        validate();
    }

    private void updateVolume(int value) {
        volumeSlider.setValue(value);
    }
    
	public static void main(String[] args) {
		JFrame frame = new JFrame();
		InplayPlayerControlsPanel playerControlsPanel = new InplayPlayerControlsPanel();
		frame.add(playerControlsPanel);
		    frame.setLocation(100, 100);
		    frame.setSize(950, 620);
		    frame.setUndecorated(true);
		    frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		  frame.setVisible(true);
		}



}