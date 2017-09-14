package bxt;

import java.io.BufferedReader;
import java.io.FileReader;

public class ReadGBConfig {
	String ssString;
	public String read(String filename) throws Exception {

		FileReader fr = new FileReader(filename);
		BufferedReader buf = new BufferedReader(fr);
		String str = "";
		while ((str = buf.readLine()) != null) {
			if (str.contains("GBConfigCover:")) {
				System.out.println(str);
				ssString=str;
			}
		}
		return ssString;
	}
}
