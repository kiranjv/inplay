package com.app.player.data;

import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.Collections;
import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedHashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.SortedSet;
import java.util.TreeSet;
import java.util.Map.Entry;

import com.app.player.common.InplayConstants;
import com.app.player.http.InplayHttpConnectorFactory;
import com.app.player.http.InplayJavaHttpConnector;

public class InplayDataProvider {

	private static final String TAG = "InplayDataProvider";
	private static Map<String, TreeSet<InplayVideoDetailsDTO>> videoMap = new HashMap<String, TreeSet<InplayVideoDetailsDTO>>();
	private static Set<InplayVideoDetailsDTO> videoSet = new TreeSet<InplayVideoDetailsDTO>(
			InplayVideoDetailsDTO.getComparator());
	private static TreeSet<String> catagorySet = new TreeSet<String>();

	public static synchronized void addToSet(InplayVideoDetailsDTO dto) {
		videoSet.add(dto);
	}

	public static Set<InplayVideoDetailsDTO> getVideoSet() {
		return videoSet;
	}

	public static synchronized void add(InplayVideoDetailsDTO dto) {
		String categoryDesc = dto.getCategoryDesc();
		String[] categories = categoryDesc
				.split(InplayConstants.CATEGORY_SEPARATOR);
		for (String category : categories) {
			TreeSet<InplayVideoDetailsDTO> treeSet = videoMap.get(category);
			if (treeSet == null) {
				treeSet = new TreeSet<InplayVideoDetailsDTO>(
						InplayVideoDetailsDTO.getComparator());
			}

			treeSet.add(dto);
			videoMap.put(category, treeSet);
		}
	}

	public static TreeSet<String> getGenereSet() {
		return catagorySet;
	}

	public static ArrayList<InplayVideoDetailsDTO> getRatingSortedDTO(
			String genere) {
		ArrayList<InplayVideoDetailsDTO> sortedList = new ArrayList<InplayVideoDetailsDTO>(
				videoSet);

		if (genere == null || genere.trim().length() == 0
				|| genere.equalsIgnoreCase("viewall")) {
			Collections.sort(sortedList, new VideoRatingComparator());
			return sortedList;
		}

		TreeSet<InplayVideoDetailsDTO> treeSet = videoMap.get(genere);
		sortedList = new ArrayList<InplayVideoDetailsDTO>(treeSet);
		Collections.sort(sortedList, new VideoRatingComparator());

		return sortedList;
	}

	public static Map<String, TreeSet<InplayVideoDetailsDTO>> getVideoMap() {
		return videoMap;
	}

	/**
	 * Reads category's name information from server.
	 * 
	 * @throws Exception
	 */
	private static void syncAndpopulateCatagoryData() throws Exception {
		String query = "select category_name from is_category where published like 'Y%'";
		String result = new String(getQueryResult(query));
		result = result.replace("<br>", "");
		String[] genres = result.split("<tr>");
		for (String genere : genres) {
			if (genere.trim().length() > 0) {
				catagorySet.add(genere);
			}
		}
	}

	/**
	 * Parse data {@link String} object to {@link InplayVideoDetailsDTO} and add
	 * to videoset and videomap sets
	 * 
	 * @param data
	 *            - Represent data from the server.
	 */
	public static void populateVideoSet(String data) {
		System.out.println(TAG + " Video set: " + data);
		String[] lines = data.split(InplayConstants.lineSeperator);
		for (int i = 0; i < lines.length; i++) {
			String[] columns = lines[i].split(InplayConstants.columnSeperator);
			if (columns.length < 10)
				continue;
			if (i == 19) {
				System.out.println("Starting empty che");
			}
			InplayVideoDetailsDTO dto = new InplayVideoDetailsDTO(
					columns[InplayVideoDetailsDTO.idIndex]);
			dto.setCategory(columns[InplayVideoDetailsDTO.categoryIndex]);
			dto.setCategoryDesc(columns[InplayVideoDetailsDTO.categoryDescIndex]);
			dto.setDateAdded(columns[InplayVideoDetailsDTO.dateAddedIndex]);
			dto.setDateModified(columns[InplayVideoDetailsDTO.dateModifiedIndex]);
			dto.setPublished(columns[InplayVideoDetailsDTO.publishedIndex]);
			dto.setVideoDescription(columns[InplayVideoDetailsDTO.videoDescriptionIndex]);
			dto.setVideoExt(columns[InplayVideoDetailsDTO.videoExtIndex]);
			dto.setVideoName(columns[InplayVideoDetailsDTO.videoNameIndex]);
			dto.setVideoPath(columns[InplayVideoDetailsDTO.videoPathIndex]);
			dto.setVideoThumb(columns[InplayVideoDetailsDTO.videoThumbIndex]);
			dto.setVideoTitle(columns[InplayVideoDetailsDTO.videoTitleIndex]);
			dto.setUserRating(columns[InplayVideoDetailsDTO.user_ratingIndex]);
			dto.setReleaseDate(columns[InplayVideoDetailsDTO.release_dateIndex]);
			dto.setPoster(columns[InplayVideoDetailsDTO.posterIdex]);
			dto.setVideoWatchCount(columns[InplayVideoDetailsDTO.videoWatchCountIndex]);
			dto.setThumbPath(columns[InplayVideoDetailsDTO.thumb_pathIndex]);
			dto.setSearchPath(columns[InplayVideoDetailsDTO.search_pathIndex]);
			add(dto);
			addToSet(dto);
		}

		System.err
				.println("/* ----------- videoset list data display ------*/");
		for (Iterator iterator = videoSet.iterator(); iterator.hasNext();) {
			InplayVideoDetailsDTO sam_dto = (InplayVideoDetailsDTO) iterator
					.next();
			System.err.println(sam_dto);

		}
		System.err.println("-------------------------------------------------");

	}

