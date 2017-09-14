package bxt;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.URL;
import java.net.URLConnection;

public class ReadNetTxt {

	 
	public String readNetTxt(String fileName) throws IOException {
		URL url = new URL(
				fileName);
		URLConnection conn = url.openConnection();
		BufferedReader reader = new BufferedReader(new InputStreamReader(
				conn.getInputStream()));
		String line = null;
		String result ="";
		 
		try {
			while ((line = reader.readLine()) != null) {
				//System.out.println(line);
				result+=line+"\r\n";
				
			}
			int i=result.lastIndexOf("\r\n");
			result=result.substring(0,i);
		} finally {
			reader.close();
		}
		return result;
	}
}
