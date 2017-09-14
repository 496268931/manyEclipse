package bxt;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.math.BigInteger;
import java.net.ServerSocket;
import java.security.MessageDigest;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

public class Task {

	@SuppressWarnings("unused")
	private static ServerSocket listenerSocket;

	/**
	 * ��ȡ�����ļ���MD5ֵ��
	 * 
	 * @param file
	 * @return
	 */
	public static String getFileMD5(File file) {
		if (!file.isFile()) {
			return null;
		}
		MessageDigest digest = null;
		FileInputStream in = null;
		byte buffer[] = new byte[1024];
		int len;
		try {
			digest = MessageDigest.getInstance("MD5");
			in = new FileInputStream(file);
			while ((len = in.read(buffer, 0, 1024)) != -1) {
				digest.update(buffer, 0, len);
			}
			in.close();
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
		BigInteger bigInt = new BigInteger(1, digest.digest());
		return bigInt.toString(16);
	}

	/**
	 * ��ȡ�ļ������ļ���MD5ֵ
	 * 
	 * @param file
	 * @param listChild
	 *            ;true�ݹ���Ŀ¼�е��ļ�
	 * @return
	 */
	public static Map<String, String> getDirMD5(File file, boolean listChild) {
		if (!file.isDirectory()) {
			return null;
		}
		// <filepath,md5>
		Map<String, String> map = new HashMap<String, String>();
		String md5;
		File files[] = file.listFiles();
		for (int i = 0; i < files.length; i++) {
			File f = files[i];
			if (f.isDirectory() && listChild) {
				map.putAll(getDirMD5(f, listChild));
			} else {
				md5 = getFileMD5(f);
				if (md5 != null) {
					map.put(f.getPath(), md5);
				}
			}
		}
		return map;
	}

	/**
	 * @param args
	 * @throws IOException
	 */
	public void taskWaiting() {

		try {
			Thread.sleep(5000);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
	}

	public static void main(String[] args) throws IOException, Exception {
		// TODO Auto-generated method stub
		// ��֤���򻥳�����
		try {
			listenerSocket = new ServerSocket(2004);
			// At this point, no other socket may listen on port 2004.
		} catch (java.net.BindException e) {
			System.out.println("��⵽���������³��������С�������");
			System.err.println("A previous instance is already running....");
			System.exit(1);
		} catch (final IOException e) { // an unexpected exception occurred
			System.exit(1);
		}
		// Do some work here.....
		// ��֤���򻥳�����

		final SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");// �������ڸ�ʽ

		// run in a second
		final long timeInterval = 600000;
		Runnable runnable = new Runnable() {
			public void run() {
				while (true) {
					// ------- code for task to run

					Task t = new Task();
					// ���ض�ȡд��ķ���
					VersionCover vc = new VersionCover();
					// �����ȡtxt�ķ���
					ReadNetTxt rnt = new ReadNetTxt();
					// int netVersionLineNumber;
					// new Date()Ϊ��ȡ��ǰϵͳʱ��
					System.out.println("���θ��¿�ʼʱ��Ϊ��" + df.format(new Date()));

					// ��ȡ��ǰ·��
					File directory = new File("");// ����Ϊ��
					String courseFile = null;
					try {
						courseFile = directory.getCanonicalPath();
					} catch (IOException e2) {
						// TODO Auto-generated catch block
						e2.printStackTrace();
					}
					System.out.println("�������·��Ϊ��" + courseFile);

					File file1 = new File(courseFile + "\\localVersion.txt");

					// http://120.27.195.31:8080/UpdateSpider/
					// 192.168.0.42
					// ��������汾txt
					try {

						DownLoad.downLoadFromUrl(
								"http://120.27.195.31:8080/UpdateSpider/VersionAndSort/netVersion.txt",
								"netVersion.txt", courseFile);
						System.out.println("tomcat�ϵ�����汾Ϊ��");
						System.out
								.println(rnt
										.readNetTxt("http://120.27.195.31:8080/UpdateSpider/VersionAndSort/netVersion.txt"));
						// ��һ�ַ���������û�õ�
						// ReadNetVersion readnetversion = new ReadNetVersion();
						// readnetversion.readNetVersion();
					} catch (Exception e) {
						// TODO: handle exception
						System.out.println("����ʧ�ܣ�δ��⵽����汾�������ȼ��tomcat�Ƿ���������");
					}
					File file2 = new File(courseFile + "\\netVersion.txt");

					// localVersion netVersion���бȽ�
					System.out.println("���ذ汾������汾�ȽϽ����"
							+ getFileMD5(file1).equals(getFileMD5(file2)));
					t.taskWaiting();

					boolean b = getFileMD5(file1).equals(getFileMD5(file2));

					if (!b) {

						try {

							// ��ʾ���н���
							// ShowTaskList taskList=new ShowTaskList();
							// taskList.showTaskList();

							// ��ȡ����������̲��ɵ�
							// new GetSpiderCount().getPid();
							GetAndKillSpider gak = new GetAndKillSpider();

							System.out.println("�ѿ������������Ϊ��");
							Thread.sleep(2000);
							gak.getAndKill("cmd /c tasklist -fi \"imagename eq 3ispider.exe\"");
							System.out.println();
							System.out.println("***��ʼɱ����***");
							System.out.println();
							Thread.sleep(2000);
							gak.getAndKill("cmd /c taskkill /im 3iSpider.exe /f");

						} catch (InterruptedException e2) {
							// TODO Auto-generated catch block
							e2.printStackTrace();
						}

						// �����ֶ�������
						// StartSpider s=new StartSpider();
						// s.startSpider();
						// RunSpider r=new RunSpider();
						// r.runSpider();

						t.taskWaiting();
 
						System.out.println();
						try {

							DownLoad.downLoadFromUrl(
									"http://120.27.195.31:8080/UpdateSpider/upload/"
											+ rnt.readNetTxt("http://120.27.195.31:8080/UpdateSpider/VersionAndSort/sortSpiderVersion.txt"),
									rnt.readNetTxt("http://120.27.195.31:8080/UpdateSpider/VersionAndSort/sortSpiderVersion.txt"),
									courseFile);
						} catch (Exception e) {
							// TODO: handle exception
							System.out
									.println("����ʧ�ܣ�δ��⵽�°����棬�����ȼ��tomcat�Ƿ���������");
						}

						t.taskWaiting();
						
						System.out.println();
						// ����GBConfig�ļ�����ΪGBConfig-backups
						if (new File(courseFile + "\\Config\\GBConfig.txt")
								.exists()) {
							new File(courseFile + "\\Config\\GBConfig.txt")
									.renameTo(new File(courseFile
											+ "\\Config\\GBConfig-backups.txt"));
							System.out
									.println("�ѽ�����GBConfig�ļ�������ΪGBConfig-backups");
						}
						t.taskWaiting();
						
						System.out.println();
						// ��ѹ������
						try {
							UnZipFile
									.unZip(new File(
											courseFile
													+ "\\"
													+ rnt.readNetTxt("http://120.27.195.31:8080/UpdateSpider/VersionAndSort/sortSpiderVersion.txt")),
											courseFile + "\\");
						} catch (Exception e) {
							e.printStackTrace();
						}
						t.taskWaiting();

						System.out.println();
						System.out.println("׼��ɾ����ѹ���zip�ļ����������·��������");
						File deleteZipFile = null;
						try {
							deleteZipFile = new File(
									courseFile
											+ "\\"
											+ rnt.readNetTxt("http://120.27.195.31:8080/UpdateSpider/VersionAndSort/sortSpiderVersion.txt"));
						} catch (IOException e2) {
							// TODO Auto-generated catch block
							e2.printStackTrace();
						}
						if (deleteZipFile.exists()) {

							if (deleteZipFile.delete()) {
								try {
									System.out
											.println("ѹ���ļ�·��Ϊ"
													+ courseFile
													+ "\\"
													+ rnt.readNetTxt("http://120.27.195.31:8080/UpdateSpider/VersionAndSort/sortSpiderVersion.txt"));
								} catch (IOException e) {
									// TODO Auto-generated catch block
									e.printStackTrace();
								}

								System.out.println("���غ��ѹ���ļ���ɾ��");
							}
						} else {
							System.out.println("ѹ���ļ�������");
						}

						t.taskWaiting();

						System.out.println();
						/*
						 * ͨ�������ļ��ж������ж��Ƿ񸲸�GBC ReadNetVersionLineNum rrrLineNum
						 * = new ReadNetVersionLineNum(); try {
						 * System.out.println
						 * ("����汾����Ϊ��"+rrrLineNum.countLines(courseFile +
						 * "\\netVersion.txt")); } catch (IOException e2) { //
						 * TODO Auto-generated catch block e2.printStackTrace();
						 * }
						 */

						t.taskWaiting();

						System.out.println();
						System.out.println("*****��ʼ����GBConfig.txt�ļ�*****");
						// �����ϵ�netVersion�У�������"GBConfigCover:",����Ĳ�����ֻ�е�����Ϊ1ʱ��ִ�в����ǲ���
						ReadGBConfig readGBConfig = new ReadGBConfig();
						try {

							System.out.print("GBConfig������Ϊ��");
							String gbc = readGBConfig.read(
									courseFile + "\\netVersion.txt").substring(
									14);
							int intgbc = Integer.parseInt(gbc.trim());

							System.out.println("����Ϊ" + intgbc);
							
							System.out.println();
							t.taskWaiting();

							
							if (intgbc == 0) {
								System.out
										.println("GBConfigCover:0�������ص�GBConfig-backups���ݸ������غ��GBConfig��");
								// ɾ�����غ��GBConfig
								if (new File(courseFile
										+ "\\Config\\GBConfig.txt").exists()) {
									new File(courseFile
											+ "\\Config\\GBConfig.txt")
											.delete();
									System.out.println("�ѽ����غ�ı���GBConfig�ļ�ɾ��");
								}
								// ����GBConfig-backups�ļ�����ΪGBConfig
								if (new File(courseFile
										+ "\\Config\\GBConfig-backups.txt")
										.exists()) {
									new File(courseFile
											+ "\\Config\\GBConfig-backups.txt")
											.renameTo(new File(courseFile
													+ "\\Config\\GBConfig.txt"));
									System.out
											.println("�ѽ�����GBConfig-backups�ļ�������ΪGBConfig");
								}

							} else if (intgbc == 1) {
								System.out
										.println("GBConfigCover:1�����ǣ�������غ��GBConfig");
								if (new File(courseFile
										+ "\\Config\\GBConfig-backups.txt")
										.exists()) {
									new File(courseFile
											+ "\\Config\\GBConfig-backups.txt")
											.delete();
									System.out
											.println("�ѽ�����ǰ������ı���GBConfig-backups�ļ�ɾ��");
								}
							} else if (intgbc == 2) {

								System.out
										.println("GBConfigCover:2��������TASKURL׷�ӵ����غ��GBConfig��");
								GetTaskUrlLineNumber getTaskUrlLineNumber = new GetTaskUrlLineNumber();
								// ����TASKURL�ı����ļ��е��к�
								// System.out.println(getTaskUrlLineNumber.getTaksUrlLineNumber(courseFile
								// + "\\Config\\GBConfig-backups.txt"));
								// ����TASKURL�����غ���ļ��е��к�
								// System.out.println(getTaskUrlLineNumber.getTaksUrlLineNumber(courseFile
								// + "\\Config\\GBConfig.txt"));

								GetAppointedLine getAppointedLine = new GetAppointedLine();

								/*
								 * ����GBConfig ��TASKURL��ַ System.out
								 * .println(getAppointedLine
								 * .readAppointedLineNumber( new File(
								 * courseFile +
								 * "\\Config\\GBConfig-backups.txt"),
								 * getTaskUrlLineNumber
								 * .getTaksUrlLineNumber(courseFile +
								 * "\\Config\\GBConfig-backups.txt")));
								 */

								// int modifyLine=10;//Ҫ�޸ĵ���
								BufferedReader in_ = new BufferedReader(
										new FileReader(courseFile
												+ "\\Config\\GBConfig.txt"));
								PrintWriter out = new PrintWriter(
										new BufferedWriter(
												new FileWriter(
														courseFile
																+ "\\Config\\GBConfig-union.txt")));
								String line;
								int count = 1;
								while ((line = in_.readLine()) != null) {
									if (count == getTaskUrlLineNumber
											.getTaksUrlLineNumber(courseFile
													+ "\\Config\\GBConfig.txt")) {
										// ������TASKURL �滻���غ��TASKURL
										out.println(line.replace(
												getAppointedLine
														.readAppointedLineNumber(
																new File(
																		courseFile
																				+ "\\Config\\GBConfig.txt"),
																getTaskUrlLineNumber
																		.getTaksUrlLineNumber(courseFile
																				+ "\\Config\\GBConfig.txt")),
												getAppointedLine
														.readAppointedLineNumber(
																new File(
																		courseFile
																				+ "\\Config\\GBConfig-backups.txt"),
																getTaskUrlLineNumber
																		.getTaksUrlLineNumber(courseFile
																				+ "\\Config\\GBConfig-backups.txt")))); // ��������滻w��Z

									} else {
										out.println(line);
									}
									count++;
								}
								in_.close();
								out.close();
								System.out
										.println("�ѽ����غ������TASKURL�滻�ɱ���TASKURL");

								
								System.out.println();
								t.taskWaiting();
								if (new File(courseFile
										+ "\\Config\\GBConfig-backups.txt")
										.exists()) {
									new File(courseFile
											+ "\\Config\\GBConfig-backups.txt")
											.delete();
									System.out
											.println("�ѽ�����ǰ������ı���GBConfig-backups�ļ�ɾ��");
								}
								if (new File(courseFile
										+ "\\Config\\GBConfig.txt").exists()) {
									new File(courseFile
											+ "\\Config\\GBConfig.txt")
											.delete();
									System.out.println("�ѽ����غ������GBConfig�ļ�ɾ��");
								}

								if (new File(courseFile
										+ "\\Config\\GBConfig-union.txt")
										.exists()) {
									new File(courseFile
											+ "\\Config\\GBConfig-union.txt")
											.renameTo(new File(courseFile
													+ "\\Config\\GBConfig.txt"));
									System.out
											.println("�ѽ�׷��TASKURL���GBConfig-union�ļ�������ΪGBConfig");
								}

								/**
								 * ��ȡ�ļ�ָ���С�
								 * 
								 * ָ����ȡ���к� int lineNumber = 11; ��ȡ�ļ� File
								 * sourceFile = new File("D:/test2.txt"); ��ȡָ������
								 * readAppointedLineNumber
								 * (sourceFile,lineNumber); ��ȡ�ļ������ݵ�������
								 * System.out
								 * .println(getAppointedLine.getTotalLines(new
								 * File(courseFile
								 * +"\\Config\\GBConfig-backups.txt")));
								 */

							}

						} catch (Exception e3) {
							// TODO Auto-generated catch block
							e3.printStackTrace();
						}

						System.out.println("*****GBConfig.txt�ļ��������*****");
						System.out.println();

						t.taskWaiting();

						// ��������
						/*
						 * try { Runtime.getRuntime().exec( "cmd /c " +
						 * courseFile + "\\3ispider.exe auto"); } catch
						 * (IOException e1) { // TODO Auto-generated catch block
						 * e1.printStackTrace(); }
						 * System.out.println("������������ʱ�䣺" + df.format(new
						 * Date())); try { Thread.sleep(10000); } catch
						 * (InterruptedException e) { e.printStackTrace(); }
						 */

						try {
							Runtime.getRuntime().exec(
									"cmd /c " + courseFile
											+ "\\3ispider.exe auto");
						} catch (IOException e1) { // TODO Auto-generated catch
													// block
							e1.printStackTrace();
						}
						System.out.println("������������ʱ�䣺" + df.format(new Date()));
						try {
							Thread.sleep(10000);
						} catch (InterruptedException e) {
							e.printStackTrace();
						}

						try {
							Runtime.getRuntime().exec(
									"cmd /c " + courseFile
											+ "\\3ispider.exe auto");
						} catch (IOException e1) { // TODO Auto-generated catch
													// block
							e1.printStackTrace();
						}
						System.out.println("������������ʱ�䣺" + df.format(new Date()));
						try {
							Thread.sleep(10000);
						} catch (InterruptedException e) {
							e.printStackTrace();
						}

						System.out.println("���θ����ѽ�����ʱ��Ϊ��"
								+ df.format(new Date()));

						System.out.println();
						System.out
								.println("*****��ʼ�����غ�����汾netVersion���Ǳ��ذ汾localVersion*****");

						String f1 = courseFile + "\\localVersion.txt";
						String f2 = courseFile + "\\netVersion.txt";

						try {
							String result = vc.read(f2);
							vc.write(result, f1);
						} catch (Exception e) {
							e.printStackTrace();
						}
						System.out.println("over");

						System.out.println("*****�汾�Ÿ������*****");
						System.out.println();
						try {
							System.out.println("���ذ汾localVersionΪ��");
							System.out.println(vc.read(f1));
						} catch (FileNotFoundException e) {
							// TODO Auto-generated catch block
							e.printStackTrace();
						}
						/*
						 * FileWriter fw = null; try { fw = new FileWriter(f1);
						 * fw.write(""); fw.close();
						 * 
						 * BufferedReader read = new BufferedReader( new
						 * FileReader(new File(f2))); FileWriter write = new
						 * FileWriter(new File(f1)); String temp; while ((temp =
						 * read.readLine()) != null) { write.write(temp); }
						 * write.close(); read.close();
						 * System.out.println("�汾���Ѵ�" + f2 + "���ǵ�" + f1); }
						 * catch (IOException e) { e.printStackTrace(); }
						 * //ͨ��ReadVersion��õ�localVersion�汾�� //new
						 * ReadVersion();
						 * //ReadVersion.readFileByLines(courseFile
						 * +"\\localVersion.txt");
						 */

						System.out.println("------------���·ָ���------------");

					} else {

						try {
							System.out.println("���ذ汾localVersionΪ��");
							System.out.println(vc.read(courseFile
									+ "\\localVersion.txt"));
						} catch (FileNotFoundException e) {
							// TODO Auto-generated catch block
							e.printStackTrace();
						}
						System.out.println("�汾����ͬ������Ҫ���£�����ʱ��Ϊ"
								+ df.format(new Date()));

						System.out.println("------------���·ָ���------------");
						System.out.println();

					}

					// ------- ends here
					try {
						Thread.sleep(timeInterval);
					} catch (InterruptedException e) {
						e.printStackTrace();
					}
				}
			}
		};
		Thread thread = new Thread(runnable);
		thread.start();

	}
}
