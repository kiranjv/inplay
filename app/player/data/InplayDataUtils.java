package com.app.player.data;

import java.util.HashMap;

public class InplayDataUtils {
	
	public static HashMap<String, String> getQueryKeyValueMap(String query){
		HashMap<String, String> keyValueMap = new HashMap<String, String>();
		keyValueMap.put("mysql_query", query);
		keyValueMap.put("formDataSubmit", "submit");
		return keyValueMap;
	}

}
