package com.app.player.data;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Comparator;
import java.util.Date;
import java.util.HashMap;
import java.util.Random;
import java.util.Set;
import java.util.TreeSet;

import com.app.player.common.InplayConstants;
import com.app.player.context.InlayPlayerContext;
import com.app.player.http.InplayHttpConector;
import com.app.player.http.InplayHttpConnectorFactory;
import com.app.player.login.InplayLoginHandler;
import com.app.player.util.InplayPlayerUtil;

public class InplayDataCreator {

	private static String getMaxVideoId() throws Exception {
		InplayHttpConector connector = InplayHttpConnectorFactory.getConnector(null);
		HashMap<String, String> map = InplayDataUtils.getQueryKeyValueMap("select max(id) from is_videos");
		byte[] response = connector.receivePostResponse(map, new URL(InplayConstants.HTTP_DB_QUERY_URL));
		String data = new String(response);
		return data.split(",")[0];
	}
	
	public static void createData() throws Exception {
//		File thumbDir = new File(""); //changing so that by default we dont end up inserting duplicates
		File thumbDir = new File("images");
		File[] listFiles = thumbDir.listFiles();
		String maxVideoId = getMaxVideoId();
		int startId = Integer.parseInt(maxVideoId);
		for(int i=13;i<listFiles.length;i++) {
			String name = listFiles[i].getName();
			String[] data = name.split(".jpg");
			String query = createInsertQuery(data[0], ++startId);
			System.out.println(query);
			executeQuery(query);
		}
	}
	
	public static String executeQuery(String insertQuery) throws Exception {
		System.out.println("executing the query = " +insertQuery);
		InplayHttpConector connector = InplayHttpConnectorFactory.getConnector(null);
		HashMap<String, String> map = InplayDataUtils.getQueryKeyValueMap(insertQuery);
		byte[] response = connector.receivePostResponse(map, new URL(InplayConstants.HTTP_DB_QUERY_URL));
		String data = new String(response);
		return data;
	}
	
	private static void testVideoWatch() throws Exception {
		String query = "select * from is_video_watch";
		System.out.println(executeQuery(query));
	}
	
	
	public static String createInsertQuery(String videoName, int id) {
		String date = InplayPlayerUtil.getCurrentFormatedDate();
		
		
		String dummyData = "1,1,WeBoughtAZoo,WeBoughtAZoo,http://www.infinitysoft.us/admin/uploads/videos/20120604031332.flv," +
				"20120604031332.flv,flv,20120604031332.jpg,yes,2012-05-2206:17:32,2012-06-0403:13:32";
		String query = "INSERT INTO is_videos (id, category_id,category_name, video_title, video_description," +
				" video_path, video_name, video_ext, video_thumb, published, date_added, date_modified) VALUES " +
		"("+id+", 1,'Family' , '"+videoName.replaceAll("_", " ")+"', '"+videoName.replaceAll("_", " ")+"', 'http://www.infinitysoft.us/admin/uploads/videos/20120604031332.flv', " +
		"'20120604031332.flv', 'flv', 'http://www.infinitysoft.us/admin/uploads/videos/thumbs/"+videoName+".jpg', 'yes'," +
				" '"+date+"', '"+date+"')";
		
		return query;
		
	}
	
	
	public static void insertImages() throws Exception {
		
		String date = InplayPlayerUtil.getCurrentFormatedDate();
		InplayDataProvider.syncDataWithServer();
		Set<InplayVideoDetailsDTO> videoSet = InplayDataProvider.getVideoSet();
		int counter = 1;
		for(InplayVideoDetailsDTO dto:videoSet) {
			String query = "INSERT INTO is_images (id, video_id,image_name," +
					" image_path, date_modified, image_type) VALUES " +
			"("+counter+", "+dto.getId()+",'"+dto.getVideoTitle().replaceAll(" ", "_")+"_thumb"+
			"' , '"+dto.getVideoThumb()+"','"+date+"' , 'thumb')";
			executeQuery(query);
			counter++;
			
			query = "INSERT INTO is_images (id, video_id,image_name," +
			" image_path, date_modified, image_type) VALUES " +
			"("+counter+", "+dto.getId()+",'"+dto.getVideoTitle().replaceAll(" ", "_")+"_poster"+
			"' , '"+dto.getPoster()+"','"+date+"' , 'poster')";
			executeQuery(query);
			counter++;
		}
		
		
	}



	public static void main(String[] args) throws Exception {
		updateImages();
	}
	