	public static void syncDataWithServer() throws Exception {
		// String query =
		// "select id, category_id, category_name, video_title, video_description, video_path,"
		// +
		// "video_name, video_ext, video_thumb, date_added, date_modified, user_rating, release_date, poster_path, thumb_path,"
		// + " search_path, publish, video_watch_count from is_videos";

		String query = "select * from is_videos";

		updateForQuery(query);
		syncAndpopulateCatagoryData();
	}

	public static void syncInitialDataWithServer() throws Exception {
		String query = "select * from is_videos order by release_date desc limit 0,10";
		updateForQuery(query);

	}

	/**
	 * Process query on server database and parse response into sets.
	 * 
	 * @param query
	 *            - Query to execute on server.
	 * @throws IOException
	 */
	private static void updateForQuery(String query) throws IOException {
		byte[] queryResults = getQueryResult(query);
		if (queryResults != null)
			populateVideoSet(new String(queryResults));
	}

	private static byte[] getQueryResult(String query)
			throws MalformedURLException, IOException {
		HashMap<String, String> keyValueMap = new HashMap<String, String>();
		keyValueMap.put("mysql_query", query);
		keyValueMap.put("formDataSubmit", "submit");
		URL url = new URL(InplayConstants.HTTP_DB_QUERY_URL);
		byte[] queryResults = InplayHttpConnectorFactory.getConnector(
				InplayConstants.HTTP_CONNECTOR_JAVA).receivePostResponse(
				keyValueMap, url);
		return queryResults;
	}

	public static void main(String[] args) throws Exception {
		// String query =
		// "select AVG(user_rating) from is_video_watch  where user_rating!=0 and video_id =15";
		// String query =
		// "select user_rating, video_id from is_video_watch  where user_rating!=0 and video_id =15";
		// String query =
		// "update  is_videos set user_rating = '4.5' where id =1";
		String query = "select * from  is_category where published like 'Y%'";
		// String query =
		// "UPDATE is_videos SET video_watch_count = video_watch_count + 1 where id =17";

		String result = new String(getQueryResult(query));
		// String query =
		// "select user_rating,video_id from is_video_watch where user_rating!=0";
		System.out.println("result = " + result.replaceAll("<br>", "\n"));
	}

	private static void printVideoSet() {
		for (InplayVideoDetailsDTO dto : videoSet) {
			System.out.println(dto.getFormattedData());
		}
	}

	private static void testSorting() {
		try {
			String query = "select * from is_videos order by release_date desc limit 0,5";
			updateForQuery(query);
		} catch (Exception e) {
			e.printStackTrace();
		}

		try {
			String query = "select * from is_videos order by release_date desc limit 0,6";
			updateForQuery(query);
		} catch (Exception e) {
			e.printStackTrace();
		}

		System.out.println("======= size ==== " + videoSet.size());
		List<InplayVideoDetailsDTO> ratingSortedDTO = getRatingSortedDTO(null);
		printVideoList(ratingSortedDTO);
	}

	private static void printVideoList(Collection<InplayVideoDetailsDTO> list) {
		for (InplayVideoDetailsDTO dto : list) {
			System.out.println("----- video details " + dto.getId()
					+ "------------- ");
			System.out.println(dto.getReleaseDate());
			System.out.println(dto.getUserRating());
			System.out.println("------- end of details -----------");
		}
	}

	private static void printVideoMap() {
		System.out.println("========= Printing the map with size = "
				+ videoMap.size());
		Set<Entry<String, TreeSet<InplayVideoDetailsDTO>>> entrySet = videoMap
				.entrySet();
		Iterator<Entry<String, TreeSet<InplayVideoDetailsDTO>>> iterator = entrySet
				.iterator();
		while (iterator.hasNext()) {
			Entry<String, TreeSet<InplayVideoDetailsDTO>> next = iterator
					.next();
			TreeSet<InplayVideoDetailsDTO> set = next.getValue();
			System.out.println("----- details for catagory " + next.getKey());
			for (Object dto : set.toArray()) {
				System.out.println(((InplayVideoDetailsDTO) dto)
						.getFormattedData());
			}
			System.out
					.println("----- -----------------------------------------------");
		}
	}

	public static ArrayList<InplayVideoDetailsDTO> getDateSortedDTO(
			String genere) {
		ArrayList<InplayVideoDetailsDTO> sortedList = new ArrayList<InplayVideoDetailsDTO>(
				videoSet);

		if (genere == null || genere.trim().length() == 0
				|| genere.equalsIgnoreCase("viewall")) {
			Collections.sort(sortedList, new VideoDateComparator());
			return sortedList;
		}

		TreeSet<InplayVideoDetailsDTO> treeSet = videoMap.get(genere);
		sortedList = new ArrayList<InplayVideoDetailsDTO>(treeSet);
		Collections.sort(sortedList, new VideoDateComparator());

		return sortedList;
	}

	public static Set<InplayVideoDetailsDTO> getSortedVideoSet(String genere) {
		ArrayList<InplayVideoDetailsDTO> ratingSortedDTO = getDateSortedDTO(genere);

		Set<InplayVideoDetailsDTO> sortedSet = new TreeSet<InplayVideoDetailsDTO>(
				InplayVideoDetailsDTO.getComparator());
		for (int i = 0; i < ratingSortedDTO.size(); i++) {
			sortedSet.add(ratingSortedDTO.get(i));
		}
		return sortedSet;
	}

}
