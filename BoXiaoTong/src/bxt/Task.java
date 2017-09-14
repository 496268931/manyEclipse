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
	 * 获取单个文件的MD5值！
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
	 * 获取文件夹中文件的MD5值
	 * 
	 * @param file
	 * @param listChild
	 *            ;true递归子目录中的文件
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
		// 保证程序互斥运行
		try {
			listenerSocket = new ServerSocket(2004);
			// At this point, no other socket may listen on port 2004.
		} catch (java.net.BindException e) {
			System.out.println("检测到有其他更新程序在运行。。。。");
			System.err.println("A previous instance is already running....");
			System.exit(1);
		} catch (final IOException e) { // an unexpected exception occurred
			System.exit(1);
		}
		// Do some work here.....
		// 保证程序互斥运行

		final SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");// 设置日期格式

		// run in a second
		final long timeInterval = 600000;
		Runnable runnable = new Runnable() {
			public void run() {
				while (true) {
					// ------- code for task to run

					Task t = new Task();
					// 本地读取写入的方法
					VersionCover vc = new VersionCover();
					// 网络读取txt的方法
					ReadNetTxt rnt = new ReadNetTxt();
					// int netVersionLineNumber;
					// new Date()为获取当前系统时间
					System.out.println("本次更新开始时间为：" + df.format(new Date()));

					// 获取当前路径
					File directory = new File("");// 参数为空
					String courseFile = null;
					try {
						courseFile = directory.getCanonicalPath();
					} catch (IOException e2) {
						// TODO Auto-generated catch block
						e2.printStackTrace();
					}
					System.out.println("爬虫更新路径为：" + courseFile);

					File file1 = new File(courseFile + "\\localVersion.txt");

					// http://120.27.195.31:8080/UpdateSpider/
					// 192.168.0.42
					// 下载网络版本txt
					try {

						DownLoad.downLoadFromUrl(
								"http://120.27.195.31:8080/UpdateSpider/VersionAndSort/netVersion.txt",
								"netVersion.txt", courseFile);
						System.out.println("tomcat上的网络版本为：");
						System.out
								.println(rnt
										.readNetTxt("http://120.27.195.31:8080/UpdateSpider/VersionAndSort/netVersion.txt"));
						// 另一种方法，但是没用到
						// ReadNetVersion readnetversion = new ReadNetVersion();
						// readnetversion.readNetVersion();
					} catch (Exception e) {
						// TODO: handle exception
						System.out.println("下载失败，未检测到网络版本，请首先检查tomcat是否正常运行");
					}
					File file2 = new File(courseFile + "\\netVersion.txt");

					// localVersion netVersion进行比较
					System.out.println("本地版本和网络版本比较结果："
							+ getFileMD5(file1).equals(getFileMD5(file2)));
					t.taskWaiting();

					boolean b = getFileMD5(file1).equals(getFileMD5(file2));

					if (!b) {

						try {

							// 显示所有进程
							// ShowTaskList taskList=new ShowTaskList();
							// taskList.showTaskList();

							// 获取所有爬虫进程并干掉
							// new GetSpiderCount().getPid();
							GetAndKillSpider gak = new GetAndKillSpider();

							System.out.println("已开启的爬虫进程为：");
							Thread.sleep(2000);
							gak.getAndKill("cmd /c tasklist -fi \"imagename eq 3ispider.exe\"");
							System.out.println();
							System.out.println("***开始杀爬虫***");
							System.out.println();
							Thread.sleep(2000);
							gak.getAndKill("cmd /c taskkill /im 3iSpider.exe /f");

						} catch (InterruptedException e2) {
							// TODO Auto-generated catch block
							e2.printStackTrace();
						}

						// 窗口手动打开爬虫
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
									.println("下载失败，未检测到新版爬虫，请首先检查tomcat是否正常运行");
						}

						t.taskWaiting();
						
						System.out.println();
						// 更改GBConfig文件名字为GBConfig-backups
						if (new File(courseFile + "\\Config\\GBConfig.txt")
								.exists()) {
							new File(courseFile + "\\Config\\GBConfig.txt")
									.renameTo(new File(courseFile
											+ "\\Config\\GBConfig-backups.txt"));
							System.out
									.println("已将本地GBConfig文件重命名为GBConfig-backups");
						}
						t.taskWaiting();
						
						System.out.println();
						// 解压缩爬虫
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
						System.out.println("准备删除解压后的zip文件。。。检查路径。。。");
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
											.println("压缩文件路径为"
													+ courseFile
													+ "\\"
													+ rnt.readNetTxt("http://120.27.195.31:8080/UpdateSpider/VersionAndSort/sortSpiderVersion.txt"));
								} catch (IOException e) {
									// TODO Auto-generated catch block
									e.printStackTrace();
								}

								System.out.println("下载后的压缩文件已删除");
							}
						} else {
							System.out.println("压缩文件不存在");
						}

						t.taskWaiting();

						System.out.println();
						/*
						 * 通过配置文件有多少行判断是否覆盖GBC ReadNetVersionLineNum rrrLineNum
						 * = new ReadNetVersionLineNum(); try {
						 * System.out.println
						 * ("网络版本行数为："+rrrLineNum.countLines(courseFile +
						 * "\\netVersion.txt")); } catch (IOException e2) { //
						 * TODO Auto-generated catch block e2.printStackTrace();
						 * }
						 */

						t.taskWaiting();

						System.out.println();
						System.out.println("*****开始操作GBConfig.txt文件*****");
						// 网络上的netVersion中，必须有"GBConfigCover:",后面的参数，只有当参数为1时，执行不覆盖操作
						ReadGBConfig readGBConfig = new ReadGBConfig();
						try {

							System.out.print("GBConfig所在行为：");
							String gbc = readGBConfig.read(
									courseFile + "\\netVersion.txt").substring(
									14);
							int intgbc = Integer.parseInt(gbc.trim());

							System.out.println("参数为" + intgbc);
							
							System.out.println();
							t.taskWaiting();

							
							if (intgbc == 0) {
								System.out
										.println("GBConfigCover:0，将本地的GBConfig-backups内容覆盖下载后的GBConfig中");
								// 删除下载后的GBConfig
								if (new File(courseFile
										+ "\\Config\\GBConfig.txt").exists()) {
									new File(courseFile
											+ "\\Config\\GBConfig.txt")
											.delete();
									System.out.println("已将下载后的本地GBConfig文件删除");
								}
								// 更改GBConfig-backups文件名字为GBConfig
								if (new File(courseFile
										+ "\\Config\\GBConfig-backups.txt")
										.exists()) {
									new File(courseFile
											+ "\\Config\\GBConfig-backups.txt")
											.renameTo(new File(courseFile
													+ "\\Config\\GBConfig.txt"));
									System.out
											.println("已将本地GBConfig-backups文件重命名为GBConfig");
								}

							} else if (intgbc == 1) {
								System.out
										.println("GBConfigCover:1，覆盖，获得下载后的GBConfig");
								if (new File(courseFile
										+ "\\Config\\GBConfig-backups.txt")
										.exists()) {
									new File(courseFile
											+ "\\Config\\GBConfig-backups.txt")
											.delete();
									System.out
											.println("已将下载前改名后的本地GBConfig-backups文件删除");
								}
							} else if (intgbc == 2) {

								System.out
										.println("GBConfigCover:2，将本地TASKURL追加到下载后的GBConfig中");
								GetTaskUrlLineNumber getTaskUrlLineNumber = new GetTaskUrlLineNumber();
								// 包含TASKURL的本地文件中的行号
								// System.out.println(getTaskUrlLineNumber.getTaksUrlLineNumber(courseFile
								// + "\\Config\\GBConfig-backups.txt"));
								// 包含TASKURL的下载后的文件中的行号
								// System.out.println(getTaskUrlLineNumber.getTaksUrlLineNumber(courseFile
								// + "\\Config\\GBConfig.txt"));

								GetAppointedLine getAppointedLine = new GetAppointedLine();

								/*
								 * 本地GBConfig 的TASKURL地址 System.out
								 * .println(getAppointedLine
								 * .readAppointedLineNumber( new File(
								 * courseFile +
								 * "\\Config\\GBConfig-backups.txt"),
								 * getTaskUrlLineNumber
								 * .getTaksUrlLineNumber(courseFile +
								 * "\\Config\\GBConfig-backups.txt")));
								 */

								// int modifyLine=10;//要修改的行
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
										// 将本地TASKURL 替换下载后的TASKURL
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
																				+ "\\Config\\GBConfig-backups.txt")))); // 处理就是替换w成Z

									} else {
										out.println(line);
									}
									count++;
								}
								in_.close();
								out.close();
								System.out
										.println("已将下载后的网络TASKURL替换成本地TASKURL");

								
								System.out.println();
								t.taskWaiting();
								if (new File(courseFile
										+ "\\Config\\GBConfig-backups.txt")
										.exists()) {
									new File(courseFile
											+ "\\Config\\GBConfig-backups.txt")
											.delete();
									System.out
											.println("已将下载前改名后的本地GBConfig-backups文件删除");
								}
								if (new File(courseFile
										+ "\\Config\\GBConfig.txt").exists()) {
									new File(courseFile
											+ "\\Config\\GBConfig.txt")
											.delete();
									System.out.println("已将下载后的网络GBConfig文件删除");
								}

								if (new File(courseFile
										+ "\\Config\\GBConfig-union.txt")
										.exists()) {
									new File(courseFile
											+ "\\Config\\GBConfig-union.txt")
											.renameTo(new File(courseFile
													+ "\\Config\\GBConfig.txt"));
									System.out
											.println("已将追加TASKURL后的GBConfig-union文件重命名为GBConfig");
								}

								/**
								 * 读取文件指定行。
								 * 
								 * 指定读取的行号 int lineNumber = 11; 读取文件 File
								 * sourceFile = new File("D:/test2.txt"); 读取指定的行
								 * readAppointedLineNumber
								 * (sourceFile,lineNumber); 获取文件的内容的总行数
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

						System.out.println("*****GBConfig.txt文件操作完成*****");
						System.out.println();

						t.taskWaiting();

						// 启动爬虫
						/*
						 * try { Runtime.getRuntime().exec( "cmd /c " +
						 * courseFile + "\\3ispider.exe auto"); } catch
						 * (IOException e1) { // TODO Auto-generated catch block
						 * e1.printStackTrace(); }
						 * System.out.println("本次爬虫启动时间：" + df.format(new
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
						System.out.println("本次爬虫启动时间：" + df.format(new Date()));
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
						System.out.println("本次爬虫启动时间：" + df.format(new Date()));
						try {
							Thread.sleep(10000);
						} catch (InterruptedException e) {
							e.printStackTrace();
						}

						System.out.println("本次更新已结束，时间为："
								+ df.format(new Date()));

						System.out.println();
						System.out
								.println("*****开始将下载后网络版本netVersion覆盖本地版本localVersion*****");

						String f1 = courseFile + "\\localVersion.txt";
						String f2 = courseFile + "\\netVersion.txt";

						try {
							String result = vc.read(f2);
							vc.write(result, f1);
						} catch (Exception e) {
							e.printStackTrace();
						}
						System.out.println("over");

						System.out.println("*****版本号覆盖完成*****");
						System.out.println();
						try {
							System.out.println("本地版本localVersion为：");
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
						 * System.out.println("版本号已从" + f2 + "覆盖到" + f1); }
						 * catch (IOException e) { e.printStackTrace(); }
						 * //通过ReadVersion类得到localVersion版本号 //new
						 * ReadVersion();
						 * //ReadVersion.readFileByLines(courseFile
						 * +"\\localVersion.txt");
						 */

						System.out.println("------------更新分割线------------");

					} else {

						try {
							System.out.println("本地版本localVersion为：");
							System.out.println(vc.read(courseFile
									+ "\\localVersion.txt"));
						} catch (FileNotFoundException e) {
							// TODO Auto-generated catch block
							e.printStackTrace();
						}
						System.out.println("版本号相同，不需要更新，本次时间为"
								+ df.format(new Date()));

						System.out.println("------------更新分割线------------");
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
