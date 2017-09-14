package weibo4j.examples.oauth2;

import java.io.BufferedInputStream;
import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

public class WWY {
	public static void main(String[] args) throws Exception {
        URL url = new URL("https://api.weibo.com/oauth2/authorize?client_id=1651601463&redirect_uri=http://sns.whalecloud.com/sina/callback?imei=c1cbc99be4cf339b14869bb91d963cf&appkey=541cf68dfd98c51895027d3c&key=1651601463&secret=84502c2a408c8de5a1c6b7f76d574a84&pcv=2.0&response_type=code");
        HttpURLConnection conn = (HttpURLConnection) url.openConnection();
        InputStream is = conn.getInputStream();
        BufferedInputStream bis = new BufferedInputStream(is);
        InputStreamReader isr = new InputStreamReader(bis, "UTF-8");//根据实际情况改编码
        BufferedReader br = new BufferedReader(isr);
        String line = null;
        while ((line = br.readLine()) != null) {
            if(line.contains("href=\"")){
                System.out.println(line);
            }
        }
        br.close();
        isr.close();
        bis.close();
        is.close();
        conn.disconnect();
    }
	
}
