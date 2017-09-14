package bxt;

import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.io.LineNumberReader;

public class GetAppointedLine {
	// ��ȡ�ļ�ָ���С�
	String readAppointedLineNumber(File sourceFile, int lineNumber)
			throws Exception {
		FileReader in ;

		in = new FileReader(sourceFile);

		LineNumberReader reader = new LineNumberReader(in);
		String s = "";

		if (lineNumber <= 0 || lineNumber > getTotalLines(sourceFile)) {
			System.out.println("�����ļ���������Χ(1��������)֮�ڡ�");
			System.exit(0);
		}

		int lines = 0;
		while (s != null) {
			lines++;

			s = reader.readLine();
			if ((lines - lineNumber) == 0) {
				reader.close();
				in.close();
				return s;
			}

			

		}
		
		return null;

	}

	// �ļ����ݵ���������
	int getTotalLines(File file) throws IOException {
		FileReader in = new FileReader(file);
		LineNumberReader reader = new LineNumberReader(in);
		String s = reader.readLine();
		int lines = 0;
		while (s != null) {
			lines++;
			s = reader.readLine();
		}
		reader.close();
		in.close();
		return lines;
	}

}
