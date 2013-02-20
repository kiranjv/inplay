package com.app.player.login;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

import org.apache.commons.codec.binary.Hex;
import org.w3c.dom.UserDataHandler;

import sun.misc.BASE64Encoder;

import com.InplayResourceFinder;
import com.app.player.InplayComponentFactory;
import com.app.player.InplayPlayerContext;
import com.app.player.common.InplayConstants;
import com.app.player.data.InplayUserDTO;
import com.app.player.header.InplayHeaderPanel;
import com.app.player.http.InplayJavaHttpConnector;
import com.app.player.util.InplayPlayerUtil;

public class InplayLoginHandler {
	
	public static String verifyLogin(String email, String password ) throws Exception {
		String passwordHash = getPasswordHash(password);
		return verifyLoginForPasswordHash(email, passwordHash);
	}

	private static String verifyLoginForPasswordHash(String email, String passwordHash) throws Exception, IOException {
		String query = "select *, NOW() from is_users where email = '"+email+"' and password = '"+passwordHash+"'";
		String queryResults = InplayJavaHttpConnector.getQueryResults(query);
		System.out.println("query results = " + queryResults);
		if(queryResults.indexOf(email)!=-1 && queryResults.indexOf(passwordHash)!=-1) {
			InplayUserDTO userDTO = InplayUserDTO.populateUserDTO(queryResults);
			String plan_end_date = userDTO.getPlan_end_date();
			Date endDate = InplayPlayerUtil.formatDate(plan_end_date);
			System.out.println(endDate);
			
			String[] data = queryResults.split(InplayConstants.columnSeperator);
			String userID = data[0];
			String currentDate = data[data.length-2];
			Date formatedCurrentDate = InplayPlayerUtil.formatDate(currentDate);
			System.out.println("current date =" +formatedCurrentDate);
			
			if(endDate.compareTo(formatedCurrentDate)<0) {
				return InplayConstants.LOGIN_PLAN_EXPIRED;
			}
			processLoginSuccess(userDTO);
			return InplayConstants.LOGIN_SUCCESSFUL;
		}
		
		return InplayConstants.LOGIN_INVALID_USER_NAME;
	}
	
	private static void processLoginSuccess(InplayUserDTO dto) throws IOException {
		saveUserCredentials(dto);
		InplayPlayerContext.setUserID(dto.getId());
		InplayPlayerContext.setUserLoggedIn(true);
		updateHeaderPanel();
	}
	
	private static void updateHeaderPanel(){
		InplayHeaderPanel headPanel = InplayComponentFactory.getHeadPanel();
		headPanel.getLoginLabel().setText("Logout");
		headPanel.validate();
		headPanel.repaint();
	}
	
	private static void saveUserCredentials(InplayUserDTO dto) throws IOException {
		StringBuffer buffer = new StringBuffer();
		buffer.append(dto.getId());
		buffer.append("\r\n");
		buffer.append(dto.getEmail());
		buffer.append("\r\n");
		buffer.append(dto.getPassword());
		
		File cacheDir = new File(InplayConstants.LOCAL_IMAGE_CACHE_DIR_NAME);
		if(!cacheDir.exists()) cacheDir.mkdir();
		
		File file = new File(cacheDir,InplayConstants.LOCAL_USER_DETAIL_PROPERTY_FILE);
		FileOutputStream fileOutputStream = null;
		try {
		fileOutputStream = new FileOutputStream(file);
		fileOutputStream.write(buffer.toString().getBytes());
		fileOutputStream.flush();
		}catch (IOException e) {
			throw new IOException(e);
		} finally {
			if(fileOutputStream!=null) {
				fileOutputStream.close();
			}
		}
		
	}
	
	public static void loginUserOnStart() {
		File cacheDir = new File(InplayConstants.LOCAL_IMAGE_CACHE_DIR_NAME);
		if(!cacheDir.exists()) {
		InplayPlayerContext.setUserLoggedIn(false);
		return;
		}
		
		File file = new File(cacheDir,InplayConstants.LOCAL_USER_DETAIL_PROPERTY_FILE);
		FileInputStream fileInputStream = null;
		try {
		fileInputStream = new FileInputStream(file);
		String data = new String(InplayPlayerUtil.readFully(fileInputStream, -1, true));
		if(data==null||data.trim().length()==0) return;
		
		String[] lines = data.split("\r\n");
		
		if(lines==null || lines.length<3) return;
		
		String userId  = lines[0];
		InplayPlayerContext.setUserID(userId);
		String email = lines[1];
		String passwordHash = lines[2];
		verifyLoginForPasswordHash(email, passwordHash);
		}catch (Exception e) {
			// TODO: Lets not do anythign here. It will just fail the login for the user
			// but all exceptions need to fed back to the server.
			e.printStackTrace();
		}finally {
			if(fileInputStream!=null) {
				try {
					fileInputStream.close();
				} catch (IOException e) {
					// TODO: Lets not do anythign here. It will just fail the login for the user
					// but all exceptions need to fed back to the server.
					e.printStackTrace();
				}
			}
		}
	}
	
	public static void main(String[] args) throws Exception {
		testLoginSucess();
	}
	
	private static void testLoginOnStart()  {
		loginUserOnStart();
		System.out.println(InplayPlayerContext.isUserLoggedIn());
	}

	private static void testLoginSucess() throws Exception {
		String verifyLogin = verifyLogin("rohit324@gmail.com", "password");
		System.out.println(verifyLogin);
	}
	
	private static void testPlanExpired() throws Exception {
		String verifyLogin = verifyLogin("planExpired@gmail.com", "password");
		System.out.println(verifyLogin);
	}
	
	private static void testLoginFailed() throws Exception {
		Object verifyLogin = verifyLogin("ravi.agarwal2@gmail.com", "da5f05e5a77b8ddb8fb308eeab603575");
		System.out.println(verifyLogin);
	}
	
	
	public static void testPassordHash() throws Exception {
		getPasswordHash("password");
	}
	
	// Change the encoding logic as per the logic from Ravi.
	
	public static String getPasswordHash(String password) throws Exception {
		MessageDigest digest = MessageDigest.getInstance("MD5");
		byte[] passwordDigest = digest.digest(password.getBytes());
        String hexString = Hex.encodeHexString(passwordDigest);
		return hexString;
	}
	
	
	public static Date getCurrentServerDate() throws Exception {
		String query = "select NOW() from is_users";
		String queryResults = InplayJavaHttpConnector.getQueryResults(query);
		String[] dates = queryResults.split(",");
		SimpleDateFormat format = new SimpleDateFormat(InplayConstants.DB_DATE_FORMAT);
		try {
			return format.parse(dates[0]);
		} catch (ParseException e) {
			throw new RuntimeException(e);
		}
	}

}
