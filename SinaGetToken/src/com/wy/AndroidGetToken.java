package com.wy;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.net.URL;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import org.apache.commons.io.FileUtils;
import org.junit.Before;
import org.junit.Test;
import org.openqa.selenium.By;
import org.openqa.selenium.Dimension;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.Point;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.ie.InternetExplorerDriver;
import org.openqa.selenium.remote.CapabilityType;
import org.openqa.selenium.remote.DesiredCapabilities;

import io.appium.java_client.android.AndroidDriver;

public class AndroidGetToken {
	private AndroidDriver<WebElement> driver;
	File file = new File("D:\\AutoScreenCapture\\token.txt");
	@Before
	public void setUp() throws Exception {

		// 设置apk的路径
		File classpathRoot = new File(System.getProperty("user.dir"));
		File appDir = new File(classpathRoot, "apps");
		
		File app = new File(appDir, "WeiboSDKDemo.apk");
		//File app = new File(appDir, "微博.apk");

		// 设置自动化相关参数
		DesiredCapabilities capabilities = new DesiredCapabilities();
		
		/*仅供参考，这里的参数都没用上
		capabilities.setCapability("deviceName","lenovo-k10e70-461dcf4");  
        capabilities.setCapability("automationName","Appium");  
        capabilities.setCapability("platformName","Android");  
        capabilities.setCapability("platformVersion","2.49.0");  
          
       //配置测试apk  
        capabilities.setCapability("appPackage", "com.zhihu.android");  
        capabilities.setCapability("appActivity", ".app.ui.activity.MainActivity ");  
        capabilities.setCapability("sessionOverride", true);    //每次启动时覆盖session，否则第二次后运行会报错不能新建session  
        capabilities.setCapability("unicodeKeyboard", true);    //设置键盘  
        capabilities.setCapability("resetKeyboard", false);     //设置默认键盘为appium的键盘  
*/		
		capabilities.setCapability(CapabilityType.BROWSER_NAME, "");
		capabilities.setCapability("platformName", "Android");
		capabilities.setCapability("deviceName", "Android Emulator");

		// 设置安卓系统版本
		capabilities.setCapability("platformVersion", "4.4.2");
		// 设置apk路径
		capabilities.setCapability("app", app.getAbsolutePath());

		// 设置网络选项·
		capabilities.setCapability(
				InternetExplorerDriver.IE_ENSURE_CLEAN_SESSION, true);
		
		
		
		// 设置app的主包名和主类名
		capabilities.setCapability("appPackage", "com.sina.weibo.sdk.demo");
		capabilities.setCapability("appActivity", ".WBDemoMainActivity");
		
		//capabilities.setCapability("appPackage", "com.sina.weibo");
		//capabilities.setCapability("appActivity", "com.sina.weibo.RedBeansInstallActivity");
		
		
//		如果在文本框元素中通过sendKeys("文本内容")，向文本框中发送数据内容，
//		某些数字自动转变成字母时，可以在配置DesiredCapabilities时，加入以下两句		 
//		使用unicodeKeyboard不会自动转码，输入完成后自动转为默认键盘
		capabilities.setCapability("unicodeKeyboard", "true"); 
		capabilities.setCapability("resetKeyboard", "true"); 
		
		// 初始化
		driver = new AndroidDriver<WebElement>(new URL(
				"http://127.0.0.1:4723/wd/hub"), capabilities);
		
		
	}

