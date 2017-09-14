package weibo4j.examples.oauth2;

import java.net.HttpURLConnection;
import java.net.URL;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;

 

public class WYYYY {    
	public static void main(String[] args)  {


//		URL url = new URL("https://api.weibo.com/oauth2/authorize?client_id=1651601463&redirect_uri=http://sns.whalecloud.com/sina/callback?imei=c1cbc99be4cf339b14869bb91d963cf&appkey=541cf68dfd98c51895027d3c&key=1651601463&secret=84502c2a408c8de5a1c6b7f76d574a84&pcv=2.0&response_type=code");
//		HttpURLConnection conn = (HttpURLConnection)url.openConnection();
//		conn.getResponseCode();
//		String realUrl=conn.getURL().toString();
//		System.out.println(realUrl);
//		conn.disconnect();
		
//		Document doc=Jsoup.connect("https://api.weibo.com/oauth2/authorize?client_id=1651601463&redirect_uri=http://sns.whalecloud.com/sina/callback?imei=c1cbc99be4cf339b14869bb91d963cf&appkey=541cf68dfd98c51895027d3c&key=1651601463&secret=84502c2a408c8de5a1c6b7f76d574a84&pcv=2.0&response_type=code").get();//如http://baike.baidu.com/view/2216.htm（唐太宗李世民）
//		//下面两种方法都能获取跳转之后的URL ——都可以获取http://baike.baidu.com/subview/2216/8684069.htm（唐太宗李世民）
//		System.out.println("123");
//		System.out.println(doc.baseUri());
//		System.out.println("123");
//		System.out.println(doc.location());
//		System.out.println("123");
		 try {  
	            String url = "http://www.javaniu.com/302.htm";  
	            System.out.println("访问地址:" + url);  
	            URL serverUrl = new URL(url);  
	            HttpURLConnection conn = (HttpURLConnection) serverUrl  
	                    .openConnection();  
	            conn.setRequestMethod("GET");  
	            // 必须设置false，否则会自动redirect到Location的地址  
	            conn.setInstanceFollowRedirects(false);  
	  
	            conn.addRequestProperty("Accept-Charset", "UTF-8;");  
	            conn.addRequestProperty("User-Agent",  
	                    "Mozilla/5.0 (Windows; U; Windows NT 5.1; zh-CN; rv:1.9.2.8) Firefox/3.6.8");  
	            conn.addRequestProperty("Referer", "http://zuidaima.com/");  
	            conn.connect();  
	            String location = conn.getHeaderField("Location");  
	  
	            serverUrl = new URL(location);  
	            conn = (HttpURLConnection) serverUrl.openConnection();  
	            conn.setRequestMethod("GET");  
	  
	            conn.addRequestProperty("Accept-Charset", "UTF-8;");  
	            conn.addRequestProperty("User-Agent",  
	                    "Mozilla/5.0 (Windows; U; Windows NT 5.1; zh-CN; rv:1.9.2.8) Firefox/3.6.8");  
	            conn.addRequestProperty("Referer", "http://zuidaima.com/");  
	            conn.connect();  
	            System.out.println("跳转地址:" + location);  
	  
	        } catch (Exception e) {  
	            e.printStackTrace();  
	        }  
	
	
	}
    
	 
       
}  