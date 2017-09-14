package weibo4j.examples.oauth2;



	import java.io.IOException;
	import java.io.InputStream;
	import java.util.ArrayList;
	import java.util.List;
	import java.util.Map;

	import org.apache.http.HttpEntity;
	import org.apache.http.HttpResponse;
	import org.apache.http.NameValuePair;
	import org.apache.http.client.ClientProtocolException;
	import org.apache.http.client.entity.UrlEncodedFormEntity;
	import org.apache.http.client.methods.HttpGet;
	import org.apache.http.client.methods.HttpPost;
	import org.apache.http.impl.client.DefaultHttpClient;
	import org.apache.http.message.BasicNameValuePair;

	/**
	 * Http工具类
	 * 
	 * @author Liuxey
	 *
	 */
	public class GetURL {

		public static final String REQUEST_TYPE_GET = "get";
		public static final String REQUEST_TYPE_POST = "post";
		
		/**
		 * 模拟发出Http请求
		 * 
		 * @param uri 请求资源,如：http://www.baidu.com/,注意严谨的格式
		 * @param params 请求参数
		 * @param type 请求方式,目前只支持get/post
		 * @param encoding 网页编码
		 * 
		 * @return HttpResponseBody
		 * 
		 * @throws IOException 
		 * @throws ClientProtocolException 
		 */
		
		public static void main(String[] args) throws ClientProtocolException, IOException {
			request("https://api.weibo.com/oauth2/authorize?client_id=1651601463&redirect_uri=http://sns.whalecloud.com/sina/callback?imei=c1cbc99be4cf339b14869bb91d963cf&appkey=541cf68dfd98c51895027d3c&key=1651601463&secret=84502c2a408c8de5a1c6b7f76d574a84&pcv=2.0&response_type=code", null, "post","UTF-8");
		}
		public static String request(String uri, Map<String,String> params, String type, String encoding) throws ClientProtocolException, IOException{
			String result = "";
			DefaultHttpClient  httpClient = new DefaultHttpClient ();
			HttpResponse httpResponse = null;
			
			// GET方式请求
			if(GetURL.REQUEST_TYPE_GET.equals(type)){
				// 加入请求参数
				if(params != null ){
					if(uri.indexOf("?") != -1){
						uri += "&";
					}else{
						uri += "?";
					}
					for(String key : params.keySet()){
						uri += key +"="+params.get(key) +"&";
					}
				}
				HttpGet httpGet = new HttpGet(uri);
				httpResponse = httpClient.execute(httpGet);
				
			// POST方式请求
			}else if(GetURL.REQUEST_TYPE_POST.equals(type)){
				HttpPost httpPost = new HttpPost(uri);
				// 加入请求参数
				if(params != null ){
					List<NameValuePair> paramList = new ArrayList<NameValuePair>();
					for(String key : params.keySet()){
						if(key != null){
							paramList.add(new BasicNameValuePair(key, params.get(key)));
						}
					}
					UrlEncodedFormEntity entity = new UrlEncodedFormEntity(paramList,"UTF-8");
					httpPost.setEntity(entity);
				}
				httpResponse = httpClient.execute(httpPost);
			}
			
			// 获取返回内容
			HttpEntity entity = httpResponse.getEntity();
			if(entity!=null){
				InputStream is = entity.getContent();
				int l ;
				byte[] buff = new byte[9192];
				while( (l = is.read(buff)) != -1){
					result += new String(buff, 0, l, encoding);
				}
			}
			return result;
		}
	}


