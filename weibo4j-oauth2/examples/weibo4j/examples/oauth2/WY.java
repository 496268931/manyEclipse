package weibo4j.examples.oauth2;

import weibo4j.Oauth;
import weibo4j.http.AccessToken;
import weibo4j.model.Status;
import weibo4j.model.WeiboException;
import weibo4j.util.BareBonesBrowserLaunch;

public class WY {
	public static void main(String[] args) throws WeiboException {
		Oauth oauth = new Oauth();
		BareBonesBrowserLaunch.openURL(oauth.authorize("code")); // 这是新浪微博提供的打开浏览器的方法
		
		
		 Oauth oauth1 = new Oauth();
//	     //返回的code
//	    String code = request.getParameter("code");
//	    AccessToken accessToken = oauth1.getAccessTokenByCode("a3d638ecf48d11cb3b666c2a9d93cbb7");//获取那个code的值
//		System.out.println(accessToken);
//		
//	    Weibo weibo = new Weibo();
//	    weibo.setToken(accessToken.getAccessToken());
//	    Timeline tm = new Timeline();
//	    Status status = tm.UpdateStatus(statuses);
	}
}
