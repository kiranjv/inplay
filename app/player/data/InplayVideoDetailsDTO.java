package com.app.player.data;

import java.lang.reflect.Field;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Comparator;
import java.util.Date;

import com.app.player.common.InplayConstants;

public class InplayVideoDetailsDTO {
	
	public static int idIndex =0;
	public static int categoryIndex=1;
	public static int categoryDescIndex = 2;
	public static int videoTitleIndex=3;
	public static int videoDescriptionIndex=4;
	public static int videoPathIndex=5;
	public static int videoNameIndex=6;
	public static int videoExtIndex=7;
	public static int videoThumbIndex=8;
	public static int dateAddedIndex=9;
	public static int dateModifiedIndex=10;
	public static int user_ratingIndex=11;
	public static int release_dateIndex = 12;
	public static int posterIdex = 13;
	public static int publishedIndex=16;
	public static int videoWatchCountIndex=17;
	
	private String id;
	private String category;
	private String categoryDesc;
	private String videoTitle;
	private String videoDescription;
	private String videoPath;
	private String videoName;
	private String videoExt;
	private String videoThumb;
	private String published;
	private String dateAdded;
	private String dateModified;
	private String userRating="0";
	private String releaseDate;
	private String poster;
	private String videoWatchCount="0";
	
	
	
	public String getVideoWatchCount() {
		return videoWatchCount;
	}

	public void setVideoWatchCount(String videoWatchCount) {
		this.videoWatchCount = videoWatchCount;
	}

	public String getPoster() {
		return poster;
	}

	public void setPoster(String poster) {
		this.poster = poster;
	}

	public String getReleaseDate() {
		return releaseDate;
	}

	public void setReleaseDate(String releaseDate) {
		this.releaseDate = releaseDate;
	}

	public String getUserRating() {
		return userRating;
	}

	public void setUserRating(String userRating) {
		this.userRating = userRating;
	}

	public InplayVideoDetailsDTO(String id) {
		this.id = id;
	}
	
	public String getCategory() {
		return category;
	}
	public void setCategory(String category) {
		this.category = category;
	}
	public String getDateAdded() {
		return dateAdded;
	}
	public void setDateAdded(String dateAdded) {
		this.dateAdded = dateAdded;
	}
	public String getDateModified() {
		return dateModified;
	}
	public void setDateModified(String dateModified) {
		this.dateModified = dateModified;
	}
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public String getPublished() {
		return published;
	}
	public void setPublished(String published) {
		this.published = published;
	}
	public String getVideoDescription() {
		return videoDescription;
	}
	public void setVideoDescription(String videoDescription) {
		this.videoDescription = videoDescription;
	}
	public String getVideoExt() {
		return videoExt;
	}
	public void setVideoExt(String videoExt) {
		this.videoExt = videoExt;
	}
	public String getVideoName() {
		return videoName;
	}
	public void setVideoName(String videoName) {
		this.videoName = videoName;
	}
	public String getVideoPath() {
		return videoPath;
	}
	public void setVideoPath(String videoPath) {
		this.videoPath = videoPath;
	}
	public String getVideoThumb() {
		return videoThumb;
	}
	public void setVideoThumb(String videoThumb) {
		this.videoThumb = videoThumb;
	}
	public String getVideoTitle() {
		return videoTitle;
	}
	public void setVideoTitle(String videoTitle) {
		this.videoTitle = videoTitle;
	}
	
	public boolean equals(Object detailsDTO) {
		if(detailsDTO instanceof InplayVideoDetailsDTO) {
			InplayVideoDetailsDTO dto = (InplayVideoDetailsDTO)detailsDTO;
			return dto.getId().equals(this.getId());
		}
		
		return detailsDTO == this;
	}
	
	public int hashCode() {
		return this.getId().toString().hashCode();
	}
	
	public String toString() {
		return this.getVideoTitle();
//		return "test";
	}

	public String getCategoryDesc() {
		return categoryDesc;
	}

	public void setCategoryDesc(String categoryDesc) {
		this.categoryDesc = categoryDesc;
	}
	
	public static Comparator<InplayVideoDetailsDTO> getComparator() {
			return new VideoDateComparator();
	}
	
	public static InplayVideoDetailsDTO getDummyDTO(){
		InplayVideoDetailsDTO videoDetailsDTO = new InplayVideoDetailsDTO("420");
		videoDetailsDTO.setCategory("family");
		videoDetailsDTO.setVideoDescription("This is a story of a man ");
		videoDetailsDTO.setVideoName("Prometheus Dummy");
		videoDetailsDTO.setVideoTitle("Prometheus Dummy");
		videoDetailsDTO.setVideoThumb("http://www.infinitysoft.us/admin/uploads/videos/thumbs/Memento.jpg");
		videoDetailsDTO.setPoster("http://www.infinitysoft.us/admin/uploads/videos/thumbs/Memento.jpg");
		videoDetailsDTO.setVideoPath("http://www.infinitysoft.us/admin/uploads/videos/20120604031332.flv");
		videoDetailsDTO.setUserRating("4");
		return videoDetailsDTO;
	}
	
	
	public String getFormattedData() {
		Field[] fields = this.getClass().getDeclaredFields();
		StringBuffer result = new StringBuffer();
	    //print field names paired with their values
	    for ( Field field : fields  ) {
	      result.append(" ");
	      try {
	        result.append( field.getName() );
	        result.append(": ");
	        //requires access to private field:
	        result.append( field.get(this) );
	      }
	      catch ( IllegalAccessException ex ) {
	        System.out.println(ex);
	      }
	      result.append("\n");
	    }
	    return result.toString();
	  }
	
	public static int dateCompare(InplayVideoDetailsDTO dto1, InplayVideoDetailsDTO dto2) {
		try {
			Date releaseDate1 = new SimpleDateFormat(InplayConstants.DB_DATE_FORMAT).parse(dto1.getReleaseDate());
			Date releaseDate2 = new SimpleDateFormat(InplayConstants.DB_DATE_FORMAT).parse(dto2.getReleaseDate());
			// reverse chronological order of video as per date.
			int result = releaseDate2.compareTo(releaseDate1);
			if(result == 0 ) {
				return  dto1.getId().compareTo(dto2.getId());
			} else {
				return result;
			}
		} catch (ParseException e) {
			throw new RuntimeException(e);
		}
	}	
}

class VideoDateComparator implements Comparator<InplayVideoDetailsDTO> {

	public int compare(InplayVideoDetailsDTO dto1, InplayVideoDetailsDTO dto2) {
			return InplayVideoDetailsDTO.dateCompare(dto1, dto2);
	}
	
}



class VideoRatingComparator implements Comparator<InplayVideoDetailsDTO> {

	// sort in desc order of rating.
	public int compare(InplayVideoDetailsDTO dto1, InplayVideoDetailsDTO dto2) {
		if(dto1.getUserRating().compareTo(dto2.getUserRating())==0) {
			return InplayVideoDetailsDTO.dateCompare(dto1, dto2);
		} else {
			return dto2.getUserRating().compareTo(dto1.getUserRating());
		}
	}
}


