package com.app.player.data;


import com.app.player.util.InplayPlayerUtil;

public class InplayQueryGenerator {
	
	public static String generateInsertQueryForVideoWatch(InplayVideoDetailsDTO dto,String userId) {
		StringBuffer insertQuery = new StringBuffer();
		String currentDate = InplayPlayerUtil.getCurrentFormatedDate();
		insertQuery.append("INSERT INTO is_video_watch (video_id, user_id, date_viewed) VALUES " +
				"('"+dto.getId()+"','"+userId+"','"+currentDate+"')");
		return insertQuery.toString();
	}
	
	public static String generateInsertQueryForUserRating(InplayVideoDetailsDTO dto,String userId,int rating) {
		StringBuffer insertQuery = new StringBuffer();
		String currentDate = InplayPlayerUtil.getCurrentFormatedDate();
		insertQuery.append("INSERT INTO is_video_rating (video_id, user_id, user_rating, date_added) VALUES " +
				"('"+dto.getId()+"','"+userId+"','"+rating+"','"+currentDate+"')");
		return insertQuery.toString();
	}

	public static String generateSelectQueryForUserRating(InplayVideoDetailsDTO dto,String userId,int rating) {
		StringBuffer insertQuery = new StringBuffer();
		insertQuery.append("SELECT count(*) from is_video_rating where video_id  = '" +dto.getId() + "' " +
				"and  user_id = '" +userId +  "'");
		return insertQuery.toString();
	}
	
	public static String generateUpdateQueryForUserRating(InplayVideoDetailsDTO dto,String userId,int rating) {
		StringBuffer query = new StringBuffer();
		query.append("UPDATE is_video_rating set user_rating = '"+rating+"' where video_id  = '" +dto.getId() + "' " +
				"and  user_id = '" +userId +  "'");
		return query.toString();
	}
	
	public static String generateAverageQueryForUserRating(InplayVideoDetailsDTO dto) {
		StringBuffer insertQuery = new StringBuffer();
		insertQuery.append("SELECT AVG(user_rating) from is_video_rating where video_id  = '" +dto.getId() + "' ");
		return insertQuery.toString();
	}
	
	public static String generateUpdateQueryForUserRatingVideoTable(InplayVideoDetailsDTO dto, int rating) {
		StringBuffer insertQuery = new StringBuffer();
		insertQuery.append("UPDATE is_videos set user_rating = '"+rating+"' where id  = '" +dto.getId() + "' ");
		return insertQuery.toString();
	}
	
	public static String generateUpdateQueryForVideoWatchVideoTable(InplayVideoDetailsDTO dto) {
		String query = "UPDATE is_videos SET video_watch_count =  video_watch_count + 1 where id =" + dto.getId();
		return query;
	}
	
	public static void main(String[] args) throws Exception {
		testInsert();
		testFetch();
	}
	
	private static void testFetch() throws Exception {
		String query = "select * from is_video_watch";
		InplayDataCreator.executeQuery(query);
	}

	private static void testInsert() throws Exception {
		InplayVideoDetailsDTO dto = InplayVideoDetailsDTO.getDummyDTO();
		dto.setId("");
		String query = generateInsertQueryForVideoWatch(dto, "3");
		System.out.println(query);
		InplayDataCreator.executeQuery(query);
	}

}
