package weibo4j.examples.oauth2;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Properties;

import org.apache.commons.httpclient.Header;
import org.apache.commons.httpclient.HttpClient;
import org.apache.commons.httpclient.methods.PostMethod;
import org.apache.commons.httpclient.params.HttpMethodParams;

import com.sun.org.apache.bcel.internal.generic.ATHROW;

import weibo4j.Oauth;
import weibo4j.http.AccessToken;
import weibo4j.model.WeiboException;

public class ZZ {
	public static AccessToken refreshToken() throws WeiboException{ 
        Properties props = new Properties(); 
        try { 
                props.load(Thread.currentThread().getContextClassLoader().getResourceAsStream("sina_account.properties")); 
                String url = props.getProperty("https://api.weibo.com/oauth2/authorize?client_id=1651601463&redirect_uri=http%3A%2F%2Fsns.whalecloud.com%2Fsina%2Fcallback%3Fimei%3Dc1cbc99be4cf339b14869bb91d963cf%26appkey%3D541cf68dfd98c51895027d3c%26key%3D1651601463%26secret%3D84502c2a408c8de5a1c6b7f76d574a84%26pcv%3D2.0&display=mobile&forcelogin=true&fc=umeng&response_type=code&scope=all&with_offical_account=1&pcv=2.0");/*模拟登录的地址，https://api.weibo.com/oauth2/authorize*/ 
                PostMethod postMethod = new PostMethod(url); 
                postMethod.addParameter("client_id", props.getProperty("1651601463"));//your client id 
                postMethod.addParameter("redirect_uri", props.getProperty("http://sns.whalecloud.com/sina/callback?imei=c1cbc99be4cf339b14869bb91d963cf&appkey=541cf68dfd98c51895027d3c&key=1651601463&secret=84502c2a408c8de5a1c6b7f76d574a84&pcv=2.0"));//your url 
                postMethod.addParameter("userId", props.getProperty("rlp390660@sina.cn"));//需要获取微薄的use id 
                postMethod.addParameter("passwd", props.getProperty("4B1ivdq92b30")); 
                postMethod.addParameter("isLoginSina", "0"); 
                postMethod.addParameter("action", "submit"); 
                postMethod.addParameter("response_type", props.getProperty("response_type"));//code 
                HttpMethodParams param = postMethod.getParams(); 
                param.setContentCharset("UTF-8"); 
                List<Header> headers = new ArrayList<Header>(); 
                headers.add(new Header("Referer", "https://api.weibo.com/oauth2/authorize?client_id=1651601463&redirect_uri=http://sns.whalecloud.com/sina/callback?imei=c1cbc99be4cf339b14869bb91d963cf&appkey=541cf68dfd98c51895027d3c&key=1651601463&secret=84502c2a408c8de5a1c6b7f76d574a84&pcv=2.0&from=sina&response_type=code"));//伪造referer 
                headers.add(new Header("Host", "api.weibo.com")); 
                headers.add(new Header("User-Agent", "Mozilla/5.0 (Windows NT 6.1; rv:11.0) Gecko/20100101 Firefox/11.0")); 
                HttpClient client  = new HttpClient(); 
                client.getHostConfiguration().getParams().setParameter("http.default-headers", headers); 
                client.executeMethod(postMethod); 
                int status = postMethod.getStatusCode(); 
                if(status != 302){ 
                	 System.out.println("refresh token failed");
                       // LOG.error("refresh token failed"); 
                        return null; 
                } 
                Header location = postMethod.getResponseHeader("Location"); 
                if(location != null){ 
                        String retUrl = location.getValue(); 
                        int begin = retUrl.indexOf("code="); 
                        if(begin != -1){ 
                                int end = retUrl.indexOf("&", begin); 
                                if(end == -1) 
                                        end = retUrl.length(); 
                                String code = retUrl.substring(begin+5, end); 
                                if(code != null){ 
                                	 Oauth oauth = new Oauth(); 
                                                AccessToken token = oauth.getAccessTokenByCode(code); 
                                               
                                                return token; 
                                } 
                        } 
                } 
        } catch (FileNotFoundException e) { 
        	System.out.println("error" + e);
                //LOG.error("error" + e); 
        } catch (IOException e) { 
        	System.out.println("error" + e);
        } 
        System.out.println("refresh token failed");
        //LOG.error("refresh token failed"); 
        return null; 
} 
	public static void main(String[] args) throws WeiboException {
	 System.out.println(refreshToken());
	}
}
