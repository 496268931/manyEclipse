package weibo4j.examples.oauth2;

import weibo4j.Oauth;
import weibo4j.examples.oauth2.Log;
import weibo4j.http.AccessToken;
import weibo4j.model.WeiboException;
import weibo4j.util.WeiboConfig;

import org.apache.commons.httpclient.*;
import org.apache.commons.httpclient.methods.PostMethod;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;


public class B {
   
    // 从跳转地址中获取code
    private static String getCodeFromLocation(String location) {
        int begin = location.indexOf("code=");
        return location.substring(begin + 5);
    }
   
   
    public static AccessToken AccessTokenRefresh(String username, String password) throws WeiboException {
        AccessToken access_token = null;
        String location = "";
        String code = "";
        Oauth oauth = new Oauth();
        HttpClient client = new HttpClient();
        C wl = new C();
       
        wl.setUsername(username);
        wl.setPassword(password);
        // 获得授权url
        String url = oauth.authorize("code");
        try {
            // 模拟用户登录
            // 模拟登录的代码在上一篇文里
            wl.login();
            // 获取登录cookie
            String cookie = wl.getCookie();
            //String cookie = "TC-V5-G0=31f4e525ed52a18c5b2224b4d56c70a1";
            HttpMethod method = new PostMethod(url);
            method.addRequestHeader("Cookie", cookie);
            int statusCode = client.executeMethod(method);
            System.out.println(statusCode);
           
            // 已授权过的，刷新增加token过期时间
            if (302 == statusCode) {
                // 获取跳转url
                location = method.getResponseHeader("Location").getValue();
                Log.logInfo(location);
               
                // 从跳转url中抓出code
                code = getCodeFromLocation(location);
               
                // 使用code换取access token
                access_token = oauth.getAccessTokenByCode(code);
            }
            else if(200 == statusCode){        // 未授权，需要模拟授权操作
                Document html = Jsoup.parse(method.getResponseBodyAsString());
                Elements params = html.select("form[name=authZForm] > input[type=hidden]");
               
                PostMethod post = new PostMethod("https://api.weibo.com/oauth2/authorize");
               
                // 设置请求报头
                post.addRequestHeader("Cookie", cookie);
                post.addRequestHeader("Referer", "https://api.weibo.com/oauth2/authorize?client_id=" +
                                            WeiboConfig.getValue("client_ID").trim() +
                                            "&redirect_uri=" +
                                            WeiboConfig.getValue("redirect_URI").trim() +
                                            "&response_type=code");
                // 填充post请求的各个参数
                for(Element param : params) {
                    post.addParameter(param.attr("name"), param.attr("value"));
                }

                statusCode = client.executeMethod(post);
                System.out.println(statusCode);
               
                // 302 跳转 则表示post成功
                if(302 == statusCode) {
                    // 获取跳转url
                    location = post.getResponseHeader("Location").getValue();
                    Log.logInfo(location);
                   
                    code = getCodeFromLocation(location);
                    access_token = oauth.getAccessTokenByCode(code);
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return access_token;
    }
   
    public static void main(String[] args) throws WeiboException {
        // 当然是需要用户名和密码的啦
        String username = "rlp390660@sina.cn";
        String password = "4B1ivdq92b30";
        System.out.println(B.AccessTokenRefresh(username, password));
    }
}