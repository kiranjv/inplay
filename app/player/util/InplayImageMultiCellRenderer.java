package com.app.player.util;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Component;
import java.awt.Dimension;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.Comparator;
import java.util.HashMap;
import java.util.Iterator;

import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JPanel;
import javax.swing.JTextArea;
import javax.swing.ListCellRenderer;
import javax.swing.border.EmptyBorder;
import javax.swing.border.LineBorder;

import com.app.player.data.InplayVideoDetailsDTO;
import com.app.player.search.InplaySearchComboBox;

public class InplayImageMultiCellRenderer implements ListCellRenderer {

	private InplaySearchComboBox box;
	private Color backgroundColor = new Color(160, 160, 160);

	public InplayImageMultiCellRenderer(int width, int height,
			InplaySearchComboBox box) {
		this.box = box;
	}

	public Component getListCellRendererComponent(JList list, Object value,
			int index, boolean isSelected, boolean cellHasFocus) {

		InplayVideoDetailsDTO dummyDTO = InplayVideoDetailsDTO.getDummyDTO();

		InplayRendererDisplay returnComp = null;

		// code added by kiran
		
		
		
		if (value instanceof InplayVideoDetailsDTO) {
			InplayVideoDetailsDTO dto = (InplayVideoDetailsDTO) value;
			
			if(dto.getVideoThumb().equalsIgnoreCase("")) {
				System.out.println("Value to dto video thumb: "+dto.getVideoThumb());
				returnComp = InplayRendererDisplay.getDisplay(dummyDTO, box);
			}
			else {
				System.out.println("creating dummy dto of path: "+dummyDTO.getVideoThumb());
			returnComp = InplayRendererDisplay.getDisplay(dto, box);
			}
		} else {
			returnComp = InplayRendererDisplay.getDisplay(dummyDTO, box);
		}

		
		if (isSelected) {
			returnComp.setBackground(backgroundColor);
			returnComp.getNameLabel().setBackground(backgroundColor);
			returnComp.getIconPanel().setBorder(
					new LineBorder(backgroundColor, 4));
			System.out.println("selecte box iten =" + value);
			box.setSelectedMovie((InplayVideoDetailsDTO) value);
		} else {
			returnComp.getNameLabel().setBackground(
					new Color(250, 250, 250, 120));
			returnComp.getIconPanel().setBorder(new LineBorder(Color.WHITE, 4));
			returnComp.setBackground(new Color(250, 250, 250, 120));
		}
		return returnComp;
	}
}

class InplayRendererDisplay extends JPanel {

	private final JTextArea nameLabel = new JTextArea();
	private JLabel iconLabel = null;
	private InplaySearchComboBox box;
	private InplayVideoDetailsDTO dto;
	private InplayBackgroundImagePanel iconPanel;

	public static HashMap<String, InplayRendererDisplay> displayMap = new HashMap<String, InplayRendererDisplay>();

	public InplayRendererDisplay(InplaySearchComboBox box,
			InplayVideoDetailsDTO dto) {
		this.box = box;
		this.dto = dto;
		createDisplay();
	}

	private void createDisplay() {
		setLayout(new BorderLayout());
		setBackground(Color.WHITE);
		nameLabel.setWrapStyleWord(true);
		nameLabel.setLineWrap(true);
		nameLabel.setEditable(false);
		nameLabel.setBorder(new EmptyBorder(3, 3, 0, 0));
		nameLabel.setText(dto.getVideoTitle());
		// iconPanel = InplayComponentUtils.getImageLabel(45,
		// 40,"images"+"/"+"loadImages"+"/"+"load1.jpg");
		try {
			System.out.println("video thumb url: " + dto.getVideoThumb());
			iconPanel = new InplayBackgroundImagePanel(new URL(
					dto.getVideoThumb()));
		} catch (MalformedURLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		iconPanel.setPreferredSize(new Dimension(50, 60));
		iconPanel.setBorder(new LineBorder(Color.WHITE, 2));
		this.add(nameLabel, BorderLayout.CENTER);
		this.add(iconPanel, BorderLayout.EAST);

	}

	public static InplayRendererDisplay getDisplay(InplayVideoDetailsDTO dto,
			InplaySearchComboBox box) {
		
		
		
		
		String videothumb = dto.getVideoThumb();
		System.out.println("ImageMultiCellRender: getDisplay dto video thumb: "
				+ videothumb);
		if (displayMap.get(dto.getId()) == null
				&& !videothumb.equalsIgnoreCase("")) {
			displayMap.put(dto.getId(), new InplayRendererDisplay(box, dto));
		}
		
		return displayMap.get(dto.getId());
	}

	public InplayBackgroundImagePanel getIconPanel() {
		return iconPanel;
	}

	public JTextArea getNameLabel() {
		return nameLabel;
	}
}
