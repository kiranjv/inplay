package com.app.player.http;

import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.ConnectException;
import java.net.HttpURLConnection;
import java.net.InetSocketAddress;
import java.net.MalformedURLException;
import java.net.ProtocolException;
import java.net.Proxy;
import java.net.URL;
import java.net.URLEncoder;
import java.util.Map;
import java.util.Set;
import java.util.Map.Entry;

import com.app.player.InplayPlayer;
import com.app.player.InplayPlayerContext;
import com.app.player.common.InplayConstants;
import com.app.player.util.InplayPlayerUtil;
import com.sun.tools.doclets.internal.toolkit.Content;


public class InplayJavaHttpConnector implements InplayHttpConector {

	public static String getQueryResults(String query) throws Exception {
		String siteURL = InplayConstants.HTTP_DB_QUERY_URL;
		System.setProperty("http.keepAlive", "false");

		// Send data
		URL url = new URL(siteURL);
		System.out.println("------- conn initiated-------------");
		HttpURLConnection connection = (HttpURLConnection) url.openConnection();
		System.out.println("------- created conn -------------");
		connection.setRequestMethod("POST");
		connection.setRequestProperty("Content-Type",
				"application/x-www-form-urlencoded");
		connection.setRequestProperty("Content-Language", "en-US");
		connection.setConnectTimeout(InplayConstants.HTTP_CONNECTION_TIMEOUT);
		//      connection.setReadTimeout(TIMEOUT_VALUE);

		connection.setUseCaches(false);
		connection.setDoInput(true);
		connection.setDoOutput(true);

		//Send request
		String urlParameters = "mysql_query="
				+ URLEncoder.encode(query, "UTF-8") + "&formDataSubmit="
				+ URLEncoder.encode("submit", "UTF-8") + "&securityToken=test";
		DataOutputStream wr = new DataOutputStream(connection.getOutputStream());
 
		wr.writeBytes(urlParameters);
		wr.flush();
		wr.close();
		System.out.println("--------- sent request ---------------");
		//Get Response	
		InputStream is = connection.getInputStream();
		BufferedReader rd = new BufferedReader(new InputStreamReader(is));
		String line;
		StringBuffer response = new StringBuffer();
		while ((line = rd.readLine()) != null) {
			response.append(line);
			response.append('\r');
		}
		rd.close();
		return response.toString();
	}



	private byte[] getBytes(URL url) throws IOException {
		HttpURLConnection connection = null;
		try {
			System.out.println("sending request ");
			InputStream inStream = null;
			for(int i=0;i<InplayConstants.HTTP_RETRY_COUNT;i++) {
				System.out.println("trying again");
			try {
				connection = createConnection(url);
				connection.setRequestMethod("GET");
				connection.setDoInput(true);
				inStream = connection.getInputStream();
				System.out.println("Try count: "+i+" inStream:"+inStream.toString());
			break;
			}catch (Exception e) {
				System.err.println("Exception to read input stream connection, index:"+i+" inStream: "+inStream);
				connection.disconnect();
				//e.printStackTrace();
				}
			}
			
			byte[] bytesRead = InplayPlayerUtil.readFully(inStream, -1, true);
			inStream.close();
			return bytesRead;
		} finally {
			if (connection != null) {
				connection.disconnect();
			}
		}
	}

	public byte[] receivePostResponse(Map<String, String> keyValueMap, URL url)  throws IOException{
		HttpURLConnection connection = null;
		try {
			connection = createConnection(url);
			connection.setRequestMethod("POST");
			connection.setDoInput(true);
			connection.setDoOutput(true);
			System.out.println("Connection timeout: "+connection.getConnectTimeout());
			StringBuffer urlParams = new StringBuffer();
			Set<Entry<String, String>> entrySet = keyValueMap.entrySet();
			for (Entry entry : entrySet) {
				urlParams.append(entry.getKey());
				urlParams.append("=");
				urlParams.append(URLEncoder.encode((String) entry.getValue(),
						"UTF-8"));
				urlParams.append("&");
			}
			urlParams.append("securityToken=test");
			

			System.out.println("sending request params = " + urlParams);
			DataOutputStream outStream = new DataOutputStream(connection
					.getOutputStream());

			outStream.writeBytes(urlParams.toString());
			outStream.flush();
			outStream.close();
			System.out.println("--------- sent request ---------------");
			//Get Response	
			
			InputStream inStream = connection.getInputStream();
			byte[] bytesRead = InplayPlayerUtil.readFully(inStream, -1, true);
			inStream.close();
			return bytesRead;
		} 
		catch (IOException e) {
			throw e;
		}
		
		finally {
			if (connection != null) {
				connection.disconnect();
			}
		}
		
	}
	

	private HttpURLConnection createConnection(URL url) throws IOException,
			ProtocolException {
		HttpURLConnection connection = null;
		System.setProperty("http.keepAlive", "false");
		System.out.println("------- conn initiated-------------");
		connection = (HttpURLConnection) url.openConnection();
		System.out.println("------- created conn -------------");
		connection.setRequestProperty("Content-Type",
				"application/x-www-form-urlencoded");
		connection.setRequestProperty("Content-Language", "en-US");
		connection.setFollowRedirects(true);
		connection.setConnectTimeout(InplayConstants.HTTP_CONNECTION_TIMEOUT);
		
		connection.setReadTimeout(InplayConstants.HTTP_READ_TIMEOUT);
		connection.setUseCaches(false);
		return connection;
	}

	public byte[] receieveGetResponse(URL url) throws IOException {
		return getBytes(url);
	}
	

	public static void main(String[] args) throws Exception {
		final URL url = new URL("http://www.infinitysoft.co.uk/admin/uploads/videos/posters/promenthus.jpg");
		
	
		for(int i=0;i<20;i++) {
		InplayPlayerContext.getExecutor().execute(new Runnable() {

			public void run() {
				byte[] bytes;
				try {
					InplayJavaHttpConnector connector = new InplayJavaHttpConnector();
					bytes = connector.getBytes(url);
					System.out.println(bytes);
				} catch (IOException e) {
					System.err.println("IO Exception. Cause:"+e.getCause());
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				
			}
			
		});
			}
		InplayPlayerContext.getExecutor().shutdown();
	}

	
}
