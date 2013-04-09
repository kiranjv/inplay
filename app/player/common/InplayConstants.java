package com.app.player.common;

import java.io.File;
import java.net.URL;

public class InplayConstants {
	
	public static String TITLE = "InPlay";
	public static final String TITLE_ICON = "images" +  "/" + "inplaytitlelogo.png";
	
	public static String VIDEO_SEARCH_TEXT = "Search Text";
	public static String columnSeperator = "<tr>";
	public static String lineSeperator = "<br>";
	public static String CATEGORY_SEPARATOR = "\\|";
	
	public static String LOGIN_INVALID_USER_NAME = "Login failed!, email/password 	is invalid";
	public static String LOGIN_PLAN_EXPIRED = "Your plan has expired";
	public static String LOGIN_SUCCESSFUL = "LOGIN_SUCCESSFUL";
	public static String LOGIN_MOVIE_MESSAGE_DISPLAY = "Please login with your details to continue watching.";
	public static String LOGIN_MESSAGE_DISPLAY = "Please login with your details.";
	public static String WEBSITE_ADDRESS = "http://www.infinitysoft.co.uk";
	public static String LOGIN_SUBSCRIBE_MESSAGE = "Click here to subscribe.";
	public static String LOGIN_USER_EMAIL = "email";
	public static String LOGIN_USER_PASSWORD = "password";
	
	public static int LOGIN_THREAD_SLEEP_INTERVAL = 5000;
	public static int LOADING_ANIMATION_TIME_INTERVAL = 150; 
	public static float LOADING_FRAME_OPACITY = 0.95f; 
	
	
	// local dir paths
	public static String ROOT_DIR = ".";
	public static String LOCAL_IMAGE_CACHE_DIR_NAME = "cache";
	public static String RESOURCE_FILE_PATH_SEPERATOR = "/";
	public static String LOADING_IMAGE_PATH = "images" + "/" + "loadImages" + "/" + "loading.jpg";
	public static String LOADING_IMAGE_DIR = "images" + "/" + "loadImages";
	public static String LOCAL_IMAGE_DIR = "images";
	public static String LOCAL_IMAGE_GOLDEN_STAR = "images/star.png" ;
	public static String LOCAL_IMAGE_GREY_STAR = "images/star3.png";
	public static final String LOCAL_IMAGE_HALF_STAR = "images/halfStar.png";
	public static String LOCAL_PROPERTY_FILE = "inplay.props";
	public static String LOCAL_USER_DETAIL_PROPERTY_FILE = "user.props";
	public static final String LOCAL_CONFIG_DIR = "config";
	public static final String NOPREVIEW_IMAGE_PATH = "images" + "/" + "nopreview.jpg";

	
	
	// Exception messages 
	
	public static String ERROR_MESSAGE_NOT_IMPLEMENTED = "Implementation not complete as yet";

	// Http connector instance identifiers
	public static String HTTP_CONNECTOR_JAVA = "HTTP_CONNECTOR_JAVA";
	public static String HTTP_CONNECTOR_APACHE = "HTTP_CONNECTOR_APACHE";
	public static int HTTP_CONNECTION_TIMEOUT = 5000;
	public static int HTTP_READ_TIMEOUT = 0;
	public static int SOCKET_TIMEOUT_RETRY = 6;
	public static int DATA_SYNC_THREAD_TIME_INTERVAL = 60000;
	public static int HTTP_RETRY_COUNT = 20;
	
	// Http paths
	
	public static String HTTP_DB_QUERY_URL = WEBSITE_ADDRESS+ "/query.php";
	
	public static String DB_DATE_FORMAT = "yyyy-MM-dd HH:mm:ss";
	
	// views
	public static String VIEW_ICONS = "VIEW_ICONS";
	public static String VIEW_MEDIA = "VIEW_MEDIA";
	
	// thread constant50
	public static String THREAD_INIT_DATA_WORKER_NAME = "Init Data Worker";
	public static int THREAD_POOL_SIZE = 50;
	
	// font
	
	public static String FONT_FELIX = "Felix Titling";
	public static String FONT_CORDIA = "CordiaUPC";
	public static String FONT_TOHOMA = "Tahoma";
	
	// error messages 
	
	public static String ERROR_MSG_INTERNET_NOT_CONNECTED = "Could Not connect to the internet. Check your internet connection.  The Application will close now.";
	public static String ERROR_MSG_TIME_OUT = "Unable to fetch data from the server. Please try again or after some time.";
	public static String ERROR_ON_LOAD_GENERIC = "Could Not initialize the player, please get in touch with our support. The Application will close now.";
	
	// logo image 
	
	public static String LOGO_SMALL_LOCAL_PATH = "images/inplayLogoSmall.jpg";
	
	// genere constants
	public static String GENERE_VIEW_ALL = "Viewall";
	
}
