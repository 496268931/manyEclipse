package bxt;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.Writer;
import java.util.Scanner;

public class VersionCover {

	public  String read(String srcFile) throws FileNotFoundException {

		Scanner in = new Scanner(new File(srcFile));
		String result = "";

		while (in.hasNextLine()) {
			result += in.nextLine() + "\r\n";
		}

		int i=result.lastIndexOf("\r\n");
		result=result.substring(0,i);
		
		in.close();

		return result;
	}

	public  void write(String result, String toFile) throws Exception {

		Writer w = new FileWriter(new File(toFile));

		w.write(result);
		w.flush();
		w.close();
	}
}
