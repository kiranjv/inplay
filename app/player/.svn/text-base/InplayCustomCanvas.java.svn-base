package com.app.player;

import java.awt.Canvas;
import java.awt.Graphics;
import java.awt.Image;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.net.URL;

import javax.imageio.ImageIO;

import com.app.player.common.InplayConstants;
import com.app.player.data.InplayVideoDetailsDTO;
import com.app.player.util.InplayImageFinder;
import com.app.player.util.InplayPlayerUtil;

import uk.co.caprica.vlcj.runtime.RuntimeUtil;
import uk.co.caprica.vlcj.runtime.windows.WindowsCanvas;

public class InplayCustomCanvas extends Canvas {
	
	
	  public void paint(Graphics paramGraphics)
	  {
		  super.paint(paramGraphics);
		  Image backgroundImage = getbackgroundImage();
  		  paramGraphics.drawImage(backgroundImage, 0,0,this.getWidth(),this.getHeight(),this);
	  }

	private Image getbackgroundImage() {
		Image backgroundImage = null;
		  InplayVideoDetailsDTO dto = InplayPlayerContext.getPlayingVideoDTO();
		  try {
		  String imagePath = InplayImageFinder.getLocalPath(new URL(dto.getPoster()));
		  backgroundImage = ImageIO.read(new File(imagePath));
		  }catch (Exception e) {
			// TODO: handle exception
			throw new RuntimeException(e);  
		}
		return backgroundImage;
	}
	  
	  public static Canvas getCanvas() {
		  if(RuntimeUtil.isWindows()) {
	            // If running on Windows and you want the mouse/keyboard event hack...
	            return new CustomWindowCanvas();
	        }
	        else {
	        	return new InplayCustomCanvas();
	        }
		  
	  }

}

class CustomWindowCanvas extends WindowsCanvas {
	
	 public void paint(Graphics paramGraphics)
	  {
		  super.paint(paramGraphics);
		  Image backgroundImage = null;
		  backgroundImage = getbackgroundImage();
		  paramGraphics.drawImage(backgroundImage, 0,0,this.getWidth(),this.getHeight(),this);
		  
	  }

		private Image getbackgroundImage() {
			Image backgroundImage = null;
			  InplayVideoDetailsDTO dto = InplayPlayerContext.getPlayingVideoDTO();
			  try {
			  String imagePath = InplayImageFinder.getLocalPath(new URL(dto.getPoster()));
			  backgroundImage = ImageIO.read(new File(imagePath));
			  }catch (Exception e) {
				throw new RuntimeException(e);  
			}
			return backgroundImage;
		}

}
