package com.app.player.data;

import java.util.Collection;
import java.util.Iterator;
import java.util.LinkedHashSet;
import java.util.Map;
import java.util.Set;
import java.util.TreeSet;

public class InplayVideoSearcher {

	private static final String TAG = "InplayVideoSearcher";

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

}
