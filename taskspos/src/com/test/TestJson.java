package com.test;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.concurrent.ConcurrentHashMap;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

import com.bxt.sptask.utils.DefParamUtil;

public class TestJson {
	
	public TestJson(){}
	
	public void jsonTest(){
		String ss = "[{\"runTimeParam\":{\"scene\":{\"dropcount\":13},\"sss\":5}},{\"constants\":null}]";
		String sJson = "[{\"tasktype\":2,\"task\":15},{\"tasktype\":3,\"task\":20}]";
		
		JSONArray resulJosn = JSONArray.fromObject(ss);
		for(Object obj:resulJosn){
			JSONObject o = (JSONObject) obj;
			o.put("endtTimeParam", (new JSONObject()));
			
			JSONObject o2 = (JSONObject) o.get("runTimeParam");
			o2.remove("scene");
			o2.put("scene", (new ArrayList<String>()));
			o.put("runTimeParam", o2);
			System.out.println(o.toString());
		}
		
//		JSONArray resulJosn = JSONArray.fromObject(sJson);
//		for(Object obj:resulJosn){
//			JSONObject o = (JSONObject) obj;
//			if(o.get("tasktype").toString().equals("2")){
//				o.put("tasktype", 1);
//			}
//			System.out.println(o.get("tasktype"));
//			System.out.println(o.get("task"));
//			
//		}
		
	}
	
	public void mapHashTest(){
		String skey1= "20&mateng:0C-84-DC-49-C2-73:4540&200";
		String skey2= "15&mateng:0C-84-DC-49-C2-73:4540&200";
		ConcurrentHashMap<String,List> mp = new ConcurrentHashMap<String,List>();
		List<String> slist = new ArrayList<String>();
		
		slist.add("2#@#2#@#2");
		slist.add("1#@#3#@#2");
		slist.add("3#@#2#@#2");
		
		mp.put("20&mateng:0C-84-DC-49-C2-73:4540&200",slist);
		mp.put("15&mateng:0C-84-DC-49-C2-73:4540&200",slist);
		if(mp.containsKey(skey1)){
			List<String> listvalue = mp.get(skey1);
			for(String s:listvalue){
				System.out.println(s);
				System.out.println(listvalue.size());
				listvalue.remove(s);
				break;
			}
		}
		if(mp.containsKey(skey2)){
			List<String> listvalue = mp.get(skey2);
			for(String s:listvalue){
				System.out.println(s);
			}
		}
		
	}
	
	public void testHashMap(){
		HashMap<Integer,String> hm = new HashMap<Integer,String>();
		hm.put(1, "{\"name\":\"test\",\"address\":\"dadress\"}");
		hm.put(2, "{\"name\":\"test22\",\"address\":\"dadress22\"}");
		
		System.out.println(hm.get(1).toString());
		System.out.println(hm.get(2).toString());
	}
	
	public void testJsonArray(){
		String sjsons = "{\"constants\":{\"constasnt1\":\"conValue\",\"conInt\":5.5},\"myjson\":\"testinfo\"}";
		JSONObject testjson = new JSONObject();
		JSONObject sjsonsObj = testjson.fromObject(sjsons);
		String sDefJsonStyle = DefParamUtil.defparamIntAndString((JSONObject)sjsonsObj.get("constants"));
		
		System.out.println("json格式数据=" + sDefJsonStyle);
		
//		JSONObject jobject =  JSONObject.fromObject(sjsons);
//		JSONObject contantObj = (JSONObject)jobject.get("constants");
//		Iterator it = contantObj.keys();
//		String keyname = "";
//		while (it.hasNext()) {
//			keyname = it.next().toString();
//			System.out.println(contantObj.get(keyname).getClass().toString());
//			if(contantObj.get(keyname).getClass().toString().equals("class java.lang.Integer") || contantObj.get(keyname).getClass().toString().equals("class java.lang.Double")){
//				contantObj.getInt(keyname);
//			}else{
//				System.out.println(contantObj.get(keyname));
//			}
//			
//		 }
		 
		
		
	}

//	public static String getType(Object o){ //获取变量类型方法
//		return o.getClass().toString(); //使用int类型的getClass()方法
//	} 
	
	public void testTaskUrls() throws Exception{
		String strJson = "{\"pathStructMap\":{\"path238\":{\"col_name\":\"run_url\",\"col_type\":\"13\",\"col_name_ex\":null,\"paramSource\":2}},\"nameinfo\":\"urlTest\",\"filters\":{},\"taskUrls\":{\"type\":\"consts\",\"urlValues\":[\"|path238|\"]}}";
		
		JSONObject testjson = new JSONObject();
		JSONObject sjsonsObj = testjson.fromObject(strJson);
		JSONObject taskUrlsJson = sjsonsObj.getJSONObject("taskUrls");
		
		DefParamUtil.getUrlFromTaskParam(sjsonsObj);
		
		
//		System.out.println("判断是不是数组urlValues：" + (taskUrlsJson.get("urlValues") instanceof JSONArray));
//		System.out.println("判断是不是字符串：" + taskUrlsJson.get("urlValues").getClass().toString().equals(""));
//		System.out.println("判断urlValues：" + (sjsonsObj.get("nameinfo") instanceof String));
//		System.out.println("判断filters：" + (sjsonsObj.get("filters") instanceof JSONObject));
	
		
	}
	
	public void testTaskAndType(){
		//String strjson = "[{\"tasktype\":\"2\",\"task\":\"20\"}]";
		String strjson = "[{\"tasktype\":\"2\",\"task\":\"20\"}]";
		
		JSONArray json_obj = JSONArray.fromObject(strjson);
		for(int i = 0;i<json_obj.size();i++){
			JSONObject obj = (JSONObject)json_obj.get(i);
			System.out.println(obj.getString("tasktype"));
			System.out.println(obj.getString("task"));
		}
	}
	
	
	public static void main(String[] args) throws Exception {
		TestJson tjson = new TestJson();
		//tjson.testTaskAndType();
		tjson.testTaskUrls();
		//tjson.testJsonArray();
		//tjson.testHashMap();
		//tjson.jsonTest();
		//tjson.mapHashTest();
		
	}

}
