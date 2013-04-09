package com.app.player.data;

import java.util.Collection;
import java.util.Iterator;
import java.util.LinkedHashSet;
import java.util.Map;
import java.util.Set;
import java.util.TreeSet;
import java.util.Vector;

import com.app.player.InplayDataWorker;

public class InplayVideoSearcher {

	private static final String TAG = "InplayVideoSearcher";
	public static Vector<InplayVideoDetailsDTO> resultVector = new Vector<InplayVideoDetailsDTO>();

	public static LinkedHashSet<InplayVideoDetailsDTO> getListForSearchText(
			String text) {
		System.err.println(TAG + "Input search text: " + text);

		LinkedHashSet<InplayVideoDetailsDTO> result = new LinkedHashSet<InplayVideoDetailsDTO>();
		if (text == null || text.trim().length() == 0)
			return result;
		Set<InplayVideoDetailsDTO> videoSet = InplayDataProvider.getVideoSet();
		int resultCounter = 0;
		for (InplayVideoDetailsDTO dto : videoSet) {
			if (resultCounter == 5)
				return result;
			String videoTitle = dto.getVideoTitle();
			if (videoTitle == null || videoTitle.trim().length() == 0)
				continue;
			// if(videoTitle.toLowerCase().indexOf(text.toLowerCase())!=-1)
			if (videoTitle.toLowerCase().contains(text.toLowerCase())) {
				result.add(dto);
				resultVector.add(dto);
				resultCounter++;
			}
		}

		return result;
	}

	public static Set<InplayVideoDetailsDTO> getGenereVideoList(String genere) {
		Map<String, TreeSet<InplayVideoDetailsDTO>> videoMap = InplayDataProvider
				.getVideoMap();
		return videoMap.get(genere);
	}

	public static void main(String[] args) {
		new InplayDataWorker().start();
		try {
			Thread.sleep(2000);
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		LinkedHashSet<InplayVideoDetailsDTO> result = getListForSearchText("o");
		System.out.println("search results size: " + resultVector.size());
		for (int i = 0; i < resultVector.size(); i++) {
			InplayVideoDetailsDTO obj = resultVector.get(i);
			System.out.println("Title: " + obj.getVideoTitle());
		}
	}

}
