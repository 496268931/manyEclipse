package bxt;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.util.ArrayList;
import java.util.List;

public class GetTaskUrlLineNumber {

	int getTaksUrlLineNumber(String GBConfigPath) throws Exception  {
		List<Integer> lstIndex = new ArrayList<Integer>();
		String taskurl = "#TASKURL:http://";
		BufferedReader br;

		br = new BufferedReader(new FileReader(new File(GBConfigPath)));

		String s = "";
		int index = 0;

		while ((s = br.readLine()) != null) {
			index++;
			if (s.startsWith(taskurl)) {
				// System.out.println(index);
				lstIndex.add(index);
				br.close();
				return index;
			}
		}

		

		return 0;

	}
}
