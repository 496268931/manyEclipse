package bxt;

import java.io.File;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.util.Enumeration;

import org.apache.commons.compress.archivers.zip.ZipArchiveEntry;
import org.apache.commons.compress.archivers.zip.ZipFile;

public class UnZipFile {

	//Java ��ѹrar�ļ���Ҫ�õ�apache��commons-compress-1.0.jar
	 

	public static void unZip(File zip, String root) throws Exception {
		System.out.println("�������ѹ����·����������׼����ѹ������");
		int countNum=0;
		long startTime=System.currentTimeMillis();  
		try {
			ZipFile zipFile = new ZipFile(zip, "GBK");
			Enumeration<?> e = zipFile.getEntries();
			byte ch[] = new byte[256];
			while (e.hasMoreElements()) {
				ZipArchiveEntry zipEntry = (ZipArchiveEntry) e.nextElement();
				String temp = zipEntry.getName();
				System.out.println("���ڽ�ѹ��"+countNum+"���ļ���" + zipEntry.getName());
				File zfile = new File(root + temp);
				File fpath = new File(zfile.getParentFile().getPath());

				if (zipEntry.isDirectory()) {
					if (!zfile.exists())
						zfile.mkdirs();
				} else {
					if (!fpath.exists())
						fpath.mkdirs();
					FileOutputStream fouts = new FileOutputStream(zfile);
					InputStream in = zipFile.getInputStream(zipEntry);
					int i;
					while ((i = in.read(ch)) != -1)
						fouts.write(ch, 0, i);
					fouts.close();
					in.close();
					
				}
				countNum++;
			}

			zipFile.close();
		} catch (Exception e) {
			System.err.println("Exception from ZipUtil -> unZip() : "
					+ e.getMessage());
			//e.printStackTrace(System.err);
			
		}
		 long endTime=System.currentTimeMillis();  
	        System.out.println("�ķ�ʱ�䣺 "+(endTime-startTime)+" ms");  
	}
}