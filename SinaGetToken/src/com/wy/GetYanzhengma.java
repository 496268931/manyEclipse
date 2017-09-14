package com.wy;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.Iterator;
import java.util.Map;

import com.show.api.ShowapiRequest;
import com.show.api.util.Base64;
import com.show.api.util.WebUtils;

public class GetYanzhengma {
	public static String getYanzhengma(String descpath) throws FileNotFoundException, IOException {
//		ShowapiRequest req = new ShowapiRequest("http://ali-checkcode2.showapi.com/checkcode","4e5510e696c748ca8d5033dd595bfbbc");
		ShowapiRequest req = new ShowapiRequest(
				"http://ali-checkcode.showapi.com/checkcode","4e5510e696c748ca8d5033dd595bfbbc");
		
		//byte img[]=WebUtils.readByteFromStream(new FileInputStream(new File("c:/a.jpg")));
		byte img[]=WebUtils.readByteFromStream(new FileInputStream(new File(descpath)));
		
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
			//System.out.println(k + "          " + map.get(k));
		}
		//打印返回体
		String	res = new String(b,"utf-8");
		String yanzhengma = res.substring(res.indexOf("Result")+9,res.indexOf("Result")+13).toUpperCase();
		//System.out.println(yanzhengma);
		
		//下面是使用阿里的fastjson包解析。如果不需要，请使用自己的解析包
//		JSONObject json=JSONObject.parseObject(res);
//		System.out.println(json.getJSONObject("showapi_res_body"));
		return yanzhengma;
	}
}
