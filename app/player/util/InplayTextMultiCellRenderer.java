package com.app.player.util;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Component;
import java.awt.Dimension;

import javax.swing.JList;
import javax.swing.JPanel;
import javax.swing.JTextArea;
import javax.swing.ListCellRenderer;
import javax.swing.border.EmptyBorder;
import javax.swing.border.LineBorder;

import com.app.player.data.InplayVideoDetailsDTO;
import com.app.player.search.InplaySearchComboBox;

public class InplayTextMultiCellRenderer extends JPanel implements ListCellRenderer{

		  private final JTextArea nameLabel = new JTextArea();
		  private Color backgroundColor =  new Color(160,160,160);
		  private InplaySearchComboBox box;
		  
		  public InplayTextMultiCellRenderer(InplaySearchComboBox box) {
			  this.box = box;
		  }


		  public InplayTextMultiCellRenderer(int width, int height, InplaySearchComboBox box) {		  
		    super(new BorderLayout());
		    this.box = box;
		    nameLabel.setWrapStyleWord(true);
		    nameLabel.setLineWrap(true);
		    nameLabel.setEditable(false);
		    nameLabel.setPreferredSize(new Dimension(width,height));
		    nameLabel.setBorder(new EmptyBorder(3,3,3,3));
		    this.add(nameLabel, BorderLayout.CENTER);
		  }

		  
		  
		  public Component getListCellRendererComponent(
		      JList list, Object value, int index,
		      boolean isSelected, boolean cellHasFocus) {
			  
			if(value instanceof InplayVideoDetailsDTO) {
				InplayVideoDetailsDTO dto = (InplayVideoDetailsDTO)value;
				nameLabel.setText((dto).getVideoTitle());
			} 
//		    nameLabel.setFont(list.getFont());

		    if(isSelected) {
		    nameLabel.setBackground(backgroundColor);
//		    box.setSelectedMovie(nameLabel.getText());
		    } else {
		    if(index%2==0) {
		    	nameLabel.setBackground(new Color(240,240,240));
		    } else {
		    nameLabel.setBackground(Color.WHITE);
		    	}
		    }
		    return this;
		  }
		  
		  
		}






