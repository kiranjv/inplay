package com.app.player.context;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.InputStream;
import java.util.Properties;
import java.util.TreeSet;


import com.InplayResourceFinder;
import com.app.player.common.InplayConstants;
import com.app.player.data.InplayDataProvider;

public class InlayPlayerContext {
	
//	private static String genreList = "viewall,action,adventure,animation,biography," +
//			"comedy,crime,documentary,drama,family,fantasy,film-noir,game-show,history," +
//			"horror,music,musical,mystery,news,reality-tv,romance,sci-fi,sport,talk-show," +
//			"thriller,war,western";

	private static String genreList = "viewall,action,adventure,animation,biography," +
	"comedy,crime,documentary,drama,family,fantasy,film-noir,game-show,history," +
	"horror,music,musical,mystery";

	private static String view = "";
	
	
	static {
		try {
			InputStream stream = InplayResourceFinder.getResourceAsStream(InplayConstants.LOCAL_CONFIG_DIR + "/" + InplayConstants.LOCAL_PROPERTY_FILE);
			Properties properties = new Properties();
			properties.load(stream);
			genreList = properties.getProperty("genreList");
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			throw new RuntimeException(e);
		}
	}
	
	public static String getGenreList() {
		TreeSet<String> set = InplayDataProvider.getGenereSet();
		
		if(set.size()==0) {
			return genreList;
		}
		
		StringBuffer buffer = new StringBuffer();
		buffer.append(InplayConstants.GENERE_VIEW_ALL+",");
		for(String genere:set) {
			buffer.append(genere + ",");
			
		}
		String result = buffer.toString();
		result =result.substring(0,result.length()-1);
		return result;
	}

	public static void setGenreList(String genreList) {
		InlayPlayerContext.genreList = genreList;
	}

	public static String getView() {
		return view;
	}

	public static void setView(String view) {
		InlayPlayerContext.view = view;
	}
	
	public static void main(String[] args) {
		System.out.println(getGenreList());
	}

}
