package com.app.player.search;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.FocusEvent;
import java.awt.event.FocusListener;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.io.File;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.LinkedHashSet;
import java.util.Set;

import javax.swing.AbstractButton;
import javax.swing.BorderFactory;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JComponent;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JPanel;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.ListCellRenderer;
import javax.swing.border.EmptyBorder;
import javax.swing.border.LineBorder;
import javax.swing.plaf.basic.BasicComboBoxUI;

import com.app.player.InplayDataWorker;
import com.app.player.common.InplayConstants;
import com.app.player.data.InplayDataProvider;
import com.app.player.data.InplayVideoDetailsDTO;
import com.app.player.data.InplayVideoSearcher;
import com.app.player.util.InplayBackgroundImagePanel;
import com.app.player.util.InplayComponentUtils;
import com.app.player.util.InplayImageMultiCellRenderer;
import com.app.player.util.InplayPlayerUtil;
import com.app.player.util.InplayTextMultiCellRenderer;

public class InplaySearchComboBox extends JComboBox {
	
	private InplayVideoDetailsDTO selectedMovie;
	private NewSearchListener searchListener;
	
	
	public NewSearchListener getSearchListener() {
		return searchListener;
	}


	public InplayVideoDetailsDTO getSelectedMovie() {
		return selectedMovie;
	}

	public void setSelectedMovie(InplayVideoDetailsDTO selectedMovie) {
		this.selectedMovie = selectedMovie;
	}

	public InplaySearchComboBox() {
		addItem("");
		addItem("");
		addItem("");
		addItem("");
		addItem("");
		setProperties();
		setLayout();
		addActionListener(new ActionListener(){

			public void actionPerformed(ActionEvent event) {
				JComboBox cb = (JComboBox)event.getSource();
		        System.out.println("from action listener = " + cb.getSelectedItem());
		        System.out.println("event generated from = " + event.getModifiers());
		        if(event.getModifiers()==16) {
		        	try {
						InplayPlayerUtil.playVideo(getSelectedMovie());
					} catch (Exception e) {
						throw new RuntimeException(e);
					}
		        }
			}
			
		});
		
		
		

		NewSearchListener newSearchListener = new NewSearchListener(this);
		this.searchListener  = newSearchListener;
		getEditor().getEditorComponent().addKeyListener(newSearchListener);
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
		setRenderer(new InplayImageMultiCellRenderer(200,50,this));
		setPreferredSize(new Dimension(275,18));
		setMaximumSize(new Dimension(275,18));
		setMinimumSize(new Dimension(275,18));
		setEditable(true);
		setMaximumRowCount(5);
		setBorder(new LineBorder(Color.GRAY,1));
		
	}
	
	public static void main(String[] args) throws Exception {
		InplayDataProvider.syncDataWithServer();
		JFrame frame = new JFrame();
		JPanel panel = new JPanel(new FlowLayout());
		panel.add(new InplaySearchComboBox());
		frame.add(panel, BorderLayout.CENTER);
		frame.setLocation(100, 100);
		frame.setSize(775, 550);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.setVisible(true);
	}

}

class NewSearchListener implements KeyListener {

	private InplaySearchComboBox box;
	String newData = null;
	
	
	
	public String getNewData() {
		return newData;
	}

	public void setNewData(String newData) {
		this.newData = newData;
	}

	public NewSearchListener(InplaySearchComboBox box) {
		this.box = box;
	}
	
	public void keyPressed(KeyEvent arg0) {
		
	}

	public void keyReleased(KeyEvent arg0) {
		
	}

	public void keyTyped(KeyEvent arg0) {
		box.showPopup();
		char keyChar = arg0.getKeyChar();
		boolean isLetterOrDigit = Character.isLetterOrDigit(keyChar);
		boolean isWhitespace = Character.isSpace(keyChar);
		Object selectedItem = box.getSelectedItem();
		String textBoxDataWithouKeyChar = "";
		if(selectedItem!=null) {
		textBoxDataWithouKeyChar = selectedItem.toString();
		}
		// this is for back space
		
		// if pressed enter
		if(keyChar==10) {
			System.out.println("movie = " + this.box.getSelectedMovie());
			box.hidePopup();
			if(this.box.getSelectedMovie()==null) {
				return;
			}
			
			try {
				InplayPlayerUtil.playVideo(box.getSelectedMovie());
			} catch (Exception e) {
				throw new RuntimeException(e);
			}
			
		}
		
		if(keyChar == 8) {	
			System.out.println("current selected item = " + selectedItem);
			System.out.println("current key pressed = " + (int)keyChar);
			if(newData!=null  && newData.trim().length()>0) {
				newData = newData.substring(0,newData.length()-1);
				if(newData.trim().length()==0) {
					box.hidePopup();
					return;
				}
				LinkedHashSet<InplayVideoDetailsDTO> list = InplayVideoSearcher.getListForSearchText(newData);
				
				if(list.size()==0) {
					box.hidePopup();
					return;
				}
				
				box.removeAllItems();
				for (InplayVideoDetailsDTO dto : list) {
					box.addItem(dto);
				}
				box.setMaximumRowCount(list.size());
				box.showPopup();
				box.setSelectedItem(newData);
			} 
			
		
		}
		if((isLetterOrDigit  || isWhitespace) && (int)keyChar!=10 ) {
			//processKeyEvent(keyChar, textBoxDataWithouKeyChar);
		}

		
	}

	private void processKeyEvent(char keyChar, String textBoxDataWithouKeyChar) {
//		if(newData==null) {
//			newData = textBoxDataWithouKeyChar;
//		}
//		LinkedHashSet<InplayVideoDetailsDTO> list = InplayVideoSearcher.getListForSearchText(newData + new String(new char[]{keyChar}));
//		
//		if(list.size()==0) {
//			box.hidePopup();
//			newData = newData + new String(new char[]{keyChar});
//			return;
//		}
//
//		
//		box.removeAllItems();
//		for (InplayVideoDetailsDTO dto : list) {
//			box.addItem(dto);
//		}
//		box.setMaximumRowCount(list.size());
//		box.showPopup();
//		box.setSelectedItem(newData);
//		
//		newData = newData + new String(new char[]{keyChar});
		
	}
	
}




