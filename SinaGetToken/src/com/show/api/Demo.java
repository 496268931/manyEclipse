package com.show.api;
import java.io.File;
import java.io.FileInputStream;
import java.net.URLEncoder;
import java.util.Iterator;
import java.util.Map;
import com.alibaba.fastjson.JSONObject;
import com.show.api.util.Base64;
import com.show.api.util.ShowApiUtils;
import com.show.api.util.WebUtils;

public class Demo {
	public static void 今日油价(String adfas[]) throws Exception {
		byte b[]=new ShowapiRequest("http://ali-todayoil.showapi.com/todayoil","appcode") 
				.addTextPara("prov", "北京")
				.getAsByte();
		String str=new String(b,"utf-8");
		System.out.println(str);
	}
	public static void 股票(String adfas[]) throws Exception {
		System.out.println("11111111111");
		ShowapiRequest req=new ShowapiRequest("http://ali-stock.showapi.com/batch-real-stockinfo","appcode") 
				.addTextPara("stocks", "sh601006")
				.addTextPara("needIndex", "0");
		byte b[]=req.getAsByte();
				
		String str=new String(b,"utf-8");
		
		//打印返回头
				Map map = req.getRes_headMap();
				Iterator it = map.keySet().iterator();
				while (it.hasNext()) {
					Object k = it.next();
					System.out.println(k + "          " + map.get(k));
				}
		System.out.println(str);
	}
	
	
	
	public static void 虾米音乐(String adfas[]) throws Exception {
		ShowapiRequest req=new ShowapiRequest("http://ali-music.showapi.com/xiamimusic-top","appcode");
		byte b[]=req
				.addTextPara("typeId", "1")
				.addTextPara("page", "1")
				.getAsByte();
		String str=new String(b,"utf-8");
		Map map = req.getRes_headMap();
		Iterator it = map.keySet().iterator();
		while (it.hasNext()) {
			Object k = it.next();
			System.out.println(k + "          " + map.get(k));
		}
		System.out.println(str);
	}
	public static void 违章(String adfas[]) throws Exception {
		ShowapiRequest req=new ShowapiRequest("http://ali-carlaw.showapi.com/break-rules","appcode") 
		.addTextPara("carCode", "")
		.addTextPara("carEngineCode", "E664222")
		.addTextPara("carNumber", "京P88881")
		.addTextPara("carType", "02")
		.addTextPara("jgjId", "beijing");
		byte b[]=req.postAsByte();
		String str=new String(b,"utf-8");
		System.out.println(str);
		Map map = req.getRes_headMap();
		Iterator it = map.keySet().iterator();
		while (it.hasNext()) {
			Object k = it.next();
			System.out.println(k + "          " + map.get(k));
		}
	}
	public static void 微信查询(String adfas[]) throws Exception {
		ShowapiRequest req=new ShowapiRequest("http://ali-weixin.showapi.com/582-9","appcode") 
		.addTextPara("url", " ");
		
		byte b[]=req.getAsByte();
		String str=new String(b,"utf-8");
		System.out.println(str);
		Map map = req.getRes_headMap();
		Iterator it = map.keySet().iterator();
		while (it.hasNext()) {
			Object k = it.next();
			System.out.println(k + "          " + map.get(k));
		}
	}
	
	
	public static void ip查询天气(String adfas[]) throws Exception {
		ShowapiRequest req=new ShowapiRequest("https://ali-weather.showapi.com/ip-to-weather",
				"appcode") 
		.addTextPara("ip", "223.5.5.5");
		byte b[]=req.getAsByte();
		String str=new String(b,"utf-8");
		System.out.println(str);
		Map map = req.getRes_headMap();
		Iterator it = map.keySet().iterator();
		while (it.hasNext()) {
			Object k = it.next();
			System.out.println(k + "          " + map.get(k));
		}
	}
	
	public static void 快递(String adfas[]) throws Exception {
		ShowapiRequest req=new ShowapiRequest("http://ali-deliver.showapi.com/showapi_expInfo",
				"appcode") 
		.addTextPara("url", "http://mp.weixin.qq.com/s?src=3&timestamp=1478797927&ver=1&signature=d11v3RzpGrZ4ohLY5t0P3KlaCI24disXNuBsIA0ME56*lB21Ox1ycwktwzSgqPWlhnAXt7lIQ3wqAm15tWm2WFUjs63x7lyXqOr6A4AFYFJPBrEldYzQadf52UvWwVbiveuEK1jJ2f*gDpqQJ1Ruqw6nCTo65Lpfs02-TF9kcmo=");
		byte b[]=req.postAsByte();
		String str=new String(b,"utf-8");
		System.out.println("aaaaaaaaaaaa");
		System.out.println(str);
		Map map = req.getRes_headMap();
		Iterator it = map.keySet().iterator();
		System.out.println("bbbbbbbbbbb");
		while (it.hasNext()) {
			Object k = it.next();
			System.out.println(k + "          " + map.get(k));
		}
	}
	
	
	
	public static void main(String adfas[]) throws Exception {
		ShowapiRequest req = new ShowapiRequest(
				"http://ali-checkcode2.showapi.com/checkcode","4e5510e696c748ca8d5033dd595bfbbc");
		//byte img[]=WebUtils.readByteFromStream(new FileInputStream(new File("c:/a.jpg")));
		byte img[]=WebUtils.readByteFromStream(new FileInputStream(new File("D:\\AutoScreenCapture\\jpg\\22.jpg")));
		
		String base64string = new String(Base64.encode(img));  
		      System.out.println(base64string);  
		byte b[] = req.addTextPara("typeId","3040")
				    .addTextPara("convert_to_jpg", "1")
				    .addTextPara("img_base64",base64string)
				    .postAsByte();
		//打印返回头
		Map map = req.getRes_headMap();
		Iterator it = map.keySet().iterator();
		while (it.hasNext()) {
			Object k = it.next();
			System.out.println(k + "          " + map.get(k));
		}
		//打印返回体
		String	res = new String(b,"utf-8");
		System.out.println(res);
		
		//下面是使用阿里的fastjson包解析。如果不需要，请使用自己的解析包
//		JSONObject json=JSONObject.parseObject(res);
//		System.out.println(json.getJSONObject("showapi_res_body"));
	}
}
