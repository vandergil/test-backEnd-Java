package br.com.uol.http;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.StringReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.SocketTimeoutException;
import java.net.URL;
import java.nio.charset.Charset;
import java.util.ArrayList;

import javax.xml.parsers.DocumentBuilderFactory;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.NodeList;
import org.xml.sax.InputSource;

/**
 * Classe utilizada para conectar com o arquivo XML disponível na internet.
 */

public class HTTPRequestXML {
	
String url;
	
	public HTTPRequestXML(String theUrl) {
		url = theUrl;
	}
	
	public StringBuffer UrlConnection() throws IOException {

		URL obj;
		int timeout = 1000;
		
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
			System.out.println("Conexao atingiu o timeout de  " + timeout + ". Favor verificar o servico." );
		} catch (Exception e) {
			System.out.println("Falha para conectar em " + url);
		}
		
		return null;

	}
	
	public ArrayList<String> getJusticeLeagueCodenames(StringBuffer response) {
		
		try {

			Document doc = DocumentBuilderFactory.newInstance().newDocumentBuilder().parse(new InputSource(new StringReader(response.toString())));
			doc.getDocumentElement().normalize();
	
			NodeList errNodes = doc.getElementsByTagName("codinomes");
			ArrayList<String> justiceLeagueCodeNameList = new ArrayList<>();
	
			if (errNodes.getLength() > 0) {
	
				Element err = (Element) errNodes.item(0);	
				err.getElementsByTagName("codinome").getLength();
	
				for (int temp = 0; temp < err.getElementsByTagName("codinome").getLength(); temp++) {
					justiceLeagueCodeNameList.add(err.getElementsByTagName("codinome").item(temp).getTextContent());
				}
				
				return justiceLeagueCodeNameList;
	
			} else {
				return null;
			}
		
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		return null;

	}

}
