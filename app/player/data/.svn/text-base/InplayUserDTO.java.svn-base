package com.app.player.data;

import java.lang.reflect.Field;

import com.app.player.common.InplayConstants;

public class InplayUserDTO {

	private String id;
	private String title;
	private String first_name;
	private String last_name;
	private String gender;
	private String email;
	private String username;
	private String password;
	private String birthday;
	private String phone;
	private String website;
	private String address1;
	private String address2;
	private String country_id;
	private String state_id;
	private String city_id;
	private String zip;
	private String image;
	private String uploaded_image;
	private String plan_id;
	private String plan_price;
	private String plan_start_date;
	private String plan_end_date;
	private String referral_amount;
	private String user_type;
	private String status;
	private String newsletter;
	private String published;
	private String date_added;
	
	public static int indid = 0;
	public static int indemail=6;
	public static int indpassword=8;
	public static int indplan_start_date=21;
	public static int indplan_end_date=23;
	
	public String getAddress1() {
		return address1;
	}
	public void setAddress1(String address1) {
		this.address1 = address1;
	}
	public String getAddress2() {
		return address2;
	}
	public void setAddress2(String address2) {
		this.address2 = address2;
	}
	public String getBirthday() {
		return birthday;
	}
	public void setBirthday(String birthday) {
		this.birthday = birthday;
	}
	public String getCity_id() {
		return city_id;
	}
	public void setCity_id(String city_id) {
		this.city_id = city_id;
	}
	public String getCountry_id() {
		return country_id;
	}
	public void setCountry_id(String country_id) {
		this.country_id = country_id;
	}
	public String getDate_added() {
		return date_added;
	}
	public void setDate_added(String date_added) {
		this.date_added = date_added;
	}
	public String getEmail() {
		return email;
	}
	public void setEmail(String email) {
		this.email = email;
	}
	public String getGender() {
		return gender;
	}
	public void setGender(String gender) {
		this.gender = gender;
	}
	public String getImage() {
		return image;
	}
	public void setImage(String image) {
		this.image = image;
	}
	public String getLast_name() {
		return last_name;
	}
	public void setLast_name(String last_name) {
		this.last_name = last_name;
	}
	public String getNewsletter() {
		return newsletter;
	}
	public void setNewsletter(String newsletter) {
		this.newsletter = newsletter;
	}
	public String getPassword() {
		return password;
	}
	public void setPassword(String password) {
		this.password = password;
	}
	public String getPhone() {
		return phone;
	}
	public void setPhone(String phone) {
		this.phone = phone;
	}
	public String getPlan_end_date() {
		return plan_end_date;
	}
	public void setPlan_end_date(String plan_end_date) {
		this.plan_end_date = plan_end_date;
	}
	public String getPlan_id() {
		return plan_id;
	}
	public void setPlan_id(String plan_id) {
		this.plan_id = plan_id;
	}
	public String getPlan_price() {
		return plan_price;
	}
	public void setPlan_price(String plan_price) {
		this.plan_price = plan_price;
	}
	public String getPlan_start_date() {
		return plan_start_date;
	}
	public void setPlan_start_date(String plan_start_date) {
		this.plan_start_date = plan_start_date;
	}
	public String getPublished() {
		return published;
	}
	public void setPublished(String published) {
		this.published = published;
	}
	public String getReferral_amount() {
		return referral_amount;
	}
	public void setReferral_amount(String referral_amount) {
		this.referral_amount = referral_amount;
	}
	public String getState_id() {
		return state_id;
	}
	public void setState_id(String state_id) {
		this.state_id = state_id;
	}
	public String getStatus() {
		return status;
	}
	public void setStatus(String status) {
		this.status = status;
	}
	public String getUploaded_image() {
		return uploaded_image;
	}
	public void setUploaded_image(String uploaded_image) {
		this.uploaded_image = uploaded_image;
	}
	public String getUser_type() {
		return user_type;
	}
	public void setUser_type(String user_type) {
		this.user_type = user_type;
	}
	public String getUsername() {
		return username;
	}
	public void setUsername(String username) {
		this.username = username;
	}
	public String getWebsite() {
		return website;
	}
	public void setWebsite(String website) {
		this.website = website;
	}
	public String getZip() {
		return zip;
	}
	public void setZip(String zip) {
		this.zip = zip;
	}
	
	
	public static InplayUserDTO populateUserDTO(String userData) {
		String[] lines = userData.split(InplayConstants.lineSeperator);
		String[] data = lines[0].split(InplayConstants.columnSeperator);
		InplayUserDTO userDTO = new InplayUserDTO();
		userDTO.setId(data[indid]);
		userDTO.setEmail(data[indemail]);
		userDTO.setPassword(data[indpassword]);
		userDTO.setPlan_end_date(data[indplan_end_date]);
		return userDTO;
	}
	public String getFirst_name() {
		return first_name;
	}
	public void setFirst_name(String first_name) {
		this.first_name = first_name;
	}
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public String getTitle() {
		return title;
	}
	public void setTitle(String title) {
		this.title = title;
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

	
}
