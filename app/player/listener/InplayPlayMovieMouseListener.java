package com.app.player.listener;

import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

import com.app.player.InplayComponentFactory;
import com.app.player.data.InplayVideoDetailsDTO;
import com.app.player.util.InplayPlayerUtil;

/**
 * Mouse click action lister for center panel video poster images.
 * @author kiran
 *
 */
public class InplayPlayMovieMouseListener implements MouseListener{
	
	private InplayVideoDetailsDTO dto;
	

	public InplayPlayMovieMouseListener(InplayVideoDetailsDTO dto) {
		this.dto = dto;
	}
	

	public void setDto(InplayVideoDetailsDTO dto) {
		this.dto = dto;
	}


	public void mouseClicked(MouseEvent paramMouseEvent) {
		if(dto.getVideoPath()==null || dto.getVideoPath().trim().length()==0) {
			throw new RuntimeException("movie url must to play the movie. video path empty");
		}
		System.out.println("playing = " + dto.getVideoPath());
		try {
			InplayPlayerUtil.playVideo(dto);
		} catch (Exception e) {
			e.printStackTrace();
			throw new RuntimeException(e);
		}
	}
	

	public void mouseEntered(MouseEvent paramMouseEvent) {
		// TODO Auto-generated method stub
		
	}

	public void mouseExited(MouseEvent paramMouseEvent) {
		// TODO Auto-generated method stub
		
	}

	public void mousePressed(MouseEvent paramMouseEvent) {
		// TODO Auto-generated method stub
		
	}

	public void mouseReleased(MouseEvent paramMouseEvent) {
		// TODO Auto-generated method stub
		
	}

}
