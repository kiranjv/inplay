package com.app.player.util;

import java.util.HashMap;

import javax.swing.ImageIcon;
import javax.swing.JLabel;
import javax.swing.JPanel;

import com.app.player.common.InplayConstants;
import com.app.player.data.InplayVideoDetailsDTO;

public class InplayRatingsUtils {
	
	private static HashMap<Integer, ImageIcon> goldMap = new HashMap<Integer, ImageIcon>(); 
	private static ImageIcon goldIcon = InplayComponentUtils.getImageIcon(10, 10, InplayConstants.LOCAL_IMAGE_GOLDEN_STAR);
	private static ImageIcon goldIcon2 = InplayComponentUtils.getImageIcon(12, 12, InplayConstants.LOCAL_IMAGE_GOLDEN_STAR);

	private static HashMap<Integer, ImageIcon> greyMap = new HashMap<Integer, ImageIcon>(); 
	private static ImageIcon greyIcon = InplayComponentUtils.getImageIcon(10, 10, InplayConstants.LOCAL_IMAGE_GREY_STAR);
	private static ImageIcon greyIcon2 = InplayComponentUtils.getImageIcon(12, 12, InplayConstants.LOCAL_IMAGE_GREY_STAR);

	private static HashMap<Integer, ImageIcon> halfMap = new HashMap<Integer, ImageIcon>(); 
	private static ImageIcon halfIcon = InplayComponentUtils.getImageIcon(10, 10, InplayConstants.LOCAL_IMAGE_HALF_STAR);
	private static ImageIcon halfIcon2 = InplayComponentUtils.getImageIcon(12, 12, InplayConstants.LOCAL_IMAGE_HALF_STAR);

	
	static {
		goldMap.put(10, goldIcon);
		goldMap.put(12, goldIcon2);
		
		greyMap.put(10, greyIcon);
		greyMap.put(12, greyIcon2);
		
		halfMap.put(10, halfIcon);
		halfMap.put(12, halfIcon2);
	}
	
	public static void addRatingStars(InplayVideoDetailsDTO dto, JPanel panel, int size) {
		panel.removeAll();
		
		JLabel[] starLabel = new JLabel[5];
		for(int i=0;i<5;i++) {
			starLabel[i] = new JLabel(greyMap.get(size));
			panel.add(starLabel[i]);
		}
        if(dto==null) return;
        
        if(dto.getUserRating()==null || dto.getUserRating().isEmpty()) return;
        
        String userRating = dto.getUserRating();
        int intValue = new Double(userRating).intValue();
        int i=0;
        for(;i<intValue;i++) {
        	panel.remove(starLabel[i]);
        	starLabel[i] = new JLabel(goldMap.get(size));
        	panel.add(starLabel[i],i);
        }
        if(userRating.indexOf(".5")!=-1) {
        	panel.remove(starLabel[i]);
        	starLabel[i] = new JLabel(halfMap.get(size));
        	panel.add(starLabel[i],i);
        }
        panel.repaint();
	}

}
