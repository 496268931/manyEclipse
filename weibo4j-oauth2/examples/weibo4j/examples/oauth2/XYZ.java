package weibo4j.examples.oauth2;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.message.BasicNameValuePair;

import weibo4j.Oauth;
import weibo4j.http.AccessToken;
import weibo4j.model.WeiboException;

public class XYZ {
	public AccessToken getAccessToken() throws WeiboException, IOException {
		Oauth oauth = new Oauth();
		String url = "https://api.weibo.com/oauth2/authorize";
		DefaultHttpClient httpClient = new DefaultHttpClient();
		HttpPost post = new HttpPost(url);

		AccessToken accessToken = null;

		// 初始化post数据
		List<NameValuePair> params = new ArrayList<NameValuePair>();
		params.add(new BasicNameValuePair("userId", "rlp390660@sina.cn"));
		params.add(new BasicNameValuePair("passwd", "4B1ivdq92b30"));
		params.add(new BasicNameValuePair("action", "login"));
		params.add(new BasicNameValuePair("display", "default"));
		params.add(new BasicNameValuePair("withOfficalFlag", "0"));
		params.add(new BasicNameValuePair("withOfficalAccount", ""));
		params.add(new BasicNameValuePair("scope", ""));
		params.add(new BasicNameValuePair("ticket", ""));
		params.add(new BasicNameValuePair("isLoginSina", ""));
		params.add(new BasicNameValuePair("response_type", "code"));
		params.add(new BasicNameValuePair("regCallback","https://api.weibo.com/2/oauth2/authorize?client_id=541cf68dfd98c51895027d3c&response_type=code&display=default&redirect_uri=http://sns.whalecloud.com/sina/callback?imei=c1cbc99be4cf339b14869bb91d963cf&appkey=541cf68dfd98c51895027d3c&key=1651601463&secret=84502c2a408c8de5a1c6b7f76d574a84&pcv=2.0&from=&with_cookie="));
		//params.add(new BasicNameValuePair("regCallback","https%3A%2F%2Fapi.weibo.com%2F2%2Foauth2%2Fauthorize%3Fclient_id%3D3616023689%26response_type%3Dcode%26display%3Ddefault%26redirect_uri%3Dhttp%3A%2F%2F127.0.0.1%26from%3D%26with_cookie%3D"));
		params.add(new BasicNameValuePair("redirect_uri", "http://sns.whalecloud.com/sina/callback?imei=c1cbc99be4cf339b14869bb91d963cf&appkey=541cf68dfd98c51895027d3c&key=1651601463&secret=84502c2a408c8de5a1c6b7f76d574a84&pcv=2.0"));
		//params.add(new BasicNameValuePair("redirect_uri", "http://127.0.0.1"));
		params.add(new BasicNameValuePair("client_id", "1651601463"));
		params.add(new BasicNameValuePair("appkey62", "5Pph2h"));
		params.add(new BasicNameValuePair("state", ""));
		params.add(new BasicNameValuePair("verifyToken", "null"));
		params.add(new BasicNameValuePair("from", ""));

		UrlEncodedFormEntity entity;
		entity = new UrlEncodedFormEntity(params, "utf-8");
		post.setEntity(entity);
		post.addHeader(
				"Referer",
				"https://api.weibo.com/oauth2/authorize?client_id=1651601463&redirect_uri=http://sns.whalecloud.com/sina/callback?imei=c1cbc99be4cf339b14869bb91d963cf&appkey=541cf68dfd98c51895027d3c&key=1651601463&secret=84502c2a408c8de5a1c6b7f76d574a84&pcv=2.0&response_type=code&state=&scope=");

		HttpResponse response = httpClient.execute(post);

		System.out.println("123");
		System.out.println(response);
		System.out.println("123");
		String[] location = response.getLastHeader("Location").toString()
				.split("=");
		String code = null;
		if (location.length > 1) {
			code = location[1];
			Log.logInfo("Code:" + code);
		}
		if (code != null) {
			accessToken = oauth.getAccessTokenByCode(code);
			Log.logInfo("Access_Token:" + accessToken.getAccessToken());
		}
		return accessToken;
	}
	public static void main(String[] args) throws WeiboException, IOException {
		XYZ x = new XYZ();
		System.out.println(x.getAccessToken());
	}
}