	@Test
	public void getToken() throws InterruptedException, IOException {
		/*String[] username = { "kva84723526@sina.cn", "zzs3634344@sina.cn",
				"zzv506845@sina.cn", "zzy54601295@sina.cn", "rlp390660@sina.cn" };
		String[] password = { "EO1iRkiNLBPy", "7B83Od9c9UOB", "123qq09pouhb",
				"JlWjkgNy378a", "4B1ivdq92b30" };*/
		
		String[] username = {  "opa93356419@sina.cn",
				"peb821507@sina.cn", "pkl85986@sina.cn", "htn12669@sina.cn",
				"tvl327675@sina.cn", "gng52961992@sina.cn", "mud47820@sina.cn",
				"bal3241561@sina.cn", "yrt901948@sina.cn", "eli34722@sina.cn",
				"dgn5117829@sina.cn", "luw15492396@sina.cn",
				"jan8865758@sina.cn", "qmo78957298@sina.cn",
				"wrt82947@sina.cn", "ubu51687547@sina.cn", "dnz181055@sina.cn",
				"ipt79899584@sina.cn", "tkr409421@sina.cn",
				"zfi82795855@sina.cn", "ocf72792@sina.cn",
				"snz04467351@sina.cn", "ifc32093@sina.cn",
				"kpt00342940@sina.cn", "wap015331@sina.cn",
				"dcu1234947@sina.cn", "ngk2457991@sina.cn", "ufb78552@sina.cn",
				"fsq022052@sina.cn", "equ515332@sina.cn", "xsm99046@sina.cn",
				"wrk41052@sina.cn", "waj41231106@sina.cn", "ulp54246@sina.cn",
				"fcj3567924@sina.cn", "adn32772394@sina.cn",
				"dwo04054953@sina.cn", "cag04133@sina.cn", "pst240060@sina.cn",
				"ynn103976@sina.cn", "ggq83681@sina.cn", "jxi088775@sina.cn",
				"wyw16909004@sina.cn", "hip33373@sina.cn", "aku143734@sina.cn",
				"vwa56786838@sina.cn", "dlm7174800@sina.cn",
				"kcy48582@sina.cn", "vmp98245@sina.cn", "mbs52014@sina.cn",
				"mqo9839005@sina.cn", "fbm46957112@sina.cn",
				"riz93721954@sina.cn", "vft13225@sina.cn", "ynz53187@sina.cn",
				"iqx76468086@sina.cn", "nvx78835@sina.cn", "lzd188698@sina.cn",
				"oej781838@sina.cn", "gmf9026226@sina.cn", "vvc23668@sina.cn",
				"fty97024@sina.cn", "ank7664890@sina.cn", "ksj64442@sina.cn",
				"lqm5075684@sina.cn", "pit6310737@sina.cn",
				"rsa9179068@sina.cn", "inf25354@sina.cn", "qzm785497@sina.cn",
				"ssp94437473@sina.cn", "xxb7138219@sina.cn",
				"pat2398121@sina.cn", "mwq08778943@sina.cn",
				"cpz61804@sina.cn", "ysn688519@sina.cn", "ofd7880145@sina.cn",
				"aiq07683@sina.cn", "yse631140@sina.cn", "vdx5775671@sina.cn",
				"xob139236@sina.cn", "kbk19331@sina.cn", "nys3110315@sina.cn",
				"rut512580@sina.cn", "gyd94850@sina.cn", "npj99756@sina.cn",
				"xow29479@sina.cn", "dyn918338@sina.cn", "dmf062846@sina.cn",
				"wbt544559@sina.cn", "dda6630966@sina.cn",
				"ywb6148370@sina.cn", "umz081416@sina.cn", "ema294822@sina.cn",
				"awh37185025@sina.cn", "pgs48147@sina.cn", "ecs470426@sina.cn",
				"wnx465703@sina.cn", "ouw69947@sina.cn", "umo090751@sina.cn","kxa5259904@sina.cn" };
		String[] password = { "WTFi3s7GETH7", "SU7Of0dB4STc",
				"Wp8rzi544wKq", "8FAwZPVy7LXx", "Xi8Kplq43hM0", "Uh5J5PEZ187X",
				"ltHUWdrF8THR", "MV1Ad40U6lU7", "2GpS0q23BHMQ", "bo7a1JI789QZ",
				"9d7L8pMxpTMD", "6u64tW4nRa8C", "6p8Vw17qRUYB", "oS4168sCdfzs",
				"iloi0214698", "Fj994Wa6m4UH", "7ioy15tW8t5X", "RsHZ89Q919ql",
				"IQf8wu17bI1Z", "o4aW5HZ42c97", "0L87Blb25sP4", "g5e5U8hm31Qa",
				"LR0A5i3uCwAq", "MM036l495IQh", "48ZcRE5Ou72A",
				"vadjnrwa1701u", "ektFh46Xe3t5", "rwaekp636125w",
				"qw123qq0911", "j92w4md1dDLY", "Z8C9iS4Bm13o", "ZA35sZfP0Y9W",
				"qB5jgfq6nLxj", "tb78z6333mGA", "RMs27m17be1K",
				"zegemqq77380b", "9C60LHaoW1z1", "Hs55I99m46v8",
				"779MNNWKgJLi", "jQW2o10MCnAS", "2a4h0qKXYSUq", "2RE4CwkyD6j9",
				"dasTTKpEEr61", "fHb1T1a1k8Sj", "YAGv54Mqb34A", "U5qLB7RENZB0",
				"zu9ujV5Z28H5", "hUHr9SDwHU5I", "sSuur78Q9V8p", "8Ix5J6473mj5",
				"0r45Lq4UKCUn", "456qq0911as", "q7N1ETNusyqo", "012zwui38Jem",
				"JBRZA2uvv3DR", "S82X4F09sN39", "q1qjTk11KT7f", "45321qq0911",
				"unBxT3hrH0T4", "FnR5v4HoQ89x", "D68bYX1hB7SM", "e2G4OdoS20eH",
				"t4I7EL5Aa204", "NAiGI0ex9CFi", "uf1iUO9e9QqZ", "gz3ZfK170275",
				"Cu1YTCz4DO2h", "O2R020Op2oH4", "Iq1mVb8K9mIw", "4xXVch18B578",
				"dX17n29lerL0", "0ni7U6qhfQ33", "u1xUG99F5H7A", "0nj8n7Z4ps4k",
				"m2uUXuNG8vx8", "Gx2eS6h40Mww", "KnriPL9iqUPt", "bvCOEm5oouO1",
				"X9BE3SLemBTV", "TCex5BvkOXGW", "b30ht9P7sZ6m", "t6mSO8zXIhd6",
				"bad6zv1Q2923", "oM9I79YPflxl", "w582Jw7J98rn", "iloi0214698",
				"03lDz6q7420Y", "A23Y8YYw9YTG", "3fg1l0n9x9ix", "921D2nnL364s",
				"0C9X3S4GpcY4", "G62S3ijuj490", "nNaUWJH1Mf7J", "PS8LX4751pn9",
				"HKZuNfhEsrq8", "j934ZURwfWx8", "rTnPCUbk7J0t", "cqj8C8Q82O4E",
				"Y5tJl57xBg9W","pP7n4qEIzk0A" };
		
		System.setProperty("sun.net.client.defaultConnectTimeout", "95000");
		System.setProperty("sun.net.client.defaultReadTimeout", "95000");
		WebElement el = driver.findElement(By.name("微博授权"));
		el.click();
		/*
		 * List<WebElement> textFieldsList =
		 * driver.findElementsByClassName("android.widget.EditText");
		 * textFieldsList.get(0).sendKeys("yuandamao1");
		 * textFieldsList.get(1).sendKeys("15648971455");
		 * textFieldsList.get(2).sendKeys("kjgierhgi@jhig.com");
		 * capabilities.setCapability
		 * ("deviceName","lge-nexus_4-005475cbccd279d4"); driver.swipe(100, 500,
		 * 100, 100, 2); driver.findElementByName("Save").click();
		 */
		for (int i = 0; i < username.length; i++) {
			System.out.println("这是第" + (i + 1) + "个账号：" + username[i]
					+ "获取tokne过程");

			// 点击 SSO 授权（仅客户端）

			driver.findElementByName("SSO 授权（仅客户端）").click();
			// System.out.println("点击SSO授权等待15s");
			Thread.sleep(20000);

			// 点击 切换账号
			driver.findElementById("com.sina.weibo:id/tvChangeAccount").click();
			// System.out.println("点击切换账号等待10s");
			Thread.sleep(1000);
			
			
			//driver.swipe(100, 500, 100, 100, 2);

			// 点击 添加账号 com.sina.weibo:id/tvAccountName
			// driver.findElementByName("添加账号").click();失败
			// driver.findElementByAndroidUIAutomator("new UiSelector().text(\"添加账号\")").click();失败
			List<WebElement> listReport = driver
					.findElementsById("com.sina.weibo:id/tvAccountName");// 获取TextView的所有元素
			// System.out.println(listReport.size()-1);
			WebElement targetReport = listReport.get(listReport.size() - 1);// 获取列表中第几个Textview
			targetReport.click();
			// System.out.println("点击添加账号等待10s");
			Thread.sleep(1000);

			
			
			// System.out.println("输入账号密码等待2s");
			driver.findElementById("com.sina.weibo:id/etLoginUsername")
					.sendKeys(username[i]);
			driver.findElementById("com.sina.weibo:id/etPwd").sendKeys(
					password[i]);
			Thread.sleep(2000);

			// 输入账号密码后点击登录

			driver.findElementById("com.sina.weibo:id/bnLogin").click();
			// System.out.println("点击登录等待20s");
			Thread.sleep(20000);

			try {

				// 没有验证码点击确定

				driver.findElementById("com.sina.weibo:id/bnLogin").click();
				// System.out.println("点击确定等待5s");
				Thread.sleep(20000);

			} catch (org.openqa.selenium.NoSuchElementException ex) {

				
				
				// takeScreenShot(driver);
				// 截图操作
				File screenShotFile = ((TakesScreenshot) driver)
						.getScreenshotAs(OutputType.FILE);
				FileUtils.copyFile(screenShotFile, new File(
						"D:\\AutoScreenCapture\\png\\" + (i + 1) + ".png"));

				Thread.sleep(3000);

				// 将png转化为jpg
				PNGtoJPG.pngTOjpg("D:\\AutoScreenCapture\\png\\" + (i + 1)
						+ ".png", "D:\\AutoScreenCapture\\jpg\\" + (i + 1)
						+ ".jpg");

				Thread.sleep(3000);

				// 将转化后的jpg进行裁剪
				//小手机尺寸
				//ImgCutUtil.cut(120, 575, 300, 65,"D:\\AutoScreenCapture\\jpg\\" + (i + 1) + ".jpg","D:\\AutoScreenCapture\\jpg\\cut" + (i + 1) + ".jpg");
				//打手机尺寸
				ImgCutUtil.cut(90, 410, 225, 90,
						"D:\\AutoScreenCapture\\jpg\\" + (i + 1) + ".jpg",
						"D:\\AutoScreenCapture\\jpg\\cut" + (i + 1) + ".jpg");

				Thread.sleep(3000);

				// 此过程是调阿里接口 获取验证码------------------------
				String yanzhengma = GetYanzhengma
						.getYanzhengma("D:\\AutoScreenCapture\\jpg\\cut"
								+ (i + 1) + ".jpg");
				// ------------------------
				Thread.sleep(5000);
				driver.findElementById("com.sina.weibo:id/et_input").sendKeys(
						yanzhengma);

				Thread.sleep(5000);

				driver.findElementByName("确定").click();
				// 输入完验证码后时间较长
				Thread.sleep(15000);

				driver.findElementById("com.sina.weibo:id/bnLogin").click();

				Thread.sleep(15000);

				/*
				 * 找不到验证码的情况下 System.out.println("准备点击  取消");
				 * driver.findElementByName("取消").click();
				 * System.out.println("完成点击  取消"); Thread.sleep(2000);
				 * driver.findElementById("com.sina.weibo:id/bnBack").click();//
				 * 点击× Thread.sleep(2000);
				 * driver.findElementById("com.sina.weibo:id/titleBack"
				 * ).click();// 点击← Thread.sleep(2000);
				 * driver.findElementById("com.sina.weibo:id/titleBack"
				 * ).click();// 点击取消
				 */
				}
				

			/*
			 * //判断是否弹出验证码 if
			 * (driver.findElementById("com.sina.weibo:id/iv_access_image")
			 * .isDisplayed()) { System.out.println("准备点击  取消");
			 * driver.findElementByName("取消").click();
			 * System.out.println("完成点击  取消"); Thread.sleep(2000);
			 * driver.findElementById("com.sina.weibo:id/bnBack").click();// 点击×
			 * Thread.sleep(2000);
			 * driver.findElementById("com.sina.weibo:id/titleBack").click();//
			 * 点击← Thread.sleep(2000);
			 * driver.findElementById("com.sina.weibo:id/titleBack").click();//
			 * 点击取消
			 * 
			 * } else {
			 * 
			 * // 没有验证码点击确定
			 * driver.findElementById("com.sina.weibo:id/bnLogin").click();
			 * 
			 * String s = driver.findElementById(
			 * "com.sina.weibo.sdk.demo:id/token_text_view").getText(); String a
			 * = "Token："; String b = "有效期";
			 * System.out.println(s.substring(s.indexOf(a) + a.length(),
			 * s.indexOf(b)).trim());
			 * 
			 * }
			 */

			String s = driver.findElementById(
					"com.sina.weibo.sdk.demo:id/token_text_view").getText();
			String a = "Token：";
			String b = "有效期：";
			System.out.println(username[0]);
			System.out.println(password[0]);
			System.out.println(s.substring(s.indexOf(a) + a.length(),
					s.indexOf(b)).trim());
			System.out.println(s.substring(s.indexOf(b)));

			// 输出到txt
			//File file = new File("D:\\AutoScreenCapture\\" + (i + 1) + ".txt");
			
			/*if (file.exists()) {
				file.delete();
			}
                                   这种方法会覆盖
			BufferedWriter bf = new BufferedWriter(new PrintWriter(file));
			bf.append(username[i] + "\r\n");
			bf.append(password[i] + "\r\n");
			bf.append(s.substring(s.indexOf(a) + a.length(), s.indexOf(b))
					.trim() + "\r\n");
			bf.append(s.substring(s.indexOf(b)) + "\r\n");
			bf.append("\r\n");			
			bf.close();*/
			
			BufferedWriter out = null; 
			out = new BufferedWriter(new OutputStreamWriter(new FileOutputStream(file, true)));
			out.write(username[i]+"\r\n");
			out.write(password[i]+"\r\n");
			out.write(s.substring(s.indexOf(a) + a.length(), s.indexOf(b)).trim()+"\r\n");
			out.write(s.substring(s.indexOf(b)) +"\r\n");
			out.write("\r\n");
			out.close();
			

			// driver.findElementByName("切换账号").click();
			driver.findElementByName("用户登出").click();

			System.out.println("第" + (i + 1) + "个账号获取tokne过程结束");
			System.out.println();

		}

		// 1. 返回：
		// driver.pressKeyCode(AndroidKeyCode.BACK);
		// 2. HOME键：
		// driver.pressKeyCode(AndroidKeyCode.HOME);

	}

	/*
	 * public static void takeScreenShot(WebDriver driver) { File screenShotFile
	 * = ((TakesScreenshot) driver) .getScreenshotAs(OutputType.FILE); try { int
	 * i = 0; FileUtils.copyFile(screenShotFile, new File(
	 * "D:\\AutoScreenCapture\\png\\" + (i++) + ".png")); } catch (IOException
	 * e) { e.printStackTrace(); } }
	 // 之前是以时间明明截图
	public static String getCurrentDateTime() {
		SimpleDateFormat df = new SimpleDateFormat("yyyyMMdd_HHmmss");
		// 设置日期格式
		return df.format(new Date());
	}

	*
	 * 退出
	 * 
	 * @After public void tearDown() throws Exception { driver.quit(); }
	 */
	
	
	
	
}