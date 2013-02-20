package com.app.test;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.HashMap;

import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JComponent;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JPanel;
import javax.swing.JTextArea;
import javax.swing.ListCellRenderer;
import javax.swing.border.EmptyBorder;
import javax.swing.border.LineBorder;

import com.app.player.InplayPlayerContext;
import com.app.player.data.InplayDataProvider;
import com.app.player.data.InplayVideoDetailsDTO;
import com.app.player.search.InplaySearchComboBox;
import com.app.player.util.InplayBackgroundImagePanel;
import com.app.player.util.InplayComponentUtils;
import com.app.player.util.InplayImageMultiCellRenderer;

public class TestSearchComboBox extends JComboBox{
	
	public TestSearchComboBox() {
		InplayVideoDetailsDTO dummyDTO = InplayVideoDetailsDTO.getDummyDTO();
		addItem(dummyDTO);
		dummyDTO = InplayVideoDetailsDTO.getDummyDTO();
		addItem(dummyDTO);
		dummyDTO = InplayVideoDetailsDTO.getDummyDTO();
		addItem(dummyDTO);
		dummyDTO = InplayVideoDetailsDTO.getDummyDTO();
		addItem(dummyDTO);
		dummyDTO = InplayVideoDetailsDTO.getDummyDTO();
		addItem(dummyDTO);
		setProperties();
		setLayout();
		addActionListener(new ActionListener(){

			public void actionPerformed(ActionEvent event) {
				JComboBox cb = (JComboBox)event.getSource();
			}
			
		});
		
	}
	
	private void setLayout() {
		for (int i = 0; i < getComponentCount(); i++) 
		{
		    if (getComponent(i) instanceof JComponent) {
		        ((JComponent) getComponent(i)).setBorder(new LineBorder(Color.WHITE,1));
		    }
		}


		for (Component child : getComponents()) {
            if (child instanceof JButton) {
            	child.setBackground(Color.WHITE);
                child.setVisible(false);
                break;
            }
        }
		
	}

	private void setProperties() {
		setFont(new Font("Tahoma",Font.PLAIN,12));
		setBackground(Color.WHITE);
//		setRenderer(new InplayTextMultiCellRenderer(200,50,this));
		setRenderer(new TestMultiCellRenderer(200,50,this));
		setPreferredSize(new Dimension(275,15));
		setMaximumSize(new Dimension(275,15));
		setMinimumSize(new Dimension(275,15));
		setEditable(true);
	//	setMaximumRowCount(5);
		setBorder(new LineBorder(Color.GRAY,1));
		
	}
	
	public static void main(String[] args) throws Exception {
		JFrame frame = new JFrame();
		JPanel panel = new JPanel(new FlowLayout());
		panel.add(new TestSearchComboBox());
		frame.add(panel, BorderLayout.CENTER);
		frame.setLocation(100, 100);
		frame.setSize(775, 550);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.setVisible(true);
	}

}


class TestMultiCellRenderer extends JPanel implements ListCellRenderer{

	JLabel label = new JLabel("display");
	
		 public TestMultiCellRenderer() {
			 setLayout(new BorderLayout());
			 setPreferredSize(new Dimension(300,100));
			 add(label,BorderLayout.CENTER);
		 }

		  public TestMultiCellRenderer(int width, int height, TestSearchComboBox box) {		  
		    this();
		  }

		  
		  
		  public Component getListCellRendererComponent(
		      JList list, Object value, int index,
		      boolean isSelected, boolean cellHasFocus) {
			  System.out.println("value = "  + value.getClass());
			  System.out.println("displaying item for index = " + index);
//			  return provider.get((InplayVideoDetailsDTO)value);
			  label.setText(value.toString());
			  if(isSelected) {
				  label.setText("selected"); 
			  }
			  return this;
		  }
		}

class SearchDisplayPanel extends JPanel {
	
	  private final JTextArea nameLabel = new JTextArea();
	  private JPanel iconPanel = null;
	  private Color backgroundColor =  new Color(160,160,160);

	  
	public SearchDisplayPanel(InplayVideoDetailsDTO dto) {
	    setBackground(Color.WHITE);
	    nameLabel.setWrapStyleWord(true);
	    nameLabel.setLineWrap(true);
	    nameLabel.setEditable(false);
	    nameLabel.setBorder(new EmptyBorder(3,3,0,0));
	    nameLabel.setText("test");
	    try {
			iconPanel = new InplayBackgroundImagePanel(new URL(dto.getVideoPath()));
		} catch (MalformedURLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		iconPanel.setPreferredSize(new Dimension(45,45));
		
	    this.add(nameLabel, BorderLayout.WEST);
	    this.add(iconPanel,BorderLayout.EAST);
		setPreferredSize(new Dimension(200,50));
		setMaximumSize(new Dimension(200,50));
		setMinimumSize(new Dimension(200,50));

	}
}

class provider {
	public static HashMap<String, SearchDisplayImagePanel> provide = new HashMap<String, SearchDisplayImagePanel>();
	
	public static SearchDisplayImagePanel get(InplayVideoDetailsDTO dto) {
		if(provide.get(dto.getVideoTitle())==null) {
			SearchDisplayImagePanel panel = new SearchDisplayImagePanel(dto);
			provide.put(dto.getVideoTitle(), panel);
		}
		
		return provide.get(dto.getVideoTitle());
	}
}


class SearchDisplayImagePanel extends JPanel {
	
    private JPanel iconPanel = null;

	  
	public SearchDisplayImagePanel(InplayVideoDetailsDTO dto) {
	    setBackground(new Color(240,240,240,100));
	    new File("cache/Memento.jpg").delete();
	    try {
			iconPanel = new InplayBackgroundImagePanel(new URL(dto.getVideoPath()));
		} catch (MalformedURLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		iconPanel.setPreferredSize(new Dimension(45,45));
	    this.add(iconPanel,BorderLayout.CENTER);
		setPreferredSize(new Dimension(100,50));
		setMaximumSize(new Dimension(100,50));
		setMinimumSize(new Dimension(100,50));
	}
}
