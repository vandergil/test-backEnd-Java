package br.com.uol.http;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.SocketTimeoutException;
import java.net.URL;
import java.nio.charset.Charset;
import java.util.ArrayList;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

/**
 * Classe utilizada para conectar com o arquivo Json disponível na internet.
 */

public class HTTPRequestJson {
	
	String url;
	
	public HTTPRequestJson(String theUrl) {
		url = theUrl;
	}
	
	
	public StringBuffer UrlConnection() throws IOException {

		URL obj;
		
		try {
			obj = new URL(url);
			
			HttpURLConnection con = (HttpURLConnection) obj.openConnection();
			con.setConnectTimeout(10000);

			BufferedReader in = new BufferedReader(new InputStreamReader(con.getInputStream(), Charset.forName("UTF-8")));

			String inputLine;

			StringBuffer response = new StringBuffer();
			while ((inputLine = in.readLine()) != null) {
				response.append(inputLine);
			}

			in.close();

			return response;
			
		} catch (MalformedURLException e) {
			e.printStackTrace();
		} catch (SocketTimeoutException e) {
			throw new RuntimeException("SocketTimeoutException");
			//System.out.println("Conexao atingiu o timeout de  " + timeout + ". Favor verificar o servico." );
		} catch (Exception e) {
			throw new RuntimeException("SocketTimeoutException");
			//System.out.println("Falha para conectar em " + url);
		}
		
		return null;

	}
	
	public ArrayList<String> getAvengersCodenames(StringBuffer response) throws JSONException {
		
		 JSONObject myresponse = new JSONObject(response.toString());
	     JSONArray jsonAvengersArray = myresponse.getJSONArray("vingadores");
	     
	     ArrayList<String> avengersCodenamesList = new ArrayList<>();
	     
	     for (int i = 0; i < jsonAvengersArray.length(); i++) {
	    	 
	         JSONObject childJSONObject = jsonAvengersArray.getJSONObject(i);
	         avengersCodenamesList.add(childJSONObject.getString("codinome"));
	     }
		
		return avengersCodenamesList;
	}
	

}
	


