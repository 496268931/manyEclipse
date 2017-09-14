package weibo4j.examples.oauth2;

import java.io.IOException;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;

 
public class A {
	 public static void main(String[] args) throws IOException {
		 Document doc=Jsoup.connect("https://api.weibo.com/oauth2/authorize?client_id=1651601463&redirect_uri=http://sns.whalecloud.com/sina/callback?imei=c1cbc99be4cf339b14869bb91d963cf&appkey=541cf68dfd98c51895027d3c&key=1651601463&secret=84502c2a408c8de5a1c6b7f76d574a84&pcv=2.0&response_type=code").get();
		 System.out.println(doc.baseUri());
		 System.out.println(doc.location());
	}
}