	private static void updateLinks() throws Exception {
		ArrayList<String> list = new ArrayList<String>();
		String video0 = "http://www.infinitysoft.co.uk/admin/uploads/videos/20120830133251.flv";
		list.add(video0);
		String video1 = "http://www.infinitysoft.co.uk/admin/uploads/videos/20120830131627.flv";
		list.add(video1);
		String video2 = "http://www.infinitysoft.co.uk/admin/uploads/videos/20120828124043.flv";
		list.add(video2);
		String video3 = "http://www.infinitysoft.co.uk/admin/uploads/videos/20120604031332.flv";
		list.add(video3);
		String video4 = "http://www.infinitysoft.co.uk/admin/uploads/videos/20120625071441.flv";
		list.add(video4);
		String video5 = "http://www.infinitysoft.co.uk/admin/uploads/videos/20120604031332.flv";
		list.add(video5);
		
		InplayDataProvider.syncDataWithServer();
		Set<InplayVideoDetailsDTO> videoSet = InplayDataProvider.getVideoSet();
		Random random = new Random();

		for(InplayVideoDetailsDTO dto:videoSet) {
			String videoPath = dto.getVideoPath();
			int nextInt = random.nextInt(6);
			String query = "update is_videos set " +
					"video_path = '"+list.get(nextInt)+"' where id = "+dto.getId();
			String result = executeQuery(query);
			System.out.println(result);
		}
	}

	
	private static void updateImages() throws Exception {
		String source = "www.infinitysoft.us";
		String target = "www.infinitysoft.co.uk";
		InplayDataProvider.syncDataWithServer();
		Set<InplayVideoDetailsDTO> videoSet = InplayDataProvider.getVideoSet();

		for(InplayVideoDetailsDTO dto:videoSet) {
			String query = "update is_videos set " +
					"poster_path = '"+dto.getPoster().replaceAll(source, target)+"' where id = "+dto.getId();
			String result = executeQuery(query);
			System.out.println(result);
		}
	}

	private static void testUser() throws Exception {
		String query = "select * from is_users where email = 'rohit324@gmail.com'";
		String result = executeQuery(query);
		InplayUserDTO dto = InplayUserDTO.populateUserDTO(result);
		System.out.println(dto.getFormattedData());
	}

	private static void updatePasswordHash() throws Exception {
		String hash = InplayLoginHandler.getPasswordHash("password");
		String query = "update is_users set password = '"+hash+"' where email in ('rohit324@gmail.com','planExpired@gmail.com')";
		executeQuery(query);
		String testUpdate = "select email, password from is_users where email in ('rohit324@gmail.com','planExpired@gmail.com')";
		executeQuery(testUpdate);
	}

	private static void updateGeneres() throws Exception {
		String genreList = InlayPlayerContext.getGenreList();
		String[] generes = genreList.split(",");
		Random random = new Random();
		for(int i=15;i<19;i++) {
			int index1 = random.nextInt(generes.length-1);
			int index2 = random.nextInt(generes.length-1);
			int index3 = random.nextInt(generes.length-1);
			String genere = generes[index1] + "," + generes[index2] + "," + generes[index3];
			String query = "update is_videos set category_name = '"+genere+"' where id = "+i;
			executeQuery(query);
		}
	}
	
	private static void updateData() throws Exception {
		
		String query = "update is_videos set poster_path = 'http://www.infinitysoft.us/admin/uploads/videos/posters/hugo_poster.jpg' where id = 4";
		executeQuery(query);
	}
	
	private static void updateCategory() throws Exception {
		for (int i=15;i<19;i++) {
			String getCat = "select category_name from is_videos where id = "+i;
			String category = executeQuery(getCat);
			System.out.println("orignal cat = " + category);
			String[] cats = category.split(",");
			StringBuffer catBuffer = new StringBuffer();
			for(String cat : cats) {
				catBuffer.append(cat + "|");
			}
			String updateCat = catBuffer.toString().substring(0,catBuffer.toString().length()-2);
			updateCat = updateCat.replaceAll("<tr>", "");
			updateCat = updateCat.replaceAll("<br>", "");
			System.out.println("updated cat = " + updateCat);
			String query = "update is_videos set category_name = '"+updateCat+"' where id =" + i;
			executeQuery(query);
		}
	}
	
	private static void updatePosters() throws Exception {
		File file = new File("D:\\personal\\work\\nikhil\\inlay\\images\\upload");
		Set<File> files = new TreeSet<File>(new Comparator<File>() {
			public int compare(File paramT1, File paramT2) {
				return new Long(paramT1.lastModified()).compareTo(new Long(paramT2.lastModified()));
			}
		});
		File[] listFiles = file.listFiles();
		files.addAll(Arrays.asList(listFiles));
		System.out.println(files.size());
		int i =1;
		for(File file1:files) {
			String query = "update is_videos " +
					"set poster_path = 'http://www.infinitysoft.us/admin/uploads/videos/posters/"+file1.getName()+"' " +
							"where id = " + i++;
			try {
			executeQuery(query);
			}catch (Exception e) {
				executeQuery(query);
			}
		}
	}
	
}
