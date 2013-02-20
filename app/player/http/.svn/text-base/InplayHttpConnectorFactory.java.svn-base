package com.app.player.http;

import java.util.HashMap;
import java.util.Map;

import com.app.player.common.InplayConstants;

public class InplayHttpConnectorFactory {
	
	private static Map<String, InplayHttpConector> connectorMap = new HashMap<String, InplayHttpConector>();
	
	
	static {
		connectorMap.put(InplayConstants.HTTP_CONNECTOR_JAVA, new InplayJavaHttpConnector());
	}
	
	public static InplayHttpConector getConnector(String connectorIdentifier) {		
		return new InplayJavaHttpConnector();
	}

}
